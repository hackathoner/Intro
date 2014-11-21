package me.anuraag.intro;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.vueanalytics.vue.VUE;

public class Edit extends Activity {
    private EditText emailHead,emailBody;
    private Button submit,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VUE.launch(this, "274f41fa-169d-42f7-9d3a-d8d643aaaf96");

        setContentView(R.layout.activity_edit);
        ActionBar bar = this.getActionBar();
        bar.hide();
        emailBody = (EditText)findViewById(R.id.email);
        emailHead = (EditText)findViewById(R.id.meetingPlace);

        submit = (Button)findViewById(R.id.submit);
        SharedPreferences sharedPref = this.getSharedPreferences("email.xml",MODE_PRIVATE);
        final Intent q = new Intent(this,MyActivity.class);
        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(q);
            }
        });
        emailBody.setText(sharedPref.getString("Body","Enter you body text here"));
        emailHead.setText(sharedPref.getString("Subject","Enter you subject text here"));


        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               editEmail();
                Toast.makeText(getApplicationContext(), "Edit Successful", Toast.LENGTH_SHORT).show();
                startActivity(q);
                
            }
        });


    }
    public void editEmail(){
        SharedPreferences sharedPref = this.getSharedPreferences("email.xml",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Subject",emailHead.getText().toString());
        editor.putString("Body", emailBody.getText().toString());
        editor.apply();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
