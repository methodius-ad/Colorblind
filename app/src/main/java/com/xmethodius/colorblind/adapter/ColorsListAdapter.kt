package com.xmethodius.colorblind.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.renderscript.Int3
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.xmethodius.colorblind.model.Cell
import com.xmethodius.colorblind.R

class ColorsListAdapter(colorsList: List<Cell>, context: Context): RecyclerView.Adapter<ColorsListAdapter.ColorViewHolder>() {

    private val list = colorsList
    private val cont = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(LayoutInflater.from(cont).inflate(
                R.layout.color_cell,
                parent,
                false
            )
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val cardView = holder.itemView
        val colorText = holder.text
        val layoutHeight = cardView.layoutParams.height
        val currentColorName = list[position].color.colorName
        val color = list[position].color.colorValue
       // createCellShape(cardView, R.color.black)
        colorText.text = currentColorName

        if(list[position].selected) {
            colorText.setTextColor(R.color.black)
        } else {
            colorText.setTextColor(color)
        }

        selectCell(cardView, colorText, layoutHeight, color, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.colorText)
    }

    private fun createCellShape(cell: View, color: Int) {
        val shape: GradientDrawable = GradientDrawable()
        shape.cornerRadius = 80F
        shape.setColor(color)
        cell.background = shape
    }

    @SuppressLint("ResourceAsColor")
    private fun selectCell(cell: View, colorText: TextView, layoutHeight: Int, color: Int, position: Int) {
        // cast color value to color int
        // check for click on cardview
        colorText.setOnClickListener {
            // check if cell was not selected
            if(!list[position].selected) {
                list[position].selected = true
                //change height of cardview
                cell.layoutParams.height = layoutHeight * 3
                colorText.setTextColor(R.color.white)
                createCellShape(cell, color)
                cell.requestLayout()
            } else {
                list[position].selected = false
                cell.layoutParams.height = layoutHeight / 3
                createCellShape(cell, R.color.gray)
                if (color != null) {
                    colorText.setTextColor(R.color.white)
                }
                cell.requestLayout()
            }
            notifyDataSetChanged()
        }
    }
}