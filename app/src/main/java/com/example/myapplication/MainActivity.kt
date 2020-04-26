package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val playBtn = findViewById<Button>(R.id.main_btn_gamestart)
        playBtn.setOnClickListener {
//            val toast = Toast.makeText(applicationContext, "ㅎㅇ", Toast.LENGTH_SHORT)
//            toast.show()
            val intent = Intent(this, PlayroomActivity::class.java).apply{
                putExtra("하이","ㅎㅇㅎㅇ")
            }
            startActivity(intent)
        }
        val setBtn = findViewById<Button>(R.id.main_btn_setting)
        setBtn.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        val rankBtn = findViewById<Button>(R.id.main_btn_rank)
        rankBtn.setOnClickListener{
            val intent = Intent(this, RankActivity::class.java)
            startActivity(intent)
        }

    }
}
