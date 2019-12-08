package io.github.snumcaa.ui.videofeed

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import io.github.snumcaa.R
import io.github.snumcaa.domain.entities.YouTubeVideo

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoFeedAdapter(val context: Context?, val viewModel: VideoFeedViewModel): RecyclerView.Adapter<YouTubeVideoViewHolder>() {
    private var set: Boolean = false
    private val videos: MutableList<YouTubeVideo> = mutableListOf()
    var videoFeedItemListener: VideoFeedItemListener? = null

    fun setYouTubeVideos(videos: List<YouTubeVideo>) {
        this.videos.clear()
        this.videos.addAll(videos)
        set = true
        notifyDataSetChanged()
    }

    fun isInitialized(): Boolean {
        return set
    }

    fun updateItem(video: YouTubeVideo) {
        val position = videos.indexOfFirst { it.id == video.id }
        if (position < 0) return

        videos[position] = video
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: YouTubeVideoViewHolder, position: Int) {
        holder.bind(videos[position], object : YouTubeVideoViewHolder.OnVideoShareListener {
            override fun onVideoShare(youTubeVideo: YouTubeVideo) {
                videoFeedItemListener?.onVideoShare(youTubeVideo)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YouTubeVideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return YouTubeVideoViewHolder(inflater, parent)
    }

    interface VideoFeedItemListener {

        fun onVideoShare(youTubeVideo: YouTubeVideo)
    }
}

class YouTubeVideoViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(R.layout.video_feed_item, parent, false)) {
    private var youTubePlayerView: YouTubePlayerView = itemView.findViewById(R.id.youtube_player_view)
    private var videoPosterIdTextView: TextView = itemView.findViewById(R.id.video_poster_id_text_view)
    private var videoTextView: TextView = itemView.findViewById(R.id.video_text_view)
    private var videoItemShareButton: Button = itemView.findViewById(R.id.video_item_share_button)

    private var youTubePlayer: YouTubePlayer? = null
    private var youTubeVideo: YouTubeVideo? = null

    init {
        val callback = object: YouTubePlayerCallback {
            override fun onYouTubePlayer(initializedYouTubePlayer: YouTubePlayer) {
                youTubePlayer = initializedYouTubePlayer
                youTubeVideo?.let {
                    initializedYouTubePlayer.cueVideo(it.videoId, 0f)
                }
            }
        }

        youTubePlayerView.getYouTubePlayerWhenReady(callback)
    }

    fun bind(youTubeVideo: YouTubeVideo, onVideoShareListener: OnVideoShareListener? = null) {
        this.youTubeVideo = youTubeVideo

        videoPosterIdTextView.text = youTubeVideo.posterName
        videoTextView.text = youTubeVideo.text ?: "${youTubeVideo.posterName} shared this video!"

        youTubePlayer?.cueVideo(youTubeVideo.videoId, 0f)

        videoItemShareButton.text = if (youTubeVideo.shared) "Shared" else "Share"
        videoItemShareButton.setOnClickListener {
            onVideoShareListener?.onVideoShare(youTubeVideo)
        }
    }

    interface OnVideoShareListener {

        fun onVideoShare(youTubeVideo: YouTubeVideo)
    }
}