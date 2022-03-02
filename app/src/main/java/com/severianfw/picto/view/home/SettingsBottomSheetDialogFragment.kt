package com.severianfw.picto.view.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.severianfw.picto.databinding.BottomSheetSettingsBinding

class SettingsBottomSheetDialogFragment: BottomSheetDialogFragment() {

    private var bottomSheetBinding: BottomSheetSettingsBinding? = null
    private var isDarkMode: Boolean = false

    companion object {
        const val TAG = "SettingsBottomSheetFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bottomSheetBinding = BottomSheetSettingsBinding.inflate(inflater, container, false)
        return bottomSheetBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (checkDarkModeIsActive()) {
            bottomSheetBinding?.scToggleMode?.isChecked = true
        }
        bottomSheetBinding?.scToggleMode?.setOnCheckedChangeListener { button, _ ->
            if (button.isChecked) {
                isDarkMode = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                isDarkMode = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomSheetBinding = null
    }

    private fun checkDarkModeIsActive(): Boolean {
        val currentDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentDarkMode == Configuration.UI_MODE_NIGHT_YES
    }
}