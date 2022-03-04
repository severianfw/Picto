package com.severianfw.picto.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.severianfw.picto.PictoApplication
import com.severianfw.picto.R
import com.severianfw.picto.databinding.ActivityMainBinding
import com.severianfw.picto.utils.DarkModeUtil
import com.severianfw.picto.view.home.HomeFragment
import com.severianfw.picto.view.home.SettingsBottomSheetDialogFragment
import com.severianfw.picto.viewmodel.HomeViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as PictoApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setupToolbar()

        showHomeFragment()

        homeViewModel.getPhotos()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            showSettingsBottomSheet()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        if (DarkModeUtil.isDarkModeActive(resources)) {
            val item = menu.findItem(R.id.menu_settings)
            item.icon.setTint(ContextCompat.getColor(this, R.color.white_500))
        }
        return true
    }

    private fun showHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(activityMainBinding.fragmentContainer.id, HomeFragment())
            .commit()
    }

    private fun showSettingsBottomSheet() {
        if (isFinishing) {
            return
        }
        SettingsBottomSheetDialogFragment().show(
            supportFragmentManager,
            SettingsBottomSheetDialogFragment.TAG
        )
    }

    private fun setupToolbar() {
        setSupportActionBar(activityMainBinding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}