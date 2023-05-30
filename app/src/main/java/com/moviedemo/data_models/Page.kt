package com.example.practicaltest.data.model

import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("content-items")
    var content: Content,
    @SerializedName("page-num")
    var page_num: String,
    @SerializedName("page-size")
    var page_size: String,
    var title: String,
    @SerializedName("total-content-items")
    var total_content_items: String
)

data class Content(
    @SerializedName("content")
    var content_items: List<MoveListModel>,
)