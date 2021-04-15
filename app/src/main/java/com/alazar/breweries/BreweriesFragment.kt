package com.alazar.breweries

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.alazar.breweries.api.ApiHelper
import com.alazar.breweries.api.ApiService
import com.alazar.breweries.data.Brewery
import com.alazar.breweries.databinding.FragmentBreweriesBinding
import com.alazar.breweries.di.ViewModelFactory
import com.alazar.breweries.di.qualifiers.ViewModelInjection
import com.alazar.breweries.recyclerview.BreweryAdapter
import com.alazar.breweries.recyclerview.RecyclerViewClickListener
import com.alazar.breweries.utils.Resource
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
        setHasOptionsMenu(true)
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

        viewModel.getBreweries().observe(requireActivity(), {
            displayData(it)
        })

    }

    private fun displayData(resource: Resource<List<Brewery>>) {
        Log.d(TAG, "STATUS ::: " + resource.status.toString())

        adapter.clearItems()

        if (resource.status == Status.SUCCESS || resource.status == Status.ERROR)
            binding.progress.visibility = View.INVISIBLE

        resource.data?.let { list ->
            if (list.isNotEmpty())
                adapter.setItems(list)
            else
                Toast.makeText(
                    requireContext(),
                    getString(R.string.nothing_to_show),
                    Toast.LENGTH_LONG
                )
                    .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        requireActivity().menuInflater.inflate(R.menu.options_menu, menu)
        val mSearch: MenuItem = menu.findItem(R.id.search)
        val mSearchView = mSearch.actionView as SearchView
        mSearchView.queryHint = getString(R.string.search)
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d(TAG, query)

                binding.progress.visibility = View.VISIBLE

                viewModel.searchBreweriesByName(query).observe(requireActivity(), {
                    displayData(it)
                })
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty())
                    viewModel.getBreweries().observe(requireActivity(), {
                        displayData(it)
                    })

                Log.d(TAG, newText)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
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
                .setNavigationBarColor(ContextCompat.getColor(requireActivity(), R.color.green_700))
                .setToolbarColor(ContextCompat.getColor(requireActivity(), R.color.green_700))
                .setSecondaryToolbarColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.green_700
                    )
                )
                .build()
            builder.setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_LIGHT, params)

            val customTabsIntent: CustomTabsIntent = builder.build()

            customTabsIntent.launchUrl(requireActivity(), Uri.parse(link))
        } else {
            Toast.makeText(requireContext(), getString(R.string.no_website), Toast.LENGTH_LONG)
                .show()
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}