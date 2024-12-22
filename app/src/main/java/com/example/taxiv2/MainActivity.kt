package com.example.taxiv2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val firstfragment = FirstFragment()
        val secondfragment = secondfragment()
        val thirdfragment = ThirdFragment()

        setCurrentFragment(firstfragment)
        val bottomNavigationView =findViewById<BottomNavigationView>(R.id.bottomNavigationView)

         bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mihome -> setCurrentFragment(firstfragment)
                R.id.miprofile -> setCurrentFragment(secondfragment)
                R.id.misetting -> setCurrentFragment(thirdfragment)
            }
            true
         }

    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flfragment, fragment)
            commit()
        }
}
