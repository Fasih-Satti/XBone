package com.example.xbone.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.xbone.R;

import java.util.ArrayList;
import java.util.List;

public class Diet extends Fragment {

    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    DataClass androidData;
    SearchView searchView;
    FloatingActionButton fabHabit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.search);
        fabHabit = view.findViewById(R.id.fab_habit);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();

        androidData = new DataClass("Strong Bones Diet", R.string.strong_bones_diet, "Diet", R.drawable.image1);
        dataList.add(androidData);

        androidData = new DataClass("Weight Bearing Exercises", R.string.weight_bearing_exercises, "Exercise", R.drawable.image2);
        dataList.add(androidData);

        androidData = new DataClass("Sunlight Vitamin D", R.string.sunlight_vitamin_d, "Health", R.drawable.image3);
        dataList.add(androidData);

        androidData = new DataClass("Calcium Rich Foods", R.string.calcium_rich_foods, "Nutrition", R.drawable.image4);
        dataList.add(androidData);

        androidData = new DataClass("Avoid Harmful Habits", R.string.avoid_harmful_habits, "Lifestyle", R.drawable.image5);
        dataList.add(androidData);

        adapter = new MyAdapter(getContext(), dataList);
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void searchList(String text) {
        List<DataClass> dataSearchList = new ArrayList<>();
        for (DataClass data : dataList) {
            if (data.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()) {
            Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }
    }
}