package com.example.retailapp.core.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.retailapp.app.RetailApp
import com.github.terrakok.cicerone.Screen
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View

    protected fun navigateTo(screen: Screen) {
        (activity as? BaseActivity)?.router?.navigateTo(screen)
    }

    protected fun back() {
        (activity as? BaseActivity)?.router?.exit()
    }

    override fun onAttach(context: Context) {
        RetailApp.instance?.appComponent?.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getRootView(inflater = inflater, container = container)
    }


    protected fun getDimen(@DimenRes dimenRes: Int): Int {
        return resources.getDimensionPixelOffset(dimenRes)
    }

    protected fun getDrawable(@DrawableRes drawableRes: Int): Drawable? {
        return ContextCompat.getDrawable(requireContext(), drawableRes)
    }

}


