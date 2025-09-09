package com.example.lab_week_02_b

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ResultActivity : AppCompatActivity() {
    companion object {
        private const val COLOR_KEY = "COLOR_KEY"
        private const val ERROR_KEY = "ERROR_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val rawInput = intent.getStringExtra(COLOR_KEY)
        val root = findViewById<ConstraintLayout>(R.id.background_screen)
        val tv = findViewById<TextView>(R.id.color_code_result_message)
        if (rawInput.isNullOrBlank()) { sendErrorAndFinish(); return }
        val normalized = normalizeColorInput(rawInput)
        try {
            root.setBackgroundColor(Color.parseColor(normalized))
            tv.text = getString(R.string.color_code_result_message, normalized.removePrefix("#"))
        } catch (e: IllegalArgumentException) {
            sendErrorAndFinish()
        }
    }

    private fun normalizeColorInput(input: String): String {
        val trimmed = input.trim()
        // If it's a named color (letters only), return as is
        val lettersOnly = trimmed.matches(Regex("[a-zA-Z_]+"))
        if (lettersOnly) return trimmed
        var candidate = trimmed
        if (!candidate.startsWith("#")) candidate = "#" + candidate
        return candidate
    }

    private fun sendErrorAndFinish() {
        val data = Intent().apply { putExtra(ERROR_KEY, true) }
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
