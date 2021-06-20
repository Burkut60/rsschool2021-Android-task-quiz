package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.fragment.FragmentFirst
import com.rsschool.quiz.interfaces.ButtonListener
import com.rsschool.quiz.interfaces.FragmentListener
import com.rsschool.quiz.interfaces.OnBackPressedListener
import com.rsschool.quiz.questions.ListQuestions
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), FragmentListener, OnBackPressedListener, ButtonListener {
    private val listQuestion = ListQuestions.listQuestions
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFirstFragment()
    }

    private fun openFirstFragment() {
        val startQuiz: Fragment = FragmentFirst.newInstance(
            listQuestion[position].id,
            listQuestion[position],
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, startQuiz)
            .addToBackStack(null)
            .commit()
    }

    private fun openSecondFragment(nextFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun second(nextFragment: Fragment) {
        openSecondFragment(nextFragment)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            this.finish()
        }
    }

    override fun shareResult() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = "Here is the share content body (result test)"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Quiz result")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share via :"))
    }

    override fun restart() {
        val startQuiz: Fragment = FragmentFirst.newInstance(
            0,
            listQuestion[position]
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, startQuiz)
            .commit()
    }

    override fun closeApp() {
        finish()
        exitProcess(0)
    }
}