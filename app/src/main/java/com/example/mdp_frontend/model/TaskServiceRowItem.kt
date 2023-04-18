package com.example.mdp_frontend.model

data class TaskServiceRowItem (
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