package io.github.snumcaa.domain.entities

data class User(
        val id: String,
        val username: String,
        val following: Boolean
)
