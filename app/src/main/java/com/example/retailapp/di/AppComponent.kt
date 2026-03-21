package com.example.retailapp.di

import com.example.retailapp.ui.base.BaseActivity
import com.example.retailapp.ui.base.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NavigationModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
    fun inject(baseFragment: BaseFragment)
}