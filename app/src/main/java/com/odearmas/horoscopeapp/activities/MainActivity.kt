package com.odearmas.horoscopeapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.odearmas.horoscopeapp.adapters.HoroscopeAdapter
import com.odearmas.horoscopeapp.R
import com.odearmas.horoscopeapp.utils.SessionManager
import com.odearmas.horoscopeapp.data.HoroscopeItem

class MainActivity : AppCompatActivity() {

    private var horoscopeList: List<HoroscopeItem> = HoroscopeItem.entries
    private lateinit var adapter: HoroscopeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        sessionManager = SessionManager(this)
        recyclerView = findViewById(R.id.activity_main_recyclerView)
        adapter = HoroscopeAdapter(horoscopeList,sessionManager) { position ->
            navigateToDetail(horoscopeList[position])
        }

        recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)



    }
    override fun onResume() {
        super.onResume()
        adapter.updateData(horoscopeList)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        val searchViewItem = menu.findItem(R.id.menu_search)
        val searchView = searchViewItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.updateData(horoscopeList.filter {
                        getString(it.zodiacName).contains(
                            newText,
                            true
                        )
                    })
                }
                return true
            }

        })
        return true
    }




    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_main_settings -> {
                Log.i("MenúSettings", "Clicado el menú Settings")
                true
            }

            R.id.menu_main_add -> {
                Log.i("MenúAdd", "Clicado el menú Add")
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }*/

    fun navigateToDetail(horoscopeItem: HoroscopeItem) {
        val callDetail: Intent = Intent(this, DetailActivity::class.java)
        callDetail.putExtra("horoscope_id", horoscopeItem.id)
        startActivity(callDetail)
    }
}
