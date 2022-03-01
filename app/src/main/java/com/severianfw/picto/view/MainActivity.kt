package com.severianfw.picto.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.severianfw.picto.PictoApplication
import com.severianfw.picto.R
import com.severianfw.picto.databinding.ActivityMainBinding
import com.severianfw.picto.databinding.BottomSheetBinding
import com.severianfw.picto.view.home.HomeFragment
import com.severianfw.picto.viewmodel.HomeViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var bottomSheetBinding: BottomSheetBinding

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as PictoApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        supportFragmentManager.beginTransaction()
            .replace(activityMainBinding.fragmentContainer.id, HomeFragment())
            .commit()

        checkDarkModeIsActive()
        homeViewModel.isDarkMode.observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }
    }

    private fun checkDarkModeIsActive(): Boolean {
        val currentDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return if (currentDarkMode == Configuration.UI_MODE_NIGHT_YES) {
            homeViewModel.setDarkMode(true)
            true
        } else {
            homeViewModel.setDarkMode(false)
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_settings){
            // start bottom sheet
            val dialog = BottomSheetDialog(this)
            bottomSheetBinding = BottomSheetBinding.inflate(layoutInflater)
            dialog.setContentView(bottomSheetBinding.root)
            if (checkDarkModeIsActive()) {
                bottomSheetBinding.switchMode.isChecked = true
            }
            bottomSheetBinding.switchMode.setOnCheckedChangeListener { button, _ ->
                if (button.isChecked) {
                    homeViewModel.setDarkMode(true)
                } else {
                    homeViewModel.setDarkMode(false)
                }
            }
            dialog.show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        if (checkDarkModeIsActive()) {
            val item = menu.findItem(R.id.menu_settings)
            item.icon = ContextCompat.getDrawable(this, R.drawable.ic_settings_white)
        }
        return true
    }
}