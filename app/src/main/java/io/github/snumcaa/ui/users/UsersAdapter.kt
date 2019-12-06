package io.github.snumcaa.ui.users

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

import io.github.snumcaa.R
import io.github.snumcaa.domain.entities.User

interface FollowerDelegate {
    fun followClicked(id: String)

    fun unfollowClicked(id: String)
}

class UsersAdapter(val context: Context?, val viewModel: UsersViewModel, val delegate: FollowerDelegate): RecyclerView.Adapter<UserViewHolder>() {
    private var set = false
    private var users: List<User> = emptyList()

    fun isInitialized(): Boolean {
        return set
    }

    fun setUsers(users: List<User>) {
        this.users = users
        set = true
        notifyDataSetChanged()
    }

    fun setUserFollowed(userId: String) {
        this.users = this.users.map { user ->
            if (user.id == userId)
                user.copy(following = true)
            else user
        }
    }

    fun setUserUnFollowed(userId: String) {
        this.users = this.users.map { user ->
            if (user.id == userId)
                user.copy(following = false)
            else user
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.followButton.setOnClickListener { view ->
            val current = users[position]

            if (current.following)
                delegate.unfollowClicked(current.id)
            else
                delegate.followClicked(current.id)
        }

        holder.bind(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(inflater, parent)
    }
}

class UserViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(R.layout.user_item, parent, false)) {
    var usernameText: TextView = itemView.findViewById(R.id.users_username_text)
    var followButton: ToggleButton = itemView.findViewById(R.id.follow_button)

    fun bind(user: User) {
        usernameText.text = user.username

        if (user.following != followButton.isChecked)
            followButton.toggle()
    }
}
