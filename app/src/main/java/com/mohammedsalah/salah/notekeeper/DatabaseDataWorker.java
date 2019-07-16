package com.mohammedsalah.salah.notekeeper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseDataWorker {
    private SQLiteDatabase mDb;

    public DatabaseDataWorker(SQLiteDatabase db) {
        mDb = db;
    }

    public void insertCourses() {
        insertCourse("A_I", "Artificial Intelligence & Machine Learning");
        insertCourse("android_prog", "Android development courses");
        insertCourse("ios_prog", "ios development courses");
        insertCourse("web_dev", " Web Development courses");
        insertCourse("web_des", "Web Design courses");
        insertCourse("c_programming","C & C++ Programming courses");
        insertCourse("game_dev" , "Game Development courses");
        insertCourse("Ui","UI & UX courses");
        insertCourse("design" , "PhotoShop & Illustrator courses");
        insertCourse("flutter","Flutter platform Cross courses ");
        insertCourse("algorithms & data structure","Algorithms & Data structure courses");
        insertCourse("database","DataBase Courses");
        insertCourse("Security","Cyber Security courses");
        insertCourse("Languages" , "Language Courses");
        insertCourse("programming" ,"programming basics");
        insertCourse("soft skills","Soft Skills courses");
        insertCourse("icdl","ICDL courses");
        insertCourse("Networks","Networks courses");
        insertCourse("p_l","Programming Languages");
        insertCourse("hr","HR courses");
        insertCourse("mangement","Management courses");
    }

    public void insertSampleNotes() {
    }

    private void insertCourse(String courseId, String title) {
        ContentValues values = new ContentValues();
        values.put(NoteKeeperDatabaseContract.CourseInfoEntry.COLUMN_COURSE_ID, courseId);
        values.put(NoteKeeperDatabaseContract.CourseInfoEntry.COLUMN_COURSE_TITLE, title);

        long newRowId = mDb.insert(NoteKeeperDatabaseContract.CourseInfoEntry.TABLE_NAME, null, values);
    }

     private void insertNote(String courseId, String title, String text) {
        ContentValues values = new ContentValues();
        values.put(NoteKeeperDatabaseContract.NoteInfoEntry.COLUMN_COURSE_ID, courseId);
        values.put(NoteKeeperDatabaseContract.NoteInfoEntry.COLUMN_NOTE_TITLE, title);
        values.put(NoteKeeperDatabaseContract.NoteInfoEntry.COLUMN_NOTE_TEXT, text);

        long newRowId = mDb.insert(NoteKeeperDatabaseContract.NoteInfoEntry.TABLE_NAME, null, values);
    }

}
