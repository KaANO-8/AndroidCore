package com.kaano8.androidcore.com.kaano8.androidcore.workmanager.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.workmanager.KEY_IMAGE_URI

class BlurWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private val TAG = "BlurWorker"
    val resourceUri = inputData.getString(KEY_IMAGE_URI)

    override fun doWork(): Result {

        makeStatusNotification("Blurring image", applicationContext)

        sleep()

        try {
            if (resourceUri?.isEmpty() == true) {
                Log.e(TAG, "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }

            val resolver = applicationContext.contentResolver

            val picture = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri)))

            val outputUri =
                writeBitmapToFile(applicationContext, blurBitmap(picture, applicationContext))

            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())

            return Result.success(outputData)
        } catch (exception: Exception) {
            Log.e(TAG, "doWork: Error applying blur")
            return Result.failure()
        }
    }
}
