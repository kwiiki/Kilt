package com.example.kilt.otp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


class SmsBroadcastReceiver : BroadcastReceiver() {
    var smsBroadcastReceiverListener: SmsBroadcastReceiverListener? = null

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("SMS_RETRIEVER", "BroadcastReceiver onReceive called with action: ${intent.action}")
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val status = extras?.get(SmsRetriever.EXTRA_STATUS) as? Status

            Log.d("SMS_RETRIEVER", "Status: ${status?.statusCode}")

            when (status?.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val message = extras.getString(SmsRetriever.EXTRA_SMS_MESSAGE)
                    Log.d("SMS_RETRIEVER", "SMS received: $message")
                    smsBroadcastReceiverListener?.onSuccess(message)
                }
                CommonStatusCodes.TIMEOUT -> {
                    Log.d("SMS_RETRIEVER", "SMS retrieval timed out")
                    smsBroadcastReceiverListener?.onFailure()
                }
                else -> {
                    Log.d("SMS_RETRIEVER", "SMS retrieval failed with status: ${status?.statusCode}")
                    smsBroadcastReceiverListener?.onFailure()
                }
            }
        } else {
            Log.d("SMS_RETRIEVER", "Received intent with unexpected action: ${intent.action}")
        }
    }

    interface SmsBroadcastReceiverListener {
        fun onSuccess(message: String?)
        fun onFailure()
    }
}
