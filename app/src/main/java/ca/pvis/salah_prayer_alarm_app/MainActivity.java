package ca.pvis.salah_prayer_alarm_app;

import android.os.Bundle;
import android.os.Looper;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Handler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.List;

import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ProgressBar progressBarSunriseTime;
    private ProgressBar progressBarSunsetTime;
    private Handler dateChangeHandler;
    private Runnable dateCheckRunnable;
    private String currentDate;
    RecyclerView recyclerView;
    TextView sunriseTextView;
    TextView sunsetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        progressBar = findViewById(R.id.progress_bar);
        progressBarSunriseTime = findViewById(R.id.progress_bar_sunrise_time);
        progressBarSunsetTime = findViewById(R.id.progress_bar_sunset_time);
        sunriseTextView = findViewById(R.id.sunrise_time);
        sunsetTextView = findViewById(R.id.sunset_time);
        recyclerView = findViewById(R.id.prayer_times_recycler_view);

        // Authenticate with Firebase
        firebaseAuthenticateAndSetup();
        updateClockAndDate();
        // Monitor date changes
        startDateChangeListener();
    }

    private void updateClockAndDate() {
        TextView clockView = findViewById(R.id.clock_view);
        TextView dateView = findViewById(R.id.date_view);

        Handler clockHandler = new Handler(Looper.getMainLooper()); // Use the main looper
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                String currentDate = new SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(new Date());
                clockView.setText(currentTime);
                dateView.setText(currentDate);
                clockHandler.postDelayed(this, 1000);
            }
        };
        clockHandler.post(runnable);
    }

    private void fetchSunriseAndSunsetFromFirebase(String date) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("prayer_time_list").child(date);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String sunrise = snapshot.child("Sunrise").getValue(String.class);
                String sunset = snapshot.child("Sunset").getValue(String.class);

                sunriseTextView.setText(DateTimeHelper.convertTo12HourFormat(sunrise));
                progressBarSunriseTime.setVisibility(View.GONE);
                sunriseTextView.setVisibility(View.VISIBLE);

                sunsetTextView.setText(DateTimeHelper.convertTo12HourFormat(sunset));
                progressBarSunsetTime.setVisibility(View.GONE);
                sunsetTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to fetch sunrise and sunset times: " + error.getMessage());
            }
        });
    }

    private void fetchPrayerTimesFromFirebase(String date) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("prayer_time_list").child(date);
        List<PrayerTimeModel> prayerTimes = new ArrayList<>();
        // set prayer times heading
        prayerTimes.add(new PrayerTimeModel("", "STARTS", "ADHAN", "IQAMAH", true));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Fetch prayer times
                    String fajrStart = snapshot.child("Fajr Starts").getValue(String.class);
                    String fajrAdhanTime = snapshot.child("Fajr Adhan").getValue(String.class);
                    String fajrIqamaTime = snapshot.child("Fajr Iqama").getValue(String.class);
                    String dhuhrStart = snapshot.child("Dhuhr Starts").getValue(String.class);
                    String dhuhrAdhanTime = snapshot.child("Dhuhr Adhan").getValue(String.class);
                    String dhuhrIqamaTime = snapshot.child("Dhuhr Iqama").getValue(String.class);
                    String asrStart = snapshot.child("Asr Starts").getValue(String.class);
                    String asrAdhanTime = snapshot.child("Asr Adhan").getValue(String.class);
                    String asrIqamaTime = snapshot.child("Asr Iqama").getValue(String.class);
                    String maghribStart = snapshot.child("Maghrib Starts").getValue(String.class);
                    String maghribAdhanTime = snapshot.child("Maghrib Adhan").getValue(String.class);
                    String maghribIqamaTime = snapshot.child("Maghrib Iqama").getValue(String.class);
                    String ishaStart = snapshot.child("Isha Starts").getValue(String.class);
                    String ishaAdhanTime = snapshot.child("Isha Adhan").getValue(String.class);
                    String ishaIqamaTime = snapshot.child("Isha Iqama").getValue(String.class);

                    // Log times
                    Log.d("PrayerTimes", "Fajr: " + fajrStart + ", Dhuhr: " + dhuhrStart + ", Asr: " + asrStart + ", Maghrib: " + maghribStart + ", Isha: " + ishaStart);


                    // detect Jumu‘ah
                    String dhuhrPrayerLabel = "Dhuhr";
                    if (DateTimeHelper.isFriday(date)) {
                        dhuhrPrayerLabel = "Jumu‘ah";
                    }
                    prayerTimes.add(new PrayerTimeModel("Fajr", fajrStart, fajrAdhanTime, fajrIqamaTime, false));
                    prayerTimes.add(new PrayerTimeModel(dhuhrPrayerLabel, dhuhrStart, dhuhrAdhanTime, dhuhrIqamaTime, false));
                    prayerTimes.add(new PrayerTimeModel("Asr", asrStart, asrAdhanTime, asrIqamaTime, false));
                    prayerTimes.add(new PrayerTimeModel("Maghrib", maghribStart, maghribAdhanTime, maghribIqamaTime, false));
                    prayerTimes.add(new PrayerTimeModel("Isha", ishaStart, ishaAdhanTime, ishaIqamaTime, false));

                    // Update RecyclerView
                    populateRecyclerView(prayerTimes);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.e("PrayerTimes", "No data found for date: " + date);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("FirebaseError", "Failed to fetch prayer times: " + error.getMessage());
            }
        });
    }

    private void populateRecyclerView(List<PrayerTimeModel> prayerTimes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PrayerTimeAdapter adapter = new PrayerTimeAdapter(prayerTimes);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void firebaseAuthenticateAndSetup() {
        recyclerView.setVisibility(View.GONE);
        sunriseTextView.setVisibility(View.GONE);
        sunsetTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        progressBarSunriseTime.setVisibility(View.VISIBLE);
        progressBarSunsetTime.setVisibility(View.VISIBLE);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInAnonymously()
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Log.d("FirebaseAuth", "Anonymous sign-in successful.");
                    // Set up Prayer Times RecyclerView
                    currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    fetchSunriseAndSunsetFromFirebase(currentDate);
                    fetchPrayerTimesFromFirebase(currentDate);
                    // Set up Announcement Ticker
                    setupAnnouncementTicker();
                } else {
                    Log.e("FirebaseAuth", "Anonymous sign-in failed.", task.getException());
                }
            });
    }

    private void setupAnnouncementTicker() {
        TextView announcementTicker = findViewById(R.id.announcement_ticker);
        announcementTicker.setSelected(true);

        Log.d("Setting up announcement", "Fetching firebase database");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("announcements");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String announcement = snapshot.getValue(String.class);
                if (announcement != null) {
                    Spanned spannedAnnouncementText = HtmlCompat.fromHtml(announcement, HtmlCompat.FROM_HTML_MODE_LEGACY);
                    announcementTicker.setText(spannedAnnouncementText);
                    announcementTicker.setMovementMethod(LinkMovementMethod.getInstance());
//                    announcementTicker.setText(announcement);
                } else {
                    announcementTicker.setText(R.string.text_announcement_ticker_text_not_found);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
                announcementTicker.setText("");
            }
        });
    }

    private void startDateChangeListener() {
        dateChangeHandler = new Handler(Looper.getMainLooper());
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()); // Initial date
        Log.d("Current Date", currentDate);
        dateCheckRunnable = new Runnable() {
            @Override
            public void run() {
                String newDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                Log.d("New Date", newDate);

                if (!newDate.equals(currentDate)) {
                    Log.d("Fetching Data again", "From Firebase");
                    recyclerView.setVisibility(View.GONE);
                    sunsetTextView.setVisibility(View.GONE);
                    sunsetTextView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    progressBarSunriseTime.setVisibility(View.VISIBLE);
                    progressBarSunsetTime.setVisibility(View.VISIBLE);

                    currentDate = newDate;
                    fetchSunriseAndSunsetFromFirebase(currentDate);
                    fetchPrayerTimesFromFirebase(currentDate);
                }
                dateChangeHandler.postDelayed(this, 60 * 1000); // Check every minute
            }
        };

        dateChangeHandler.post(dateCheckRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dateChangeHandler != null) {
            dateChangeHandler.removeCallbacks(dateCheckRunnable);
        }
    }
}
