package com.myapplicationdev.android.moremovieslesson12;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    Button btnUpdate, btnDelete, btnCancel;
    EditText etId,etTitle, etGenre, etYear;
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etId = findViewById(R.id.etId);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        spinner = (Spinner) this.findViewById(R.id.spn);

        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent i = getIntent();
        final Movie currentMovie = (Movie) i.getSerializableExtra("movie");

        etId.setText(String.valueOf(currentMovie.getId()));
        etTitle.setText(currentMovie.getTitle());
        etGenre.setText(currentMovie.getGenre());
        etYear.setText(currentMovie.getYear()+"");
        spinner.getSelectedItem();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the dialog builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to update the changes?");
                myBuilder.setCancelable(true);

                currentMovie.setTitle(etTitle.getText().toString().trim());
                currentMovie.setGenre(etGenre.getText().toString().trim());
                int year = 0;
                try {
                    year = Integer.valueOf(etYear.getText().toString().trim());
                } catch (Exception e){
                    Toast.makeText(EditActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentMovie.setYear(year);

                currentMovie.setRating(spinner.getSelectedItem().toString());


               /* int result = dbh.updateMovie(currentMovie);
                if (result>0){
                    Toast.makeText(EditActivity.this, "Movie updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }*/
                //configure the 'positive' button
                myBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        DBHelper dbh = new DBHelper(EditActivity.this);
                        int result = dbh.updateMovie(currentMovie);
                        if (result>0){
                            Toast.makeText(EditActivity.this, "Movie updated", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the dialog builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage(String.format("Are you sure you want to delete the movie %s?", currentMovie.getTitle()));
                myBuilder.setCancelable(false);

                //configure the 'negative' button
                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        DBHelper dbh = new DBHelper(EditActivity.this);
                        int result = dbh.deleteMovie(currentMovie.getId());

                        if (result>0){
                            Toast.makeText(EditActivity.this, "Movie deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //configure the 'neutral' button
                myBuilder.setNeutralButton("cancel",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();



            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the dialog builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes?");
                myBuilder.setCancelable(false);

                //configure the 'positive' button
                myBuilder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent i = new Intent(EditActivity.this, ViewActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

                //configure the 'neutral' button
                myBuilder.setNeutralButton("Do not discard",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();


            }
        });
    }
}