package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID=
            "com.example.todoapp.EXTRA_ID";
    public static final String EXTRA_TITLE=
            "com.example.todoapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION=
            "com.example.todoapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY=
            "com.example.todoapp.EXTRA_PRIORITY";
    public static final String EXTRA_DATE=
            "com.example.todoapp.EXTRA_DATE";


    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    private EditText editTextDate;
    DatePickerDialog mDatePicker;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        editTextDate=findViewById(R.id.edit_text_date);

        editTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DisplayTestDate();

            }
        });

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(3);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

       Intent intent = getIntent();
       if(intent.hasExtra(EXTRA_ID)){
           setTitle("Edit Note");
           editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
           editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
           numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
           editTextDate.setText(intent.getStringExtra(EXTRA_DATE));

       }else {
           setTitle("Add Note");
       }
    }




    private void saveNote(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();
        String date = editTextDate.getText().toString();



        if (title.trim().isEmpty() || description.trim().isEmpty() || date.trim().isEmpty()){
            Toast.makeText(this, "Please insert the field" , Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data= new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);
        data.putExtra(EXTRA_DATE,date);


        int id= getIntent().getIntExtra(EXTRA_ID, -1);
        if (id !=-1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    void DisplayTestDate(){

        Calendar calendar= Calendar.getInstance();
        int cDay=calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth=calendar.get(Calendar.MONTH);
        int cYear=calendar.get(Calendar.YEAR);

        mDatePicker= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editTextDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
            }
        },cYear,cMonth,cDay);
        mDatePicker.show();
    }

}
