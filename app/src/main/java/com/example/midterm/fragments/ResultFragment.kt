package com.example.midterm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.midterm.R
import com.example.midterm.databinding.SbFragmentResultBinding
import com.example.midterm.model.FinanceManager
import com.example.midterm.model.FinanceModel
import androidx.fragment.app.Fragment
import java.text.NumberFormat
import java.util.Locale

/**
 * Fragment B (Result) — აჩვენებს გათვლების შედეგებს.
 * მონაცემები მიიღება Bundle Arguments-ით.
 * ვიზუალური ვალიდაცია: წითელი/მწვანე ფერები surplus/deficit-ისთვის.
 * Dynamic Identity: კოდიდან (Kotlin) გამოაჩინებს სახელს, გვარსა და დაბადების წელს.
 */
class ResultFragment : Fragment() {

    private var _binding: SbFragmentResultBinding? = null
    private val binding get() = _binding!!
    private val financeManager = FinanceManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SbFragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // მონაცემების მიღება Arguments-დან (Bundle)
        val salary = arguments?.getDouble("salary", 0.0) ?: 0.0
        val rent = arguments?.getDouble("rent", 0.0) ?: 0.0
        val food = arguments?.getDouble("food", 0.0) ?: 0.0

        // FinanceModel ობიექტის შექმნა
        val model = FinanceModel(salary, rent, food)

        // შედეგების გამოტანა
        displayResults(model)

        // Dynamic Identity — კოდიდან (Kotlin) გამოაჩენს სახელს, გვარს, დაბადების წელს
        displayIdentity()

        // Back ღილაკი
        binding.sbBtnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    /**
     * გამოთვლილი შედეგების ჩვენება Fragment B-ში.
     * ვიზუალური ვალიდაცია: წითელი ფერი თუ ხელფასი < ხარჯები, მწვანე — სხვა შემთხვევაში.
     */
    private fun displayResults(model: FinanceModel) {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)

        val totalExpenses = financeManager.calculateTotalExpenses(model)
        val balance = financeManager.calculateBalance(model)
        val savingsPercent = financeManager.calculateSavingsPercent()
        val isSurplus = financeManager.isSurplus(model)

        // ხელფასი (Total Inflows)
        binding.sbResultSalary.text = currencyFormat.format(model.salary)

        // ხარჯები (Total Outflows)
        binding.sbResultExpenses.text = currencyFormat.format(totalExpenses)

        // ბალანსი (Projected Retained Capital)
        binding.sbResultBalance.text = currencyFormat.format(balance)

        // დანაზოგის პროცენტი (უნიკალური ფორმულა)
        binding.sbResultSavingsRate.text = "${savingsPercent}%"

        // სტატუსი
        binding.sbResultStatus.text = if (isSurplus) {
            getString(R.string.sb_status_optimal)
        } else {
            getString(R.string.sb_status_deficit)
        }

        // ==========================================
        // ვიზუალური ვალიდაცია — ფერების ცვლა
        // ==========================================
        if (isSurplus) {
            // მწვანე — ხელფასი >= ხარჯები
            val greenColor = ContextCompat.getColor(requireContext(), R.color.sb_positive)
            binding.sbResultBalance.setTextColor(greenColor)
            binding.sbResultStatus.setTextColor(greenColor)
            binding.sbHighlightCard.setBackgroundResource(R.drawable.sb_result_highlight_bg)
        } else {
            // წითელი — ხელფასი < ხარჯები
            val redColor = ContextCompat.getColor(requireContext(), R.color.sb_negative)
            binding.sbResultBalance.setTextColor(redColor)
            binding.sbResultStatus.setTextColor(redColor)
            binding.sbHighlightCard.setBackgroundResource(R.drawable.sb_result_highlight_bg_negative)
        }
    }

    /**
     * Dynamic Identity — კოდიდან (Kotlin) გამოაჩინებს:
     * სახელი: Saba
     * გვარი: Baramidze
     * დაბადების წელი: 2004
     */
    private fun displayIdentity() {
        binding.sbIdentityInitials.text = "SB"
        binding.sbIdentityName.text = "Name: Saba"
        binding.sbIdentitySurname.text = "Surname: Baramidze"
        binding.sbIdentityBirthYear.text = "Birth Year: 2004"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
