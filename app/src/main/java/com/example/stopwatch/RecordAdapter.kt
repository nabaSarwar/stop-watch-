package com.example.stopwatch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class RecordAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val resourceId: Int = resource

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: inflater.inflate(resourceId, null)

        val recordText: TextView = view.findViewById(R.id.recordText)
        recordText.text = getItem(position)

        return view
    }
}
