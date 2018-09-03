package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Calendar;

public class QuoteActivity extends AppCompatActivity implements RewardedVideoAdListener, View.OnClickListener {

    private RewardedVideoAd mAd;
    private Dialog mDialog;
    private PrefManager prefManager;
    private Button btnSetTime;
    private Switch switchQuote;
    private int qHour, qMin, hour, minute;
    private TextView txtQuoteTime;
    private ImageButton imgBtnQuote;
    Animation animSequential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        prefManager = new PrefManager(this);

        if (prefManager.getPoints("IsQuoteFirstTime") == 0) {
            //first time app is being launched
            //set the coins and timer for notifications
            prefManager.setPoints("IsQuoteFirstTime", 1);
            prefManager.setPoints("QuoteSwitch", 0);
            prefManager.setPoints("QuoteTimeHour", 7);
            prefManager.setPoints("QuoteTimeMin", 0);

        }
        initIds();
        updateDisplay();

        /*---------Use an activity context to get the rewarded video instance.-------*/
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();


    }

    private void initIds() {
        btnSetTime = (Button) findViewById(R.id.btnSetTime);
        switchQuote = (Switch) findViewById(R.id.switchQuote);
        txtQuoteTime = (TextView) findViewById(R.id.txtQuoteTime);
        imgBtnQuote = (ImageButton) findViewById(R.id.imgQuoteOwl);

        btnSetTime.setOnClickListener(this);
        imgBtnQuote.setOnClickListener(this);

        hour = prefManager.getPoints("QuoteTimeHour");
        minute = prefManager.getPoints("QuoteTimeMin");

        if (prefManager.getPoints("QuoteSwitch") == 1) {
            switchQuote.setChecked(true);
            //handleNotification();
        } else {
            //stopNotification();
            switchQuote.setChecked(false);
        }

        animSequential = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);

        switchQuote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(QuoteActivity.this, "Daily Quote enabled!", Toast.LENGTH_SHORT).show();
                    prefManager.setPoints("QuoteSwitch", 1);
                    handleNotification();
                } else {
                    Toast.makeText(QuoteActivity.this, "Daily Quote disabled!", Toast.LENGTH_SHORT).show();
                    prefManager.setPoints("QuoteSwitch", 0);
                    stopNotification();
                }
            }
        });

    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-7250925653938754/1825162070", new AdRequest.Builder().addTestDevice("62157022C502ECE4B82BE08B1F2CE1EE").build());
    }

    private void updateDisplay() {
        String formattedMin = String.format("%02d", minute);
        txtQuoteTime.setText("Quote Time: " + hour + ":" + formattedMin);
    }

    private void handleNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 00);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 30 * 1000, pendingIntent);
    }

    private void stopNotification() {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_points:
                displayPoints();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QuoteActivity.this, MainActivity.class));
        super.onBackPressed();
        finish();
    }

    private void displayPoints() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_pointsinfo);


        // set the custom dialog components - text, image and button
        TextView textTitle = (TextView) dialog.findViewById(R.id.txtTotalPoins);
        textTitle.setText("" + prefManager.getPoints("total_points"));

        ImageButton dialogButtonAds = (ImageButton) dialog.findViewById(R.id.btnVidAds);
        dialogButtonAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //display ads and update points
                mDialog = dialog;
                if (mAd.isLoaded()) {
                    mAd.show();
                } else {
                    loadRewardedVideoAd();
                    if (mAd.isLoaded()) {
                        mAd.show();
                    }
                }
            }
        });

        ImageButton dialogButtonClose = (ImageButton) dialog.findViewById(R.id.btnClose);
        dialogButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSetTime:
                btnSetTime.startAnimation(animSequential);
                setTimer();
                break;
            case R.id.switchQuote:
                break;
            case R.id.imgQuoteOwl:
                //imgBtnQuote.startAnimation(animSequential);
                displayQuote();
                break;
        }
    }

    private void displayQuote() {
        TextView txtQuote = (TextView) findViewById(R.id.txtQuote);

        String strQuote = getIntent().getStringExtra("Quote");
        if (strQuote.equalsIgnoreCase("")) {
            strQuote = prefManager.getStringValue("TodaysQuote");
            if (strQuote.equalsIgnoreCase("")) {
                strQuote = "\"After all this time?\" \"Always\" said Snape.";
            }
        } else {
            prefManager.setStringValue("TodaysQuote", strQuote);
        }
        strQuote += "\n- J.K.R.";
        txtQuote.setText(strQuote);
        txtQuote.setBackgroundResource(R.drawable.bg_games);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        animation.setDuration(900);
        txtQuote.setAnimation(animation);
        animation.start();
    }

    private void setTimer() {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(QuoteActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                updateDisplay();
                handleNotification();
            }
        }, hour, minute, false);//No 12 hour time
        mTimePicker.show();
    }

    private void saveDetails() {
        prefManager.setPoints("QuoteTimeHour", hour);
        prefManager.setPoints("QuoteTimeMin", minute);
    }

    @Override
    protected void onPause() {
        saveDetails();
        mAd.pause(this);
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onResume() {
        mAd.resume(this);
        updateDisplay();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();
    }

    @Override
    public void onRewarded(RewardItem reward) {
        int points = prefManager.getPoints("total_points") + 100;
        prefManager.setPoints("total_points", points);

        TextView textTitle = (TextView) mDialog.findViewById(R.id.txtTotalPoins);
        textTitle.setText("" + prefManager.getPoints("total_points"));
        boolean isSound = prefManager.getPoints("GameSound") == 0 ? false : true;
        GameSound gameSound = new GameSound(this);
        if (isSound) {
            gameSound.playWin();
        }

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        //Toast.makeText(this, "Video closed! Could not reward the points.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //Toast.makeText(this, "Video closed! Could not reward the points.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        //Toast.makeText(this, "Sorry! Could not load the video.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        //Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        //Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        //Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }
}
