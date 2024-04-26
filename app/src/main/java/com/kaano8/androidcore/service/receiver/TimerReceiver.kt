package com.kaano8.androidcore.com.kaano8.androidcore.service.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kaano8.androidcore.com.kaano8.androidcore.main.MainActivity
import com.kaano8.androidcore.com.kaano8.androidcore.service.ui.MemoFragment.Companion.NOTIFICATION_TEXT
import com.kaano8.androidcore.com.kaano8.androidcore.service.ui.MemoFragment.Companion.TIMER_ACTION

class TimerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == TIMER_ACTION) {
            val activityIntent = Intent(context, MainActivity::class.java).also {
                it.putExtra(
                    NOTIFICATION_TEXT, getElapsedTime(intent)
                )
            }
            activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(activityIntent)
        }
    }

    private fun getElapsedTime(intent: Intent?): Int =
        intent?.getIntExtra(NOTIFICATION_TEXT,0) ?: 0
}