package com.example.fuelstationmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class VehiListActivity extends AppCompatActivity {

    EditText search_np;
    Button seach_btn;
    RecyclerView recyclerView;
    ArrayList<String> number_plate,type,capacity,rm_capacity;
    DBhelper database;
    DataModel datamodel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehi_list);


        RecyclerView re;

        database=new DBhelper(this);

        number_plate=new ArrayList<>();
        type=new ArrayList<>();
        capacity=new ArrayList<>();
        rm_capacity=new ArrayList<>();
        recyclerView=findViewById(R.id.rview);

        datamodel=new DataModel(this,number_plate,type,capacity,rm_capacity);
        recyclerView.setAdapter(datamodel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata(null);

    }



    private void displaydata(String np) {
        Cursor cursor;
        if (np==null){
            cursor=database.View();
        }else {
            number_plate.clear();
            type.clear();
            capacity.clear();
            rm_capacity.clear();
            cursor=database.View2(np);
        }
        if(cursor.getCount()==0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while (cursor.moveToNext()){
                number_plate.add(cursor.getString(0));
                type.add(cursor.getString(1));
                capacity.add(cursor.getString(2));
                rm_capacity.add(cursor.getString(3));
            }
        }
    }
}