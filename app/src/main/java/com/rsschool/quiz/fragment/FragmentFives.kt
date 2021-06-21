package com.rsschool.quiz.fragment


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.interfaces.FragmentListener
import com.rsschool.quiz.interfaces.OnBackPressedListener
import com.rsschool.quiz.questions.ListQuestions

class FragmentFives : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBackPressedListener: OnBackPressedListener
    private lateinit var fragmentListener: FragmentListener
    private lateinit var userOption: String

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
        window?.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.deep_purple_100_dark)

        context?.theme?.applyStyle(R.style.Theme_Quiz_Fives, true)

        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radioGroup = binding.radioGroup
        val toolbar = binding.toolbar
        val listQuestion = ListQuestions.listQuestions
        var score: Int

        val position = 4

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
        with(binding) {
            nextButton.text = "Result"
            nextButton.isEnabled = false

            radioGroup.setOnCheckedChangeListener { _, checkedId ->

                val idBtn: Int = binding.radioGroup.checkedRadioButtonId
                val checkBtn: RadioButton = binding.radioGroup.findViewById(idBtn)
                val text = checkBtn.text.toString()
                userOption = text

                nextButton.isEnabled = true

                nextButton.setOnClickListener {
                    if (checkedId == R.id.option_one) {
                        score = arguments?.get(SCORE) as Int
                        score += 1
                    } else {
                        score = arguments?.get(SCORE) as Int
                    }

                    fragmentListener.second(FragmentResult.newInstance(score))
                }
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
        fun newInstance(score: Int): FragmentFives {
            val fragment = FragmentFives()
            fragment.arguments = Bundle().apply {
                putInt(SCORE, score)
            }
            return fragment
        }
        private const val SCORE = "SCORE"
    }
}