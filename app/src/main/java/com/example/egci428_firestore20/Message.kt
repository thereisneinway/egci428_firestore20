package com.example.egci428_firestore20

class Message(val id:String, val message:String,val rating:Float, val timeStamp: String) {
    constructor(): this("","",0f,"")
}