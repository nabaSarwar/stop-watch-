package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
//declaring variables
    private lateinit var stopwatchDisplay: TextView
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var pauseButton: Button
    private lateinit var recordButton: Button
    private lateinit var recordListView: ListView

    private var running: Boolean = false
    private var paused: Boolean = false
    private var startTime: Long = 0
    private var elapsedTime: Long = 0

    private val handler = Handler()
    private val recordList = ArrayList<String>()
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//connecting layout
        stopwatchDisplay = findViewById(R.id.stopwatchDisplay)
        startButton = findViewById(R.id.startButton)

        resetButton = findViewById(R.id.resetButton)
        pauseButton = findViewById(R.id.pauseButton)
        recordButton = findViewById(R.id.recordButton)
        recordListView = findViewById(R.id.recordListView)

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recordList)
        recordListView.adapter = arrayAdapter

        startButton.setOnClickListener { startStopwatch() }

        resetButton.setOnClickListener { resetStopwatch() }
        pauseButton.setOnClickListener { pauseStopwatch() }
        recordButton.setOnClickListener { recordTime() }
    }

    private val runnable = object : Runnable {
        override fun run() {
            elapsedTime = SystemClock.uptimeMillis() - startTime
            updateTimerText(elapsedTime)
            handler.postDelayed(this, 10)
        }
    }

    private fun startStopwatch() {
        if (!running) {
            startTime = SystemClock.uptimeMillis() - elapsedTime
            handler.postDelayed(runnable, 0)
            running = true
            paused = false

            resetButton.visibility = View.INVISIBLE
            pauseButton.visibility = View.VISIBLE
            recordButton.visibility = View.VISIBLE
        }
    }

    private fun stopStopwatch() {
        if (running) {
            handler.removeCallbacks(runnable)
            running = false

            resetButton.visibility = View.VISIBLE
            pauseButton.visibility = View.INVISIBLE
            recordButton.visibility = View.INVISIBLE
        }
    }

    private fun resetStopwatch() {
        if (!running) {
            elapsedTime = 0
            updateTimerText(elapsedTime)
            arrayAdapter.clear()
            recordList.clear()
        }
    }

    private fun pauseStopwatch() {
        if (running && !paused) {
            handler.removeCallbacks(runnable)
            elapsedTime = SystemClock.uptimeMillis() - startTime
            running = false
            paused = true

            resetButton.visibility = View.VISIBLE
            pauseButton.visibility = View.INVISIBLE
            recordButton.visibility = View.VISIBLE
        }
    }

    private fun recordTime() {
        if (running || paused) {
            val recordText = formatTime(elapsedTime)
            recordList.add(recordText)
            arrayAdapter.notifyDataSetChanged()
        }
    }

    private fun updateTimerText(elapsedTime: Long) {
        stopwatchDisplay.text = formatTime(elapsedTime)
    }

    private fun formatTime(time: Long): String {
        val hours = time / 3600000
        val minutes = (time % 3600000) / 60000
        val seconds = (time % 60000) / 1000
        val milliseconds = time % 1000

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds)
    }
}
