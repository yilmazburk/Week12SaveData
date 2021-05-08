package com.istinye.week12savedata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button button;
    private Button saveToFile;
    private Button readFromFile;
    private LinearLayout noDataLayout;

    private ListView studentListView;
    private SimpleCursorAdapter adapter;

    private DBManager dbManager;

    public static final String SHARED_PREF_KEY = "NAME_DATA_KEY";
    public static final String filename = "week12.txt";

    public static final int[] viewIds = new int[] { R.id.recordId, R.id.recordName, R.id.recordSurname};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        dbManager.open();

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        saveToFile = findViewById(R.id.saveToFile);
        readFromFile = findViewById(R.id.readFromFile);
        noDataLayout = findViewById(R.id.noDataLayout);


        studentListView = findViewById(R.id.myListView);
        studentListView.setEmptyView(noDataLayout);
        Cursor fetchedCursor = dbManager.fetch();
        adapter = new SimpleCursorAdapter(this, R.layout.layout_list_item, fetchedCursor, DBManager.columnNames, viewIds);
        studentListView.setAdapter(adapter);
        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView idTextView = view.findViewById(R.id.recordId);
                TextView nameTextView = view.findViewById(R.id.recordName);
                TextView surnameTextView = view.findViewById(R.id.recordSurname);

                String id = idTextView.getText().toString();
                String name = nameTextView.getText().toString();
                String surname = surnameTextView.getText().toString();

                Intent updateIntent = new Intent(MainActivity.this, ModifyActivity.class);
                updateIntent.putExtra("ID", id);
                updateIntent.putExtra("NAME", name);
                updateIntent.putExtra("SURNAME", surname);
                startActivity(updateIntent);
            }
        });





        SharedPreferences mySharedPreferences = getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
        String data = mySharedPreferences.getString(SHARED_PREF_KEY, "");
        if (!data.isEmpty()) {
            textView.setText("Welcome, " + data);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                if (name.isEmpty()) return;

                textView.setText("Welcome, " + name);

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SHARED_PREF_KEY, name);
                editor.commit();

                editText.setText("");
            }
        });


        saveToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                if (name.isEmpty()) return;

                try {
                    FileOutputStream fileOutputStream = openFileOutput(filename, MODE_PRIVATE);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    outputStreamWriter.write(name);
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), filename + " File not found.", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "An error occurred while writing data to file.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        readFromFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    FileInputStream fileInputStream = openFileInput(filename);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader reader = new BufferedReader(inputStreamReader);

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    reader.close();
                    textView.setText("Welcome, " + stringBuilder.toString());

                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), filename + " File not found.", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "An error occurred while reading data on file.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        //Important point
        dbManager.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.insert) {
            Intent insertIntent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(insertIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}