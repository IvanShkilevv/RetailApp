package com.example.retailapp.ui.main

import android.os.Bundle
import com.example.retailapp.R
import com.example.retailapp.navigation.Screens
import com.example.retailapp.ui.base.BaseActivity

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