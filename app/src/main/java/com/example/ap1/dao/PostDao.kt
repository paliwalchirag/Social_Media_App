package com.example.ap1.dao

import com.example.ap1.models.Post
import com.example.ap1.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import io.grpc.util.GracefulSwitchLoadBalancer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    // to get the accese from firebase we make db
    val db = FirebaseFirestore.getInstance()
    val postCollections = db.collection("posts")

    //to find the currently sign in user we need auth
    val auth=Firebase.auth

    fun addPost(text: String){
        //!! is use to ensure that the person is signIN
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val userDao = UserDao()
            //await get us documentsnapshot but not document so we convert
            //it in user class to get user
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!

            //current time millis return long
            val currentTime = System.currentTimeMillis()
            val post = Post(text,user,currentTime)

            postCollections.document().set(post)
        }

    }

    fun getPostById(postId: String): Task<DocumentSnapshot>{
        return postCollections.document(postId).get()
    }

    fun updateLikes(postId: String){

        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)
            val isLiked = post!!.likedBy!!.contains(currentUserId)

            if(isLiked) {
                post.likedBy.remove(currentUserId)
            } else {
                post.likedBy.add(currentUserId)
            }
            postCollections.document(postId).set(post)
        }
    }
}