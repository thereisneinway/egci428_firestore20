package com.example.egci428_firestore20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    lateinit var dataReference: FirebaseFirestore
    lateinit var editText: EditText
    lateinit var submitBtn: Button
    lateinit var ratingBar: RatingBar
    lateinit var msgList: MutableList<Message>
    lateinit var adapter: MessageAdapter
    lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        submitBtn = findViewById(R.id.submitBtn)
        ratingBar = findViewById(R.id.ratingBar)
        listView = findViewById(R.id.listView)

        msgList = mutableListOf()

        dataReference = FirebaseFirestore.getInstance()

        submitBtn.setOnClickListener {
            submitData()
            readFirestoreData()
        }
        readFirestoreData()
    }

    private fun submitData(){
        val msg = editText.text.toString()
        if(msg.isEmpty()){
            editText.error = "Please submit a message"
            return
        }

        var db = dataReference.collection("dataMessage")
        val messageId = db.document().id
        val messageData = Message(messageId, msg, ratingBar.rating.toFloat(), System.currentTimeMillis().toString())

        db.add(messageData)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Message is saved successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(applicationContext, "Fail to save message", Toast.LENGTH_SHORT).show()
            }
    }

    private fun readFirestoreData(){
        var db = dataReference.collection("dataMessage")
        db.orderBy("timeStamp").get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null){
                    msgList.clear()
                    val messages = snapshot.toObjects(Message::class.java)
                    for (message in messages){
                        msgList.add(message)
                    }
                    adapter = MessageAdapter(applicationContext, R.layout.message, msgList)
                    listView.adapter = adapter
                    //Log.d("Firestore Read", messages.toString())
                }

            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Fail to read messages from Firestore", Toast.LENGTH_SHORT).show()
            }

    }
}