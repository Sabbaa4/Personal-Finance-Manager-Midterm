package com.example.midterm.model

/**
 * FinanceManager კლასი — ასრულებს ყველა ფინანსურ გათვლას.
 *
 * უნიკალური ფორმულა:
 * savingsPercent = გვარის ასოების რაოდენობა + დაბადების თვის რიცხვი
 * "ბარამიძე" = 8 ასო
 * დაბადების თვე = 2
 */
class FinanceManager {

    companion object {
        // გვარი: "ბარამიძე" — 8 ქართული ასო
        private const val SURNAME_LETTER_COUNT = 8

        // დაბადების თვე (მაგ: იანვარი=1, თებერვალი=2, ... დეკემბერი=12)
        private const val BIRTH_MONTH = 2
    }

    /**
     * უნიკალური ფორმულა: დანაზოგის პროცენტი
     * savingsPercent = გვარის ასოების რაოდენობა + დაბადების თვის რიცხვი
     */
    fun calculateSavingsPercent(): Int {
        return SURNAME_LETTER_COUNT + BIRTH_MONTH
    }

    /**
     * გამოთვლის მთლიან ხარჯებს (ქირა + კვება)
     */
    fun calculateTotalExpenses(model: FinanceModel): Double {
        return model.rent + model.food
    }

    /**
     * გამოთვლის ბალანსს (ხელფასი - ხარჯები)
     */
    fun calculateBalance(model: FinanceModel): Double {
        return model.salary - calculateTotalExpenses(model)
    }

    /**
     * გამოთვლის რეკომენდებულ დანაზოგს უნიკალური ფორმულით
     */
    fun calculateSavingsAmount(model: FinanceModel): Double {
        return model.salary * calculateSavingsPercent() / 100.0
    }

    /**
     * ამოწმებს ხელფასი ხარჯებზე მეტია თუ არა
     * true = surplus (მწვანე), false = deficit (წითელი)
     */
    fun isSurplus(model: FinanceModel): Boolean {
        return model.salary >= calculateTotalExpenses(model)
    }
}
