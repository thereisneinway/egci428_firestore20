package com.example.egci428_firestore20

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MessageAdapter(val mContext: Context, val layoutResId: Int, val messageList: List<Message> ): ArrayAdapter<Message>(mContext, layoutResId, messageList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(layoutResId, null)
        val msgTextView = view.findViewById<TextView>(R.id.msgView)
        val ratTextView = view.findViewById<TextView>(R.id.ratView)
        val idTextView = view.findViewById<TextView>(R.id.idView)
        msgTextView.text = "Message:" + messageList[position].message
        idTextView.text = messageList[position].id
        ratTextView.text = messageList[position].rating.toString()
        return view
    }
}