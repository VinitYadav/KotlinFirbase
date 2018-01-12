package com.kotlinfirebase.bean

data class AllPostBean(var commentCount: Int = 0, var description: String = "",
                       var likeCount: Int = 0, var postImage: String = "",
                       var shareCount: Int = 0, var timestamp: Long = 0, var name: String = "",
                       var userId: String = "", var emailId: String = "",
                       var userImage: String = "") {
}