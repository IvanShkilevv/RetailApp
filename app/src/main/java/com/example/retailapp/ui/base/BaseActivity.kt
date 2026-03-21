package com.example.retailapp.ui.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retailapp.RetailApp
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

abstract class BaseActivity : FragmentActivity() {

    abstract fun getFragmentContainerId(): Int

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetailApp.instance?.appComponent?.inject(this)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(AppNavigator(this, getFragmentContainerId()))
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

}