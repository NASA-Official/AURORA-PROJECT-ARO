package com.nassafy.aro.util

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class ChartAxisFormatter(var labelList: ArrayList<String>) : IndexAxisValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return labelList[value.toInt()]
    }

}