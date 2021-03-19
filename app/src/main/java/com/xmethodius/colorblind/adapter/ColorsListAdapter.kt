package com.xmethodius.colorblind.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xmethodius.colorblind.model.Cell
import com.xmethodius.colorblind.R

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
        val layoutHeight = cell.layoutParams.height

        changeCellColor(cell, position)
        selectCell(cell, layoutHeight, position)
        changeCellText(cellText, position)
        changeTextColor(cellText, position)
    }

    override fun getItemCount(): Int = colorsList.size

    @SuppressLint("ResourceAsColor")
    private fun updateCellShape(cell: View, color: Int) {
        val cornerRadiusValue = 80F
        val shape = GradientDrawable()
        shape.cornerRadius = cornerRadiusValue
        shape.setColor(color)
        cell.background = shape
    }

    private fun changeCellColor(cell: View, position: Int) {
        val colorValue: Int = colorsList[position].color.colorValue
         if(!colorsList[position].selected)
            updateCellShape(cell, R.color.gray)
        else
            updateCellShape(cell, colorValue)
    }

    @SuppressLint("ResourceAsColor")
    private fun changeTextColor(cellText: TextView, position: Int) {
        val currentColorValue = colorsList[position].color.colorValue

        if(colorsList[position].selected)
            cellText.setTextColor(R.color.black)
        else
            cellText.setTextColor(currentColorValue)
    }

    private fun changeCellText(cellText: TextView, position: Int) {
        val currentColorName = colorsList[position].color.colorName
        cellText.text = currentColorName
    }

    @SuppressLint("ResourceAsColor")
    private fun selectCell(cell: View, layoutHeight: Int, position: Int) {
        cell.setOnClickListener {
            if(!colorsList[position].selected) {
                colorsList[position].selected = true
                cell.layoutParams.height = layoutHeight * 3
            } else {
                colorsList[position].selected = false
                cell.layoutParams.height = layoutHeight / 3
            }
            notifyDataSetChanged()
        }
    }
}