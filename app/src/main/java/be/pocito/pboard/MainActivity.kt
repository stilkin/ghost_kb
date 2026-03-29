package be.pocito.pboard

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.enable_keyboard_button).setOnClickListener { openInputMethodSettings() }
        findViewById<Button>(R.id.select_keyboard_button).setOnClickListener { openInputMethodSettings() }
        findViewById<TextView>(R.id.kofi_link).setOnClickListener { openKofiPage() }
    }

    @Suppress("SwallowedException")
    private fun openInputMethodSettings() {
        try {
            startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "Could not open settings. Please enable PBoard in Settings > Languages & input > Virtual keyboard",
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    @Suppress("SwallowedException")
    private fun openKofiPage() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://ko-fi.com/stilkin")))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Could not open Ko-fi page", Toast.LENGTH_SHORT).show()
        }
    }
}
