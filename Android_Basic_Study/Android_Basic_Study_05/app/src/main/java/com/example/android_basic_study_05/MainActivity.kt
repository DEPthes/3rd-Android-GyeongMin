package com.example.android_basic_study_05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_basic_study_05.databinding.ActivityMainBinding
import com.example.android_basic_study_05.databinding.ItemMovieBinding
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runBlocking {
            var boxOffice = BoxOfficeRepositoryImpl().getBoxOfficeList("dd852bc870e6fd6b9a9380ca10ccd9ad", "20111215")
            Log.d("ddd", boxOffice.size.toString())
        }

        val adapter = MovieAdapter()
        // RecyclerView에 어댑터 설정
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val editTextRank = binding.editTextRank
        val editTextMovieName = binding.editTextMovieName
        val editTextReleaseDate = binding.editTextReleaseDate
        val editTextAudienceCount = binding.editTextAudienceCount
        val buttonAdd = binding.buttonAdd

        buttonAdd.setOnClickListener {
            val rank = editTextRank.text.toString()
            val movieName = editTextMovieName.text.toString()
            val releaseDate = editTextReleaseDate.text.toString()
            val audienceCount = editTextAudienceCount.text.toString()

            // 입력된 데이터를 Movie 객체로 만들어서 RecyclerView에 추가
            val movie = Movie(rank, movieName, releaseDate, audienceCount)
            adapter.addMovie(movie)

            // 입력 필드 초기화
            editTextRank.text.clear()
            editTextMovieName.text.clear()
            editTextReleaseDate.text.clear()
            editTextAudienceCount.text.clear()
        }
    }
}

data class Movie(val rank: String, val movieName: String, val releaseDate: String, val audienceCount: String)

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val movies: MutableList<Movie> = mutableListOf()

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        val textViewRank: TextView = binding.textViewRank
        val textViewMovieName: TextView = binding.textViewMovieName
        val textViewReleaseDate: TextView = binding.textViewReleaseDate
        val textViewAudienceCount: TextView = binding.textViewAudienceCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.textViewRank.text = movie.rank
        holder.textViewMovieName.text = movie.movieName
        holder.textViewReleaseDate.text = movie.releaseDate
        holder.textViewAudienceCount.text = movie.audienceCount
    }

    override fun getItemCount(): Int = movies.size

    fun addMovie(movie: Movie) {
        movies.add(movie)
        notifyItemInserted(movies.size - 1)
    }
}