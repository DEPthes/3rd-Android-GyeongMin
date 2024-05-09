package com.example.android_basic_study_04

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_basic_study_04.databinding.ActivityMainBinding
import com.example.android_basic_study_04.databinding.ItemLayoutBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    var memoList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MyAdapter(memoList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val newText = data?.getStringExtra("text")
                newText?.let {
                    memoList.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        binding.add.setOnClickListener {
            val intent = Intent(this, MemoActivity::class.java)
            launcher.launch(intent)
        }


    }


}