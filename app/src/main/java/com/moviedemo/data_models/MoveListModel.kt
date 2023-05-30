package com.example.practicaltest.data.model

import com.google.gson.annotations.SerializedName

data class MoveListModel(
    var id: Int = 1,
    var name: String,
    @SerializedName("poster-image")
    var poster_image: String
)