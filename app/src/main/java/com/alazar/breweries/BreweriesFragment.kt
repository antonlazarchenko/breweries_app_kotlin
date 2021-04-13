package com.alazar.breweries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.alazar.breweries.api.ApiHelper
import com.alazar.breweries.api.ApiService
import com.alazar.breweries.databinding.FragmentBreweriesBinding
import com.alazar.breweries.di.ViewModelFactory
import com.alazar.breweries.di.qualifiers.ViewModelInjection
import com.alazar.breweries.viewmodel.BreweriesVM
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BreweriesFragment : DaggerFragment() {

    private var _binding: FragmentBreweriesBinding? = null

    private val binding get() = _binding!!

    @Inject
    @ViewModelInjection
    lateinit var viewModel: BreweriesVM

    @Inject
    lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreweriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(apiService))
        ).get(BreweriesVM::class.java)

        viewModel.getUsers().observe(requireActivity(), {
            Log.d("DATA", it.status.toString())
            it.data?.map { brewery ->
                println(brewery)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}