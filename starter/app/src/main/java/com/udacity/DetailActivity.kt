package com.udacity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val filename: String? = intent.getStringExtra("FILENAME")
        val status: String? = intent.getStringExtra("STATUS")

        filename_text.setText(filename)
        status_text.setText(status)

        setSupportActionBar(toolbar)
    }

    fun closeActivity(view: View) {
        finish()
    }

    companion object {
        const val FILENAME = "FILENAME"
        const val STATUS = "STATUS"
    }

}
