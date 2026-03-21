package com.example.retailapp.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.retailapp.ui.base.BaseFragment
import com.example.ghusers.ui.theme.GHUsersTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.example.retailapp.data.model.User
import com.example.retailapp.navigation.Screens
import com.example.retailapp.ui.composables.ErrorFullSize
import com.example.retailapp.ui.composables.FullScreenLoading
import com.kevinnzou.compose.core.paginglist.widget.PagingListContainer
import kotlinx.coroutines.Deferred

class UsersFragment : BaseFragment() {

    private lateinit var viewModel: UsersViewModel

    companion object {
        fun newInstance(): UsersFragment {
            return UsersFragment()
        }
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[UsersViewModel::class.java]

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                UsersScreen(viewModel = viewModel, ::navigateToUserDetails)
            }
        }

    }

    private fun navigateToUserDetails(user: User) {
        navigateTo(Screens.productDetails(user.login))
    }

}

enum class UsersUiState {
    LOADING, DATA, ERROR
}

@Composable
fun UsersScreen(viewModel: UsersViewModel, onItemClick: (User) -> Unit) {
    val state: UsersUiState by viewModel.uiState
    val pagerData = viewModel.pager.collectAsLazyPagingItems()

    when (state) {
        UsersUiState.LOADING -> FullScreenLoading()
        UsersUiState.DATA -> UsersColumn(
            pagerData,
            modifier = Modifier.fillMaxWidth(),
            refreshData = viewModel::refreshUsers,
            onItemClick = onItemClick
        )

        UsersUiState.ERROR -> ErrorFullSize()
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UsersColumn(
    pagerData: LazyPagingItems<User>,
    modifier: Modifier = Modifier,
    refreshData: () -> Deferred<List<User>>,
    onItemClick: (User) -> Unit
) {
    val listState = rememberLazyListState()
    val refreshScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    fun refreshInternal() = refreshScope.launch {
        isRefreshing = true
        val deferred = refreshData()
        deferred.await()
        isRefreshing = false
    }

    val pullRefreshState = rememberPullRefreshState(isRefreshing, ::refreshInternal)

    PagingListContainer(pagingData = pagerData) {
        Box(modifier.pullRefresh(pullRefreshState)) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                state = listState
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                itemsIndexed(pagerData) { index, value ->
                    UserItem(data = value!!, onItemClick = onItemClick)
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

    }

}

@Composable
fun UserItem(data: User, modifier: Modifier = Modifier, onItemClick: (User) -> Unit) {
    Row(modifier =
    modifier
        .fillMaxWidth()
        .clickable { onItemClick(data) }
    ) {
        AsyncImage(
            model = data.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            Text(
                text = data.login ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.paddingFromBaseline(bottom = 5.dp)
            )

            Text(
                text = data.id.toString(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(widthDp = 360, showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun UserItemPreview() {
    val dummyUser = User(
        id = 1,
        login = "title",
        avatarUrl = "https://www.drcommodore.it/wp-content/uploads/2022/02/233b624e43a04fe9bfd43ef00ebcb2c9.jpg"
    )
    GHUsersTheme { UserItem(data = dummyUser, onItemClick = {}) }
}

