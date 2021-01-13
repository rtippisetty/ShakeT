package com.rrr.shaket.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.rrr.shaket.R
import com.rrr.shaket.databinding.FragmentMyfavouriteBinding
import com.rrr.shaket.ui.common.FavouriteAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * My favourites screen ui
 */
@AndroidEntryPoint
class MyFavouriteFragment : Fragment() {

    private val myFavouriteViewModel: MyFavouriteViewModel by viewModels()
    private var _binding: FragmentMyfavouriteBinding? = null
    private lateinit var favouriteAdapter: FavouriteAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyfavouriteBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() = with(binding.recyclerFavourites) {
        favouriteAdapter = FavouriteAdapter(activity, mutableListOf(), myFavouriteViewModel)
        adapter =favouriteAdapter
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            val drawable = ContextCompat.getDrawable(context, R.drawable.divider_decorator)
            drawable?.run { setDrawable(this) }
        })
    }

    /**
     * Configures UI for updates from viewmodel
     */
    private fun setupView() {
        myFavouriteViewModel.favouritePokemons.observe(viewLifecycleOwner, { pokemonList ->
            pokemonList?.let{
                favouriteAdapter.updateList(it)
            }
        })
    }
}