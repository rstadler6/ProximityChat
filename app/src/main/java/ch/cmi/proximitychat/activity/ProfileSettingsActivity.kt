package ch.cmi.proximitychat.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ch.cmi.proximitychat.R

class ProfileSettingsActivity : AppCompatActivity() {
    private lateinit var icon: ImageView
    private lateinit var username: TextView
    private val prefs = getPreferences(Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        icon = findViewById(R.id.icon)
        username = findViewById(R.id.username)
    }

    fun back (view: View?) {
        finish()
    }

    fun changeIcon(view: View?) {

    }

    fun changeUsername(view: View?) {

    }
}