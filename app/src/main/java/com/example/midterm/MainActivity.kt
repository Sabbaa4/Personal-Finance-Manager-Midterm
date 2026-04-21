package com.example.midterm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.midterm.databinding.ActivityMainBinding
import com.example.midterm.fragments.InputFragment
import com.example.midterm.fragments.ResultFragment

/**
 * MainActivity — აპლიკაციის მთავარი Activity.
 * მართავს Fragment-ების ჩანაცვლებას (Transaction) და მონაცემების გადაცემას.
 *
 * Fragment A (InputFragment) → მომხმარებელი შეიყვანს მონაცემებს
 * Fragment B (ResultFragment) → ნაჩვენებია გათვლების შედეგები
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // თავდაპირველად ვაჩვენებთ InputFragment-ს (Fragment A)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.sb_fragment_container, InputFragment())
                .commit()
        }

        // setFragmentResultListener — ვუსმენთ Fragment A-დან გამოგზავნილ მონაცემებს
        supportFragmentManager.setFragmentResultListener("financeData", this) { _, bundle ->
            // ResultFragment-ის (Fragment B) შექმნა და მონაცემების გადაცემა Arguments-ით
            val resultFragment = ResultFragment().apply {
                arguments = bundle
            }

            // Fragment Transaction — Fragment A-ს ჩანაცვლება Fragment B-ით
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                .replace(R.id.sb_fragment_container, resultFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}