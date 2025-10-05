package ca.pvis.salah_prayer_alarm_app;

public class NamazResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private Timings timings;

        public Timings getTimings() {
            return timings;
        }

        public void setTimings(Timings timings) {
            this.timings = timings;
        }
    }

    public static class Timings {
        private String Fajr;
        private String Dhuhr;
        private String Asr;
        private String Maghrib;
        private String Isha;

        public String getFajr() {
            return Fajr;
        }

        public void setFajr(String fajr) {
            Fajr = fajr;
        }

        public String getDhuhr() {
            return Dhuhr;
        }

        public void setDhuhr(String dhuhr) {
            Dhuhr = dhuhr;
        }

        public String getAsr() {
            return Asr;
        }

        public void setAsr(String asr) {
            Asr = asr;
        }

        public String getMaghrib() {
            return Maghrib;
        }

        public void setMaghrib(String maghrib) {
            Maghrib = maghrib;
        }

        public String getIsha() {
            return Isha;
        }

        public void setIsha(String isha) {
            Isha = isha;
        }
    }
}

