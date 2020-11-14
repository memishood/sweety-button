package net.memish.sweetybuttonsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.memish.sweetybutton.setOnEndRecordListener
import net.memish.sweetybutton.setOnStartRecordListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sweetyButton.setOnStartRecordListener {
            Toast.makeText(this, "onRecording..", Toast.LENGTH_SHORT).show()
        }

        sweetyButton.setOnEndRecordListener {
            Toast.makeText(this, "onStopRecording", Toast.LENGTH_SHORT).show()
        }
    }
}