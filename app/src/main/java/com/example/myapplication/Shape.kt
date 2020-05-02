package com.example.myapplication

import java.util.*

open class Shape(val map :Map) {
    //모든 도형은 색상, 좌표값, 회전중심의 좌표값을 갖는다.
    var color = "none"
    var coordinate = arrayOf(arrayOf(0,0),arrayOf(0,0),arrayOf(0,0),arrayOf(0,0))
    var centerRow:Int = 0
    var centerColumn:Int = 0
    fun isEnd(map:Map) : Boolean {
        return map.map[coordinate[0][0]][coordinate[0][1]].isfilled or map.map[coordinate[1][0]][coordinate[1][1]].isfilled or map.map[coordinate[2][0]][coordinate[2][1]].isfilled or map.map[coordinate[3][0]][coordinate[3][1]].isfilled
    }
    fun turn(isRight : Boolean) {

    }
    fun fillBlock(map:Map, row:Int, column:Int, color : String) {
        //맵에 특정 블록에 대한 값을 추가함.
        map.map[row][column].isfilled = true
        map.map[row][column].color = color
    }
    fun move(isToRight : Boolean) {
        val fronts = arrayListOf<Array<Int>>()
        if (isToRight) {
            //우측으로 이동
            //1. 해당 shape 안에 최우측 블럭을 구한다.
            //우선 첫번째 블럭좌표를 최우측으로 설정, 그다음좌표부터 볼 때 우선 row값이 같은지 확인하고,
            //다르면 그게 최우측, 같으면 비교해서 최우측을 수정

            //지금이 이미 최우측인, column값이 9인 경우 바로 리턴해버림.
            for (i in 0..3) {
                if (coordinate[i][1] == 9) return
            }

            println("좌표값 : "+coordinate[0])
            fronts.add(coordinate[0])
            for (i in 1..3) {
                var b = -1
                for (j in 0 until fronts.size) {
                    if( coordinate[i][0] == fronts[j][0]){
                        b = j
                    }
                }
                //여기서, b값은 동일한 row값을 가진 최전선 기록이 있는지여부를 확인하여 있다면 그 요소의 fronts 내의 index값을 가지고,
                //없으면 -1을 가진다.
                if (b > -1) {
                    //겹친다면
                    if (coordinate[i][1] > fronts[b][1]) {
                        //지금 넣을까 말까 고민하는 coordinate[i]의 column값이 기존최우방보다 어 우측이라면, 바꿔치기 해줍니다.
                        fronts[b] = coordinate[i]
                    }
                }
            }


            //2. 구한 최우측블럭기준 한칸 오른쪽에 블럭이 있는지 확인한다.
            var c = true

            for ( i in 0 until fronts.size) {
                if (map.map[fronts[i][0]][fronts[i][1]+1].isfilled) {
                    c = false
                }
            }
            //여기서 c값은 단 하나라도 최우방 한칸오른쪽에 블럭이 있을 경우 false가 됨.

            //3. 옮기거나 옮기지않는다.
            if (c) {
                //c값이 true일때만 옮긴다.
                for (i in 0..3) {
                    coordinate[i][1] = coordinate[i][1]+1
                }
            }
        } else {
            //왼쪽으로 옮기는 경우

        }
    }
}

class Rectangle(map: Map) : Shape(map) {
    init {
        coordinate = arrayOf(arrayOf(0,4),arrayOf(0,5),arrayOf(1,4),arrayOf(1,5))
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

        } else {
            //게임 끝!
        }
    }




}

class Stick(map: Map) : Shape(map) {


}

class Lshape(map: Map) : Shape(map) {

}

class Zshape(map: Map) : Shape(map) {

}

class Tshape(map: Map) : Shape(map) {

}