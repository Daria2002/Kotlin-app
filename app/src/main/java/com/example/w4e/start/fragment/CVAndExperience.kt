package com.example.w4e.start.fragment

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.w4e.start.R
import com.github.barteksc.pdfviewer.PDFView
import java.io.File


class CVAndExperience: Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var pdfView: PDFView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cv_and_experience, container, false)
    }

    // TODO: check how to execute
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view!!.findViewById(R.id.cvProgressBar)
        pdfView = view!!.findViewById(R.id.pdfView)
        downloadCV(cvUrl)
    }

    private fun getRootDirPath(context: FragmentActivity?): String {
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
        private lateinit var cvUrl: String
        // newInstance constructor for creating fragment with arguments
        fun newInstance(cv: String): CVAndExperience? {
            cvUrl = cv
            return CVAndExperience()
        }
    }
}