package com.rsschool.quiz.questions

data class Question(
    val id: Int,
    val question: String,
    val options: ArrayList<String>,
    val correctAnswer: Int
)

