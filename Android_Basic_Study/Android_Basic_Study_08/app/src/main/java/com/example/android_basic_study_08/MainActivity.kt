package com.example.android_basic_study_08

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.android_basic_study_08.databinding.ActivityMainBinding
import com.example.android_basic_study_08.view.card.CardFragment
import com.example.android_basic_study_08.view.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNav()
        replaceFragment(HomeFragment())
    }

    private fun setBottomNav() {
        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.menu_card -> {
                    replaceFragment(CardFragment())
                    return@setOnItemSelectedListener  true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment)
        fragmentTransaction.commit()
    }
}