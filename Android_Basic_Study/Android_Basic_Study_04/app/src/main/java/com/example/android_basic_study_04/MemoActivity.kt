package com.example.android_basic_study_04

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.android_basic_study_04.databinding.MemoLayoutBinding

class MemoActivity: AppCompatActivity() {
    private lateinit var binding: MemoLayoutBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MemoLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.save.setOnClickListener {
            val inputText = binding.input.text.toString()
            val intent = Intent().apply {
                putExtra("text", inputText)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}