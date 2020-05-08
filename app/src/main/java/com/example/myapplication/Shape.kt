package com.example.myapplication

import java.util.*
import kotlin.collections.ArrayList

open class Shape(val map :Map) {
    //모든 도형은 색상, 좌표값, 회전중심의 좌표값을 갖는다.
    var color = "none"
    var coordinate = arrayListOf(arrayOf(0,0),arrayOf(0,0),arrayOf(0,0),arrayOf(0,0))
    var centerIndex:Int = -1

    fun isEnd() : Boolean {
        for (item in coordinate) {
            if (map.map[item[0]][item[1]].isfilled) return true
        }
        return false
    }
    fun isButtom() : Boolean {
        //맵에 이 shape 밑에 자신의 좌표가 아닌 블럭이 채워져있다면 바텀임.
        var isbuttom = false
        for (i in 0 until coordinate.size) {
            var isme = false//바로 밑 블럭이 내 자신인지 확인하는 참거짓 값
            for (j in 0 until coordinate.size) {
                if (i != j) {
                    if((coordinate[i][0]+1).equals(coordinate[j][0]) and (coordinate[i][1].equals(coordinate[j][1]))) {
                        isme = true
                    }
                }
            }
            if ((coordinate[i][0]+1) == 20) {//맨 밑바닥인 경우
                return true
            }
            if (!(isme) and (map.map[coordinate[i][0]+1][coordinate[i][1]].isfilled)) {//내가아닌 블럭이 내 밑에 깔려있는 경우
                isbuttom = true
            }
        }
        return isbuttom
    }

    fun godown() : Boolean{
        if (isButtom()) {
            //밑바닥이라면 내려가지 못하고 결과값으로 false를 반환한다.
            return false
        }
        //밑바닥이 아니라면 한칸 내리고 true를 반환한다.
        val changecoordiate = arrayListOf<Array<Int>>()
        for (i in 0 until coordinate.size) {
            changecoordiate.add(arrayOf(coordinate[i][0]+1, coordinate[i][1]))
        }
        coordinatesChange(changecoordiate)
        return true
    }
    fun turn(isRight : Boolean) : Boolean {
        //방향전환이 가능한지 보려면, 우선 중심을 기준으로 전환했을때의 좌표를 계산해보고, 그 값이 map 좌표안에 위치할 수 있는지 확인
        //그리고 그 위치에 isfilled값이 false라면 회전.
        //우측이거나 좌측으로 돌릴 수 있겠습니다.
        if (centerIndex < 0) {//회전불가능한 도형들(예를들어 rectangle은 초기에 기준좌표값 초기화를 기준좌표값이 -1임. 요런애들은 그냥 false 리턴

            return false
        }
        val center = coordinate[centerIndex]
        println("기준 : ${center[0]} ,${center[1]} ")
        var changecoordinate = arrayListOf<Array<Int>>()
        //오른쪽 방향 회전의 경우, 다른 블럭들은 서로 기준에 대하여 자신의 row와 column을 비교하여 각 경우에 맞게 이동함.
        for (i in 0 until coordinate.size) {
            //수학적으로 밑에 식 두개면 회전이 끝남. 와 이거만든다고 진짜 힘들었다 머리 깨지는줄 ㅋㅋㅋㅋ
            if (isRight) {
                changecoordinate.add(arrayOf(
                    center[0] - (center[1] - coordinate[i][1]),
                    center[1] - (coordinate[i][0] - center[0])
                ))
            } else {
                changecoordinate.add(arrayOf(
                    center[0] + (center[1] - coordinate[i][1]),
                    center[1] + (coordinate[i][0] - center[0])
                ))
            }
        }
        //회전된 도형의 좌표를 계산완료하였으니, 맵에서 해당 칸이 filled 되어있는지 확인 후, 안되어있을때만 회전가능.
        //또한 changecoordinate안에 row가 20이상 혹은 column이 10이상인경우 회전불가임.
        for (item in changecoordinate) {
            if ((item[0] > 19) or (item[1] > 9)) return false
            for (i in 0 until changecoordinate.size) {
                var isme = false//바로 밑 블럭이 내 자신인지 확인하는 참거짓 값
                for (j in 0 until coordinate.size) {

                    if((changecoordinate[i][0]).equals(coordinate[j][0]) and (changecoordinate[i][1].equals(coordinate[j][1]))) {
                        isme = true
                    }

                }
                if (!(isme) and (map.map[changecoordinate[i][0]][changecoordinate[i][1]].isfilled)) {//내가아닌 블럭이 내 밑에 깔려있는 경우
                    return false
                }
            }
        }
        coordinatesChange(changecoordinate)
        return true
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
            //만들고 나서 든 생각이지만, 그냥 내 블럭우측에 채워진 블럭이 하나라도있으면 false 리턴하는게 개꿀이었을거같은데
            //지금이 이미 최우측인, column값이 9인 경우 바로 리턴해버림.
            for (i in 0 until coordinate.size) {
                if (coordinate[i][1].equals(9)) return
            }

            println("좌표값 : "+coordinate[0])
            fronts.add(coordinate[0])
            for (i in 1 until coordinate.size) {
                var b = -1
                for (j in 0 until fronts.size) {
                    if( coordinate[i][0].equals(fronts[j][0])){
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
                } else {
                    //안겹치면 그냥 넣어
                    fronts.add(coordinate[i])
                }
            }


            //2. 구한 최우측블럭기준 한칸 오른쪽에 블럭이 있는지 확인한다.
            var c = true

            for ( i in 0 until fronts.size) {
                if (map.map[fronts[i][0]][fronts[i][1]+1].isfilled) {
                    c = false
                }
            }
            println("c : $c")
            //여기서 c값은 단 하나라도 최우방 한칸오른쪽에 블럭이 있을 경우 false가 됨.

            //3. 옮기거나 옮기지않는다.
            if (c) {
                //c값이 true일때만 옮긴다.
                val changecoordinate = arrayListOf<Array<Int>>()
                for (i in 0 until coordinate.size) {
                    changecoordinate.add(arrayOf(coordinate[i][0],coordinate[i][1]+1))
                }
                coordinatesChange(changecoordinate)
            }
        } else {
            //왼쪽으로 옮기는 경우
            //좌측으로 이동
            //1. 해당 shape 안에 최좌측 블럭을 구한다.
            //우선 첫번째 블럭좌표를 최좌측으로 설정, 그다음좌표부터 볼 때 우선 row값이 같은지 확인하고,
            //다르면 그게 최좌측, 같으면 비교해서 최좌측을 수정

            //지금이 이미 최좌측인, column값이 0인 경우 바로 리턴해버림.
            for (i in 0 until coordinate.size) {
                if (coordinate[i][1].equals(0)) return
            }
            println("좌표값 : "+coordinate[0])
            fronts.add(coordinate[0])
            for (i in 1 until coordinate.size) {
                var b = -1
                for (j in 0 until fronts.size) {
                    if( coordinate[i][0].equals(fronts[j][0])){
                        b = j
                    }
                }
                //여기서, b값은 동일한 row값을 가진 최전선 기록이 있는지여부를 확인하여 있다면 그 요소의 fronts 내의 index값을 가지고,
                //없으면 -1을 가진다.
                if (b > -1) {
                    //겹친다면
                    if (coordinate[i][1] < fronts[b][1]) {
                        //지금 넣을까 말까 고민하는 coordinate[i]의 column값이 기존최좌방보다 어 좌측이라면, 바꿔치기 해줍니다.
                        fronts[b] = coordinate[i]
                    }
                } else {
                    //안겹치면 그냥 넣어
                    fronts.add(coordinate[i])
                }
            }


            //2. 구한 최좌측블럭기준 한칸 왼쪽에 블럭이 있는지 확인한다.
            var c = true

            for ( i in 0 until fronts.size) {
                if (map.map[fronts[i][0]][fronts[i][1]-1].isfilled) {
                    c = false
                }
            }
            //여기서 c값은 단 하나라도 최좌측 한칸왼쪽에 블럭이 있을 경우 false가 됨.

            //3. 옮기거나 옮기지않는다.
            if (c) {
                //c값이 true일때만 옮긴다.
                val changecoordinate = arrayListOf<Array<Int>>()
                for (i in 0 until coordinate.size) {
                    changecoordinate.add(arrayOf(coordinate[i][0],coordinate[i][1]-1))
                }
                coordinatesChange(changecoordinate)
            }
        }
    }

    fun coordinatesChange(changeCoordinate : ArrayList<Array<Int>>) {
        for (item in coordinate) {
            //맵에서 빼버린다.
            map.map[item[0]][item[1]].color = "none"
            map.map[item[0]][item[1]].isfilled = false
        }
        for (item in changeCoordinate) {
            map.map[item[0]][item[1]].color = color
            map.map[item[0]][item[1]].isfilled = true
        }
        coordinate = changeCoordinate
    }
    fun coordinateChange(fromChangeCoordinate : Array<Int>, toChangeCoordinate: Array<Int>) {
        //맵에서 빼버린다.
        map.map[fromChangeCoordinate[0]][fromChangeCoordinate[1]].color = "none"
        map.map[fromChangeCoordinate[0]][fromChangeCoordinate[1]].isfilled = false

        map.map[toChangeCoordinate[0]][toChangeCoordinate[1]].color = color
        map.map[toChangeCoordinate[0]][toChangeCoordinate[1]].isfilled = true

//        coordinate = changeCoordinate
    }
}

class Rectangle(map: Map) : Shape(map) {
    fun make(makecolor: String) : Boolean {
        coordinate = arrayListOf(arrayOf(0,4),arrayOf(0,5),arrayOf(1,4),arrayOf(1,5))

        //놓으려는 위치에 블럭이 있는지여부 확인
        if (!isEnd()) {
            //블럭 생성

            color = makecolor
            fillBlock(map,0,4,color)
            fillBlock(map,0,5,color)
            fillBlock(map,1,4,color)
            fillBlock(map,1,5,color)
            return true
        } else {
            //게임 끝!
            return false
        }
    }




}

class Stick(map: Map) : Shape(map) {
    fun make(makecolor: String) : Boolean {
        coordinate = arrayListOf(arrayOf(0,4),arrayOf(1,4),arrayOf(2,4),arrayOf(3,4))
        centerIndex = 2
        //놓으려는 위치에 블럭이 있는지여부 확인
        if (!isEnd()) {
            //블럭 생성
            color = makecolor
            for (item in coordinate) {
                fillBlock(map, item[0],item[1],color)
            }
            return true
        } else {
            //게임 끝!
            return false
        }
    }

}

class Lshape(map: Map) : Shape(map) {
    fun make(makecolor: String) : Boolean {
        coordinate = arrayListOf(arrayOf(0,4),arrayOf(1,4),arrayOf(2,4),arrayOf(2,5))
        centerIndex = 1
        //놓으려는 위치에 블럭이 있는지여부 확인
        if (!isEnd()) {
            //블럭 생성
            color = makecolor
            for (item in coordinate) {
                fillBlock(map, item[0],item[1],color)
            }
            return true
        } else {
            //게임 끝!
            return false
        }
    }
}

class Zshape(map: Map) : Shape(map) {
    fun make(makecolor: String) : Boolean {
        coordinate = arrayListOf(arrayOf(0,4),arrayOf(0,5),arrayOf(1,5),arrayOf(1,6))
        centerIndex = 1
        //놓으려는 위치에 블럭이 있는지여부 확인
        if (!isEnd()) {
            //블럭 생성
            color = makecolor
            for (item in coordinate) {
                fillBlock(map, item[0],item[1],color)
            }
            return true
        } else {
            //게임 끝!
            return false
        }
    }
}

class Tshape(map: Map) : Shape(map) {
    fun make(makecolor: String) : Boolean {
        coordinate = arrayListOf(arrayOf(0,4),arrayOf(0,5),arrayOf(0,6),arrayOf(1,5))
        centerIndex = 1
        //놓으려는 위치에 블럭이 있는지여부 확인
        if (!isEnd()) {
            //블럭 생성
            color = makecolor
            for (item in coordinate) {
                fillBlock(map, item[0],item[1],color)
            }
            return true
        } else {
            //게임 끝!
            return false
        }
    }
}