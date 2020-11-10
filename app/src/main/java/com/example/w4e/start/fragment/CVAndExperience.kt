package com.example.w4e.start.fragment

import android.os.Build
import android.os.Bundle
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
import com.example.w4e.start.R
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.android.synthetic.main.fragment_cv_and_experience.view.*
import java.io.File

class CVAndExperience: Fragment() {
    // TODO: get argument from previous activity
    private var cvUrl: String = "https://mindorks.s3.ap-south-1.amazonaws.com/courses/MindOrks_Android_Online_Professional_Course-Syllabus.pdf"
    lateinit var progressBar: ProgressBar
    lateinit var pdfView: PDFView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progressBar)
        pdfView = view.findViewById(R.id.pdfView)
        downloadCV(cvUrl)
    }

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

    private fun downloadCV(url: String) {
        progressBar.visibility = View.VISIBLE
        val fileName = "myFile.pdf"
        downloadPdf(
            url,
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
        fun newInstance(cv_url: String) = CVAndExperience().apply {
            cvUrl = cv_url
            cvUrl = "https://mindorks.s3.ap-south-1.amazonaws.com/courses/MindOrks_Android_Online_Professional_Course-Syllabus.pdf"
        }
    }
}