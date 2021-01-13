package com.rrr.shaket.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.rrr.shakemon.ShakemonManager
import com.rrr.shaket.R
import com.rrr.shaket.data.Result
import com.rrr.shaket.data.local.model.Pokemon
import com.rrr.shaket.databinding.FragmentSearchBinding
import com.rrr.shaket.ui.common.FavouriteAdapter
import com.rrr.shaket.ui.favourite.MyFavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Search screen UI
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object{
        val TAG: String = SearchFragment::class.java.simpleName
    }
    private val searchViewModel: SearchViewModel by viewModels()
    private val myFavouriteViewModel: MyFavouriteViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private lateinit var favouriteAdapter: FavouriteAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val historyMap = mutableMapOf<String, Pokemon>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupSearchView()
        setupSearchResult()
        setupRecyclerView()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() = with(binding.saveRecycler){
        favouriteAdapter = FavouriteAdapter(activity, mutableListOf(), myFavouriteViewModel)
        adapter =favouriteAdapter
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            val drawable = ContextCompat.getDrawable(context, R.drawable.divider_decorator)
            drawable?.run { setDrawable(this) }
        })
    }

    private fun setupSearchResult() {
        searchViewModel.searchResult.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> showProgress()
                is Result.Error -> {
                    hideProgress()
                    showError(result.exception)
                }
                is Result.Success -> {
                    hideProgress()
                    saveSearch(result.data)
                    displayUI(result.data)
                }
            }
        })
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.run {
                    searchViewModel.searchPokemon(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun hideProgress() {
        binding.progressView.visibility = View.GONE
    }

    private fun showProgress() {
        binding.progressView.visibility = View.VISIBLE
    }

    private fun showError(exception: Exception) {
        Log.d(TAG, exception.message?:"unable to fetch data")
        Snackbar.make(binding.root, exception.toString(), Snackbar.LENGTH_LONG).show()
    }

    private fun saveSearch(pokemon: Pokemon) {
        historyMap[pokemon.name] = pokemon
        favouriteAdapter.updateList(historyMap.values.toList())
    }

    private fun displayUI(pokemon: Pokemon) {
        activity?.let {
            ShakemonManager.INSTANCE.displayPokemon (
                activity = it,
                shakspeareText = pokemon.shakespeareDescription,
                pokemonSprite = pokemon.pokemonSprite
            )
        }
    }
}