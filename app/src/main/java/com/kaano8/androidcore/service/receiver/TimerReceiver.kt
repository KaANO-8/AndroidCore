package com.kaano8.androidcore.com.kaano8.androidcore.service.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kaano8.androidcore.com.kaano8.androidcore.main.MainActivity
import com.kaano8.androidcore.com.kaano8.androidcore.service.ui.ServiceFragment
import com.kaano8.androidcore.com.kaano8.androidcore.service.ui.ServiceFragment.Companion.NOTIFICATION_TEXT
import com.kaano8.androidcore.com.kaano8.androidcore.service.ui.ServiceFragment.Companion.TIMER_ACTION
import com.kaano8.androidcore.com.kaano8.androidcore.service.ui.ServiceFragment.Companion.UPDATED_TIMER_VALUE

class TimerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == TIMER_ACTION) {
            val activityIntent = Intent(context, MainActivity::class.java).also {
                it.putExtra(UPDATED_TIMER_VALUE, getElapsedTime(intent))
            }
            activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(activityIntent)
        }
    }

    private fun getElapsedTime(intent: Intent?): Int =
        intent?.getIntExtra(UPDATED_TIMER_VALUE,0) ?: 0
}