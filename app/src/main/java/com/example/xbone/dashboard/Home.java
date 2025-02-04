package com.example.xbone.dashboard;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xbone.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Home extends Fragment {

    public Home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        List<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.img1, "Weight-bearing", "6:00 AM every day"));
        items.add(new Item(R.drawable.img2, "Resistance training", "7:00 AM every day"));
        items.add(new Item(R.drawable.img3, "Jumping exercises:", "8:00 AM every day"));
        items.add(new Item(R.drawable.img5, "Yoga", "12:00 AM every day"));
        items.add(new Item(R.drawable.img4, "Swimming", "4:00 PM every day"));
        items.add(new Item(R.drawable.img6, "Pilates", "5:00 PM every day"));
        HorizontalAdapter adapter = new HorizontalAdapter(items);
        recyclerView.setAdapter(adapter);

        EditText hourEditText = view.findViewById(R.id.hourEditText);
        EditText minuteEditText = view.findViewById(R.id.minuteEditText);
        Spinner amPmSpinner = view.findViewById(R.id.amPmSpinner);
        Button setAlarmButton = view.findViewById(R.id.setAlarmButton);

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(requireContext(),
                R.array.am_pm_array, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amPmSpinner.setAdapter(adapterSpinner);

        setAlarmButton.setOnClickListener(v -> {
            try {
                String hourText = hourEditText.getText().toString().trim();
                String minuteText = minuteEditText.getText().toString().trim();

                if (hourText.isEmpty() || minuteText.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter both hour and minute", Toast.LENGTH_SHORT).show();
                    return;
                }

                int hour = Integer.parseInt(hourText);
                int minute = Integer.parseInt(minuteText);

                if (hour < 1 || hour > 12 || minute < 0 || minute > 59) {
                    Toast.makeText(requireContext(), "Invalid time entered. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String amPm = amPmSpinner.getSelectedItem().toString();

                if (amPm.equals("PM") && hour != 12) {
                    hour += 12;
                } else if (amPm.equals("AM") && hour == 12) {
                    hour = 0;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }

                AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
                if (alarmManager == null) {
                    Toast.makeText(requireContext(), "AlarmManager not available", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(requireContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                String timeSet = String.format("%02d:%02d %s",
                        (hour > 12 ? hour - 12 : hour == 0 ? 12 : hour),
                        minute, amPm);
                Toast.makeText(requireContext(), "Alarm set for " + timeSet, Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(requireContext(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
