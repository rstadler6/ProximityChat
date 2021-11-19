package ch.cmi.proximitychat.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ch.cmi.proximitychat.R
import ch.cmi.proximitychat.model.AppDatabase
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.permissionx.guolindev.PermissionX

var db: AppDatabase? = null

private val PERMISSIONS = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.INTERNET)

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    private val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
            ) { _: Map<String, Boolean> ->
            }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "proximitychat"
        ).createFromAsset("database/proximitychat.db").allowMainThreadQueries().build()

        viewPager = findViewById(R.id.pager)
        viewPager.adapter = PagerAdapter(this)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "Chats" else "Devices"
        }.attach()


        Log.i("PERMISSION", "requesting permission")
        PermissionX.init(this)
            .permissions(PERMISSIONS)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    //Handling Action Bar button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        openProfileSettings()
        return true
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = 0
        }
    }

    private inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment = if (position == 0) ChatsFragment() else DevicesFragment()
    }

    private fun openProfileSettings() {
        val intent = Intent(this, ProfileSettingsActivity::class.java)
        startActivity(intent)
    }
}