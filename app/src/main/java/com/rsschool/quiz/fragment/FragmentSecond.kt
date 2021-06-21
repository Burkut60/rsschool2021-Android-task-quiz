package com.rsschool.quiz.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.interfaces.FragmentListener
import com.rsschool.quiz.interfaces.OnBackPressedListener
import com.rsschool.quiz.questions.ListQuestions

class FragmentSecond : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBackPressedListener: OnBackPressedListener
    private lateinit var fragmentListener: FragmentListener
    private var userOption: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            fragmentListener = context as FragmentListener
            onBackPressedListener = context as OnBackPressedListener
        } catch (e: Exception) {
            throw RuntimeException("$context must implement QuizFragmentListener")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val window = activity?.window
        window?.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.yellow_100_dark)

        context?.theme?.applyStyle(R.style.Theme_Quiz_Second, true)

        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radioGroup = binding.radioGroup
        val toolbar = binding.toolbar
        val listQuestion = ListQuestions.listQuestions
        var score:Int
        val position = 1

        toolbar.title = "Question ${listQuestion[position].id}"
        toolbar.setNavigationOnClickListener {
            onBackPressedListener.onBackPressed()
        }
        binding.question.text = listQuestion[position].question

        for (i in 0 until radioGroup.childCount) {
            val radioButton: View = radioGroup.getChildAt(i)
            if (radioButton is RadioButton) {
                radioButton.text = listQuestion[position].options[i]
            }
        }
        with(binding){
            nextButton.isEnabled = false

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                val idBtn: Int = binding.radioGroup.checkedRadioButtonId

                nextButton.isEnabled = true
                arguments?.putInt(OPTION, idBtn)
                userOption = requireArguments().get(OPTION) as Int

                nextButton.setOnClickListener {
                    if (checkedId == R.id.option_one) {
                        score = arguments?.get(SCORE) as Int
                        score += 1
                    } else {
                        score = arguments?.get(SCORE) as Int
                    }
                    fragmentListener.second(FragmentThird.newInstance(score))
                }
            }

            nextButton.setOnClickListener {
                Toast.makeText(activity, "Nothing selected", Toast.LENGTH_SHORT).show()
            }
            previousButton.setOnClickListener {
                onBackPressedListener.onBackPressed()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(score: Int): FragmentSecond{
            val fragment = FragmentSecond()
            fragment.arguments = Bundle().apply{
                putInt(SCORE, score)
            }
            return fragment
        }
        private const val OPTION = "OPTION"
        private const val SCORE = "SCORE"
    }
}