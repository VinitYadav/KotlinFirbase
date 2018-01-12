package com.kotlinfirebase.model

data class GetPostBean(var commentCount: Int = 0, var description: String = "",
                       var likeCount: Int = 0, var postImage: String = "",
                       var shareCount: Int = 0, var timestamp: Long = 0,
                       var userId: String = "") {

}
