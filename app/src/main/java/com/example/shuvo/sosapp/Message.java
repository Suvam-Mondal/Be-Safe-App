package com.example.shuvo.sosapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Shuvo on 5/2/2017.
 */
public class Message extends AppCompatActivity {
    Button done;
    EditText message;
    FileOutputStream outmsg;
    String msg=";";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        message = (EditText)findViewById(R.id.message);
        done = (Button)findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                  msg = message.getText().toString();
              if (msg.length()>0)
              {
                File file = new File(getFilesDir(), "message");
                try {
                    outmsg = openFileOutput("message", Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                }

                try {
                    outmsg.write(msg.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(Message.this, "Setup Successful ! Be Safe !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Message.this, MainActivity.class));
             }
                else
                    Toast.makeText(Message.this, "Please enter an emergency message", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
