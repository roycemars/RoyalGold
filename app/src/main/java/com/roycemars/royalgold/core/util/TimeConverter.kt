package com.roycemars.royalgold.core.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.text.format
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import java.time.Instant as JavaInstant

@OptIn(ExperimentalTime::class)
object TimeConverter {
    // Formatter for ensuring ".SSSZ" in the output string
    // It's defined once here for efficiency.
    @RequiresApi(Build.VERSION_CODES.O)
    private val OUTPUT_FORMATTER: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneOffset.UTC) // Essential for ensuring 'Z' means UTC

    /**
     * Converts an ISO 8601 date-time string (UTC, typically ending in 'Z')
     * to a Long representing milliseconds since the epoch.
     *
     * @param isoDateTimeString The ISO 8601 formatted date-time string.
     *                          Example: "2010-07-13T00:00:00.000Z"
     * @return The Long timestamp, or null if parsing fails.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun isoStringToLong(isoDateTimeString: String): Long? {
        return try {
            val instant = Instant.parse(isoDateTimeString) // Handles standard ISO 8601 with 'Z'
            instant.toEpochMilliseconds()
        } catch (e: DateTimeParseException) {
            // Optionally log the error or handle it more gracefully
            // println("Error parsing date string '$isoDateTimeString': ${e.message}")
            null
        }
    }

    /**
     * Converts a Long timestamp (milliseconds since the epoch)
     * to an ISO 8601 formatted date-time string in UTC, including milliseconds.
     *
     * @param timestamp The Long timestamp (milliseconds since 1970-01-01T00:00:00Z).
     * @return The ISO 8601 formatted date-time string.
     *         Example: "2010-07-13T00:00:00.000Z"
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun longToIsoString(timestamp: Long): String {
        val instant = JavaInstant.ofEpochMilli(timestamp)
        return OUTPUT_FORMATTER.format(instant)
    }

}