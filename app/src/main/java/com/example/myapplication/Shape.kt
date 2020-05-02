package com.example.myapplication

import java.util.*

abstract class Shape {
    //모든 도형은 색상, 좌표값, 회전중심의 좌표값을 갖는다.
    var color = "none"
    var coordinate = arrayOf(arrayOf(0,0),arrayOf(0,0),arrayOf(0,0),arrayOf(0,0))
    var centerRow:Int = 0
    var centerColumn:Int = 0
    abstract fun isEnd(map:Map) : Boolean
    abstract fun turn(isRight : Boolean)
    fun fillBlock(map:Map, row:Int, column:Int, color : String) {
        //맵에 특정 블록에 대한 값을 추가함.
        map.map[row][column].isfilled = true
        map.map[row][column].color = color
    }
    fun move(isToRight : Boolean) {

    }
}

class Rectangle(val map :Map) : Shape() {
    init {

        //놓으려는 위치에 블럭이 있는지여부 확인
        if (!isEnd(map)) {
            //블럭 생성
            //랜덤으로 색상 부여 후 좌표 결정. 좌표 결정 시 맵의 해당 좌표에 블럭이 있다면 게임 종료임.
            //색상은 랜덤으로 빨노초파 4색
            val random = Random()
            val num = random.nextInt(4)
            when(num) {
                0 -> color = "red"
                1 -> color = "yellow"
                2 -> color = "green"
                3 -> color = "blue"
            }
            fillBlock(map,0,4,color)
            fillBlock(map,0,5,color)
            fillBlock(map,1,4,color)
            fillBlock(map,1,5,color)
            coordinate = arrayOf(arrayOf(0,4),arrayOf(0,5),arrayOf(1,4),arrayOf(1,5))

        } else {
            //게임 끝!
        }
    }


    override fun isEnd(map:Map): Boolean {
        //(0,4)(0,5) 14 15중 하나라도 블록이 있으면 겜끝
        return map.map[0][4].isfilled or map.map[0][5].isfilled or map.map[1][4].isfilled or map.map[1][5].isfilled
    }

    override fun turn(isRight: Boolean) {
        TODO("Not yet implemented")
    }
}

class Stick(val map :Map) : Shape() {


    override fun isEnd(map: Map): Boolean {
        TODO("Not yet implemented")
    }
    override fun turn(isRight: Boolean) {
        TODO("Not yet implemented")
    }
}

class Lshape(val map:Map) : Shape() {
    override fun isEnd(map: Map): Boolean {
        TODO("Not yet implemented")
    }
    override fun turn(isRight: Boolean) {
        TODO("Not yet implemented")
    }
}

class Zshape(val map:Map) : Shape() {
    override fun isEnd(map: Map): Boolean {
        TODO("Not yet implemented")
    }
    override fun turn(isRight: Boolean) {
        TODO("Not yet implemented")
    }
}

class Tshape(val map:Map) : Shape() {
    override fun isEnd(map: Map): Boolean {
        TODO("Not yet implemented")
    }
    override fun turn(isRight: Boolean) {
        TODO("Not yet implemented")
    }
}