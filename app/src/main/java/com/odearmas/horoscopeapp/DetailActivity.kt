package com.odearmas.horoscopeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.odearmas.horoscopeapp.model.HoroscopeItem

class DetailActivity : AppCompatActivity() {

    private lateinit var favoriteIcon: ImageButton
    private val horoscopeList: List<HoroscopeItem> = HoroscopeItem.entries
    private var position: Int = 0
    private var favorite: Boolean = false
    private lateinit var favoriteMenuItem: MenuItem
    private lateinit var sessionManager: SessionManager

    //private val favoriteZodiac : ImageButton = findViewById(R.id.menu_detail_favorite_icon)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Recibir la petición desde activity_main y construir la vista de detalle
        val zodiacId = intent.getStringExtra("horoscope_id")
        if (zodiacId != null) {
            val zodiac = HoroscopeItem.fromId(zodiacId)
            //val zodiac = zodiacId?.let { HoroscopeItem.fromId(it) }
            if (zodiac != null) {
                findViewById<TextView>(R.id.selected_name_textView).text =
                    getString(zodiac.zodiacName)
                findViewById<TextView>(R.id.selected_date_textView).text = getString(zodiac.date)
                findViewById<ImageView>(R.id.selected_icon_imageView).setImageResource(zodiac.logo)
                //determinar cuál ítem de horóscopo se ha seleccionado mediante contador
                var i: Int = 0
                for (item in horoscopeList) {
                    (if (horoscopeList[i].id != zodiac.id) {
                        i++
                    } else {
                        break
                    })
                }
                position = i
            }
        }

        //sessionManager y favorito
        sessionManager = SessionManager(this)

        favorite = assertFavorite()

        favoriteIcon = findViewById<ImageButton>(R.id.detail_favorite_icon)

        //Implementar los botones de adelante y atrás
        val previousButton: Button = findViewById<Button>(R.id.activity_detail_previous_button)
        val nextButton: Button = findViewById<Button>(R.id.activity_detail_next_button)

        previousButton.setOnClickListener {
            if (position > 0) {
                position--
                val zodiac = horoscopeList[position]
                findViewById<TextView>(R.id.selected_name_textView).text =
                    getString(zodiac.zodiacName)
                findViewById<TextView>(R.id.selected_date_textView).text = getString(zodiac.date)
                findViewById<ImageView>(R.id.selected_icon_imageView).setImageResource(zodiac.logo)
                favorite = assertFavorite()
                setFavoriteIcon()
            }

        }
        nextButton.setOnClickListener {
            if (position < 11) {
                position++
                val zodiac = horoscopeList[position]
                findViewById<TextView>(R.id.selected_name_textView).text =
                    getString(zodiac.zodiacName)
                findViewById<TextView>(R.id.selected_date_textView).text = getString(zodiac.date)
                findViewById<ImageView>(R.id.selected_icon_imageView).setImageResource(zodiac.logo)
                favorite = assertFavorite()
                setFavoriteIcon()
            }
        }

        favoriteIcon.setOnClickListener {
            if (favorite) {
                sessionManager.setFavoriteHoroscope("")
            } else {
                sessionManager.setFavoriteHoroscope(horoscopeList[position].id)
            }
            favorite = !favorite
            setFavoriteIcon()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_detail_favorite_icon -> {
                if (favorite) {
                    sessionManager.setFavoriteHoroscope("")
                } else {
                    sessionManager.setFavoriteHoroscope(horoscopeList[position].id)
                }
                favorite = !favorite
                setFavoriteIcon()
                true
            }

            R.id.menu_detail_share -> {
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                sendIntent.setType("text/plain")

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    //Implementación del menú de vista detalle
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_detail, menu)
        favoriteMenuItem = menu.findItem(R.id.menu_detail_favorite_icon)
        favoriteIcon = findViewById(R.id.detail_favorite_icon)
        setFavoriteIcon()
        return true
    }

    //Colocar el ícono correspondiente para favorito o no favorito
    private fun setFavoriteIcon() {
        if (favorite) {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_selected)
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_selected)
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_border)
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_border)
        }
    }

    private fun assertFavorite(): Boolean {
            return sessionManager.getFavoriteHoroscope()!! == horoscopeList[position].id
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_detail_settings -> {
                Log.i("DetailMenúSettings", "Clicado el menú Settings")
                true
            }

            R.id.menu_detail_add -> {
                Log.i("DetailMenúAdd", "Clicado el menú Add")
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }*/
}