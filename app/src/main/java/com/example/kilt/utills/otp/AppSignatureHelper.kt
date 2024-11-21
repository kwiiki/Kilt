package com.example.kilt.utills.otp

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays

class AppSignatureHelper(private val context: Context) {
    fun getAppSignatures(): ArrayList<String> {
        val appSignatures = ArrayList<String>()
        try {
            val packageName = context.packageName
            val packageManager = context.packageManager
            val signatures =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures
            for (signature in signatures) {
                val hash = hash(packageName, signature.toCharsString())
                if (hash != null) {
                    appSignatures.add(String.format("%s", hash))
                    Log.d("AppSignature", "App Signature: $hash")
                }
            }
        } catch (e: Exception) {
            Log.e("AppSignature", "Error getting app signatures", e)
        }
        return appSignatures
    }

    private fun hash(packageName: String, signature: String): String? {
        val appInfo = "$packageName $signature"
        try {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            messageDigest.update(appInfo.toByteArray(StandardCharsets.UTF_8))
            val hashSignature = messageDigest.digest()

            val hashBase64 =
                Base64.encodeToString(hashSignature, Base64.NO_WRAP or Base64.NO_PADDING)
            return hashBase64.substring(0, 11)
        } catch (e: NoSuchAlgorithmException) {
            Log.e("AppSignature", "Hash error", e)
        }
        return null
    }

    companion object {
        val TAG = AppSignatureHelper::class.java.simpleName
        private const val HASH_TYPE = "SHA-256"
        private const val NUM_HASHED_BYTES = 9
        private const val NUM_BASE64_CHAR = 11
        private fun hash(packageName: String, signature: String): String? {
            val appInfo = "$packageName $signature"
            try {
                val messageDigest = MessageDigest.getInstance(HASH_TYPE)
                messageDigest.update(appInfo.toByteArray(StandardCharsets.UTF_8))
                var hashSignature = messageDigest.digest()

                // truncated into NUM_HASHED_BYTES
                hashSignature = Arrays.copyOfRange(hashSignature, 0, NUM_HASHED_BYTES)
                // encode into Base64
                var base64Hash: String =
                    Base64.encodeToString(hashSignature, Base64.NO_PADDING or Base64.NO_WRAP)
                base64Hash = base64Hash.substring(0, NUM_BASE64_CHAR)
                Log.d(TAG, String.format("pkg: %s -- hash: %s", packageName, base64Hash))
                return base64Hash
            } catch (e: NoSuchAlgorithmException) {
                Log.e(TAG, "hash:NoSuchAlgorithm", e)
            }
            return null
        }
    }
}
