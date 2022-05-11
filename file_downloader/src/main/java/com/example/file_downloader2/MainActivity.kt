package com.example.file_downloader2

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.*
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManagerCompat

    private lateinit var mFileSizeView: TextView
    private lateinit var mFileTypeView: TextView
    private lateinit var mFileSizeDownloadedView: TextView
    private lateinit var mprogressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannels()
        notificationManager = NotificationManagerCompat.from(this)

        val murlPlainText = findViewById<EditText>(R.id.urlPlainText)
        val mdownloadFileDataButton = findViewById<Button>(R.id.downloadDataButton)
        val mdownloadFileButton = findViewById<Button>(R.id.downloadFileButton)

        val fileDownloadExecutor = Executors.newSingleThreadExecutor()
        val donwloadHandler = Handler(Looper.getMainLooper())

        //https://pngimg.com/uploads/lion/lion_PNG3809.png
        var mWebPath = ""

        mFileSizeView = findViewById(R.id.fileSizeView)
        mFileTypeView = findViewById(R.id.fileTypeView)
        mFileSizeDownloadedView = findViewById(R.id.bytesDownloadedView)
        mprogressBar = findViewById(R.id.progressBar)


        murlPlainText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                  mWebPath = murlPlainText.text.toString()
                if (URLUtil.isValidUrl(mWebPath)) {
                    mdownloadFileDataButton.isEnabled = true
                    mdownloadFileButton.isEnabled = true
                } else {
                    mdownloadFileDataButton.isEnabled = false
                    mdownloadFileButton.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        mdownloadFileDataButton.setOnClickListener {
            fileDownloadExecutor.execute {
                //"https://cdn.kernel.org/pub/linux/kernel/v5.x/linux-5.4.36.tar.xz"
                val fileDetails = loadUrl(mWebPath)
                if (fileDetails != null) {
                    donwloadHandler.post {
                        mFileSizeView.text = fileDetails.first.toString()
                        mFileTypeView.text = fileDetails.second
                        Toast.makeText(applicationContext, "Pobrano informacje", Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    donwloadHandler.post {
                        Toast.makeText(
                            applicationContext,
                            "Nic nie pobrano",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        mdownloadFileButton.setOnClickListener {
            mprogressBar.visibility = View.VISIBLE
            downloadImage(mWebPath)
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                channelId,
                "Progress Notification",
                //IMPORTANCE_HIGH = shows a notification as peek notification.
                //IMPORTANCE_LOW = shows the notification in the status bar.
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "Progress Notification Channel"
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channel1)
        }
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createNotification(): NotificationCompat.Builder {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent, 0
        )

        val progressMax = 100
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Pobieranie")
            .setContentText("Pobieranie...")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setProgress(progressMax, 0, true)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    @SuppressLint("Range")
    private fun downloadImage(url: String) {
        val downloadManager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle("${System.currentTimeMillis()}")
                .setDescription("None")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    url.substring(url.lastIndexOf("/") + 1)
                )
        }
        //use when just to download the file with getting status
        downloadManager.enqueue(request)

        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)

        lifecycleScope.launchWhenStarted {
            var downloading = true
            val postepInfo = PostepInfo()

            val notification = createNotification()
            notificationManager.notify(1, notification.build())

            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                val msg: String = statusMessage(url, File(Environment.DIRECTORY_DOWNLOADS), status)
                postepInfo.mRozmiar =
                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                postepInfo.mPobranychBajtow =
                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                postepInfo.mStatus = msg

                val progress =
                    (postepInfo.mPobranychBajtow.toDouble() * 100f / postepInfo.mRozmiar.toDouble()).toInt()
                notification.setProgress(100, progress, false)
                notification.setContentText("$progress%")

                Log.i("Downloading:", progress.toString())
                withContext(Dispatchers.Main) {
                    mprogressBar.progress = progress
                    mFileSizeDownloadedView.text = postepInfo.mPobranychBajtow.toString()
                    notificationManager.notify(1, notification.build())
                }

                cursor.close()
            }
            notification.setContentText("Pobieranie zakonczone")
                .setProgress(0, 0, false)
                .setOngoing(false)
            notificationManager.notify(1, notification.build())
            mprogressBar.visibility = View.INVISIBLE
        }
    }

    private fun statusMessage(url: String, directory: File, status: Int): String {
        return when (status) {
            DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
            DownloadManager.STATUS_PAUSED -> "Paused"
            DownloadManager.STATUS_PENDING -> "Pending"
            DownloadManager.STATUS_RUNNING -> "Downloading..."
            DownloadManager.STATUS_SUCCESSFUL -> "File downloaded successfully in $directory" + File.separator + url.substring(
                url.lastIndexOf("/") + 1
            )
            else -> "There's nothing to download"
        }
    }


    private fun loadUrl(mWebPath: String): Pair<Int, String>? {
        val url = URL(mWebPath)
        val connection: HttpURLConnection
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()


            val fileSize = connection.contentLength
            val fileType = connection.contentType

            connection.disconnect()
            return Pair(fileSize, fileType)

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArrayList(
            INFO_ARRAY_LIST_KEY, arrayListOf<String>(
                mFileSizeView.text.toString(),
                mFileTypeView.text.toString(),
                mFileSizeDownloadedView.text.toString(),
                mprogressBar.visibility.toString(),
                mprogressBar.progress.toString()
            )
        )
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val arrayList = savedInstanceState.getStringArrayList(INFO_ARRAY_LIST_KEY)!!
        mFileSizeView.text = arrayList[0]
        mFileTypeView.text = arrayList[1]
        mFileSizeDownloadedView.text = arrayList[2]
        mprogressBar.visibility = arrayList[3].toInt()
        mprogressBar.progress = arrayList[4].toInt()
        super.onRestoreInstanceState(savedInstanceState)
    }

    companion object {
        private const val INFO_ARRAY_LIST_KEY = "com.example.file_downloader2.INFO_ARRAY_LIST_KEY"
        private const val channelId = "Progress Notification"
    }
}