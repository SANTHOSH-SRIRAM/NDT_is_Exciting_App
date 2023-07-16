package com.example.ndt_is_exciting_app.resources

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


//Approximation Class for holding all the Approximation functions when evaluating the answers for DragBox and Point for now

fun rectangeApproximation(startPos : MutableList<Float>, endPos : MutableList<Float>, ansStartPos : MutableList<Float>, ansEndPos : MutableList<Float>): Int {

    //Creating truth varibles
    var area_feasible = 0
    var distance_feasible = 0
    var ratio_feasible = 0

    //constraint Variables
    var corr_size = 0.2f
    var approx_size = 0.4f

    var maxDistance = corr_size
    var min_area_ratio = 1.0f - corr_size
    var max_area_ratio = 1.0f + corr_size
    var min_ratio = 1.0f - corr_size
    var max_ratio = 1.0f + corr_size

    var maxCloseDistance = approx_size
    var min_close_area_ratio = 1.0f - approx_size
    var max_close_area_ratio = 1.0f + approx_size
    var min_close_ratio = 1.0f - approx_size
    var max_close_ratio = 1.0f + approx_size

    //currently don't need the position correction
//        //correcting for Position
//        var correctedPos = arrangeCoords(startPos,endPos)
//        var correctedAns = arrangeCoords(ansStartPos,ansEndPos)
//
//        //unpacking varibles
//        var cor_start_pos = correctedPos[0]
//        var cor_end_pos = correctedPos[1]
//        var cor_ans_start = correctedAns[0]
//        var cor_ans_end = correctedAns[1]

    //finding Lenght and Width
    var pos_Width = abs(startPos[0] - endPos[0])
    var pos_Lenght = abs(startPos[1] - endPos[1])
    var ans_Width = abs(ansStartPos[0] - ansEndPos[0])
    var ans_Lenght = abs(ansStartPos[1] - ansEndPos[1])

    //Calculating Area's
    var area_pos = pos_Lenght * pos_Width
    var area_ans = ans_Lenght * ans_Width

    //Calculating Ratio of areas
    var area_ratio = area_pos/area_ans

    //Centre Positions
    var center_pos = mutableListOf(((startPos[0] + endPos[0])/2),((startPos[1] + endPos[1])/2))
    var center_ans = mutableListOf(((ansStartPos[0] + ansEndPos[0])/2),((ansStartPos[1] + ansEndPos[1])/2))

    //Calculating Distances
    var distance = distance(center_pos,center_ans)

    //calculating Ratios of Side Lenghts
    var ratio_pos = pos_Lenght/pos_Width
    var ratio_ans = ans_Lenght/ans_Width

    var ratio_of_ratios = ratio_pos/ratio_ans

    if( (min_area_ratio <  area_ratio)  and  (area_ratio < max_area_ratio) ){
        area_feasible = 2
    }else if( (min_close_area_ratio <  area_ratio)  and  (area_ratio < max_close_area_ratio) ){
        area_feasible = 1
    }

    if(distance < maxDistance){
        distance_feasible = 2
    }else if(distance < maxCloseDistance){
        distance_feasible = 1
    }
    if((min_ratio < ratio_of_ratios) and (ratio_of_ratios < max_ratio)){
        ratio_feasible = 2
    }else if((min_close_ratio < ratio_of_ratios) and (ratio_of_ratios < max_close_ratio)){
        ratio_feasible = 1
    }

    if ((area_feasible == 2) and (distance_feasible == 2) and (ratio_feasible == 2)){
        return 2
    }else if ((area_feasible >= 1) and (distance_feasible >= 1) and (ratio_feasible >= 1)){
        return 1
    }else {return 0}
}


fun CoordinateApproximation(point : MutableList<Float>,ansPoint : MutableList<Float>, max_distance : Float = 0.2f) : Int{

    var maxCorrectDistance = 0.2f
    var maxOkayDistance = 0.4f

    var distance = distance(point,ansPoint)
    if ( distance < max_distance ){
        return 2
    } else if( distance < maxOkayDistance ){
        return 1
    } else{
        return 0
    }
}

fun arrangeCoords(startPos: MutableList<Float>, endPos: MutableList<Float>): MutableList<MutableList<Float>> {
    var x1 = min(startPos[0],endPos[0])
    var x2 = max(startPos[0],endPos[0])
    var y1 = min(startPos[1],endPos[1])
    var y2 = max(startPos[1],endPos[1])

    return mutableListOf(mutableListOf(x1,y1), mutableListOf(x2,y2))
}





