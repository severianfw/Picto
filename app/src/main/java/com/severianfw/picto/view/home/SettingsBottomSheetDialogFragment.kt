package com.severianfw.picto.view.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.severianfw.picto.PictoApplication
import com.severianfw.picto.databinding.BottomSheetSettingsBinding
import com.severianfw.picto.utils.DarkModeUtil
import javax.inject.Inject

class SettingsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var darkModeUtil: DarkModeUtil

    companion object {
        const val TAG = "SettingsBottomSheetFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as PictoApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkDarkMode()
    }

    private fun checkDarkMode() {
        if (darkModeUtil.isDarkModeActive()) {
            binding.scToggleMode.isChecked = true
        }
        binding.scToggleMode.setOnCheckedChangeListener { button, _ ->
            if (button.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkModeUtil.setDarkModeStatus(true)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkModeUtil.setDarkModeStatus(false)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}