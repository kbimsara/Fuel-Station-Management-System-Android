package com.example.fuelstationmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {

    DBhelper database;
    TextView num_vehicle,mon_capacity,num_tank,tank_cap;

    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //DataBase
        database=new DBhelper(this);


        num_vehicle=findViewById(R.id.out_numberofvehicle);
        mon_capacity=findViewById(R.id.out_moncapacity);
        num_tank=findViewById(R.id.out_numberoftank);
        tank_cap=findViewById(R.id.out_tankcapacity);

        reset=findViewById(R.id.btn_reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        count_v();
        count_f();

        
    }

    private void reset() {
        Cursor cursor=database.View();
        String g="0";
        if(cursor.getCount()==0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while (cursor.moveToNext()){
                if (database.updateRemainCapacity(cursor.getString(0), "0")){
                    Toast.makeText(this, "Vehicle QT Reset", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Vehicle QT Reset Un-Successfull", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void count_v(){
        Cursor c =database.View();
        int n,vtot;
        //Toast.makeText(this, n+"", Toast.LENGTH_SHORT).show();
        vtot=0;
        n=0;

        if(c.getCount()==0){
            Toast.makeText(this, "No Vehicle Data", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while (c.moveToNext()){
                n=n+1;
                vtot=Integer.parseInt(c.getString(2))+vtot;
            }
        }
        num_vehicle.setText(n+"");
        mon_capacity.setText(vtot+"");
    }
    public void count_f(){
        Cursor c =database.Viewf();
        int a,ftot;
        //Toast.makeText(this, n+"", Toast.LENGTH_SHORT).show();
        ftot=0;
        a=0;

        if(c.getCount()==0){
            Toast.makeText(this, "No Tank Data", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while (c.moveToNext()){
                a=a+1;
                ftot=Integer.parseInt(c.getString(2))+ftot;
            }
        }
        //Toast.makeText(this, "dd"+a, Toast.LENGTH_SHORT).show();
        num_tank.setText(a+"");
        tank_cap.setText(ftot+"");
    }
}