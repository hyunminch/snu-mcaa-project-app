package io.github.snumcaa.ui.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import io.github.snumcaa.R
import io.github.snumcaa.domain.entities.User

class UsersFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener, FollowerDelegate {

    private lateinit var viewModel: UsersViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UsersAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, UsersViewModelFactory(context)).get(UsersViewModel::class.java)
        adapter = UsersAdapter(context, viewModel, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_users, container, false)

        recyclerView = view.findViewById(R.id.users_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout = view.findViewById(R.id.users_swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener(this)

        recyclerView.adapter = adapter

        if (!adapter.isInitialized())
            onRefresh()

        return view
    }

    override fun onRefresh() {
        viewModel.getUsers()
                .observe(this, Observer<List<User>> { users ->
                    users?.let {
                        Log.i("UsersFragment", "Received users")
                        adapter.setUsers(it)
                    }
                })

        swipeRefreshLayout.isRefreshing = false
    }

    override fun followClicked(id: String) {
        viewModel.followUser(id)
                .observe(this, Observer<Boolean> { result ->
                    if (result) {
                        adapter.setUserFollowed(id)
                    } else {

                    }
                })
    }

    override fun unfollowClicked(id: String) {
        viewModel.unfollowUser(id)
                .observe(this, Observer<Boolean> { result ->
                    if (result) {
                        adapter.setUserUnFollowed(id)
                    } else {

                    }
                })
    }
}