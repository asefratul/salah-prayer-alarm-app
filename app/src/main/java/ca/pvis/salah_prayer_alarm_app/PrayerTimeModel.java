package ca.pvis.salah_prayer_alarm_app;

import java.util.Date;

public class PrayerTimeModel {
    private String name;
    private String startTime;
    private String adhanTime;
    private String iqamahTime;
    private Boolean header;

    public PrayerTimeModel(String name, String startTime, String adhanTime, String iqamahTime, Boolean header) {
        this.name = name;
        this.startTime = startTime;
        this.adhanTime = adhanTime;
        this.iqamahTime = iqamahTime;
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        if (this.header) {
            return startTime;
        }
        return DateTimeHelper.convertTo12HourFormat(startTime);
    }

    public String getAdhanTime() {
        if (this.header) {
            return adhanTime;
        }
        return DateTimeHelper.convertTo12HourFormat(adhanTime);
    }

    public String getIqamahTime() {
        if (this.header) {
            return iqamahTime;
        }
        return DateTimeHelper.convertTo12HourFormat(iqamahTime);
    }
}
