package com.fear.passcrypter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Delete extends AppCompatActivity {

    MyDBHandler dbHandler;
    EditText deleteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAGd", "onCreate active");
        setContentView(R.layout.activity_delete);
        dbHandler = new MyDBHandler(this, null, null, 2);
    }

    public void deleteOnClick(View view){
        Intent i = new Intent(this,MainActivity.class);
        deleteText = (EditText) findViewById(R.id.deleteText);
        String delete = deleteText.getText().toString();
        dbHandler.deleteCredential(delete);
        startActivity(i);
    }

}


