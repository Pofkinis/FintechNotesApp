package com.example.third_assignment_template;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class DeleteNoteActivity extends AppCompatActivity {
    private ListView lvNotes;
    private ArrayAdapter listAdapter;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        this.lvNotes = findViewById(R.id.notes);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.notesList = new ArrayList<String>(sp.getStringSet("notes", new HashSet<String>()));
        this.listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, notesList);
        this.lvNotes.setAdapter(this.listAdapter);
        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedNote = notesList.get(position);
                notesList.remove(position);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.putStringSet("notes", new HashSet<>(notesList));
                editor.apply();
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        notesList.clear();
        this.notesList.addAll((sp.getStringSet("notes", new HashSet<String>())));
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note_activity:
                Intent addActivityIntent = new Intent(getApplicationContext(), com.example.third_assignment_template.AddNoteActivity.class);
                startActivity(addActivityIntent);
                return true;
            case R.id.delete_note_activity:
                Intent mainActivityIntent = new Intent(getApplicationContext(), com.example.third_assignment_template.MainActivity.class);
                startActivity(mainActivityIntent);
                return true;
            case R.id.all_notes:
                Intent mainActivity = new Intent(getApplicationContext(), com.example.third_assignment_template.MainActivity.class);
                startActivity(mainActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
