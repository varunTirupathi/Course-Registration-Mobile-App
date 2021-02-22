package com.assignment.projectassignment3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CourseDBHelper extends SQLiteOpenHelper {

    // instance variables
    private final static String DB_NAME = "assignment-4.db";
    private final static String TABLE_NAME = "coursesregistration";
    private final static String UNIQUE_COLUMN_ID = "column_id";
    private final static String COURSE_NAME = "course_name";
    private final static String COURSE_ID = "course_ID";
    private final static String PREREQUISITE = "course_prerequisite";
    private final static String TERM = "course_term";
    private final static String REGISTEREDCOURSES = "registered_courses";
    private final static String IMG_URL = "img_url";
    ArrayList<Course> courses;

    public CourseDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createtable = "create table " + TABLE_NAME +
                "(" + UNIQUE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COURSE_NAME + " text, " +
                COURSE_ID + " text, " +
                PREREQUISITE + " text, " +
                TERM + " text, " +
                REGISTEREDCOURSES + " INTEGER DEFAULT 0, " +
                IMG_URL + " text)";
        sqLiteDatabase.execSQL(createtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
        onCreate(db);
    }


    public void insertData(int uniquecolumnid, String coursename, String courseid, String courseprereq, String term, int registeredcourses, String imageUrl) {
        courses = new ArrayList<>();
        System.out.println(courseprereq);
        if (!coursepresent(uniquecolumnid)) {
            SQLiteDatabase db = this.getWritableDatabase();
            String insertq = "INSERT INTO " + TABLE_NAME + " (column_id,course_name, course_ID, course_prerequisite,course_term,registered_courses,img_url)" + " VALUES " +
                    " (" + uniquecolumnid + ",'" + coursename + "','" + courseid + "', '" + courseprereq + "','" + term + "', " + registeredcourses + ", '" + imageUrl + "')";
            db.execSQL(insertq);
            db.close();
        }
    }

    public ArrayList<Course> getTotalAvailableCourses() {
        courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //selct only some columns
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE registered_courses= 1";
        Cursor result = db.rawQuery(query, null);
        result.moveToFirst();
        if (result != null && result.getCount() > 0) {
            do {
                courses.add(new Course(result.getInt(0), result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5)));
            } while (result.moveToNext());
        }
        result.close();
        db.close();
        return courses;
    }

    public ArrayList<Course> registeredCourses() {
        courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //selct only some columns
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE registered_courses= 0";
        Cursor result = db.rawQuery(query, null);
        result.moveToFirst();
        if (result != null && result.getCount() > 0) {
            do {
                courses.add(new Course(result.getInt(0), result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5)));
            } while (result.moveToNext());
        }
        result.close();
        db.close();
        return courses;
    }

    public boolean coursepresent(int id) {
        courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isExist = false;

        String query = "SELECT * FROM " + TABLE_NAME + " where column_id = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        Cursor result = db.rawQuery(query, whereArgs);
        if (result.moveToFirst()) {
            isExist = true;
        }
        result.close();
        db.close();
        return isExist;
    }

    public ArrayList<Course> getallCoursesdata() {
        courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //selct only some columns
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor result = db.rawQuery(query, null);
        result.moveToFirst();
        do {
            courses.add(new Course(result.getInt(0), result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5)));
        } while (result.moveToNext());
        result.close();
        db.close();
        return courses;
    }

    public void deleteCourse(int column_id) {
        courses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " where column_id =?";
        String[] whereArgs = new String[]{String.valueOf(column_id)};
        Cursor result = db.rawQuery(query, whereArgs);
        result.moveToFirst();
        db.close();
    }
}