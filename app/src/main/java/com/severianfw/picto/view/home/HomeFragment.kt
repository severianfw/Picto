package com.severianfw.picto.view.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.severianfw.picto.PictoApplication
import com.severianfw.picto.databinding.FragmentHomeBinding
import com.severianfw.picto.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var isLoading = false

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        getInitialPhotos()
        return binding.root
    }

    private fun getInitialPhotos() {
        if (!homeViewModel.isInitial) {
            homeViewModel.getPhotos()
            homeViewModel.isInitial = true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as PictoApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingCheck()
        errorStatusCheck()
        setupPhotoRecyclerView()
    }

    private fun errorStatusCheck() {
        homeViewModel.hasError.observe(viewLifecycleOwner) { hasError ->
            if (hasError) {
                Toast.makeText(activity, "Failed to call API!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadingCheck() {
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
        val gridLayoutManager = GridLayoutManager(context, 2)
        val photoAdapter = PhotoAdapter()
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
            photoAdapter.submitList(it.toMutableList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}