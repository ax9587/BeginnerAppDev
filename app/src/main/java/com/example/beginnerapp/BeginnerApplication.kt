package com.example.beginnerapp

import android.app.Application
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BeginnerApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51QKkq9FA8pQOwelx94fQQ4Q0pEG3wPPs5OewqsbCWskup3q7l5fjBBdijISVtcwIgX93g94fdAzmp3S6UKUtDHk60057nUxmtM"
        )
    }
}