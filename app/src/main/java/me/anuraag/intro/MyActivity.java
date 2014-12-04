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

import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.UserVoice;
import co.vueanalytics.vue.VUE;


public class MyActivity extends Activity {

    private Button submit;
    private ImageView question,bubble;
    private EditText email,place;
    private TextView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VUE.launch(this, "6c6184c3-0a89-48aa-839a-0a75b8bbbaa9");

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

        // Set this up once when your application launches
        Config config = new Config("killthebusinesscard.uservoice.com");
        config.setShowKnowledgeBase(false);
        config.setShowForum(false);
        config.setShowPostIdea(false);
// config.identifyUser("USER_ID", "User Name", "email@example.com");
        UserVoice.init(config, this);

    }

    @Override
    public void onResume(){
        super.onResume();
        VUE.startSession();
    }

    @Override
    public void onPause(){
        super.onPause();
        VUE.endSession();
    }

    public void feedbackMail(){

// Call this wherever you want to launch UserVoice
        UserVoice.launchContactUs(this);
//        Intent i = new Intent(Intent.ACTION_SEND);
//        i.setType("message/rfc822");
//        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"brian@vueanalytics.co"});
//        i.putExtra(Intent.EXTRA_SUBJECT,"Feedback for Intro" );
//        try {
//            startActivity(Intent.createChooser(i, "Send mail..."));
//
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//        }
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
        String location = place.getText().toString().trim();
        if(location.length() > 0) {
            i.putExtra(Intent.EXTRA_SUBJECT, sharedPref.getString("Subject", "Nice meeting you at ") + place.getText().toString());
        } else {
            i.putExtra(Intent.EXTRA_SUBJECT, sharedPref.getString("Subject","Nice meeting you"));
        }
        i.putExtra(Intent.EXTRA_TEXT   , sharedPref.getString("Body","Lets sync up this week!"));



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
