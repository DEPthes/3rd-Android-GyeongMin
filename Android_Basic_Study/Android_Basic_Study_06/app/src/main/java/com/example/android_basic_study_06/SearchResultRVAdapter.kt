package com.example.android_basic_study_06

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_basic_study_06.databinding.ItemJjimBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchResultRVAdapter(
    private var data: MutableList<Product>,
    private var showDeleteButton: Boolean // 찜 삭제 버튼 활성화 상태
) : RecyclerView.Adapter<SearchResultRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemJjimBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.title.text = itemView.context.getString(R.string.title, item.title)
            binding.price.text = itemView.context.getString(R.string.price, item.price.toString())
            Glide.with(itemView.context)
                .load(item.thumbnail)
                .centerCrop()
                .into(binding.thumbnail);

            if (showDeleteButton) {
                binding.btnJjim.visibility = View.GONE
                binding.btnDel.visibility = View.VISIBLE
            } else {
                binding.btnJjim.visibility = View.VISIBLE
                binding.btnDel.visibility = View.GONE
            }

            binding.btnJjim.setOnClickListener {
                val product = item.run { ProductEntity(id, title, description, price, discountPercentage, rating, stock, brand, category, thumbnail, images) }
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = AppDatabase.getInstance(itemView.context).getProductListDAO()

                    // 이미 추가된 항목인지 확인
                    val existingProduct = dao.getProductById(product.id)
                    if (existingProduct == null) {
                        // 데이터베이스에 추가
                        dao.insertProduct(product)

                        // UI 업데이트는 메인 스레드에서 수행
                        CoroutineScope(Dispatchers.Main).launch {
                            // JjimActivity로 이동
                            val context = itemView.context
                            val intent = Intent(context, JjimActivity::class.java)
                            context.startActivity(intent)
                        }
                    } else {
                        // 이미 추가된 상품임을 알림
                        CoroutineScope(Dispatchers.Main).launch {
                            // 이미 추가된 상품임을 알리는 Toast 메시지 표시
                            Toast.makeText(itemView.context, "이미 추가된 상품입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            binding.btnDel.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = AppDatabase.getInstance(itemView.context).getProductListDAO()
                    dao.deleteProductById(item.id)
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(itemView.context, "상품이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        removeItem(adapterPosition)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJjimBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setList(list: List<Product>) {
        data = list.toMutableList()
        notifyDataSetChanged()
    }

    private fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, data.size)
    }
}
