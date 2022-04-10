package com.example.file_downloader2

import android.util.Log
import androidx.work.*
import com.example.file_downloader2.DownloadWorker.Companion.KEY_INPUT_URL
import java.util.*
import java.util.concurrent.TimeUnit

class WorkersManager {
    private lateinit var manager: WorkManager
    private lateinit var activity: MainActivity

    fun init(activity: MainActivity){
        this.activity = activity
        manager = WorkManager.getInstance(activity)
    }

    fun downloadContentWorker(
        inputUrl: String
    ): OneTimeWorkRequest{
        val data = Data.Builder().apply {
            putString(KEY_INPUT_URL,inputUrl)
        }.build()

        val oneTimeWorkerRequest = buildOneTimeWorkRemoteRequest(DownloadWorker::class.java, data)
        manager.enqueue(oneTimeWorkerRequest)
        return oneTimeWorkerRequest
    }

    fun observeWorkerBy(workerId: UUID) {
        manager.getWorkInfoByIdLiveData(workerId).observe(activity) { workInfo ->
            if (workInfo?.state == WorkInfo.State.FAILED) {
                Log.e("Download","Download worker: ${workInfo.id} was failed")
            } else if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
                Log.e("Download","Download worker: ${workInfo.id} was failed")
            }
        }
    }

    private fun buildOneTimeWorkRemoteRequest(
        listenableWorkerClass: Class<out ListenableWorker>,
        inputData: Data,): OneTimeWorkRequest {
        return OneTimeWorkRequest.Builder(listenableWorkerClass)
            .setInputData(inputData)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                10 * 1000,
                TimeUnit.MILLISECONDS
            )
            .build()
    }
}