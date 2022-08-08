package com.myapplicationdev.android.moremovieslesson12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NOTE = "Movie";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_RATING = "rating";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT, " + COLUMN_GENRE + " TEXT, " + COLUMN_YEAR + " INTEGER," + COLUMN_RATING + " INTEGER) ";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");

        //Dummy records, to be inserted when the database is created
       /* for (int i = 0; i< 4; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOTE_CONTENT, "Data number " + i);
            db.insert(TABLE_NOTE, null, values);
        }
        Log.i("info", "dummy records inserted");*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_NOTE + " ADD COLUMN  module_name TEXT ");       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(db);

    }

    public long insertMovie(String title, String genre, int year, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_RATING, rating);
        long result = db.insert(TABLE_NOTE, null, values);
        if (result == -1) {
            Log.d("DBHelper", "Insert failed");
        }
        Log.d("SQL Insert", "ID:" + result); //id returned, shouldn’t be -1
        db.close();
        return result;
    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movielist = new ArrayList<Movie>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE + "," + COLUMN_GENRE + ","
                + COLUMN_YEAR + ","
                + COLUMN_RATING + " FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getReadableDatabase();

        // String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_YEAR, COLUMN_STARS};
        // Cursor cursor = db.query(TABLE_NOTE, columns, null, null,
        //       null, null, null, null);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String movieTitle = cursor.getString(1);
                String genre = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4);
                Movie newMovie = new Movie(id, movieTitle, genre, year, rating);
                movielist.add(newMovie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movielist;
    }

    public ArrayList<String> getRatings() {
        ArrayList<String> codes = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_RATING};

        Cursor cursor;
        cursor = db.query(true, TABLE_NOTE, columns, null, null, null, null, null, null);
        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                codes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return codes;
    }

   public ArrayList<Movie> getAllMoviesByRating(String i) {
        ArrayList<Movie> songs = new ArrayList<Movie>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
        String condition = COLUMN_RATING + " = ?";
        String[] args = {i};
        Cursor cursor = db.query(TABLE_NOTE, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String movieTitle = cursor.getString(1);
                String genre = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4);
                Movie m = new Movie(id,movieTitle, genre, year, rating);
                songs.add(m);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public int updateMovie(Movie data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_GENRE, data.getGenre());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_RATING, data.getRating());
        String condition = COLUMN_ID + "= ?"; //? is substituted by a string
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_NOTE, values, condition, args);
        if (result < 1) {
            Log.d("DBHelper", "Update failed");
        }

        db.close();
        return result;
    }

    public int deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NOTE, condition, args);
        if (result < 1) {
            Log.d("DBHelper", "Delete failed");
        }
        db.close();
        return result;
    }

}
