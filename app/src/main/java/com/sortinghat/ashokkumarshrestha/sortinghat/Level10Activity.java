package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.Arrays;
import java.util.Collections;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class Level10Activity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {

    private AdView mAdView;
    private RewardedVideoAd mAd;
    private PrefManager prefManager;
    private GameSound gameSound;
    private boolean isSound;
    private TextView txtQnos, txtQuestions;
    private int cQno = 0, tQno = 8, aCorrect = 0, aWrong = 0;
    private ImageButton btnAds, btnHome, btnInfo;
    private Animation animSequential;
    private Integer[] arrQno;
    private Button btnNo, btnYes;

    private String[][] arrQuestions = {
            {"Alecto Carrow", "Y"},
            {"Amycus Carrow", "Y"},
            {"Antonin Dolohov", "Y"},
            {"Augustus Rookwood", "Y"},
            {"Avery Snr", "Y"},
            {"Barty Crouch Jr", "Y"},
            {"Bellatrix Lestrange", "Y"},
            {"Crabbe Snr", "Y"},
            {"Goyle Snr", "Y"},
            {"Lucius Malfoy", "Y"},
            {"Peter Pettigrew", "Y"},
            {"Severus Snape", "N"},
            {"Draco Malfoy", "N"},
            {"Corban Yaxley", " Y"},
            {"Fenrir Greyback", "Y"},
            {"Narcissa Malfoy", "N"},
            {"Pansy Parkinson", "N"},
            {"Dolores Umbridge", "Y"},
            {"Edgar Bones", "N"},
            {"Bertha Jorkins", "N"},
            {"Sirius Black", "N"},
            {"Rufus Scrimgeour", "N"},
            {"Gregorovitch", "N"},
            {"Gellert Grindelwald", "N"},
            {"Walden Macnair", "Y"},
            {"Nott Snr", "Y"},
            {"Thorfinn Rowle", "Y"},
            {"Barty Crouch Sr", "N"},
            {"Travers", "Y"},
            {"Hepzibah Smith", "N"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level10);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        prefManager = new PrefManager(this);
        gameSound = new GameSound(this);
        isSound = prefManager.getPoints("GameSound") == 0 ? false : true;
        animSequential = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);

        /*-----------------Setup Ads------------------*/
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7250925653938754/9701398620");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("62157022C502ECE4B82BE08B1F2CE1EE").build();
        //AdRequest.Builder.addTestDevice("62157022C502ECE4B82BE08B1F2CE1EE");
        mAdView.loadAd(adRequest);

        /*-----Use an activity context to get the rewarded video instance.--------*/
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        initId();
        genRanQuestion();
        createUI();
        updateDisplay();
    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-7250925653938754/6608331425", new AdRequest.Builder().addTestDevice("62157022C502ECE4B82BE08B1F2CE1EE").build());
    }

    private void displayInfo() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_levelsinfo);


        // set the custom dialog components - text, image and button
        TextView textInfo = (TextView) dialog.findViewById(R.id.txtInstructions);
        textInfo.setText("You are fighting against Voldemort's Army in wizarding war. Choose your first spell when you see them. Remember your first spell can be your last." +
                "\n2 points will be awarded for correct answer." +
                "\n1 point will be deducted for incorrect answer." +
                "\n\nLet the magic begin!");


        ImageButton dialogButtonClose = (ImageButton) dialog.findViewById(R.id.btnClose);
        dialogButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        LinearLayout layoutAds = (LinearLayout) dialog.findViewById(R.id.layoutAds);
        layoutAds.setVisibility(View.GONE);

        dialog.show();
    }

    private void initId() {

        txtQnos = (TextView) findViewById(R.id.txtQno);
        txtQuestions = (TextView) findViewById(R.id.txtQues);

        btnAds = (ImageButton) findViewById(R.id.btnAds);
        btnAds.setOnClickListener(this);

        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(this);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);

        btnNo = (Button) findViewById(R.id.btnExpelliarmus);
        btnYes = (Button) findViewById(R.id.btnAvada);
        btnNo.setOnClickListener(this);
        btnYes.setOnClickListener(this);

        tQno = arrQuestions.length;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHome:
                onBackPressed();
                break;
            case R.id.btnAds:
                //Double the points on ads viewed if successful in viewing ad increase points with sound
                if (mAd.isLoaded()) {
                    mAd.show();
                } else {
                    loadRewardedVideoAd();
                    if (mAd.isLoaded()) {
                        mAd.show();
                    }
                }
                break;
            case R.id.btnInfo:
                displayInfo();
                break;

            case R.id.btnExpelliarmus:
                checkAns("N");
                break;
            case R.id.btnAvada:
                checkAns("Y");
                break;
        }
    }

    private void checkAns(String isDeathEater) {
        if (arrQuestions[arrQno[cQno]][1].equalsIgnoreCase(isDeathEater)) {
            if (isSound) {
                gameSound.playWin();
            }
            aCorrect++;
        } else {
            if (isSound) {
                gameSound.playLoose();
            }
            aWrong++;
        }
        cQno++;
        updateDisplay();
    }

    private void createUI() {
        final int index = arrQno[cQno];
        txtQuestions.setText(arrQuestions[index][0]);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        animation.setDuration(900);
        txtQuestions.startAnimation(animation);
    }

    private void updateDisplay() {
        if (checkQno()) {
            prepareExit();
        } else {
            int currQno = cQno + 1;
            txtQnos.setText(currQno + "/" + tQno);
            createUI();
        }
    }

    private void prepareExit() {
        ((LinearLayout) findViewById(R.id.layoutQuestion)).setVisibility(View.GONE);
        ((LinearLayout) findViewById(R.id.layoutGameButtons)).setVisibility(View.GONE);
        findViewById(R.id.layoutGameResult).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.txtCorrect)).setText("Correct : " + aCorrect);
        ((TextView) findViewById(R.id.txtCorrectPoints)).setText("" + aCorrect * 2);
        ((TextView) findViewById(R.id.txtInCorrect)).setText("Incorrect : " + aWrong);
        ((TextView) findViewById(R.id.txtInCorrectPoints)).setText("-" + aWrong);
        int tpoints = aCorrect - aWrong;
        int tCoins = aCorrect * 2 - aWrong;
        tpoints = tpoints < 0 ? 0 : tpoints;
        tCoins = tCoins < 0 ? 0 : tCoins;

        ((TextView) findViewById(R.id.txtTCorrect)).setText("Total : " + tpoints);
        ((TextView) findViewById(R.id.txtTCorrectPoints)).setText("" + tCoins);
        prefManager.setPoints("Level 10", tCoins);

        CountDownTimer countDownTimer = new CountDownTimer(60000, 3000) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                btnAds.startAnimation(animSequential);
            }

            @Override
            public void onFinish() {

                //onBackPressed();
            }
        }.start();
    }

    private void genRanQuestion() {
        arrQno = new Integer[arrQuestions.length];
        for (int i = 0; i < arrQno.length; i++) {
            arrQno[i] = i;
        }
        Collections.shuffle(Arrays.asList(arrQno));
    }

    private boolean checkQno() {
        if (cQno < tQno) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        mAd.pause(this);
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onResume() {
        mAd.resume(this);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();
    }

    // Required to reward the user.
    @Override
    public void onRewarded(RewardItem reward) {
        // Reward the user.
        int tCoins = aCorrect * 2 - aWrong + 100;
        int tpoints = aCorrect - aWrong;
        tCoins = tCoins < 0 ? 0 : tCoins;
        tpoints = tpoints < 0 ? 0 : tpoints;

        ((TextView) findViewById(R.id.txtTCorrect)).setText("Total : " + tpoints);
        ((TextView) findViewById(R.id.txtTCorrectPoints)).setText("" + tCoins);

        int points = prefManager.getPoints("total_points") + tCoins;
        prefManager.setPoints("total_points", points);
        prefManager.setPoints("Level 10", tCoins);
    }

    // The following listener methods are optional.
    @Override
    public void onRewardedVideoAdLeftApplication() {

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
