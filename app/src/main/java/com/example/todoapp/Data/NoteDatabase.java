package com.example.todoapp.Data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todoapp.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsycTask(instance).execute();
        }
    };

    private static class PopulateDbAsycTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;

        private PopulateDbAsycTask(NoteDatabase db){
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids){
            noteDao.insert(new Note("Developing Mobile Application","Make a Todo App \n Component 2 (40%)",1,"07-06-2020"));
            noteDao.insert(new Note("Digital Security","Report on Ethical Hacking \n Component 3(60%)",2,"08-05-2020"));
            noteDao.insert(new Note("Project Management","Make a Tourism Development Website (60%)",3,"21-07-2020"));
            return null;
        }
    }
}
