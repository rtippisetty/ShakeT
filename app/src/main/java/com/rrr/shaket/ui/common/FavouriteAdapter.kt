package com.rrr.shaket.ui.common

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.rrr.shakemon.ShakemonManager
import com.rrr.shaket.R
import com.rrr.shaket.data.local.model.Pokemon
import com.rrr.shaket.databinding.FavouriteItemBinding
import com.rrr.shaket.ui.favourite.MyFavouriteViewModel

/**
 * Common Adapter to display the list of favourite pokemons in recycler view
 */
class FavouriteAdapter(
    private val activity: FragmentActivity?,
    private var favouriteList: MutableList<Pokemon>,
    private val favouriteViewModel: MyFavouriteViewModel):
    RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    private val TAG = FavouriteAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FavouriteItemBinding.inflate(layoutInflater)
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(favouriteList[position])
    }

    /**
     * returns the count of pokemons in the list
     */
    override fun getItemCount(): Int {
        return favouriteList.size
    }

    /**
     * @param newList new list of pokemons to display
     */
    fun updateList(newList: List<Pokemon>) {
        favouriteList.clear()
        favouriteList.addAll(newList)
        notifyDataSetChanged()
    }

    /**
     * Favourite item view holder to display favourite pokemon and option to save or remove from favourites
     */
    inner class FavouriteViewHolder(private val favouriteItemBinding: FavouriteItemBinding)
        :RecyclerView.ViewHolder(favouriteItemBinding.root){
        /**
         * @param pokemon data object to bind to the display view
         */
        fun bind(pokemon: Pokemon) = with(favouriteItemBinding) {
                pokemonName.text = pokemon.name
                icFavourite.setImageDrawable(getFavouriteDrawable(pokemon.isFavourite))
                // favourite image click action to save or remove from favourites
                icFavourite.setOnClickListener {
                    val tag = icFavourite.tag
                    when(tag){
                        // Save it to favourites
                        "false" -> {
                            Log.d(TAG, "Pokemon save to favourites initiated..")
                            favouriteViewModel.saveFavourite(pokemon)
                            icFavourite.setImageDrawable(getFavouriteDrawable(true))
                            icFavourite.tag = "true"
                        }
                        "true" -> {
                            Log.d(TAG, "Pokemon remove from favourites initiated..")
                            favouriteViewModel.removeFavourite(pokemon)
                            icFavourite.setImageDrawable(getFavouriteDrawable(false))
                            icFavourite.tag = "false"
                        }
                    }
                }

                // Display build-in UI component with favourite pokemon
                itemView.setOnClickListener {
                    activity?.let {
                        ShakemonManager.INSTANCE.displayPokemon(
                            activity = it,
                            pokemonSprite = pokemon.pokemonSprite,
                            shakspeareText = pokemon.shakespeareDescription
                        )
                    }
                }
            }

        /**
         * helper method to get favourite saved or not icon
         */
        private fun getFavouriteDrawable(isFavourite: Boolean) =
            if(isFavourite) {
                ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.ic_favorite_selected_24)
            } else {
                ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.ic_favorite_border_24)
            }
    }
}