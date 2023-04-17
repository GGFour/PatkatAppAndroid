package com.example.mdp_frontend.ui.screen.subscreen

// A data class to represent a service
data class Service(
    val name: String,
    val status: Status,
    val price: Int,
)

// An enum class to represent the status of a service
enum class Status {
    Active,
    InProgress,
    Finished
}