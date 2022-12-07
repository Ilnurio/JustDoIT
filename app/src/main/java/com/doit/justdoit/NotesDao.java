package com.doit.justdoit;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//Dao - Data access object
@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Note> getNotes();

    @Insert
    void add(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void remove(int id);
}
