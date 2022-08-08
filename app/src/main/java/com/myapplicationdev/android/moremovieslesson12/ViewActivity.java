package com.myapplicationdev.android.moremovieslesson12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Movie> movieList;
    //ArrayAdapter<Song> adapter;

    CustomAdapter adapter;

    ArrayList<String> ratings;
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(this);
        movieList.clear();
        movieList.addAll(dbh.getAllMovies());
        adapter.notifyDataSetChanged();

        ratings.clear();
        ratings.addAll(dbh.getRatings());
        spinnerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        lv = (ListView) this.findViewById(R.id.lv);
        spinner = (Spinner) this.findViewById(R.id.spinner2);

        DBHelper dbh = new DBHelper(this);
        movieList = dbh.getAllMovies();

        ratings = new ArrayList<>();
        ratings.addAll(dbh.getRatings());
        dbh.close();

        adapter = new CustomAdapter(this, R.layout.row, movieList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ViewActivity.this, EditActivity.class);
                i.putExtra("movie", movieList.get(position));
                startActivity(i);
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ratings);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(ViewActivity.this);
                movieList.clear();
                movieList.addAll(dbh.getAllMoviesByRating(ratings.get(position)));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}