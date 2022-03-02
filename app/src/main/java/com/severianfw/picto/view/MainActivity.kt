package com.severianfw.picto.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.severianfw.picto.PictoApplication
import com.severianfw.picto.R
import com.severianfw.picto.databinding.ActivityMainBinding
import com.severianfw.picto.view.home.HomeFragment
import com.severianfw.picto.view.home.SettingsBottomSheetDialogFragment
import com.severianfw.picto.viewmodel.HomeViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private var isDarkMode: Boolean = false

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as PictoApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setupToolbar()

        supportFragmentManager.beginTransaction()
            .replace(activityMainBinding.fragmentContainer.id, HomeFragment())
            .commit()

        isDarkMode = checkDarkModeIsActive()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            showSettingsBottomSheet()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        if (checkDarkModeIsActive()) {
            val item = menu.findItem(R.id.menu_settings)
            item.icon.setTint(ContextCompat.getColor(this, R.color.white_500))
        }
        return true
    }

    private fun showSettingsBottomSheet() {
        SettingsBottomSheetDialogFragment().show(
            supportFragmentManager,
            SettingsBottomSheetDialogFragment.TAG
        )
    }

    private fun setupToolbar() {
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun checkDarkModeIsActive(): Boolean {
        val currentDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentDarkMode == Configuration.UI_MODE_NIGHT_YES
    }
}