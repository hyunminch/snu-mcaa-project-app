package io.github.snumcaa.ui.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.snumcaa.R
import io.github.snumcaa.domain.entities.Notification

class NotificationsAdapter(private val context: Context) : RecyclerView.Adapter<NotificationViewHolder>() {

    private val notifications = mutableListOf<Notification>()

    fun setNotifications(notifications: List<Notification>) {
        this.notifications.clear()
        this.notifications.addAll(notifications)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.view_notification_item, parent, false))
    }

    override fun getItemCount() = notifications.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }
}

class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val notificationTextView: TextView = itemView.findViewById(R.id.notification_text_view)

    fun bind(notification: Notification) {
        notificationTextView.text = notification.message
    }
}