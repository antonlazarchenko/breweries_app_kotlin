package com.alazar.breweries.di

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        getComponent().inject(this)

    }

    companion object {
        lateinit var appComponent: AppComponent

        fun getComponent(): AppComponent {
            if (!::appComponent.isInitialized) {
                appComponent = DaggerAppComponent
                    .builder()
                    .application(this)
                    .build()
            }
            return appComponent
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = activityInjector
}