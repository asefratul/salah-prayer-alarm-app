package ca.pvis.salah_prayer_alarm_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PrayerTimeAdapter extends RecyclerView.Adapter<PrayerTimeAdapter.PrayerTimeViewHolder> {

    private List<PrayerTimeModel> prayerTimes;

    public PrayerTimeAdapter(List<PrayerTimeModel> prayerTimes) {
        this.prayerTimes = prayerTimes;
    }

    @NonNull
    @Override
    public PrayerTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prayer_time, parent, false);
        return new PrayerTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrayerTimeViewHolder holder, int position) {
        PrayerTimeModel prayerTime = prayerTimes.get(position);
        holder.prayerName.setText(prayerTime.getName());
        holder.startTime.setText(prayerTime.getStartTime());
        holder.athanTime.setText(prayerTime.getAdhanTime());
        holder.iqamahTime.setText(prayerTime.getIqamahTime());
    }

    @Override
    public int getItemCount() {
        return prayerTimes.size();
    }

    static class PrayerTimeViewHolder extends RecyclerView.ViewHolder {
        TextView prayerName, startTime, athanTime, iqamahTime;

        public PrayerTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            prayerName = itemView.findViewById(R.id.prayer_name);
            startTime = itemView.findViewById(R.id.prayer_start_time);
            athanTime = itemView.findViewById(R.id.prayer_adhan_time);
            iqamahTime = itemView.findViewById(R.id.prayer_iqamah_time);
        }
    }
}
