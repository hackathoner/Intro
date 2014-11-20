package me.anuraag.intro;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity {

    private Button submit;
    private ImageView question,bubble;
    private EditText email,place;
    private TextView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ActionBar bar = this.getActionBar();
        bar.hide();

        submit = (Button)findViewById(R.id.submit);
        question = (ImageView)findViewById(R.id.question);
        bubble = (ImageView)findViewById(R.id.bubble);

        email = (EditText)findViewById(R.id.email);
        place = (EditText)findViewById(R.id.meetingPlace);

        edit = (TextView)findViewById(R.id.edit);


        bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackMail();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
        final Intent q = new Intent(this,Help.class);

        question.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(q);
                return true;
            }
        });

        final Intent edits = new Intent(this,Edit.class);
        edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(edits);
                return true;
            }
        });

    }

    public void feedbackMail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"brian@vueanalytics.co"});
        i.putExtra(Intent.EXTRA_SUBJECT,"Feedback for Intro" );
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }
    public void sendEmail(){
        SharedPreferences sharedPref = this.getSharedPreferences("email.xml",MODE_PRIVATE);
        boolean worked=false;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
        i.putExtra(Intent.EXTRA_SUBJECT, sharedPref.getString("Subject","Nice meeting you at ")+place.getText().toString());
        i.putExtra(Intent.EXTRA_TEXT   , sharedPref.getString("Body","It was great to meet you! Lets sync up this week"));



        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
            worked = true;

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

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
