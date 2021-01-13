package com.rrr.shakemon.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RestrictTo
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.rrr.shakemon.R
import com.squareup.picasso.Picasso

/**
 * Shared UI component to display Pokemon's shakespearean description and it's sprite
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
class ShakemonActivity: AppCompatActivity() {

    companion object{
        private const val SHAKESPEARE_TEXT = "shakespeare text"
        private const val POKEMON_SPRITE = "pokemon sprite"
        fun getArgBundle(shakespeareText: String, pokemonSprite: String) =
            Bundle().apply {
                putString(SHAKESPEARE_TEXT, shakespeareText)
                putString(POKEMON_SPRITE, pokemonSprite)
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shakespeareText = intent.extras?.getString(SHAKESPEARE_TEXT)
        val pokemonSprite = intent.extras?.getString(POKEMON_SPRITE)
        displayDialog(shakespeareText!!, pokemonSprite!!)
    }

    private fun displayDialog(shakespeareText: String, sprite: String) {
        val translatedLayout = LayoutInflater.from(applicationContext).inflate(R.layout.translated_layout, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(translatedLayout)
            .setPositiveButton(R.string.dimiss) { _, _ -> finish() }
            .create()

        translatedLayout.findViewById<TextView>(R.id.text_translated).text = shakespeareText

        alertDialog.setCancelable(false)
        alertDialog.show()
        loadIcon(translatedLayout.findViewById(R.id.image_sprite), sprite)
    }

    private fun loadIcon(targetView: ImageView, imageUrl: String) {
        val picasso = Picasso.get()
        picasso.isLoggingEnabled = true
        picasso
            .load(imageUrl)
            .error(R.drawable.cloud_off)
            .placeholder(R.drawable.cloud_download)
            .into(targetView)
    }
}