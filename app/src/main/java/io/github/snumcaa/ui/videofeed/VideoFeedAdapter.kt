package io.github.snumcaa.ui.videofeed

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import io.github.snumcaa.R
import io.github.snumcaa.domain.entities.YouTubeVideo

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoFeedAdapter(val context: Context?, val viewModel: VideoFeedViewModel): RecyclerView.Adapter<YouTubeVideoViewHolder>() {
    var set: Boolean = false
    var videos: List<YouTubeVideo> = emptyList()

    fun setYouTubeVideos(videos: List<YouTubeVideo>) {
        this.videos = videos
        set = true
        notifyDataSetChanged()
    }

    fun isInitialized(): Boolean {
        return set
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: YouTubeVideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YouTubeVideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return YouTubeVideoViewHolder(inflater, parent)
    }
}

class YouTubeVideoViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(R.layout.video_feed_item, parent, false)) {
    private var youTubePlayerView: YouTubePlayerView = itemView.findViewById(R.id.youtube_player_view)
    private var videoPosterIdTextView: TextView = itemView.findViewById(R.id.video_poster_id_text_view)
    private var videoTextView: TextView = itemView.findViewById(R.id.video_text_view)

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

    fun bind(youTubeVideo: YouTubeVideo) {
        this.youTubeVideo = youTubeVideo

        videoPosterIdTextView.text = youTubeVideo.posterId
        videoTextView.text = youTubeVideo.text ?: "${youTubeVideo.posterId} shared this video!"

        youTubePlayer?.cueVideo(youTubeVideo.videoId, 0f)
    }
}
