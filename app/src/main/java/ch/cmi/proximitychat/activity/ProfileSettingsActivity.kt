package ch.cmi.proximitychat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ch.cmi.proximitychat.R

class ProfileSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)
    }

    fun back (view: View?) {
        finish()
    }

    fun changePicture(view: View?) {

    }

    fun changeUsername(view: View?) {

    }
}