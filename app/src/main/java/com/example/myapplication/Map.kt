package com.example.myapplication

class Map(rowsize: Int,columnsize:Int ) {
    val map= ArrayList<ArrayList<Block>>()
    var rowmap = ArrayList<Block>()
    var block:Block = Block(false, "none")
    init{
        //처음 맵을 만들 때, 해당 사이즈값에 맞춰서 각 행렬에 대해 블럭 객체들로 채워져있는 배열이 만들어짐.

        for (i in 1..rowsize){
            rowmap = ArrayList<Block>()
            for (j in 1..columnsize) {
                // 한 행을 만든다. 맨 윗줄부터
                block = Block(false, "none")
                rowmap.add(block)
                //println(j)
            }
            map.add(rowmap)

        }
        println("${map.size},${map[0].size}")
    }

    fun destroyBlocksinLow() : Boolean {
        //먼저 색깔이 붙어있는 블럭들의 좌표를 모두 뽑아오고, 그 후 꽉찬 줄이 있는지 여부를 확인하여 꽉찬 줄을 전부 가져온다.
        //그 후 그 줄에 해당하는 모든 블럭, 10개이상 동일색 붙어있는 모든 좌표의 블럭들을 소멸시킨다.
        //근데 일단은 그냥 한줄 단위로 완성된것만 부수게 하겠습니다.
        val targetarray = arrayListOf<Int>()
        for (i in 19 downTo 0) {
            var allfilled = true
            for (j in 0..9) {
                if (!map[i][j].isfilled) {
                    allfilled = false
                }
            }
            if (allfilled) {
                targetarray.add(i)
            }
        }

        return if (targetarray.size < 1) {
            false
        } else {
            for (item in targetarray) {
                destroyRow(item)
            }
            gravity(targetarray)
            true
        }
    }

    fun destroyRow(row : Int) {
        //한 줄을 부수는 함수
        for (i in 0 .. 9) {
            map[row][i].delete()
        }
    }
    fun gravity(array : ArrayList<Int>) {
        //중력을 실행한다. 주로 destroyrows를 발동한 후, 남은 블럭들의 가장 큰 row값과 19의 차이를 계산하여, 모든 블럭들을 해당값만큼 내리는 것.
        val gmap= ArrayList<ArrayList<Block>>()
        var growmap = ArrayList<Block>()
        var gblock: Block
        var down = 0
        for (i in 19 downTo 0) {
            for (j in 0 until array.size) {
                if ( i-down == array[j]) {
                    //요줄이 부서진 줄입니다.
                    down++
                }
            }
            growmap = ArrayList<Block>()
            for (j in 0..9) {
                gblock = if (i < down) {
                    Block(false,"none")
                } else {
                    map[i-down][j]
                }
                growmap.add(gblock)
            }
            gmap.add(growmap)
        }
        for (i in 0..19) {
            for (j in 0..9) {
                map[i][j] = gmap[19-i][j]
            }
        }
    }
    class Block(var isfilled : Boolean, var color : String) {
        //맵 안에 있는 하나하나의 좌표점. 이 안에는 블럭이 차있는지 비어있는지에 대한 booblean값과, 색상이 들어있음.
        fun delete() {
            isfilled = false
            color = "none"
        }

    }
}