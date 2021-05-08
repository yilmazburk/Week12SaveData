package com.istinye.week12savedata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyActivity extends Activity {

    private EditText nameEditText;
    private EditText surnameEditText;

    private Button updateButton;
    private Button deleteButton;

    private DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        dbManager = new DBManager(this);
        dbManager.open();

        nameEditText = findViewById(R.id.modifyNameEditText);
        surnameEditText = findViewById(R.id.modifySurnameEditText);

        updateButton = findViewById(R.id.modifyButton);
        deleteButton = findViewById(R.id.deleteButton);

        Intent updateIntent = getIntent();
        String id = updateIntent.getStringExtra("ID");
        final String name = updateIntent.getStringExtra("NAME");
        String surname = updateIntent.getStringExtra("SURNAME");

        final long _id =  Long.valueOf(id);
        nameEditText.setText(name);
        surnameEditText.setText(surname);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = nameEditText.getText().toString();
                String newSurname = surnameEditText.getText().toString();

                if (newName.isEmpty()) {
                    Toast.makeText(ModifyActivity.this, "Name can not be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbManager.update(_id, newName, newSurname);
                returnToMain();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.delete(_id);
                returnToMain();
            }
        });

    }

    private void returnToMain(){
        Intent returnToMain = new Intent(ModifyActivity.this, MainActivity.class);
        startActivity(returnToMain);
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}