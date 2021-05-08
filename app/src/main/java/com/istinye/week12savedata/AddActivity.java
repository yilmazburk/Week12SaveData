package com.istinye.week12savedata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends Activity {

    private DBManager dbManager;

    private EditText nameEditText;
    private EditText surnameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbManager = new DBManager(this);
        dbManager.open();

        nameEditText = findViewById(R.id.addNameEditText);
        surnameEditText = findViewById(R.id.addSurnameEditText);

        Button insert = findViewById(R.id.insertButton);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Name can not be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbManager.insert(name ,surname);
                Intent turnBackIntent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(turnBackIntent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}