package com.example.retailapp.feature.products.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.retailapp.feature.common.data.model.User
import com.example.retailapp.feature.common.domain.UsersInteractor
import com.example.retailapp.core.base.BaseViewModel
import com.kevinnzou.compose.core.paginglist.easyPager
import com.kevinnzou.compose.core.paginglist.pagerconfig.PagingListWrapper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.NoSuchElementException
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val usersInteractor: UsersInteractor
) : BaseViewModel() {

    val pager = easyPager {
        loadDataPage()
    }

    val uiState = mutableStateOf(UsersUiState.LOADING)
    private val usersData = MutableStateFlow<List<User>>(emptyList())

    private val defaultPageSinceID = 0

    fun refreshUsers(): Deferred<List<User>> {
        return loadUsers(defaultPageSinceID)
    }

    private suspend fun loadDataPage(): PagingListWrapper<User> {
        val pageSinceID = try {
            usersData.value.last().id
        } catch (e: NoSuchElementException) {
            defaultPageSinceID
        }

        val data = loadUsers(pageSinceID).await()
        return PagingListWrapper(data, true)
    }

    private fun loadUsers(sinceID: Int): Deferred<List<User>> {
        val deferred = viewModelScope.async {
//            delay added for a better testing PullToRefresh and ProgressIndicator
            delay(1000)
            usersInteractor.loadUsersPage(sinceID)
        }

        viewModelScope.launch {
            try {
                val items = deferred.await()
                usersData.value = items
                uiState.value = UsersUiState.DATA
            } catch (error: Throwable) {
                if (error is CancellationException) throw error else uiState.value =
                    UsersUiState.ERROR
            }
        }

        return deferred
    }

}