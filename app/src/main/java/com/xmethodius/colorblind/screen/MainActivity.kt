package com.xmethodius.colorblind.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xmethodius.colorblind.R
import com.xmethodius.colorblind.adapter.ColorsListAdapter
import com.xmethodius.colorblind.model.Cell
import com.xmethodius.colorblind.model.Color
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.jvm.Throws

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ColorsListAdapter
    private lateinit var colorList: ArrayList<Color>
    private lateinit var cellList: ArrayList<Cell>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cellList = ArrayList<Cell>()
        colorList = ArrayList<Color>()

        parseFile()
        fillCellList()
        setupAdapter()
        setupRecycler()
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun prepareParser(): InputStream {
        val fileName = "colors.xml"
        return assets.open(fileName)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun fetchColorValues(parser: XmlPullParser) {
        val attribute = "color"
        val colorNameIndex = 0
        val colorValueIndex = 1

        if(parser.name.equals(attribute)) {
            val colorName: String = parser.getAttributeValue(colorNameIndex)
            val colorValue: Int = parser.getAttributeValue(colorValueIndex).toColorInt()
            val color = Color(colorName, colorValue)
            addNewColorItem(color)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun parseFile(){
        val inputStream: InputStream = prepareParser()
        val parser: XmlPullParser   = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(InputStreamReader(inputStream))

        while(parser.eventType != XmlPullParser.END_DOCUMENT) {
            if(parser.eventType == XmlPullParser.START_TAG)
                fetchColorValues(parser)
            parser.next()
        }
    }

    private fun addNewColorItem(color: Color) {
         colorList.add(color)
    }

    private fun fillCellList() {
        var ins = 0
        for(i in colorList) {
            cellList.add(Cell(i, false))
            ins += 1
        }
    }

    private fun setupAdapter() {
        adapter = ColorsListAdapter(cellList, this)
    }

    private fun setupRecycler() {
        val list: RecyclerView = findViewById(R.id.colorsList)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
    }
}