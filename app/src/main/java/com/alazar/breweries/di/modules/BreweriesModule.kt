package com.alazar.breweries.di.modules

import com.alazar.breweries.BreweriesFragment
import com.alazar.breweries.api.ApiHelper
import com.alazar.breweries.api.ApiService
import com.alazar.breweries.di.InjectionViewModelProvider
import com.alazar.breweries.viewmodel.BreweriesVM
import dagger.Module
import dagger.Provides
import com.alazar.breweries.di.qualifiers.ViewModelInjection
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class BreweriesModule {

    @Provides
    @ViewModelInjection
    fun provideBreweriesFragmentVM(
        fragment: BreweriesFragment,
        viewModelProvider: InjectionViewModelProvider<BreweriesVM>
    ): BreweriesVM = viewModelProvider.get(fragment)



    @Provides
    internal fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Reusable
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openbrewerydb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(interceptor)
        client.connectTimeout(20, TimeUnit.SECONDS)
        client.readTimeout(20, TimeUnit.SECONDS)
        return client.build()
    }
}