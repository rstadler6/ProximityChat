package ch.cmi.proximitychat.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import ch.cmi.proximitychat.R
import ch.cmi.proximitychat.model.User
import java.io.ByteArrayOutputStream

class ProfileSettingsActivity : AppCompatActivity() {
    lateinit var currentIcon: Bitmap
    lateinit var currentUsername: String
    private lateinit var icon: ImageView
    private lateinit var username: TextView
    private var localUser: User? = null
    //private lateinit var prefs = getPreferences(Context.MODE_PRIVATE) TODO move to onCreate

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            currentIcon = result.data?.extras?.get("data") as Bitmap
            icon.setImageBitmap(currentIcon)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        icon = findViewById(R.id.icon)
        username = findViewById(R.id.username)
        localUser = db!!.userDao().findByMacAddress("local")

        if (localUser == null)
            localUser = User("local", "localUser", null)

        if (localUser!!.icon != null) {
            currentIcon = BitmapFactory.decodeByteArray(localUser!!.icon, 0, localUser!!.icon!!.size)
            icon.setImageBitmap(currentIcon)
        }

        currentUsername = localUser!!.username
        username.text = currentUsername
    }

    override fun onDestroy() {
        super.onDestroy()

        val stream = ByteArrayOutputStream()
        currentIcon.compress(Bitmap.CompressFormat.PNG, 90, stream)
        localUser!!.icon = stream.toByteArray()

        localUser!!.username = currentUsername

        db!!.userDao().updateAll(localUser!!)
    }

    fun back (view: View?) {
        finish()
    }

    fun changeIcon(view: View?) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(takePictureIntent)
    }

    fun changeUsername(view: View?) {
        currentUsername = username.text.toString()
    }
}