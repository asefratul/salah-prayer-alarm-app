package ca.pvis.salah_prayer_alarm_app;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeHelper {
    public static String convertTo12HourFormat(String time24Hour) {
        try {
            // Parse the 24-hour time string
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm", Locale.US); // 24-hour format
            Date date = inputFormat.parse(time24Hour);

            // Format it into 12-hour time with AM/PM
            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a", Locale.US); // 12-hour format
            assert date != null;
            return outputFormat.format(date);
        } catch (Exception e) {
            Log.e("DateTimerHelperError", "Error converting hour format: " + time24Hour, e);
            return null;
        }
    }

    public static boolean isFriday(String dateStr) {
        try {
            // Locale-aware date format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US); // Ensure Locale.US for standard parsing
            Date date = sdf.parse(dateStr);

            Calendar calendar = Calendar.getInstance();
            assert date != null;
            calendar.setTime(date);

            // Check if it's Friday
            return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;

        } catch (Exception e) {
            Log.e("DateTimerHelperError", "Error parsing date: " + dateStr, e);
            return false;
        }
    }
}
