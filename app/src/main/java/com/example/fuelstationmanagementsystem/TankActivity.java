package com.example.fuelstationmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TankActivity extends AppCompatActivity {
    EditText ftype,fcapacity,frmcapacity;
    Button fsave,fsearch,fclear,fupdate,fdelete;
    DBhelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank);

        ftype=findViewById(R.id.txt_fuel_type);
        fcapacity=findViewById(R.id.txt_fcapacity);
        frmcapacity=findViewById(R.id.txt_frmcapacity);

        fclear=findViewById(R.id.btn_fclear);
        fsave=findViewById(R.id.btn_fsave);
        fsearch=findViewById(R.id.btn_fsearch);
        fupdate=findViewById(R.id.btn_fupdate);
        fdelete=findViewById(R.id.btn_fdelete);

        //Database Set!!
        database = new DBhelper(this);

        fclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        fsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    String t,c;
                    t=ftype.getText().toString();
                    c=fcapacity.getText().toString();
                    if(database.InsertDataf(t,c)){
                        Toast.makeText(TankActivity.this, "Fuel Tank Registration Successfull "+t, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(TankActivity.this, "Fuel Tank Vehicle Registration Un-successfull", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        fsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t;
                t=ftype.getText().toString();
                if(!t.isEmpty()){
                    setDataf(t);
                }else{
                    Toast.makeText(TankActivity.this, "Please Enter Fuel Type", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t,c,rmc;
                t=ftype.getText().toString();
                c=fcapacity.getText().toString();
                rmc=frmcapacity.getText().toString();
                if(validation()){
                    //setDataf(t);
                    Cursor a = database.ViewDatef(t);
                    if(a.getCount()>0){
                        if(database.updateDataf(t,c,rmc)){
                            Toast.makeText(TankActivity.this, "Update Succesfull", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TankActivity.this, "Update Un-succesfull", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        setDataf(t);
                    }
                }
            }
        });
        fdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t;
                t=ftype.getText().toString();
                database.Deletef(t);
                Cursor a = database.ViewDate(t);
                if(a.getCount()>0){
                    boolean x = database.Delete(t);
                    if(x){
                        Toast.makeText(TankActivity.this, "Data Delete Successfully"+t, Toast.LENGTH_SHORT).show();
                        clear();
                    }else {
                        Toast.makeText(TankActivity.this, "Data Delete Un-successfully", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    setDataf(t);
                }
            }
        });

    }

    //set data -> feilds
    private void setDataf(String t) {
        Cursor c = database.ViewDatef(t);
        if(c.getCount()>0){
            c.moveToFirst();
            fcapacity.setText(c.getString(2));
            frmcapacity.setText(c.getString(3));
        }else{
            Toast.makeText(this, "Please Enter Valid Fuel Type", Toast.LENGTH_SHORT).show();
            clear();
        }
    }

    private void clear() {
        ftype.setText("");
        fcapacity.setText("");
        frmcapacity.setText("");
    }

    //Validation
    private boolean validation() {
        if (ftype.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Fuel Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (fcapacity.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Fuel Capacity", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}