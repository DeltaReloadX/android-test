package ro.vlad.androidtest.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ro.vlad.androidtest.R
import ro.vlad.androidtest.adapters.UsersAdapter
import ro.vlad.androidtest.models.UserModel
import ro.vlad.androidtest.utils.UsersUtil


class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    private var recyclerViewAdapter: UsersAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var progressBar: ProgressBar? = null

    private var usersList: ArrayList<UserModel.Result> = ArrayList()

    private var page = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = requireView().findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)

        populateData()
        initScrollListener()
    }


    private fun populateData() {
        Log.d("STATUS", "Init list with 20 users")
        context?.let {
            UsersUtil(it).fetchFromRemote { list ->
                usersList = list
                initAdapter()
            }
        }
    }

    private fun initAdapter() {
        Log.d("STATUS", "Init the user adapter")

        recyclerViewAdapter = UsersAdapter(usersList)
        linearLayoutManager = LinearLayoutManager(context)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = recyclerViewAdapter

            progressBar?.visibility = View.GONE
            visibility = View.VISIBLE
        }
    }

    private fun initScrollListener() {
        Log.d("STATUS", "Init the scroll listener")

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //check if it's the last element
                if (linearLayoutManager?.findLastCompletelyVisibleItemPosition() == usersList.size - 1) {
                    if (page < 3) {
                        loadMore()
                        progressBar?.visibility = View.VISIBLE


                    } else {
                        Log.d("STATUS", "Page 3 has been reached")
                    }
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadMore() {
        Log.d("STATUS", "Loading more users")

        context?.let {
            page++
            UsersUtil(it).fetchFromRemote { list ->
                usersList.addAll(list)
                recyclerViewAdapter?.notifyDataSetChanged()
                progressBar?.visibility = View.GONE
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}