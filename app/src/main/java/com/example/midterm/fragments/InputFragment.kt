package com.example.midterm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.midterm.databinding.SbFragmentInputBinding

/**
 * Fragment A (Input) — შეიცავს EditText ველებს (ხელფასი, ქირა, კვება)
 * და "Calculate" ღილაკს. ღილაკზე დაჭერისას მონაცემები გადაეცემა
 * Fragment B-ს setFragmentResult-ის საშუალებით.
 */
class InputFragment : Fragment() {

    private var _binding: SbFragmentInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SbFragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sbBtnCalculate.setOnClickListener {
            // EditText-ებიდან მნიშვნელობების წაკითხვა
            val salary = binding.sbEditSalary.text.toString().toDoubleOrNull() ?: 0.0
            val rent = binding.sbEditRent.text.toString().toDoubleOrNull() ?: 0.0
            val food = binding.sbEditFood.text.toString().toDoubleOrNull() ?: 0.0

            // მონაცემების Bundle-ში შეფუთვა
            val bundle = Bundle().apply {
                putDouble("salary", salary)
                putDouble("rent", rent)
                putDouble("food", food)
            }

            // setFragmentResult — მონაცემების გადაცემა MainActivity-ს საშუალებით
            parentFragmentManager.setFragmentResult("financeData", bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
