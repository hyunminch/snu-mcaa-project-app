package io.github.snumcaa.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.snumcaa.R
import io.github.snumcaa.networking.Result
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    private lateinit var viewModel: NotificationsViewModel
    private lateinit var adapter: NotificationsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        viewModel = ViewModelProviders
                .of(this, NotificationsViewModelFactory(context))
                .get(NotificationsViewModel::class.java)
        adapter = NotificationsAdapter(requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeList()
        refresh()
    }

    private fun initializeList() {
        list_notifications.run {
            layoutManager = LinearLayoutManager(context)
            adapter = this@NotificationsFragment.adapter
        }

        swipe_refresh_layout.setOnRefreshListener {
            refresh()
        }
    }

    private fun refresh() {
        viewModel.loadNotifications()
                .observe(this, Observer { result ->
                    when (result) {
                        is Result.Success -> {
                            adapter.setNotifications(result.data)
                        }
                        is Result.Error -> {
                            // TODO Error Handling
                        }
                    }
                })
    }

}