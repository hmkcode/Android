package com.hmkcode.intent_action_send

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class ReceivingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiving)

        val editText = findViewById<EditText>(R.id.editText)


        if(intent?.action == Intent.ACTION_SEND)
            editText.setText(intent.getStringExtra(Intent.EXTRA_TEXT))

    }
}
