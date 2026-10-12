package com.example.tripbuddyv10;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class plan_trip extends AppCompatActivity {
   private DatePickerDialog datePickerDialog;
    private DatePickerDialog startDatePickerDialog;

    EditText destination;
    EditText transport;
    EditText meal;
    EditText rent;
    EditText activities;
    Button btnplanTrip;
    EditText extra;
    Button save;
    Button start;
    Button end;
    TextView results;
    DatePicker datePicker;
    int total;
    RecyclerView rv;

    TextView info;
    MyDatabaseHelper myDB;

    private static final String PREFS_NAME = "TripBuddyPrefs";
    private static final String KEY_TRIP_COUNT = "trip_count";
    SharedPreferences sp3;

    ArrayList<String> id, destination1, transport1, rent1, meal1, activities1, addtional_cost;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plan_trip);
        Spinner aspinner = findViewById(R.id.spinner);
        btnplanTrip = findViewById(R.id.planTrip);
        extra = findViewById(R.id.addExp);
        results = findViewById(R.id.cal);
        destination = findViewById(R.id.destination);
        transport= findViewById(R.id.transport);
        activities= findViewById(R.id.activities);
        rent = findViewById(R.id.rent);
        meal = findViewById(R.id.meal);
        save = findViewById(R.id.saveTrip);
        start = findViewById(R.id.startDate);
        end = findViewById(R.id.endDate);
        datePicker = findViewById(R.id.datePick1);

        sp3 = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day, month, year;
                day = String.valueOf(datePicker.getDayOfMonth());
                month = String.valueOf(datePicker.getMonth() + 1);
                year = String.valueOf(datePicker.getYear());
                Toast.makeText(plan_trip.this, day + "/" + month +"/" + year,Toast.LENGTH_SHORT).show();

            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day, month, year;
                day = String.valueOf(datePicker.getDayOfMonth());
                month = String.valueOf(datePicker.getMonth() + 1);
                year = String.valueOf(datePicker.getYear());
                Toast.makeText(plan_trip.this, day + "/" + month +"/" + year,Toast.LENGTH_SHORT).show();

            }
        });







        aspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(plan_trip.this, "Item Selected", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Sightseeing");
        arrayList.add("Hiking");
        arrayList.add("Dining");
        arrayList.add("Museum Tours");
        arrayList.add("Music Concert");
        arrayList.add("Festival");
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice);
        aspinner.setAdapter(adapter);






        btnplanTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                calculate();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(plan_trip.this);
                myDB.planTrip(destination.getText().toString().trim(),
                        Integer.valueOf(transport.getText().toString().trim()),
                        Integer.valueOf(rent.getText().toString().trim()),
                        Integer.valueOf(meal.getText().toString().trim()),
                        Integer.valueOf(activities.getText().toString().trim()),
                        Integer.valueOf(extra.getText().toString().trim())


                );


            }
        });
        myDB = new MyDatabaseHelper(plan_trip.this);
        id = new ArrayList<>();
        destination1 = new ArrayList<>();
        transport1 = new ArrayList<>();
        rent1 = new ArrayList<>();
        meal1 = new ArrayList<>();
        activities1 = new ArrayList<>();
        addtional_cost = new ArrayList<>();
        tripInfo();


    }
    void tripInfo() {
        Cursor cursor = myDB.showData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                destination1.add(cursor.getString(0));
                transport1.add(cursor.getString(0));
                rent1.add(cursor.getString(0));
                meal1.add(cursor.getString(0));
                activities1.add(cursor.getString(0));
                addtional_cost.add(cursor.getString(0));
            }
        }
        cursor.close();


    }



        String extraExp = extra.getText().toString().trim();
        String meals = meal.getText().toString().trim();
        String rental = rent.getText().toString().trim();
        String act = activities.getText().toString().trim();
        String trans = transport.getText().toString().trim();



    private void calculate() {
        double number1 = 0;
        double number2 = 0;
        double number3 = 0;
        double number4 = 0;
        double number = 0;
        int trips = 0;
        trips = trips + 1;
        int currentCount = sp3.getInt(KEY_TRIP_COUNT, 0);
        int newCount = currentCount + 1;

        SharedPreferences.Editor editor = sp3.edit();
        editor.putInt(KEY_TRIP_COUNT, newCount);
        editor.apply();

        if (extraExp.isEmpty()) {
            number = Double.parseDouble(extraExp);
            if (number < 0) {
                Toast.makeText(this, "Number cannot be less than 0", Toast.LENGTH_SHORT).show();

            }
        } else if (meals.isEmpty()) {
            number1 = Double.parseDouble(meals);
            if (number1 < 0) {
                Toast.makeText(this, "Number cannot be less than 0", Toast.LENGTH_SHORT).show();
            }


        } else if (rental.isEmpty()) {
            number2 = Double.parseDouble(rental);
            if (number2 < 0) {
                Toast.makeText(this, "Number cannot be less than 0", Toast.LENGTH_SHORT).show();
            }


        } else if (trans.isEmpty()) {
            number3 = Double.parseDouble(trans);
            if (number3 < 0) {
                Toast.makeText(this, "Number cannot be less than 0", Toast.LENGTH_SHORT).show();
            }
        } else if (act.isEmpty()) {
            number4 =Double.parseDouble(act);
            if (number4 < 0) {
                Toast.makeText(this, "Number cannot be less than 0", Toast.LENGTH_SHORT).show();
            }
        } else {
            double subtotal = 0;
            subtotal = number+number1+number2+number3+number4 ;
            int tripCount = sp3.getInt(KEY_TRIP_COUNT, 0);

            double amount = 0;
            double discount =0.10;
            double totalDiscount = subtotal * discount;
            if (tripCount >= 3) {

                amount = subtotal - totalDiscount;
            } else {
                amount = subtotal;
            }
            results.setText(
                    "Subtotal: R" + subtotal + "\n" +
                            "Discount: R" + totalDiscount + "\n" +
                            "Amount: R" + amount
            );



        }


    }}