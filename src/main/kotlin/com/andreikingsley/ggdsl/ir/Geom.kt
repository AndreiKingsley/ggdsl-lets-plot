package com.andreikingsley.ggdsl.ir

data class Geom(val name: String){
    companion object {
        val POINT = Geom("point")
        val BAR = Geom("bar")
        val LINE = Geom("line")
    }
}
