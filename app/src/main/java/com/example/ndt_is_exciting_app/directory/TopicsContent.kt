package com.example.ndt_is_exciting_app.directory

import com.example.ndt_is_exciting_app.R

var typesOfSections = listOf("text full","text side","image with text","Image")
var sections = listOf(R.layout.text_full,R.layout.text_side,R.layout.image_with_text,R.layout.full_image)

// List the Topics Content and there Respective Layout

var X_Ray_topic = mutableMapOf(
    "topic" to "Magnetic Particle",
    "_noOfSections" to 2,
    "section_type" to sections(1,2,3,4),
    "section_info" to info(
        "X-Ray",
        "this is an Image of X-Rays ,you can see the defects visible on the side",
        R.drawable.blank_fill_in
    )
)




fun sections(vararg section_nos :Int) : MutableList<String>{

    var types_select = mutableListOf<String>()
    section_nos.forEach {
        types_select.add(typesOfSections[it-1])
    }
    return types_select
}

fun info(vararg info : Any) : MutableMap<Any,Any> {
    var map = mutableMapOf<Any,Any>()
    var text_counter = 0
    var image_counter = 0
    var counter : String = ""

    info.forEach {
        if (it is String){
            counter = "text $text_counter"
            text_counter += 1
        }else if(it is R.drawable){
            counter = "image $image_counter"
            image_counter += 1
        }
        map[counter] = it
    }
    return map
}

