package com.example.xbone.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.xbone.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class Habit extends Fragment {

    private LinearLayout habitLayout;
    private FloatingActionButton saveButton, clearButton;
    private Spinner statusSpinner;
    private EditText frequencyEditText;
    private final List<String> habitNames = new ArrayList<>();
    private final List<String> habitStatuses = new ArrayList<>();
    private final List<String> habitFrequencies = new ArrayList<>();
    private int dayCounter = 1;
    private int habitIndex = 0;

    public Habit() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit, container, false);

        habitLayout = view.findViewById(R.id.habitLayout);
        saveButton = view.findViewById(R.id.saveButton);
        clearButton = view.findViewById(R.id.addHabitButton);
        statusSpinner = view.findViewById(R.id.statusSpinner);
        frequencyEditText = view.findViewById(R.id.frequencyEditText);

        habitNames.add(getString(R.string.strong_bones_diet));
        habitNames.add(getString(R.string.weight_bearing_exercises));
        habitNames.add(getString(R.string.sunlight_vitamin_d));
        habitNames.add(getString(R.string.calcium_rich_foods));
        habitNames.add(getString(R.string.avoid_harmful_habits));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.status_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        displayHabit();

        saveButton.setOnClickListener(v -> {
            if (frequencyEditText.getText().toString().isEmpty() || statusSpinner.getSelectedItem().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                habitStatuses.add(statusSpinner.getSelectedItem().toString());
                habitFrequencies.add(frequencyEditText.getText().toString());

                habitIndex++;
                if (habitIndex < habitNames.size()) {
                    displayHabit();
                } else {
                    displayResults();
                }

                statusSpinner.setSelection(0);
                frequencyEditText.setText("");
            }
        });

        clearButton.setOnClickListener(v -> {
            habitStatuses.clear();
            habitFrequencies.clear();
            habitIndex = 0;
            displayHabit();
            Toast.makeText(getContext(), "Fields Cleared", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void displayHabit() {
        habitLayout.removeAllViews();

        TextView dayTextView = new TextView(getContext());
        dayTextView.setText("Day " + dayCounter);
        dayTextView.setTextSize(24);
        habitLayout.addView(dayTextView);

        if (habitIndex < habitNames.size()) {
            String habitName = habitNames.get(habitIndex);

            TextView titleTextView = new TextView(getContext());
            titleTextView.setText(habitName);
            titleTextView.setTextSize(18);
            titleTextView.setTypeface(null, android.graphics.Typeface.BOLD);
            habitLayout.addView(titleTextView);

            TextView statusTextView = new TextView(getContext());
            statusTextView.setText("Status: " + statusSpinner.getSelectedItem().toString());
            habitLayout.addView(statusTextView);

            TextView frequencyTextView = new TextView(getContext());
            frequencyTextView.setText("Frequency: " + frequencyEditText.getText().toString() + " times");
            habitLayout.addView(frequencyTextView);
        }
    }

    private void displayResults() {
        habitLayout.removeAllViews();

        TextView dayTextView = new TextView(getContext());
        dayTextView.setText("Day " + dayCounter + " - Results");
        dayTextView.setTextSize(24);
        habitLayout.addView(dayTextView);

        for (int i = 0; i < habitNames.size(); i++) {
            TextView habitNameTextView = new TextView(getContext());
            habitNameTextView.setText(habitNames.get(i));
            habitNameTextView.setTextSize(18);
            habitNameTextView.setTypeface(null, android.graphics.Typeface.BOLD);
            habitLayout.addView(habitNameTextView);

            TextView statusTextView = new TextView(getContext());
            statusTextView.setText("Status: " + habitStatuses.get(i));
            habitLayout.addView(statusTextView);

            TextView frequencyTextView = new TextView(getContext());
            frequencyTextView.setText("Frequency: " + habitFrequencies.get(i) + " times");
            habitLayout.addView(frequencyTextView);
        }

        dayCounter++;
        habitIndex = 0;
        Toast.makeText(getContext(), "Welcome to Day " + dayCounter, Toast.LENGTH_SHORT).show();
    }
}
