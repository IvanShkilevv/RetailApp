package com.example.retailapp.ui.user_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.retailapp.data.model.User
import com.example.retailapp.ui.base.BaseFragment
import com.example.retailapp.ui.composables.ErrorFullSize
import com.example.retailapp.ui.composables.FullScreenLoading
import com.example.ghusers.ui.theme.GHUsersTheme

// No swipeRefresh, due to the task description
class UserDetailsFragment : BaseFragment() {

    private lateinit var viewModel: UserDetailsViewModel


    companion object {
        const val USER_LOGIN = "USER_LOGIN"

        fun newInstance(login: String?): UserDetailsFragment {
            val bundle = Bundle()
            bundle.putString(USER_LOGIN, login)

            return UserDetailsFragment().apply { arguments = bundle }
        }
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[UserDetailsViewModel::class.java]

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                UserDetailsScreen(viewModel = viewModel)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userLogin = arguments?.getString(USER_LOGIN) ?: "NAME_UNDEFINED"
        viewModel.loadUserDetails()
    }

}

enum class UserDetailsUiState {
    LOADING, DATA, ERROR
}

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel) {
    val state: UserDetailsUiState by viewModel.uiState
    val data: State<User?> = viewModel.userDetailsData.observeAsState()

    when (state) {
        UserDetailsUiState.LOADING -> FullScreenLoading()
        UserDetailsUiState.DATA -> UserInfo(
            data,
            modifier = Modifier.fillMaxWidth()
        )

        UserDetailsUiState.ERROR -> ErrorFullSize()
    }

}

@Composable
fun UserInfo(data: State<User?>, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val value = data.value

    Column(modifier = modifier) {
        AsyncImage(
            modifier = Modifier.size(screenWidth),
            model = value?.avatarUrl,
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(20.dp))

        value?.name?.let { UserInfoLine(text = it) }
        value?.email?.let { UserInfoLine(text = it) }
        value?.organization?.let { UserInfoLine(text = it) }
        value?.followingCount?.let { UserInfoLine(text = it) }
        value?.followersQuantity?.let { UserInfoLine(text = it) }
        value?.creationDate?.let { UserInfoLine(text = it) }
    }
}

@Preview(widthDp = 360, showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun UserInfoPreview() {
    val dummyUser = User(
        id = 1,
        login = "title",
        avatarUrl = "https://www.drcommodore.it/wp-content/uploads/2022/02/233b624e43a04fe9bfd43ef00ebcb2c9.jpg"
    )

    GHUsersTheme { UserInfo(mutableStateOf(dummyUser)) }
}

@Composable
fun UserInfoLine(text: String?) {
    Text(
        text = text ?: "",
        style = MaterialTheme.typography.titleMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
    )
}

@Preview(widthDp = 360, showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun UserInfoLinePreview() {
    GHUsersTheme { UserInfoLine("TEST") }
}