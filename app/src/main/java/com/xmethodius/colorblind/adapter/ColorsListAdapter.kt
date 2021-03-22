package com.xmethodius.colorblind.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.xmethodius.colorblind.model.Cell
import com.xmethodius.colorblind.R
import java.util.prefs.Preferences

class ColorsListAdapter(private  var colorsList: List<Cell>, private val context: Context): RecyclerView.Adapter<ColorsListAdapter.ColorViewHolder>() {
    
    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cellText: TextView = itemView.findViewById(R.id.colorText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(LayoutInflater.from(context).inflate(
                R.layout.color_cell,
                parent,
                false
            )
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val cell = holder.itemView
        val cellText = holder.cellText

        setCellColor(cell, position)
        selectCell(cell, position)
        setCellText(cellText, position)
        setTextColor(cellText, position)

    }

    override fun getItemCount(): Int = colorsList.size

    @SuppressLint("ResourceAsColor")
    private fun updateCellItem(cell: View, position: Int) {
        if (!colorsList[position].selected) {
            colorsList[position].selected = true
            cell.layoutParams.height *= 3
        } else {
            colorsList[position].selected = false
            cell.layoutParams.height /= 3
        }
        notifyItemChanged(position, 0)
    }

    @SuppressLint("ResourceAsColor")
    private fun setCellShape(cell: View, color: Int) {
        val cornerRadiusValue = 80F
        val shape = GradientDrawable()
        shape.cornerRadius = cornerRadiusValue
        shape.setColor(color)
        cell.background = shape
    }

    private fun setCellColor(cell: View, position: Int) {
        val colorValue: Int = colorsList[position].color.colorValue
         if(!colorsList[position].selected)
            setCellShape(cell, R.color.gray)
        else
            setCellShape(cell, colorValue)
    }

    @SuppressLint("ResourceAsColor")
    private fun setTextColor(cellText: TextView, position: Int) {
        val currentColorValue = colorsList[position].color.colorValue

        if(colorsList[position].selected)
            cellText.setTextColor(R.color.gray)
        else
            cellText.setTextColor(currentColorValue)
    }

    private fun setCellText(cellText: TextView, position: Int) {
        val currentColorName = colorsList[position].color.colorName
        cellText.text = currentColorName
    }

    @SuppressLint("ResourceAsColor")
    private fun selectCell(cell: View, position: Int) {
        cell.setOnClickListener {
            updateCellItem(cell, position)
        }
    }
}