package com.example.ap1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ap1.dao.PostDao
import kotlinx.android.synthetic.main.activity_creat_post.*

class CreatPostActivity : AppCompatActivity() {

    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_post)

        postDao = PostDao()

        button.setOnClickListener {
            val input = editTextTextPersonName.text.toString().trim()
            if(input.isNotEmpty()){
                postDao.addPost(input)
                finish()
            }
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

    }
}