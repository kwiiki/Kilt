package com.example.kilt

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kilt.utills.otp.AppSignatureHelper
import com.example.kilt.utills.otp.OTPReceiver
import com.example.kilt.utills.otp.SmsBroadcastReceiver
import com.example.kilt.utills.otp.SmsViewModel
import com.example.kilt.utills.otp.startSMSRetrieverClient
import com.example.kilt.presentation.theme.BorderDark
import com.example.kilt.presentation.theme.BorderLight
import com.google.android.gms.auth.api.phone.SmsRetriever
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.regex.Pattern


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val smsViewModel: SmsViewModel by viewModels()
    private lateinit var smsBroadcastReceiver: SmsBroadcastReceiver
    private lateinit var appSignatureHelper: AppSignatureHelper

    companion object {
        private const val REQ_USER_CONSENT = 200
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            registerReceiver(loginViewModel.smsVerificationReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
//        } else {
//            registerReceiver(loginViewModel.smsVerificationReceiver, intentFilter)
//        }
        appSignatureHelper = AppSignatureHelper(this)
        val appSignatures = appSignatureHelper.getAppSignatures()
        for (signature in appSignatures) {
            Log.d("AppSignature", "App Signature: $signature")
        }
        startSmsUserConsent()
        setContent {
//            BenefitsScreen()
//            MyAnnouncementScreen(navController = rememberNavController())
            KiltApp()
//            EnterFourCodePage(navController = rememberNavController(), authViewModel = hiltViewModel())

        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_USER_CONSENT) {
            if (resultCode == RESULT_OK && data != null) {
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                Log.d("SMS_RETRIEVER", "SMS content: $message")
                smsViewModel.handleSmsIntent(data)
            } else {
                Log.d("SMS_RETRIEVER", "SMS retrieval failed")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerBroadcastReceiver()
        Log.d("SMS_RETRIEVER", "BroadcastReceiver registered")
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsBroadcastReceiver)
        Log.d("SMS_RETRIEVER", "BroadcastReceiver unregistered")
    }

    private fun startSmsUserConsent() {
        SmsRetriever.getClient(this).startSmsUserConsent(null)
            .addOnSuccessListener {
                Log.d("SMS_RETRIEVER", "SMS retriever started successfully")
            }
            .addOnFailureListener {
                Log.e("SMS_RETRIEVER", "Failed to start SMS retriever", it)
            }
    }
    private fun registerBroadcastReceiver() {
        smsBroadcastReceiver = SmsBroadcastReceiver()
        smsBroadcastReceiver.smsBroadcastReceiverListener = object : SmsBroadcastReceiver.SmsBroadcastReceiverListener {
            override fun onSuccess(message: String?) {
                Log.d("SMS_RETRIEVER", "onSuccess called in MainActivity with message: $message")
                message?.let {
                    val code = extractCodeFromMessage(it)
                    // Здесь вы можете использовать полученный код, например, заполнить поле ввода
                    // или передать его в ViewModel
                    smsViewModel.handleSmsCode(code)
                }
            }

            override fun onFailure() {
                Log.d("SMS_RETRIEVER", "onFailure called in MainActivity")
            }
        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(smsBroadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(smsBroadcastReceiver, intentFilter)
        }
    }
//    override fun onDestroy() {
//        super.onDestroy()
//        unregisterReceiver(loginViewModel.smsVerificationReceiver)
//    }
}
private fun extractCodeFromMessage(message: String): String {
    val pattern = Pattern.compile("Код подтверждения в Kilt - (\\d{4})")
    val matcher = pattern.matcher(message)
    return if (matcher.find()) {
        matcher.group(1) ?: ""
    } else {
        Log.d("SMS_RETRIEVER", "Failed to extract code from message: $message")
        ""
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun OtpScreen() {
    val context = LocalContext.current
    var otpValue by remember { mutableStateOf("") }
    var isOtpFilled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    /**
     * Right now we don't have support for Autofill in Compose.
     *
     * If we have support in the future and want user to autofill OTP from keyboard manually,
     * then we do not need to fetch OTP automatically using Google SMS Retriever API and in
     * that case, we can totally remove this [OtpReceiverEffect] and let Autofill handle it.
     * But Google SMS Retriever API is a great way anyways to fetch and populate OTP!
     */
    OtpReceiverEffect(
        context = context,
        onOtpReceived = { otp ->
            otpValue = otp
            if (otpValue.length == 6) {
                keyboardController?.hide()
                isOtpFilled = true
            }
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    (LocalView.current.context as Activity).window.statusBarColor = Color.White.toArgb()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                    .drawWithContent {
                        drawContent()
                    },
                navigationIcon = {
                    Box(
                        Modifier
                            .size(48.dp)
                            .clickable { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.link_icon),
                            tint = Color.DarkGray,
                            contentDescription = "Back",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.DarkGray,
                    actionIconContentColor = Color.DarkGray
                ),
                title = { Text(text = "Enter One Time Password") },
                windowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
            )
        },
        bottomBar = {
            Button(
                onClick = {},
                enabled = isOtpFilled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            ) {
                Text(text = "Continue")
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 0.dp),
                    text = "Please verify your phone number with the OTP we sent to (***)***-2193.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(24.dp),
                    color = Color.White
                ) {
                    OtpInputField(
                        modifier = Modifier
                            .padding(top = 48.dp)
                            .focusRequester(focusRequester),
                        otpText = otpValue,
                        shouldCursorBlink = false,
                        onOtpModified = { value, otpFilled ->
                            otpValue = value
                            isOtpFilled = otpFilled
                            if (otpFilled) {
                                keyboardController?.hide()
                            }
                        }
                    )
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun OtpReceiverEffect(
    context: Context,
    onOtpReceived: (String) -> Unit
) {
    val otpReceiver = remember { OTPReceiver() }

    // Lifecycle effect handling to manage the registration and unregistration of the receiver
    DisposableEffect(Unit) {
        Log.e("OTPReceiverEffect", "SMS retrieval has been started.")
        startSMSRetrieverClient(context)

        otpReceiver.init(object : OTPReceiver.OTPReceiveListener {
            override fun onOTPReceived(otp: String?) {
                Log.e("OTPReceiverEffect", "OTP Received: $otp")
                otp?.let { onOtpReceived(it) }
                unregisterReceiverSafely(context, otpReceiver)
            }

            override fun onOTPTimeOut() {
                Log.e("OTPReceiverEffect", "Timeout")
            }
        })

        registerReceiverSafely(context, otpReceiver)

        // Cleanup when the composable is removed or paused
        onDispose {
            unregisterReceiverSafely(context, otpReceiver)
        }
    }
}

// Utility functions to safely register/unregister receiver
@RequiresApi(Build.VERSION_CODES.O)
private fun registerReceiverSafely(context: Context, receiver: BroadcastReceiver) {
    try {
        Log.e("OTPReceiverEffect", "Registering receiver")
        context.registerReceiver(
            receiver,
            IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION),
            Context.RECEIVER_EXPORTED
        )
    } catch (e: IllegalArgumentException) {
        Log.e("OTPReceiverEffect", "Error in registering receiver: ${e.message}")
    }
}

private fun unregisterReceiverSafely(context: Context, receiver: BroadcastReceiver) {
    try {
        Log.e("OTPReceiverEffect", "Unregistering receiver")
        context.unregisterReceiver(receiver)
    } catch (e: IllegalArgumentException) {
        Log.e("OTPReceiverEffect", "Error in unregistering receiver: ${e.message}")
    }
}

@Composable
fun OtpInputField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpLength: Int = 4,
    shouldShowCursor: Boolean = false,
    shouldCursorBlink: Boolean = false,
    onOtpModified: (String, Boolean) -> Unit
) {
    require(otpText.length <= otpLength) { "OTP should be $otpLength digits" }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpLength) {
                onOtpModified(it.text, it.text.length == otpLength)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpLength) { index ->
                    CharacterContainer(
                        index = index,
                        text = otpText,
                        shouldShowCursor = shouldShowCursor,
                        shouldCursorBlink = shouldCursorBlink,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
internal fun CharacterContainer(
    index: Int,
    text: String,
    shouldShowCursor: Boolean,
    shouldCursorBlink: Boolean,
) {
    val isFocused = text.length == index
    val character = if (index < text.length) text[index].toString() else ""

    var cursorVisible by remember { mutableStateOf(shouldShowCursor) }

    // Blinking effect for the cursor when focused
    LaunchedEffect(isFocused) {
        if (isFocused && shouldShowCursor && shouldCursorBlink) {
            while (true) {
                delay(800)
                cursorVisible = !cursorVisible
            }
        } else {
            cursorVisible = false
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier
                .width(36.dp)
                .border(
                    width = if (isFocused) 2.dp else 1.dp,
                    color = if (isFocused) BorderDark else BorderLight,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(2.dp),
            text = character,
            style = MaterialTheme.typography.headlineLarge,
            color = if (isFocused) BorderLight else BorderDark,
            textAlign = TextAlign.Center
        )
        AnimatedVisibility(visible = isFocused && cursorVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(2.dp)
                    .height(24.dp)
                    .background(BorderDark)
            )
        }
    }
}



