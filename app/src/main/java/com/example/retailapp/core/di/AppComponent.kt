package com.example.retailapp.core.di

import com.example.retailapp.core.base.BaseActivity
import com.example.retailapp.core.base.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NavigationModule::class, StorageModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
    fun inject(baseFragment: BaseFragment)
}