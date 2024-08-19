package com.recipes_app.learningapp.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.recipes_app.learningapp.R

class ThemesSpinnerAdapter(context: Context, items: List<String>) :
    ArrayAdapter<String>(context, R.layout.spinner_main_item, items) {

    private val customFont: Typeface? = ResourcesCompat.getFont(context, R.font.nunito_regular)
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_main_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.tvSpinnerAdapter)
        customFont?.let { textView?.typeface = it }
        textView?.text = getItem(position)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_main_item, parent, false)
        val textView = view.findViewById<TextView>(R.id.tvSpinnerAdapter)
        customFont?.let { textView?.typeface = it }
        textView?.text = getItem(position)
        return view
    }
}
