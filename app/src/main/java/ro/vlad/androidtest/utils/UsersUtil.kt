package ro.vlad.androidtest.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ro.vlad.androidtest.models.UserModel

class UsersUtil(private var ctx: Context) {

     fun fetchFromRemote(onComplete: (ArrayList<UserModel.Result>) -> Unit) {
        Log.d("STATUS", "Fetch from remote")


        val remoteService = UsersBuilder.buildService(UsersInterface::class.java)
        val request = remoteService.getUsers()

        var randomUsersList: ArrayList<UserModel.Result>

        request.enqueue(object : Callback<UserModel> {
            override fun onResponse(
                call: Call<UserModel>,
                response: Response<UserModel>
            ) {
                if (response.isSuccessful) {
                    val rsp = response.body()!!
                    randomUsersList = rsp.results
                    onComplete.invoke(randomUsersList)

                } else {
                    Toast.makeText(ctx,
                        "Error: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Toast.makeText(
                    ctx,
                    "Something went wrong $t",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        )
    }
}