package com.severianfw.picto.view.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.severianfw.picto.PictoApplication
import com.severianfw.picto.data.remote.ImageUrl
import com.severianfw.picto.databinding.FragmentHomeBinding
import com.severianfw.picto.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val _listPhoto = mutableListOf<ImageUrl>()
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
            if (isLoading) {
                binding.pbPhotos.visibility = View.VISIBLE
            } else {
                binding.pbPhotos.visibility = View.INVISIBLE
            }
        }
        val layoutManager = GridLayoutManager(context, 2)
        val photoAdapter = PhotoAdapter()
        binding.rvPhotos.layoutManager = layoutManager
        binding.rvPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val itemCount = layoutManager.itemCount.minus(1)
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val isLastPosition = itemCount == lastVisibleItemPosition

                if (!rvIsLoading && isLastPosition) {
                    rvIsLoading = true
                    homeViewModel.loadPage()
                    rvIsLoading = false
                }

            }
        })
        homeViewModel.listPhotos.observe(viewLifecycleOwner) {
            if (it != null) {
                _listPhoto.addAll(it)
                Log.d("SIZE", _listPhoto.size.toString())
                binding.rvPhotos.adapter = photoAdapter
                photoAdapter.submitList(_listPhoto)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}