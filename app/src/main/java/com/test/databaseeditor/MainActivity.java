package com.test.databaseeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.databaseeditor.model.StudentAdapter;
import com.test.databaseeditor.model.Students;

import java.util.ArrayList;
@SuppressLint("NotifyDataSetChanged")


public class MainActivity extends AppCompatActivity {

    ArrayList<Students> studentsArrayList;
    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    SearchView id;
    Button insert,update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentsArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler);
        id = findViewById(R.id.id);
        insert = findViewById(R.id.insert);
        update = findViewById(R.id.update);

        studentAdapter = new StudentAdapter(this,studentsArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter.fetchData();
        recyclerView.setAdapter(studentAdapter);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        studentsArrayList.addAll(fetchData());
        Log.d("new list", studentsArrayList.toString());

        insert.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this,InsertActivity.class);
            startActivity(intent);
        });

        update.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
            startActivity(intent);
        });

        id.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                studentsArrayList.clear();
                if(s.isEmpty()){
                    studentAdapter.fetchData();
                    studentAdapter.notifyDataSetChanged();
                }
                else{
                    studentAdapter.fetchOne(s);
                    studentAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

    }
}