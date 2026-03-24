package com.example.retailapp.core.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

    private val cicerone: Cicerone<Router> = Cicerone.create(Router())

    @Provides
    @Singleton
    fun providesRouter(): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    fun providesNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

}