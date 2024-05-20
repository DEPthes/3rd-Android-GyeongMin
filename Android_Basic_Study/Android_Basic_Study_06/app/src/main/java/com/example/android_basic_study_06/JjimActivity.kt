package com.example.android_basic_study_06

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_basic_study_06.databinding.JjimLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JjimActivity: AppCompatActivity() {
    private lateinit var binding: JjimLayoutBinding
    //private lateinit var adapter: SearchResultRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = JjimLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = SearchResultRVAdapter(mutableListOf(), showDeleteButton = true) // 찜 삭제 버튼 활성화
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            val productEntityList =  AppDatabase.getInstance(this@JjimActivity).getProductListDAO().getProductList()
            val productList = productEntityList.map { it.toProduct() }
            launch(Dispatchers.Main){adapter.setList(productList)}
        }

    }
    fun ProductEntity.toProduct(): Product {
        return Product(id, title, description, price, discountPercentage, rating, stock, brand, category, thumbnail, images)
    }
}