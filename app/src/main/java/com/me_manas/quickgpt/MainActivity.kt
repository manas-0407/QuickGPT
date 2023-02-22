package com.me_manas.quickgpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var  recycler_view : RecyclerView
    lateinit var start_message : TextView
    lateinit var enter_message : EditText
    lateinit var send_button : ImageButton
    lateinit var messageList : ArrayList<Message_Chat>
    lateinit var Message_aAdapter : MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view = findViewById(R.id.recycler_view)
        start_message = findViewById(R.id.start_chat)
        enter_message = findViewById(R.id.enter_text)
        send_button = findViewById(R.id.sendButton)

         // set up recycler view
        messageList = ArrayList()
        Message_aAdapter = MessageAdapter(messageList)
        recycler_view.adapter = Message_aAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)


        send_button.setOnClickListener {
            val text = enter_message.text.toString().trim()
            addToMessageList(text,"me")
            enter_message.setText("")
            start_message.visibility = View.GONE
        }
    }

    fun addToMessageList(message : String, sentBy : String) {
        this@MainActivity.runOnUiThread(Runnable {
            messageList.add(Message_Chat(message,sentBy))
            Message_aAdapter.notifyItemChanged(Message_aAdapter.itemCount)      //mark this part for message orientation
            recycler_view.smoothScrollToPosition(Message_aAdapter.itemCount)
        })

    }
}
