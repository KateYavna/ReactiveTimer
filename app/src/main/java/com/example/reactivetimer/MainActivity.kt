package com.example.reactivetimer


import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher

import android.view.WindowManager
import android.widget.EditText
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
           this.addTextChangedListener(object : TextWatcher {
               override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
               }

               override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               }

               override fun afterTextChanged(editable: Editable?) {
                   afterTextChanged.invoke(editable.toString())
               }
           })
       }

        fun timer (time :Long,count :Long,sec: String){
            Observable.intervalRange(
                time,
                count,
                0L,
                1L,
                TimeUnit.SECONDS
            )
                .subscribe { it ->
                    if ((it*(-1)/60L) >= 10) etMinutes.setText((it*(-1)/60L).toString())
                    else etMinutes.setText("0" + (it*(-1)/60L).toString())
                    if ((it*(-1)%60L) >= 10) etSeconds.setText((it*(-1)%60L).toString())
                    else etSeconds.setText("0" + (it*(-1)%60L).toString())
                    if (it == 0L){
                        var mediaPlayer = MediaPlayer.create(this, R.raw.bethoven)
                        mediaPlayer.start()
                    }
                }
        }

        etSeconds.afterTextChanged { btStart.setOnClickListener {
            etSeconds.isFocusable = false
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            var min = etMinutes.text.toString()
            var sec = etSeconds.text.toString()
            if (min.equals("")){
                var time = sec.toLong() * (-1)
                var count = sec.toLong() + 1
                timer(time,count,sec) }
            else {
                var time = (min.toLong() * 60 + sec.toLong()) * (-1)
                var count = min.toLong() * 60 + sec.toLong() + 1
                timer(time,count,sec)}


        } }

    }
}