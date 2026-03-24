package com.example.retailapp.app

import android.os.Bundle
import com.example.retailapp.R
import com.example.retailapp.feature.common.navigation.Screens
import com.example.retailapp.core.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        router.newRootScreen(Screens.products())
    }

    override fun getFragmentContainerId(): Int {
        return R.id.fragment_container
    }

}