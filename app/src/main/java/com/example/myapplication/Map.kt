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

                if(rowsize == columnsize) {
                    block = Block(true, "none")
                } else {
                    block = Block(false, "none")
                }
                rowmap.add(block)
                //println(j)
            }
            map.add(rowmap)

        }
        println("${map.size},${map[0].size}")
    }

    class Block(var isfilled : Boolean, var color : String) {
        //맵 안에 있는 하나하나의 좌표점. 이 안에는 블럭이 차있는지 비어있는지에 대한 booblean값과, 색상이 들어있음.


    }
}