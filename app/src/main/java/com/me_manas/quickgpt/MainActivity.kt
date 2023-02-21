package com.me_manas.quickgpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var  recycler_view : RecyclerView
    lateinit var start_message : TextView
    lateinit var enter_message : EditText
    lateinit var send_button : ImageButton
    lateinit var messageList : MutableList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view = findViewById(R.id.recycler_view)
        start_message = findViewById(R.id.start_chat)
        enter_message = findViewById(R.id.enter_text)
        send_button = findViewById(R.id.sendButton)

        send_button.setOnClickListener {
            var text = enter_message.text.toString().trim()
            Toast.makeText(this,text,Toast.LENGTH_LONG).show()
        }
    }
}
