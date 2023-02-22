package com.me_manas.quickgpt

import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


public class MessageAdapter(val messageList : ArrayList<Message_Chat>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.chat_item,null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val msg : Message_Chat = messageList.get(position)
        if(msg.sentBy == msg.SentByMe){
//            sent by me
            holder.leftLinearLayout.visibility = View.GONE
            holder.rightLinearLayout.visibility = View.VISIBLE
            holder.rightchat.text = msg.message

        }
        else{
            // sent by bot
            holder.leftLinearLayout.visibility = View.VISIBLE
            holder.rightLinearLayout.visibility = View.VISIBLE
            holder.leftchat.text = msg.message
        }
    }

}
class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val leftLinearLayout: LinearLayout
    val rightLinearLayout: LinearLayout
    val leftchat : TextView
    val rightchat : TextView
    init {
        leftLinearLayout = itemView.findViewById(R.id.left_chat_linearL)
        rightLinearLayout = itemView.findViewById(R.id.right_chat_linearL)
        leftchat = itemView.findViewById<TextView>(R.id.bot_text)
        rightchat = itemView.findViewById<TextView>(R.id.human_text)
    }
}