package com.xmethodius.colorblind

import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        return assets.open("colors.xml")
    }
    @Throws(XmlPullParserException::class, IOException::class)
    private fun fetchColorValues(s: String, parser: XmlPullParser) {
        Log.d("log", "START TAG, NAME IS " + parser.name + " TEXT IS " + parser.lineNumber+
        " ATTRIBUTE COUNT IS " + parser.attributeCount)

        if(parser.name.equals("color"))
            fillColorList(Color(parser.getAttributeValue(0).toString(), parser.getAttributeValue(1).toColorInt()))
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun parseFile(){
        val inputStream: InputStream = prepareParser()
        val parser: XmlPullParser   = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(InputStreamReader(inputStream))

        while(parser.eventType != XmlPullParser.END_DOCUMENT) {
            var s: String = ""
            when(parser.eventType) {
                XmlPullParser.START_DOCUMENT ->
                    Log.d("log", "START DOCUMENT")
                XmlPullParser.START_TAG ->
                    fetchColorValues(s, parser)
                XmlPullParser.END_TAG ->
                    Log.d("log", "END TAG, NAME IS " + parser.name+ " TEXT IS " + parser.text)
                XmlPullParser.TEXT -> {
                    Log.d("log", "TEXT IS " + parser.text)}
            }
            parser.next()
        }
    }

    private fun fillColorList(color: Color) {
         colorList.add(color)
    }

    private fun fillCellList() {
        for(i in colorList) {
            cellList.add(Cell(i, false))
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