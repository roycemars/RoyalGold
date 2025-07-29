package com.roycemars.royalgold.feature.market.data

class GeminiRepository {
    val prompt = "Suggest which crypto is best to buy today, using following list of crypto in format [Symbol, price, % change 1 hour, % change 24 hours]:\n" +
            "\n" +
            "BTC, 117880.31722817733 -0.17744344 -1.04860029\n" +
            "LTC, 108.79108851281202 -0.19924454 -5.35351776\n" +
            "XRP, 3.124183406464968 0.06732171 -4.25292472\n" +
            "DOGE, 0.22535682243589572 -0.29235607 -6.14554677\n" +
            "XLM, 0.4192367712760907 0.20855056 -6.14119226\n" +
            "\n" +
            "Provide response in 2 parts:\n" +
            "\n" +
            "1) symbol & short one-string motivation why\n" +
            "2) analysis of data"
}