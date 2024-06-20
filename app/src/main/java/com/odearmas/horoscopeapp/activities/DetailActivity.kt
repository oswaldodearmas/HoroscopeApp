package com.odearmas.horoscopeapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.odearmas.horoscopeapp.R
import com.odearmas.horoscopeapp.utils.SessionManager
import com.odearmas.horoscopeapp.data.HoroscopeItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailActivity : AppCompatActivity() {

    private lateinit var favoriteIcon: ImageButton
    private val horoscopeList: List<HoroscopeItem> = HoroscopeItem.entries
    private var position: Int = 0
    private var favorite: Boolean = false
    private lateinit var favoriteMenuItem: MenuItem
    private lateinit var sessionManager: SessionManager
    private lateinit var progressBar: ProgressBar


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

        progressBar = findViewById(R.id.progressBar)

        //Recibir la petición desde activity_main y construir la vista de detalle
        val zodiacId = intent.getStringExtra("horoscope_id")
        if (zodiacId != null) {
            val zodiac = HoroscopeItem.fromId(zodiacId)
            //val zodiac = zodiacId?.let { HoroscopeItem.fromId(it) }
            if (zodiac != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true) //Flecha de ir atrásf
                findViewById<TextView>(R.id.selected_name_textView).text =
                    getString(zodiac.zodiacName)
                findViewById<TextView>(R.id.selected_date_textView).text = getString(zodiac.date)
                findViewById<ImageView>(R.id.selected_icon_imageView).setImageResource(zodiac.logo)
                getDailyHoroscope()

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
                getDailyHoroscope()
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
                getDailyHoroscope()
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

//Llamada a la API para traer el horóscopo diario
    fun getDailyHoroscope() {

    runOnUiThread {
        progressBar.visibility = ProgressBar.VISIBLE
    }
// Llamada en hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url =
                    URL("https://horoscope-app-api.vercel.app/api/v1/get-horoscope/daily?sign=${horoscopeList[position].id}&day=TODAY")
                val connection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"
                val responseCode = connection.responseCode
                Log.i("HTTP", "Response Code = $responseCode")
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                    var inputLine: String?
                    val response = StringBuffer()
                    while (bufferedReader.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    bufferedReader.close()

                    //parsear el JSON
                    val jsonResponse = JSONObject(response.toString())
                    val result = jsonResponse.getJSONObject("data").getString("horoscope_data")

                    runOnUiThread {
                        progressBar.visibility = ProgressBar.GONE
                        findViewById<TextView>(R.id.daily_horoscope_textView).text = result
                    }

                } else { // Hubo un error
                    Log.w("HTTP", "Response :: Hubo un error")
                }
            } catch (e: Exception) {
                Log.e("HTTP", "Response Error :: ${e.stackTraceToString()}")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            android.R.id.home -> {
                finish()
                true
            }

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
                sendIntent.putExtra(Intent.EXTRA_TEXT, findViewById<TextView>(R.id.daily_horoscope_textView).text.toString())
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
            favoriteIcon.setImageResource(R.drawable.ic_favorite_border_black)
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_border)
        }
    }

    private fun assertFavorite(): Boolean {
        if(sessionManager.getFavoriteHoroscope()!=null){
        return sessionManager.getFavoriteHoroscope() == horoscopeList[position].id}
        return false
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