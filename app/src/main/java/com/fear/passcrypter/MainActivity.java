package com.fear.passcrypter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText pass;
    EditText title;
    MyDBHandler dbHandler;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.pass);
        title = (EditText) findViewById(R.id.title);
        dbHandler =  new MyDBHandler(this, null, null, 2);
        printDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                return true;

            case R.id.about:
                i = new Intent(this,Info.class);
                startActivity(i);
                return true;

            case R.id.clog:
                i = new Intent(this,Log.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Add prod to db
    public void addOnClicked(View view){
        String titleService = title.getText().toString();
        if (titleService.matches("")) {
            Toast.makeText(this, "You did not enter a title", Toast.LENGTH_LONG).show();
            return;
        } else {
            Credentials credential = new Credentials(username.getText().toString(),pass.getText().toString(),title.getText().toString());
            dbHandler.addCredential(credential);
            printDatabase();
        }
    }

    //Delete Prod
    public void deleteOnClicked(View view){
        Intent intent = new Intent(this, Delete.class);
        startActivity(intent);
    }

    public void printDatabase(){
        dbHandler.getWritableDatabase();
        List<Credentials> values = dbHandler.getAllCredentials();
        ListAdapter fearsAdapter = new CustomAdapter(this, (ArrayList<Credentials>) values);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(fearsAdapter);
        username.setText("");
        pass.setText("");
        title.setText("");

        //Intent start on click list item
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int pos = adapterView.getPositionForView(view);
                        //Toast.makeText(MainActivity.this, pos + "", Toast.LENGTH_LONG).show();

                        //Intent for cryption
                        Intent intent = new Intent(MainActivity.this, Crypter.class);
                        intent.putExtra("mainMsg",pos);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

}


