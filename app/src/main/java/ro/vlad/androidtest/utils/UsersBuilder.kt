package ro.vlad.androidtest.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsersBuilder {

    //remote url
    private const val remoteUrl ="https://randomuser.me"

    //http client
    private val okHttp =OkHttpClient.Builder()

    //builder for retrofit
    private val builder =Retrofit.Builder().baseUrl(remoteUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    // retrofit instance
    private val retrofit = builder.build()


    fun <T> buildService (serviceType :Class<T>):T{
        return retrofit.create(serviceType)
    }



}