package com.alazar.breweries

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.alazar.breweries.api.ApiHelper
import com.alazar.breweries.api.ApiService
import com.alazar.breweries.data.BreweryAdapter
import com.alazar.breweries.data.RecyclerViewClickListener
import com.alazar.breweries.databinding.FragmentBreweriesBinding
import com.alazar.breweries.di.ViewModelFactory
import com.alazar.breweries.di.qualifiers.ViewModelInjection
import com.alazar.breweries.utils.Status
import com.alazar.breweries.viewmodel.BreweriesVM
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BreweriesFragment : DaggerFragment(), RecyclerViewClickListener {

    companion object {
        private val TAG = BreweriesFragment::class.simpleName
    }

    private var _binding: FragmentBreweriesBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: BreweryAdapter

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

        initRecyclerView()

        binding.progress.visibility = View.VISIBLE

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(apiService))
        ).get(BreweriesVM::class.java)

        viewModel.getUsers().observe(requireActivity(), {
            Log.d(TAG, "STATUS ::: " + it.status.toString())

            if (it.status == Status.SUCCESS || it.status == Status.ERROR)
                binding.progress.visibility = View.INVISIBLE

            it.data?.let { list -> adapter.setItems(list) }
        })

    }


    private fun initRecyclerView() {
        val recyclerView = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        adapter = BreweryAdapter(this)
        recyclerView.adapter = adapter
    }


    override fun recyclerViewListClicked(link: String, v: View, position: Int) =
        if (link.isNotEmpty()) {

            val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()

            builder.setStartAnimations(
                requireActivity(),
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            builder.setExitAnimations(
                requireActivity(),
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )

            val params: CustomTabColorSchemeParams = CustomTabColorSchemeParams.Builder()
                .setNavigationBarColor(ContextCompat.getColor(requireActivity(), R.color.purple_700))
                .setToolbarColor(ContextCompat.getColor(requireActivity(), R.color.purple_700))
                .setSecondaryToolbarColor(ContextCompat.getColor(requireActivity(), R.color.purple_500))
                .build()
            builder.setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_LIGHT, params)

            val customTabsIntent: CustomTabsIntent = builder.build()

            customTabsIntent.launchUrl(requireActivity(), Uri.parse(link))
        } else {
            Toast.makeText(requireContext(), getString(R.string.no_website), Toast.LENGTH_SHORT)
                .show()
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}