package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PlayroomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playroom)
        //여기서 특정 조건마다 발동되는, drawmap 메소드가 필요행.
        //drawmap(map) 요거는, map에서 각 좌표에 해당하는 모든 block들을 가져와서 layout의 view와 매칭시켜주는 함수야.
//        gameStart()
        drawBlock(0,0)
        drawBlock(1,1)
        drawBlock(2,2)

    }



    //테트리스 내부 구성요소
    //우선 도형.
    //게임 시작 메소드
    //도형은 색상, 종류(모양), 회전중심, 좌표가 있어야함.
    //도형은 네모, L자, Z자 등 여러개의 클래스에 상위클래스로 존재함.
    //그리고 이 블록이란 것은, 땅에 떨어지고 나면, 객체가 사라지고, 그 모양을 맵에 그대로 좌표에 이식하게 됨.
    //맵이란 객체도 필요하겠다. 맵은 각 좌표에 대한 정보인 '블럭' 객체의 집합임.
    //블럭은, 채워져있는지 여부, 색상을 가지고, 파괴되기(점수업, 애니메이션 효과 등),떨어지기(밑에 블록이 파괴되면 위에블록들이 떨어짐) 등의 메소드를 가짐.
    fun gameStart() {
        //coroutine을 시작하는 것.
        //맵 객체를 생성하고, 맵 객체는 생성시 각 좌표에 대한 블럭들을, 빈 상태로 만듦
        //맵 객체에는, 한줄이 완성되었거나 동일색상이 여러개 중첩되었는지를 판단하여 부수는 메소드가 존재해야함.
        val map = Map(20, 10)
//        var mapimgview = ImageView(this)
//        mapimgview.layoutParams.height = 32
//        mapimgview.layoutParams.width = 32
//        mapimgview.id =
    }

    fun drawmap(map:Map) {

    }

    fun drawBlock(row:Int, column:Int) {//블럭 하나를 해당 좌표에 그리는 것.
        val primg = findViewById<ImageView>(R.id.pr_map)
        val bitmap: Bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)
        var shapeDrawable: ShapeDrawable
        val left = 0 + column * 50
        val top = 0 + row * 38
        val right = 50 + column * 50
        val bottom = 38 + row * 38
        val outr = floatArrayOf(12f,12f,12f,12f,12f,12f,12f,12f)//느낌알았다 근데 이걸 글로어케쓰지
        //          2   3
        //      1           4
        //      8           6
        //          7   5
        //의 순서임. 각각 해당 부분의 원의 반지름을 정하는 것.
        //
        //
        val inset = RectF(6F, 6F, 6F, 6F)//이게 테두리의 두께임.
        val innerr = floatArrayOf(12f,12f,12f,12f,12f,12f,12f,12f)
        shapeDrawable = ShapeDrawable(RoundRectShape(outr,inset,innerr))
        shapeDrawable.setBounds( left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.parseColor("#009944"))
        shapeDrawable.draw(canvas)
        primg.background = BitmapDrawable(getResources(), bitmap)


    }

}