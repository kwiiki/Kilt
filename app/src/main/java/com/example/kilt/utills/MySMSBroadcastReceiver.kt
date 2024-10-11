package com.example.kilt.utills

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


//class MySMSBroadcastReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent) {
//        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
//            val extras = intent.extras
//            val status: Status? = extras!![SmsRetriever.EXTRA_STATUS] as Status?
//
//            when (status?.getStatusCode()) {
//                CommonStatusCodes.SUCCESS -> {
//                    // (Optional) Get SMS Sender address - only available in
//                    // GMS version 24.20 onwards, else it will return null
//                    val senderAddress = extras.getString(SmsRetriever.EXTRA_SMS_ORIGINATING_ADDRESS)
//                    // Get SMS message contents
//                    val message = extras.getString(SmsRetriever.EXTRA_SMS_MESSAGE)
//                }
//
//                CommonStatusCodes.TIMEOUT -> {}
//            }
//        }
//    }
//}