package com.example.ndt_is_exciting_app.resources

import kotlin.math.pow
import kotlin.math.sqrt

fun distance(pos1 : MutableList<Float>,pos2 : MutableList<Float>): Float {
    return sqrt((pos1[0]-pos2[0]).pow(2) + (pos1[1] - pos2[1]).pow(2))
}