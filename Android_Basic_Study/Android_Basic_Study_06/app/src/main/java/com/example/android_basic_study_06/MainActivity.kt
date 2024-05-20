package com.example.android_basic_study_06

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_basic_study_06.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = SearchResultRVAdapter(mutableListOf(), showDeleteButton = false) // 찜 삭제 버튼 비활성화
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnSearch.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val productList =  ProductRepositoryImpl().searchProducts(binding.search.text.toString())
                launch(Dispatchers.Main){adapter.setList(productList)}
            }
        }

        binding.btnJjimList.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                // JjimActivity로 이동
                val intent = Intent(this@MainActivity, JjimActivity::class.java)
                startActivity(intent)
            }
        }
    }
}