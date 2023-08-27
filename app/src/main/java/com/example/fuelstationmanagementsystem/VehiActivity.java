package com.example.fuelstationmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VehiActivity extends AppCompatActivity {

    EditText numberplate, type,capacity,capacity2,rmcp;
    Button save,view,update,clear,delete;
    DBhelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehi);

        // Connect Database Helper
        database = new DBhelper(this);

        //txt input
        numberplate=findViewById(R.id.txt_numberplate);
        type=findViewById(R.id.txt_type);
        capacity=findViewById(R.id.txt_capacity);
        capacity2=findViewById(R.id.txt_capacity2);

        //btn
        save=findViewById(R.id.btn_save);
        view=findViewById(R.id.btn_search);
        update=findViewById(R.id.btn_update);
        clear=findViewById(R.id.btn_clear);
        delete=findViewById(R.id.btn_delete);

        //txt out
        rmcp=findViewById(R.id.txt_rmCapacity);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t,n,c;
                n=numberplate.getText().toString();
                t=type.getText().toString();
                c=capacity.getText().toString();
                Cursor a = database.ViewDatef(t);
                if(validationSave()){
                    if(a.getCount()>0){
                        if(database.InsertData(n,t,c)){
                            Toast.makeText(VehiActivity.this, "Vehicle Registration Successfull "+n, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(VehiActivity.this, "Vehicle Registration Un-successfull", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(VehiActivity.this, "Please Enter Valide Fuil Type", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n;
                n=numberplate.getText().toString();
                if(!n.isEmpty()){
                    setData(n);
                }else{
                    Toast.makeText(VehiActivity.this, "Please Enter Vehicle Plate Number!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                Toast.makeText(VehiActivity.this, "This is data Clear", Toast.LENGTH_SHORT).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String np,t,c,c2,st,rm;
                np=numberplate.getText().toString();
                t=type.getText().toString();
                c=capacity.getText().toString();
                c2=capacity2.getText().toString();
                rm=rmcp.getText().toString();

                Cursor a = database.ViewDate(np);
                Cursor q = database.ViewDate(np);
                q.moveToFirst();
                String t2=q.getString(1);
                int rmc=Integer.parseInt(c2);
                if(a.getCount()>0){
                    if(validationSave()){
                        if(capacity2.getText().toString().isEmpty()){
                            if(Integer.parseInt(c)>0){
                                if(database.updateData(np,t,c)){
                                    Toast.makeText(VehiActivity.this, "Vehicle Data Update Successfuly", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(VehiActivity.this, "Vehicle Data Update Un-successfuly", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(VehiActivity.this, "Please Enter Valide Capacity", Toast.LENGTH_SHORT).show();
                                capacity.setText("");
                            }
                        }else{
                            if(Integer.parseInt(c2)<Integer.parseInt(c)){
                                if(0<Integer.parseInt(c2)){
                                    int tot;
                                    tot=Integer.parseInt(rm)-Integer.parseInt(c2);
                                    st=Integer.toString(tot);
                                    Toast.makeText(VehiActivity.this, "Successfuly Entered Remaining Capacity "+st, Toast.LENGTH_SHORT).show();
                                    database.updateRemainCapacity(np,st);
                                    manage(t,Integer.parseInt(c2));
                                }else{
                                    Toast.makeText(VehiActivity.this, "Please Enter Valide Remaining Capacity", Toast.LENGTH_SHORT).show();
                                    capacity2.setText("");
                                }
                            }else{
                                Toast.makeText(VehiActivity.this, "Please Enter Remaining Capacity Less Than "+c+" OR More Than 0", Toast.LENGTH_SHORT).show();
                                capacity2.setText("");
                            }
                        }
                    }
                }else {
                    setData(np);
                }
//                if(t2==t && Integer.parseInt(rm)<=rmc){
//                }else {
//                    Toast.makeText(VehiActivity.this, "Please Enter Valide Fuel Type And Fuel Remaining Capacity", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String np=numberplate.getText().toString();
                Cursor a = database.ViewDate(np);
                if(a.getCount()>0){
                    boolean x = database.Delete(np);
                    if(x){
                        Toast.makeText(VehiActivity.this, "Data Delete Successfully"+np, Toast.LENGTH_SHORT).show();
                        clear();
                    }else {
                        Toast.makeText(VehiActivity.this, "Data Delete Un-successfully", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    setData(np);
                }
            }
        });

    }

    //set data -> feilds
    private void setData(String np) {
        //Boolean x= database.ViewDate(np);
        Cursor c = database.ViewDate(np);
        if(c.getCount()>0){
            c.moveToFirst();
            type.setText(c.getString(1));
            capacity.setText(c.getString(2));
            int g=Integer.parseInt(c.getString(3));
            if (g==0){
                g=Integer.parseInt(c.getString(2));
            }
            capacity2.setText(g+"");
            rmcp.setText(g+"");
        }else{
            Toast.makeText(this, "Please Enter Valid Number Plate", Toast.LENGTH_SHORT).show();
        }
    }

    //Min(Manage) fuel Capacity
    private void manage(String type,int x){
        Cursor c =database.ViewDatef(type);
        c.moveToNext();
        int cp =Integer.parseInt(c.getString(2));
        int t=cp-x;
        database.updateDataf(type,null,t+"");
    }

    //clear fields
    private void clear() {
        numberplate.setText("");
        type.setText("");
        capacity.setText("");
        capacity2.setText("");
        rmcp.setText("0");
    }

    //Validation
    private boolean validationSave() {
        if (numberplate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Vehicle Number Plate", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (type.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Vehicle Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (capacity.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Capacity", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validationUpdate() {
        if (numberplate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Vehicle Number Plate", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (type.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Vehicle Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (capacity.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Capacity", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (capacity2.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter New Capacity", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}