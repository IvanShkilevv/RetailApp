package com.example.retailapp.feature.common.domain

import com.example.retailapp.app.Constants.PAGE_SIZE
import com.example.retailapp.feature.common.data.model.User
import com.example.retailapp.feature.common.data.source.UsersService
import javax.inject.Inject

class UsersInteractor @Inject constructor(
    private val usersService: UsersService
) {

    suspend fun loadUsersPage(pageSinceID: Int): List<User> {
        return usersService.getUsers(since = pageSinceID, perPage = PAGE_SIZE)
    }

    suspend fun loadUserDetails(login: String): User {
        return usersService.getUserDetails(login)
    }

}