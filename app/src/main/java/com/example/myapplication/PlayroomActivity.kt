package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.media.Image
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class PlayroomActivity : AppCompatActivity(){
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playroom)
        val map = Map(20,10)
        val primg = findViewById<ImageView>(R.id.pr_map)
        val bitmap: Bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)


//            .setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                TODO("Not yet implemented")
//            }
//        })

        
        gameStart(map,primg,bitmap,canvas)



    }
//    private var previousX: Float = 0f
//    private var previousY: Float = 0f
//    override fun onTouchEvent(e: MotionEvent): Boolean {
//        // MotionEvent reports input details from the touch screen
//        // and other input controls. In this case, you are only
//        // interested in events where the touch position changed.
//
//        val x: Float = e.x
//        val y: Float = e.y
//
//        when (e.action) {
//            MotionEvent.ACTION_MOVE -> {
//
//                var dx: Float = x - previousX
//                var dy: Float = y - previousY
//
//                // reverse direction of rotation above the mid-line
//                if (dx >= 200) { //우측으로 이동
//                    val toast = Toast.makeText(applicationContext,"오른쪽",Toast.LENGTH_SHORT)
//                    toast.show()
//                }
//
//                // reverse direction of rotation to left of the mid-line
//                if (dx <= -200) {
//                    val toast = Toast.makeText(applicationContext,"왼쪽",Toast.LENGTH_SHORT)
//                    toast.show()
//                }
//
//                if (dy >= 200) {
//                    val toast = Toast.makeText(applicationContext,"아랫쪽",Toast.LENGTH_SHORT)
//                    toast.show()
//                }
//                if (dy <= -200) {
//                    val toast = Toast.makeText(applicationContext,"윗쪽",Toast.LENGTH_SHORT)
//                    toast.show()
//                }
//            }
//        }
//
//        previousX = x
//        previousY = y
//        return true
//    }


    //테트리스 내부 구성요소
    //우선 도형.
    //게임 시작 메소드
    //도형은 색상, 종류(모양), 회전중심, 좌표가 있어야함.
    //도형은 네모, L자, Z자 등 여러개의 클래스에 상위클래스로 존재함.
    //그리고 이 블록이란 것은, 땅에 떨어지고 나면, 객체가 사라지고, 그 모양을 맵에 그대로 좌표에 이식하게 됨.
    //맵이란 객체도 필요하겠다. 맵은 각 좌표에 대한 정보인 '블럭' 객체의 집합임.
    //블럭은, 채워져있는지 여부, 색상을 가지고, 파괴되기(점수업, 애니메이션 효과 등),떨어지기(밑에 블록이 파괴되면 위에블록들이 떨어짐) 등의 메소드를 가짐.

    @SuppressLint("ClickableViewAccessibility")
    fun gameStart(map:Map, primg:ImageView, bitmap: Bitmap, canvas:Canvas) {
        //coroutine을 시작하는 것.
        //맵 객체를 생성하고, 맵 객체는 생성시 각 좌표에 대한 블럭들을, 빈 상태로 만듦
        //맵 객체에는, 한줄이 완성되었거나 동일색상이 여러개 중첩되었는지를 판단하여 부수는 메소드가 존재해야함.
        val scope = CoroutineScope(Dispatchers.Main)
        var isgameEnd = false


        val job = scope.launch {
            val miniscope = CoroutineScope(Dispatchers.Main)
            val minijob = miniscope.launch {
                val playlayout = findViewById<LinearLayout>(R.id.pr_playlayout)
                var newturn = true
                var pDownX = 0
                var pDownY = 0
                var pUpX = 0
                var pUpY = 0

                newturn = false//새로운 턴이 시작되는지 여부를 알리는 밸류. 조종가능한 블럭이 있다면 false값을 가진다.
                var shape = Shape(map)
                val r = Random()
                val n = r.nextInt(5)
                when(n) {
                    0 -> {
                        shape = Rectangle(map)
                        if (!shape.make()) {
                            isgameEnd = true
                        }
                    }
                    1 -> {
                        shape = Stick(map)
                        if (!shape.make()) {
                            isgameEnd = true
                        }
                    }
                    2 -> {
                        shape = Lshape(map)
                        if (!shape.make()) {
                            isgameEnd = true
                        }
                    }
                    3 -> {
                        shape = Zshape(map)
                        if (!shape.make()) {
                            isgameEnd = true
                        }
                    }
                    4 -> {
                        shape = Tshape(map)
                        if (!shape.make()) {
                            isgameEnd = true
                        }
                    }

                }

                playlayout.setOnTouchListener { v, event ->
                    val action = event.action
                    when(action){
                        MotionEvent.ACTION_DOWN -> {
                            pDownX= event.x.toInt()
                            pDownY= event.y.toInt()
                        }
                        MotionEvent.ACTION_MOVE -> { }
                        MotionEvent.ACTION_UP -> {
                            pUpX= event.x.toInt()
                            pUpY= event.y.toInt()
                            //여기서 어떤 조작을 했는지에 따라 도형을 이동시키고 회전시키고 낙하시킬거에요.
                            //본 터치리스너를 장착하면 원래 막 엄청 경고문구가 뜨는데, 그걸 없애기위해선 본 프로잭트의 oncreate 위에있는
                            //@SuppressLint("ClickableViewAccessibility") 요 문구를 입력해주셔야 합니다. 시각장애인들에게도 이용할 수 있는 어플을 제작하라고
                            //이런 경고문구를 만들었다고 합니다.
                            if (!(isgameEnd or newturn)) {//현재 조작가능한 도형이 있을때만 발동
                                when {
                                    pUpY - pDownY <= -200 -> {
                                        println("위$newturn")
                                        //회전
                                        shape.turn(true)
                                    }
                                    pUpX - pDownX >= 200 -> {
                                        println("오른쪽")
                                        //
                                        shape.move(true)
                                    }
                                    pUpX - pDownX <= -200 -> {
                                        println("왼쪽")
                                        shape.move(false)
                                    }
                                    pUpY - pDownY >= 200 -> {
                                        println("아래")
                                    }
                                }
                            }

                        }
                    }
                    true
                }

                while (!isgameEnd) {
                    //한 턴마다 진행되어야 하는 것을 나열해보자면,
                    //1. 현재 플래이어가 조종하고있는 블럭이 없다면 블럭을 생성
                    if (newturn) {
                        val random = Random()
                        val num = random.nextInt(5)
                        when(num) {
                            0 -> {
                                shape = Rectangle(map)
                                if (!shape.make()) {
                                    isgameEnd = true
                                }
                            }

                            1 -> {
                                shape = Stick(map)
                                if (!shape.make()) {
                                    isgameEnd = true
                                }
                            }
                            2 -> {
                                shape = Lshape(map)
                                if (!shape.make()) {
                                    isgameEnd = true
                                }
                            }
                            3 -> {
                                shape = Zshape(map)
                                if (!shape.make()) {
                                    isgameEnd = true
                                }
                            }
                            4 -> {
                                shape = Tshape(map)
                                if (!shape.make()) {
                                    isgameEnd = true
                                }
                            }
                        }
                        newturn = false
                    } else {
                        //2. 조종중인 블럭이 있다면 한칸 godown
                        if (!shape.godown()) {
                            //2.1 조종중인 블럭이 godown을 false를 리턴했다면, 맵에서 부숴질 수 있는지 여부를 확인하여 부수든 말든 하고, 새로운 블럭을 생성
                            map.destroyBlocksinLow()
                            newturn = true
                            println("바닥$newturn")
                        }
                    }

                    //3. 맵을 그려줍니당.
                    drawMap(map,primg,bitmap,canvas)
                    delay(200)

                }
            }
            minijob.join()

            println("게임 종료!")
        }
    }

//    fun fillBlock(map:Map, row:Int, column:Int, color : String) {
//        //맵에 특정 블록에 대한 값을 추가함.
//        map.map[row][column].isfilled = true
//        map.map[row][column].color = color
//    }


    fun drawMap(map:Map, primg:ImageView, bitmap: Bitmap, canvas:Canvas) {//맵 객체를 불러와 전부 그린다!
        var shapeDrawable: ShapeDrawable
        val outr = floatArrayOf(12f,12f,12f,12f,12f,12f,12f,12f)//느낌알았다 근데 이걸 글로어케쓰지
        //          2   3
        //      1           4
        //      8           6
        //          7   5
        //의 순서임. 각각 해당 부분의 원의 반지름을 정하는 것.
        val inset = RectF(6F, 6F, 6F, 6F)//이게 테두리의 두께임.
        val innerr = floatArrayOf(12f,12f,12f,12f,12f,12f,12f,12f)
//        val toast = Toast.makeText(applicationContext, "${map.map.size},${map.map[1].size}", Toast.LENGTH_SHORT)
//        toast.show()

        for(row in 0 until map.map.size) {

            for (column in 0 until map.map[row].size) {
                if (map.map[row][column].isfilled) {
//                    val toast = Toast.makeText(applicationContext, "$row,$column", Toast.LENGTH_SHORT)
//                    toast.show()
                    val left = 0 + column * 50
                    val top = 0 + row * 38
                    val right = 50 + column * 50
                    val bottom = 38 + row * 38

                    shapeDrawable = ShapeDrawable(RoundRectShape(outr, inset, innerr))
                    shapeDrawable.setBounds(left, top, right, bottom)
                    //해당 아이템들의 색상에 맞춰 프린트한다.
                    when (map.map[row][column].color) {
                        "red" -> shapeDrawable.paint.color = Color.parseColor("#FF0000")
                        "blue" -> shapeDrawable.paint.color = Color.parseColor("#000080")
                        "green" -> shapeDrawable.paint.color = Color.parseColor("#00FF2F")
                        "yellow" -> shapeDrawable.paint.color = Color.parseColor("#FFFF00")
                    }
                    shapeDrawable.draw(canvas)
                } else {
                    //빈공간을 그린다.
                    val left = 0f + column * 50f
                    val top = 0f + row * 38f
                    val right = 50f + column * 50f
                    val bottom = 38f + row * 38f
                    val paint = Paint()
                    paint.color = Color.DKGRAY
//                    shapeDrawable = ShapeDrawable(RoundRectShape(outr, inset, innerr))
//                    shapeDrawable.setBounds(left, top, right, bottom)
//                    shapeDrawable.paint.color = Color.parseColor("#A9A9A9")
//                    shapeDrawable.draw(canvas)
                    canvas.drawRect(left,top,right,bottom,paint)
                }
            }
        }

        primg.background = BitmapDrawable(resources, bitmap)
    }

}