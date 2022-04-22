package com.example.file_downloader2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class DownloadWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    private val CHANNEL_ID: String = "glowny"

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        val inputUrl = inputData.getString(KEY_INPUT_URL)
            ?: return Result.failure()
        // Mark the Worker as important
        val progress = "Starting Download"
        setForeground(createForegroundInfo(progress))
        return download(inputUrl)
    }

    private fun download(inputUrl: String): Result {
        val SIZE_BLOCK = 1024
        var output: File? = null
        var url: URL? = null
        try{
            url = URL(inputUrl)
            File(url.file)
            output = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS),
                "${System.currentTimeMillis()}"
            )
            if (output.exists()) output.delete()
        }catch (e : IOException){
            e.printStackTrace()
        }
        val netStream: InputStream? = null
        val streamToFile: FileOutputStream?
        var connection: HttpURLConnection? = null
        val postepInfo = PostepInfo()
        try {
            connection = url!!.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val reader = DataInputStream(connection.inputStream)
            streamToFile = FileOutputStream(output!!.path)
            val buffer = ByteArray(SIZE_BLOCK)
            var downloaded = reader.read(buffer, 0, SIZE_BLOCK)
            while (downloaded != -1){
                streamToFile.write(buffer, 0, downloaded)
                postepInfo.mPobranychBajtow += downloaded
                downloaded = reader.read(buffer, 0, SIZE_BLOCK)
            }
            streamToFile.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (netStream != null){
            try{
                netStream.close()
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
        connection!!.disconnect()

        return Result.success()
    }
    // Creates an instance of ForegroundInfo which can be used to update the
    // ongoing notification.
    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val id = applicationContext.getString(R.string.channelId)
        val title = applicationContext.getString(R.string.download)
        val cancel = applicationContext.getString(R.string.cancel)
        // This PendingIntent can be used to cancel the worker
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())

        // Create a Notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(applicationContext)
        }

        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)
            // Add the cancel action to the notification which can
            // be used to cancel the worker
            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .build()

        return ForegroundInfo(0,notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(applicationContext: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.channelName)
            val descriptionText = applicationContext.getString(R.string.channeDesc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val KEY_INPUT_URL = "KEY_INPUT_URL"
        const val KEY_OUTPUT_FILE_NAME = "KEY_OUTPUT_FILE_NAME"
    }

}