package com.example.taxiv2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class ThirdFragment : Fragment(R.layout.fragment_third) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Accessing the views from the Fragment layout
        val submitButton: Button = view.findViewById(R.id.submitButton)
        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)

        // Setting up the button click listener
        submitButton.setOnClickListener {
            val selectedOption = when (radioGroup.checkedRadioButtonId) {
                R.id.option1 -> "english"
                R.id.option2 -> "francais"
                R.id.option3 -> "arabic"
                else -> "No option selected"
            }
            Toast.makeText(requireContext(), "Selected: $selectedOption", Toast.LENGTH_SHORT).show()
        }
    }
}
