package com.example.file_downloader2

import android.os.*
import android.text.Editable
import android.text.TextWatcher
import android.webkit.URLUtil
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    var postepInfo = PostepInfo()
    val INFO_KEY = "com.example.file_downloader2.INFO_KEY"
    val INFO_ARRAY_LIST_KEY = "com.example.file_downloader2.INFO_ARRAY_LIST_KEY"

    lateinit var mFileSizeView: TextView
    lateinit var mFileTypeView: TextView
    lateinit var mFileSizeDownloadedView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val murlPlainText = findViewById<EditText>(R.id.urlPlainText)
        val mdownloadFileDataButton = findViewById<Button>(R.id.downloadDataButton)
        val mdownloadFileButton = findViewById<Button>(R.id.downloadFileButton)

        val fileDownloadExecutor = Executors.newSingleThreadExecutor()
        val donwloadHandler = Handler(Looper.getMainLooper())

        var mWebPath =
            "https://s3.amazonaws.com/appsdeveloperblog/Micky.jpg"

        mFileSizeView = findViewById(R.id.fileSizeView)
        mFileTypeView = findViewById(R.id.fileTypeView)
        mFileSizeDownloadedView = findViewById(R.id.bytesDownloadedView)

        murlPlainText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  mWebPath = murlPlainText.text.toString()
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
            fileDownloadExecutor.execute {
                val file = downloadFile(mWebPath)
                if (file != null) {
                    donwloadHandler.post {
                        saveFileExternalStorage(file)
                        postepInfo.mRozmiar = file.length().toInt()
                        mFileSizeDownloadedView.text = file.length().toString()
                        Toast.makeText(applicationContext, "Pobrano", Toast.LENGTH_LONG).show()
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
    }

    //https://www.zoftino.com/saving-files-to-internal-storage-&-external-storage-in-android
    //TODO(NAPRAWIC)
    private fun saveFileExternalStorage(file: File) {
        var outputStream: FileOutputStream? = null
        try {
            file.createNewFile()
            //second argument of FileOutputStream constructor indicates whether to append or create new file if one exists
            outputStream = FileOutputStream(file, false)
            outputStream.write(file.readBytes()) //2GB LIMIT
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //TODO(BROKEN)
    private fun downloadFile(webPath: String): File? {
        val url: URL = StringToUrl(webPath)
        val connection: HttpURLConnection
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val inputStream: InputStream = connection.inputStream

            connection.disconnect()

            if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                return null;
            }
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "${System.currentTimeMillis()}"
            )
            file.copyInputStreamToFile(inputStream)
            return file

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    //TODO(CO TU SIE DZIEJE?)
    /*
    private fun saveFileToStorage(FileDownloaded: File) {
        val filename = "${System.currentTimeMillis()}"
        var fos: OutputStream
        this.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val fileUri: Uri? = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            fos = fileUri?.let { resolver.openOutputStream(it) }!!
        }

    }
    */

    private fun File.copyInputStreamToFile(inputStream: InputStream) {
        this.outputStream().use { fileOut ->
            inputStream.copyTo(fileOut)
        }
    }

    private fun loadUrl(mWebPath: String): Pair<Int, String>? {
        val url: URL = StringToUrl(mWebPath)
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

    //exceptions?
    private fun StringToUrl(string: String): URL {
        return URL(string)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(INFO_KEY, postepInfo)
        outState.putStringArrayList(
            INFO_ARRAY_LIST_KEY, arrayListOf<String>(
                mFileSizeView.text.toString(),
                mFileTypeView.text.toString(),
                mFileSizeDownloadedView.text.toString()
            )
        )
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        postepInfo = savedInstanceState.getParcelable(INFO_KEY)!!
        val arrayList = savedInstanceState.getStringArrayList(INFO_ARRAY_LIST_KEY)!!
        mFileSizeView.text = arrayList[0]
        mFileTypeView.text = arrayList[1]
        mFileSizeDownloadedView.text = arrayList[2]
        super.onRestoreInstanceState(savedInstanceState)
    }


}