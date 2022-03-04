package com.severianfw.picto.view.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.severianfw.picto.PictoApplication
import com.severianfw.picto.databinding.FragmentHomeBinding
import com.severianfw.picto.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        binding.rvPhotos.layoutManager = GridLayoutManager(context, 2)
        homeViewModel.listPhotos.observe(viewLifecycleOwner) {
            if (it != null) {
                val photoAdapter = PhotoAdapter()
                binding.rvPhotos.adapter = photoAdapter
                photoAdapter.submitList(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}