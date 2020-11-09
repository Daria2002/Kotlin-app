package com.example.w4e.start.fragment

import android.os.Build
import android.os.Environment
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class CVAndExperience: Fragment() {
    private lateinit var userName: String
    private lateinit var userCv: String
    lateinit var progressBar: ProgressBar
    lateinit var pdfView: PDFView

    fun getRootDirPath(context: FragmentActivity?): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(
                context!!.applicationContext,
                null
            )[0]
            file.absolutePath
        } else {
            context!!.applicationContext.filesDir.absolutePath
        }
    }

    fun getPdfUrl(): String {
        return "https://mindorks.s3.ap-south-1.amazonaws.com/courses/MindOrks_Android_Online_Professional_Course-Syllabus.pdf"
    }

    private fun downloadCV() {
        progressBar.visibility = View.VISIBLE
        val fileName = "myFile.pdf"
        downloadPdf(
            getPdfUrl(),
            getRootDirPath(activity),
            fileName
        )
    }

    private fun showPdfFromFile(file: File) {
        pdfView.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, _ ->
                Toast.makeText(
                    activity,
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }

    private fun downloadPdf(url: String, dirPath: String, fileName: String) {
        PRDownloader.initialize(activity);
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Toast.makeText(activity, "downloadComplete", Toast.LENGTH_LONG)
                        .show()
                    val downloadedFile = File(dirPath, fileName)
                    progressBar.visibility = View.GONE
                    showPdfFromFile(downloadedFile)
                }

                override fun onError(error: com.downloader.Error?) {
                    Toast.makeText(
                        activity,
                        "Error in downloading file : $error",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        @JvmStatic
        fun newInstance(name: String, cv: ByteArray) = CVAndExperience().apply {
            userName = name
            userCv = cv.toString()
        }
    }
}