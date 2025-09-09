package com.example.lab_week_02_b

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    companion object {
        private const val COLOR_KEY = "COLOR_KEY"
        private const val ERROR_KEY = "ERROR_KEY"
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
        if (result.resultCode == Activity.RESULT_OK && data?.getBooleanExtra(ERROR_KEY, false) == true) {
            Toast.makeText(this, getString(R.string.color_code_input_invalid), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById<TextInputEditText>(R.id.color_code_input_text)
        val submit = findViewById<Button>(R.id.submit_button)
        submit.setOnClickListener {
            val code = input.text?.toString()?.trim()?.uppercase() ?: ""
            when {
                code.isEmpty() -> Toast.makeText(this, getString(R.string.color_code_input_empty), Toast.LENGTH_SHORT).show()
                code.length != 6 -> Toast.makeText(this, getString(R.string.color_code_input_wrong_length), Toast.LENGTH_SHORT).show()
                else -> {
                    val intent = Intent(this, ResultActivity::class.java).apply { putExtra(COLOR_KEY, code) }
                    startForResult.launch(intent)
                }
            }
        }
    }
}
