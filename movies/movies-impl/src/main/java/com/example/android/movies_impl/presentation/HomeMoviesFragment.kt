package com.example.android.movies_impl.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.core_impl.base.BaseFragment
import com.example.android.core_impl.di.InjectUtils
import com.example.android.core_impl.di.component.DaggerCoreComponent
import com.example.android.core_impl.functional.isSuccessful
import com.example.android.core_impl.functional.onSuccess
import com.example.android.core_impl.view.MarginItemDecoration
import com.example.android.movies_impl.R
import com.example.android.movies_impl.databinding.FragmentHomeMoviesBinding
import com.example.android.movies_impl.di.DaggerMovieComponent
import com.example.android.movies_impl.domain.repository.MoviesRepository
import com.example.android.movies_impl.domain.usecase.SearchMovieUseCase
import com.example.android.movies_impl.presentation.adapter.MoviesAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeMoviesFragment : BaseFragment<FragmentHomeMoviesBinding, MovieViewModel>() {

    @Inject
    lateinit var useCase: SearchMovieUseCase
    private var columnCount = 2

    override fun getViewModelClass(): Class<MovieViewModel> = MovieViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_home_movies

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerMovieComponent.builder()
            .coreComponent(InjectUtils.provideCoreComponent(requireContext().applicationContext))
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.gridMovies.apply {
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.grid_margin)))
            layoutManager = GridLayoutManager(context, columnCount)
            adapter = MoviesAdapter {
                Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
            }
            viewModel.searchMovies("batman")
        }
    }

}
