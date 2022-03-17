package com.severianfw.picto.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import com.severianfw.picto.PictoApplication
import com.severianfw.picto.R
import com.severianfw.picto.databinding.FragmentHomeBinding
import com.severianfw.picto.utils.Constant
import com.severianfw.picto.view.detail.PhotoDetailActivity
import com.severianfw.picto.viewmodel.HomeViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var isLoading = false
    private var compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var homeViewModel: HomeViewModel

    companion object {
        const val SPAN_COUNT = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as PictoApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInitialPhotos()
        setupSwipeRefreshLayout()
        setupLoadingObserver()
        setupErrorObserver()
        setupPhotoRecyclerView()
        setupSearchView()
    }

    private fun setupSwipeRefreshLayout() {
        binding.srlPhotos.setOnRefreshListener {
            refreshPhotos()
            getInitialPhotos()
            binding.srlPhotos.isRefreshing = false
        }
    }

    private fun refreshPhotos() {
        homeViewModel.apply {
            clearPhotos()
            setIsInitial(true)
            setIsSearching(false)
        }
    }

    private fun getInitialPhotos() {
        if (homeViewModel.getIsInitial()) {
            homeViewModel.apply {
                setPageNumber(1)
                getPhotos()
                setIsInitial(false)
            }
        }
    }

    private fun setupSearchView() {
        binding.svPhotos.isSubmitButtonEnabled = false
        compositeDisposable.addAll(
            binding.svPhotos.queryTextChanges().debounce(Constant.DEBOUNCE_TIME, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    if (it.isNotEmpty()) {
                        searchPhotos(it.toString())
                    }
                }
        )
    }

    private fun searchPhotos(query: String) {
        homeViewModel.apply {
            clearPhotos()
            searchPhotos(query)
        }
    }

    private fun setupErrorObserver() {
        homeViewModel.hasError.observe(viewLifecycleOwner) { hasError ->
            if (hasError) {
                Toast.makeText(
                    activity,
                    getString(R.string.message_failed_get_photos),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupLoadingObserver() {
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            this.isLoading = isLoading
            if (isLoading) {
                binding.pbPhotos.visibility = View.VISIBLE
            } else {
                binding.pbPhotos.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupPhotoRecyclerView() {
        val gridLayoutManager = GridLayoutManager(context, SPAN_COUNT)
        val photoAdapter = PhotoAdapter { photoItem ->
            val detailIntent = Intent(activity, PhotoDetailActivity::class.java)
            detailIntent.putExtra(Constant.PHOTO_ITEM, photoItem)
            startActivity(detailIntent)
        }
        binding.rvPhotos.apply {
            layoutManager = gridLayoutManager
            adapter = photoAdapter
        }
        binding.rvPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val itemCount = gridLayoutManager.itemCount.minus(1)
                val lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition()
                val isLastPosition = itemCount == lastVisibleItemPosition

                if (!isLoading && isLastPosition) {
                    homeViewModel.loadMorePage()
                }
            }
        })
        homeViewModel.photos.observe(viewLifecycleOwner) {
            photoAdapter.submitList(it.toList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        _binding = null
    }
}