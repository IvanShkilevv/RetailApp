package com.example.retailapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.retailapp.di.AppComponent
import com.example.retailapp.di.AppModule
import com.example.retailapp.di.DaggerAppComponent

class RetailApp : Application(), Application.ActivityLifecycleCallbacks {

    var appComponent: AppComponent? = null
        private set

    companion object {
        var instance: RetailApp? = null
            private set
    }

    @SuppressLint("ThrowableNotAtBeginning")
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this, this)).build()
        instance = this
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        return
    }

    override fun onActivityStarted(activity: Activity) {
        return
    }

    override fun onActivityResumed(activity: Activity) {
        return
    }

    override fun onActivityPaused(activity: Activity) {
        return
    }

    override fun onActivityStopped(activity: Activity) {
        return
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        return
    }

    override fun onActivityDestroyed(activity: Activity) {
        return
    }

}