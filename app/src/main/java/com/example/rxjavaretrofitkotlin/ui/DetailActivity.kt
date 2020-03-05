package com.example.rxjavaretrofitkotlin.ui

import android.os.Bundle
import android.util.Log.d
import androidx.core.net.toUri
import com.example.rxjavaretrofitkotlin.R
import com.example.rxjavaretrofitkotlin.model.Row
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle: Bundle? = intent.extras
        val title: String? = bundle?.getString("mainTitle")
        val rowItems: Row? = bundle?.get("itemRows") as Row

        d(
            "DetailActivity",
            "Arguments  title : $title  and row items Title : ${rowItems?.title}  Row Item description : ${rowItems?.description}  Row Image URL : ${rowItems?.imageHref}"
        )

        main_title.text = title.toString()
        subTitle_value.text = rowItems?.title
        description_value.text = rowItems?.description

        val imageURL = rowItems?.imageHref
        if (imageURL.isNullOrEmpty() || imageURL.isNullOrBlank()) {
            profile.setImageResource(R.drawable.ic_broken_image)
        } else {
            val imgUri = imageURL.toUri().buildUpon().scheme("https").build()
            Picasso.get()
                .load(imgUri)
                .error(R.drawable.ic_broken_image)
                .placeholder(R.drawable.loading_animation)
                .into(profile, object : Callback {
                    override fun onSuccess() {
                        d("DetailActivity", "Picasso image loading success")
                    }

                    override fun onError(e: Exception?) {
                        d("DetailActivity", "Picasso image loading error : ${e?.message}")
                    }
                })
        }
    }
}