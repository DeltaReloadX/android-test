package ro.vlad.androidtest.utils

import ro.vlad.androidtest.models.UserModel
import retrofit2.Call
import retrofit2.http.GET

interface UsersInterface {

    @GET("/api/?page=0&results=20")
    fun getUsers(
    ): Call<UserModel>





}