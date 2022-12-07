package com.doit.justdoit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddNotes;
    private RecyclerView recyclerViewNotes;
    private NotesAdapter notesAdapter;

    private NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDatabase = NoteDatabase.getInstance(getApplication());
        initViews();

        notesAdapter = new NotesAdapter();
        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {

            }
        });

        recyclerViewNotes.setAdapter(notesAdapter);

        /* Для реагирования перемещения обьектов на свайп, нам нужен экземпляр класса
        ItemTouchHelper
    */
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target
                    ) {
                        return false;
                    }

                    @Override
                    public void onSwiped(
                            @NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Note note = notesAdapter.getNotes().get(position);
                        noteDatabase.notesDao().remove(note.getId());
                        showNotes();
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);

        buttonAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void initViews() {
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        buttonAddNotes = findViewById(R.id.buttonAddNote);
    }

    private void showNotes() {
        notesAdapter.setNotes(noteDatabase.notesDao().getNotes());
    }
}