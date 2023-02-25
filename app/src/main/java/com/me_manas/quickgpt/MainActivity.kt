package com.me_manas.quickgpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*

import okhttp3.RequestBody.Companion.toRequestBody

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var  recycler_view : RecyclerView
    lateinit var start_message : TextView
    lateinit var enter_message : EditText
    lateinit var send_button : ImageButton
    lateinit var messageList : ArrayList<Message_Chat>
    lateinit var Message_aAdapter : MessageAdapter
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageList  = ArrayList()
        recycler_view = findViewById(R.id.recycler_view)
        start_message = findViewById(R.id.start_chat)
        enter_message = findViewById(R.id.enter_text)
        send_button = findViewById(R.id.sendButton)

         // set up recycler view
        Message_aAdapter = MessageAdapter(messageList)
        recycler_view.adapter = Message_aAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)


        send_button.setOnClickListener {
            val text = enter_message.text.toString().trim()
            addToMessageList(text,"me")
            apiCall(text)
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

    fun addToMessageResponse(repsonse: String){
        addToMessageList(repsonse,"bot")
    }

    fun apiCall(question:String){

        val jsonBody = JSONObject()
        try {
            jsonBody.put("model", "text-davinci-003")
            jsonBody.put("prompt", question)
            jsonBody.put("max_tokens", "4000")
            jsonBody.put("temperature", 0.6)
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }

        val body = jsonBody.toString().toRequestBody(JSON)

        val headers = Headers.Builder()
            .add("Authorization", "Bearer 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")
        val request : Request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .headers(headers.build())
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                // Handle the failure
                addToMessageResponse("Failed to load response due to(onFailure) "+e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                // Handle the response
                if(response.isSuccessful){
                    try {
                        val jsonObject = JSONObject(response.body.toString())  // .string()
                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result = jsonArray.getJSONObject(0).getString("text")
                        addToMessageResponse(result.trim())
                    }catch (e : java.lang.Exception) {
                        e.printStackTrace()
                    }
                } else{
                    addToMessageResponse("Failed to load response due to "+response.body.toString())
                }
            }
        })
    }
}
