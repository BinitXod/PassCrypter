package com.fear.passcrypter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Crypter extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    MyDBHandler dbHandler;
    Handler handler = new Handler();
    int progress = 0, keyx;
    Credentials credential;
    ProgressBar progressBar;
    Switch switch1= null;
    boolean sc = false;
    TextView title3;
    String key, pass, epass, dpass;
    int mainMsg;

    private static final String TAG = "com.fear.passcrypter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypter);
        dbHandler =  new MyDBHandler(this, null, null, 3);
        Bundle mainsData = getIntent().getExtras();
        if (mainsData == null)
            return;
        mainMsg = mainsData.getInt("mainMsg");
        /*int foo = 0;
        try{
            foo = Integer.parseInt(mainMsg);
            Log.d("TAG", "Trying to parse...");
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
            Log.d("TAG", "FAIL");
        }
        */
        TextView title = (TextView) findViewById(R.id.title);
        TextView title2 = (TextView) findViewById(R.id.title2);
        title3 = (TextView) findViewById(R.id.title3);
        Switch switch1 = (Switch)  findViewById(R.id.switch1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        title.setMovementMethod(new ScrollingMovementMethod());
        title2.setMovementMethod(new ScrollingMovementMethod());
        title3.setMovementMethod(new ScrollingMovementMethod());

        dbHandler.getWritableDatabase();
        List<Credentials> values = dbHandler.getAllCredentials();
        credential = values.get(mainMsg);
        title.setText(credential.get_title());
        title2.setText(credential.get_username());
        title3.setText(credential.get_pass());
        sc = credential.get_switchcond();
        switch1.setChecked(sc);
        //this has to be called after setChecked bcz it sees setChecked same as user input.
        switch1.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {
            switch1 = (Switch)  findViewById(R.id.switch1);
            switch1.setEnabled(false);
            progress=0;
            sc = false;
            title3 = (TextView) findViewById(R.id.title3);
            EditText key1 = (EditText) findViewById(R.id.key1);
            pass = title3.getText().toString();
            key = key1.getText().toString();
            if ((key.matches(""))||(!key.matches("[0-9]+"))||(key.length() > 4)) {
                //key = "0";
                switch1.setChecked(false);
                Toast.makeText(this, "Invalid key, should be 0-9999", Toast.LENGTH_LONG).show();
                sc = false;
                switch1.setEnabled(true);
                return; //break out of function
            }
            keyx = Integer.parseInt(key);
            epass=new String();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //while (progress< 100){

                        for (int i = 0; i < pass.length(); i++) {
                            if(i%2==0)
                                epass += (char) (pass.charAt(i) + keyx++ + 2);
                            else
                                epass += (char) (pass.charAt(i) + keyx++ - 5);
                            progress=progress+100/pass.length();
                            android.os.SystemClock.sleep(500);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(progress);
                                }
                            });
                        }

                        //progress++
                        //android.os.SystemClock.sleep(500);
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                title3.setText(epass);
                                progressBar.setProgress(100);
                                sc = true;
                                credential.set_key(Integer.parseInt(key));
                                switch1.setEnabled(true);
                            }
                        });

                    }
                //}
            }).start();


/*        if (isChecked) {
            progress=0;
            sc = true;
            title3 = (TextView) findViewById(R.id.title3);
            EditText key1 = (EditText) findViewById(R.id.key1);
            pass = title3.getText().toString();
            key = key1.getText().toString();
            if ((key.matches(""))||(!key.matches("[0-9]+"))||(key.length() > 2)) {
                key = "0";
                Toast.makeText(this, "Invalid Key, should be (0-99)", Toast.LENGTH_LONG).show();
            }
            keyx = Integer.parseInt(key);
            epass=new String();
            for (int i = 0; i < pass.length(); i++) {
                epass += (char) (pass.charAt(i) + keyx);
            }
            title3.setText(epass);
*/

        } else {
            switch1 = (Switch)  findViewById(R.id.switch1);
            switch1.setEnabled(false);
            progress=0;
            //sc = true;
            title3 = (TextView) findViewById(R.id.title3);
            EditText key1 = (EditText) findViewById(R.id.key1);
            pass = title3.getText().toString();
            key = key1.getText().toString();
            if (!key.matches(Integer.toString(credential.get_key()))){
                Log.d("keymatch", Integer.toString(credential.get_key()));
                //key = "0";
                switch1.setChecked(true);
                Toast.makeText(this, "Invalid key!", Toast.LENGTH_SHORT).show();
                sc = true;
                switch1.setEnabled(true);
                return; //break out of function
            }
            keyx = Integer.parseInt(key);
            dpass=new String();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //while (progress< 100){

                    for (int i=0; i<pass.length(); i++) {
                        if(i%2==0)
                            dpass += (char) (pass.charAt(i) - keyx++ - 2);
                        else
                            dpass += (char) (pass.charAt(i) - keyx++ + 5);
                        progress=progress+100/pass.length();
                        android.os.SystemClock.sleep(500);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(progress);
                            }
                        });
                    }

                    //progress++
                    //android.os.SystemClock.sleep(500);
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            title3.setText(dpass);
                            progressBar.setProgress(100);
                            sc = false;
                            switch1.setEnabled(true);
                        }
                    });

                }
                //}
            }).start();
        }

    }

    public void goBack(View view){
        Intent i = new Intent(this,MainActivity.class);
        TextView title3 = (TextView) findViewById(R.id.title3);
        TextView title = (TextView) findViewById(R.id.title);
        String y = title.getText().toString();
        String x = title3.getText().toString();
        dbHandler.setaCredential(x, y);
        dbHandler.setaSwitch(sc, y);
        dbHandler.setaKey(credential.get_key(), y);
        startActivity(i);
    }

    public void copyUser(View view){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        TextView title2 = (TextView) findViewById(R.id.title2);
        ClipData clip = ClipData.newPlainText("username", title2.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Username copied (to clipboard)", Toast.LENGTH_SHORT).show();

    }

    public void copyPass(View view){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        TextView title3 = (TextView) findViewById(R.id.title3);
        ClipData clip = ClipData.newPlainText("password", title3.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Password copied (to clipboard)", Toast.LENGTH_SHORT).show();
    }


}

//Keys are now stored in database (db updated)
//Decryption now only allowed if dkey=ekey
//Key input is now mandatory
//Encryption algorithm complexed
//Interrupt in cryption process on back press fixed
//Key input parameter range updated
