package ro.vlad.androidtest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ro.vlad.androidtest.R
import ro.vlad.androidtest.models.UserModel

class UsersAdapter(private val usersList: List<UserModel.Result>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view  = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =  usersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =  holder.bind(usersList[position])


    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        var ivAvatar = itemView.findViewById<ImageView>(R.id.avatar_image)
        var tvName = itemView.findViewById<TextView>(R.id.user_name)
        var tvDesc = itemView.findViewById<TextView>(R.id.user_desc)
        var tvTime = itemView.findViewById<TextView>(R.id.time_text_view)

        fun bind(user: UserModel.Result) {

            tvName.text = "${user.name.first} ${user.name.last}"
            tvDesc.text = "${user.dob.age} years from ${user.location.country}"
            tvTime.text =
                user.registered.date.substringAfter("T").substringBefore(".").substring(0,5)

            // Loading img url into image view using Glide Library
            Glide.with(itemView.context).load(user.picture.medium).apply(RequestOptions.circleCropTransform()).into(ivAvatar);

        }

    }

}

