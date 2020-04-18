package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("하이");
        println("좀 이상하넹");
        println("그냥 커밋은 뭐지? 왜 커밋엔 푸시만 된다는거지???");

    }
}
