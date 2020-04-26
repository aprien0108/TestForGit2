package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val playroom : PlayroomActivity = PlayroomActivity()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainPlayBtn = findViewById<Button>(R.id.main_btn_gamestart)
        mainPlayBtn.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "ㅎㅇ", Toast.LENGTH_SHORT)
            toast.show()
            val intent = Intent(this, PlayroomActivity::class.java).apply{
                putExtra("하이","ㅎㅇㅎㅇ")
            }
            startActivity(intent)
        }

    }
}
