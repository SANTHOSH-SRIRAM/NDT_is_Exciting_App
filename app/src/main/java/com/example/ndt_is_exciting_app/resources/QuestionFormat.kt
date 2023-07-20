package com.example.ndt_is_exciting_app.resources

data class QuestionFormat(
    private val questionName:String = "No Question Name",
    private val Question : String = "No question given",
    private val questionType : String = "MCQ",
    private val correctAnswer : Int = -1,
    private val answers : List<Any> = listOf("0","0","0","0"),
    private val noOfOptions : Int = answers.size
    ){

    val map : MutableMap<Any,Any> = mutableMapOf(
        "_name" to questionName,
        "_Question" to Question,
        "_QuestionType" to questionType,
        "_no Of Options" to noOfOptions,
        "_CorrectAnswer" to correctAnswer,
    )

    init{
        var i = 1
        for(answer_prompt in answers){
            map[i] = "$answer_prompt"
            i += 1
        }
    }
}