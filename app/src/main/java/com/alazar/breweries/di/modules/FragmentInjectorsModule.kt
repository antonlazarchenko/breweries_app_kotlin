package com.alazar.breweries.di.modules

import com.alazar.breweries.BreweriesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjectorsModule {

    @ContributesAndroidInjector(modules = [BreweriesModule::class])
    abstract fun breweriesFragmentFragmentInjector(): BreweriesFragment
}