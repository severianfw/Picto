package com.severianfw.picto.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.severianfw.picto.PictoApplication
import com.severianfw.picto.data.remote.ImageUrl
import com.severianfw.picto.databinding.FragmentHomeBinding
import com.severianfw.picto.view.detail.DetailActivity
import com.severianfw.picto.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var rvIsLoading = false

    @Inject
    lateinit var homeViewModel: HomeViewModel

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

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            rvIsLoading = isLoading
            if (isLoading) {
                binding.pbPhotos.visibility = View.VISIBLE
            } else {
                binding.pbPhotos.visibility = View.INVISIBLE
            }
        }
        setupPhotoRecyclerView()
    }

    private fun setupPhotoRecyclerView() {
        val gridLayoutManager = GridLayoutManager(context, 2)
        val photoAdapter = PhotoAdapter()
        photoAdapter.setOnItemClickListener(object : PhotoAdapter.OnItemClickListener {
            override fun onItemClick(photo: ImageUrl) {
                val detailIntent = Intent(activity, DetailActivity::class.java)
                TODO("Change item type to PhotoResponse for getting photo & user data")
//                detailIntent.putExtra(DetailActivity.TAG, photo)
//                startActivity(detailIntent)
            }
        })
        binding.rvPhotos.apply {
            layoutManager = gridLayoutManager
            adapter = photoAdapter
        }
        binding.rvPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val itemCount = gridLayoutManager.itemCount.minus(1)
                val lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition()
                val isLastPosition = itemCount == lastVisibleItemPosition

                if (!rvIsLoading && isLastPosition) {
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