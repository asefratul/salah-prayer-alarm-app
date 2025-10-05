//package com.example.salat_prayer_alarm_app;
//
//import java.time.LocalDate;
//import java.time.chrono.HijrahDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Locale;
//
//public class HijriDateUtil {
//
//    public static String getHijriDate() {
//        // Get the current date in the Hijri calendar
//        HijrahDate hijrahDate = HijrahDate.now();
//
//        // Create a custom formatter for the desired format
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy G", Locale.ENGLISH)
//                .withChronology(HijrahDate.chronology());
//
//        // Format the Hijri date
//        return formatter.format(hijrahDate);
//    }
//
//    public static void main(String[] args) {
//        String hijriDate = getHijriDate();
//        System.out.println("Hijri Date: " + hijriDate); // Example: Jumada II 3, 1446 AH
//    }
//}
