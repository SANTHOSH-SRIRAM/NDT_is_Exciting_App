package com.example.ndt_is_exciting_app.directory

import com.example.ndt_is_exciting_app.R
import com.example.ndt_is_exciting_app.resources.QuestionFormat


//this can be a list or map according to if Question Description is Required

private var X_ray_Questions = mapOf(
    0 to X_Ray_topic,
    1 to QuestionFormat(
        "Question1",
        "This is a Place Holder Question",
        "DragBoxQuestion",
        4,
        listOf(
            mutableListOf(mutableListOf(0.5,0.3), mutableListOf(0.4,0.2))
        )
    ).map,
    2 to QuestionFormat(
        "Question2",
        "This is a Place Holder Question",
        "GridSelection",
        4,
        listOf(
            R.drawable.fish_tessalate,
            R.drawable.default_background,
            R.drawable.blank_fill_in,
            R.drawable.app_icon_your_company,
            R.drawable.fish_tessalate,
            R.drawable.default_background,
            R.drawable.blank_fill_in,
            R.drawable.app_icon_your_company,
            R.drawable.fish_tessalate,
            R.drawable.default_background,
            R.drawable.blank_fill_in,
            R.drawable.app_icon_your_company,
            R.drawable.fish_tessalate,
            R.drawable.default_background,
            R.drawable.blank_fill_in,
            R.drawable.app_icon_your_company
        )
    ).map,
    3 to QuestionFormat(
        "Question3",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map,
    4 to QuestionFormat(
        "Question4",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map
)

private var subTopics2_Questions = mapOf(
    1 to QuestionFormat(
        "Question1",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map,
    2 to QuestionFormat(
        "Question2",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map,
    3 to QuestionFormat(
        "Question3",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map,
    4 to QuestionFormat(
        "Question4",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map
)

private var subTopics3_Questions = mapOf(
    1 to QuestionFormat(
        "Question1",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map,
    2 to QuestionFormat(
        "Question2",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map,
    3 to QuestionFormat(
        "Question3",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map,
    4 to QuestionFormat(
        "Question4",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map
)

private var subTopics4_Questions = mapOf(
    1 to QuestionFormat(
        "Question1",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map,
    2 to QuestionFormat(
        "Question2",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map,
    3 to QuestionFormat(
        "Question3",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map,
    4 to QuestionFormat(
        "Question4",
        "This is a Place Holder Question",
        "MCQ",
        4,
        listOf(
            "answer1",
            "answer2",
            "answer3",
            "answer4")
    ).map
)


var subTopics1 = mapOf(
    "subTopic1" to X_ray_Questions,
    "subTopic2" to subTopics2_Questions,
    "subTopic3" to subTopics3_Questions,
    "subTopic4" to subTopics4_Questions,
)