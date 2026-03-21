package com.example.retailapp.data.source

import com.example.retailapp.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersService {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<User>

    @GET("users/{login}")
    suspend fun getUserDetails(@Path("login") login: String): User

}