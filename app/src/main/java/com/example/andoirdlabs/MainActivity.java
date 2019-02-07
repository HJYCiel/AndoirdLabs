package com.example.andoirdlabs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    android.content.SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);



        EditText et2 = (EditText)findViewById(R.id.Editxt1);
        sp = getSharedPreferences("typed", Context.MODE_PRIVATE);
        String savedString = sp.getString("typed","");
        et2.setText(savedString);





        Button next = (Button)findViewById(R.id.button1);
        next.setOnClickListener( c -> {

            Intent nextPage = new Intent(this, ProfileActivity.class);

            //Give directions to go from this page, to SecondActivity
            EditText et = (EditText)findViewById(R.id.Editxt1);
            nextPage.putExtra("typed", et.getText().toString());
            android.content.SharedPreferences.Editor editor = sp.edit();
            String whatWasTyped = et.getText().toString();
            editor.putString("typed", whatWasTyped);

            //write it to disk:
            editor.commit();
            startActivity(nextPage);
            //Now make the transition:



        });
    }



    @Override
    protected void onPause() {
        super.onPause();

        sp=getPreferences(Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sp.edit();
        EditText typeField=(EditText)findViewById(R.id.Editxt1);
        String whatWasTyped = typeField.getText().toString();
        editor.putString("typed", whatWasTyped);

        //write it to disk:
        editor.commit();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
