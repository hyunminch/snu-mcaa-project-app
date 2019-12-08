package io.github.snumcaa.domain.entities

data class YouTubeVideo(val id: Long,
                        val videoId: String,
                        val posterName: String,
                        val text: String?,
                        val shared: Boolean)