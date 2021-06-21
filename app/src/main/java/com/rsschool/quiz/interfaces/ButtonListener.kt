package com.rsschool.quiz.interfaces

interface ButtonListener {
    fun shareResult(score: Int)
    fun restart()
    fun closeApp()
}