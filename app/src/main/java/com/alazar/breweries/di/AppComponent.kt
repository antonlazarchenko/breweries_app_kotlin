package com.alazar.breweries.di

import com.alazar.breweries.base.NetworkProvider
import com.alazar.breweries.di.modules.ActivityInjectorsModule
import com.alazar.breweries.di.modules.FragmentInjectorsModule
import com.alazar.breweries.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityInjectorsModule::class,
        FragmentInjectorsModule::class,
        AppModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App.Companion): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
    fun inject(networkProvider: NetworkProvider)
}