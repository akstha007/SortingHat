package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Arrays;
import java.util.Collections;

public class Level6Activity extends AppCompatActivity implements RewardedVideoAdListener, View.OnClickListener {

    private Integer[] arrQno;
    private AdView mAdView;
    private RewardedVideoAd mAd;
    private PrefManager prefManager;
    private GameSound gameSound;
    private boolean showHint, isSound, isVidAds;
    private TextView txtPoints, txtTimer, txtCharacter;
    private int cQno = 0, tQno = 47, points = 0, aCorrect = 0, aWrong = 0;
    private ImageButton btnAds, btnHome, btnInfo;
    private Button btnG, btnR, btnS, btnH;
    private ProgressBar progressTimer;
    CountDownTimer countDownTimer;
    Animation animSequential;
    private String[] arrQuestions = {"The Fat Friar", "Oliver Wood", "Albus Dumbledore", "Hannah Abbott", "Parvati Patil",
            "Gregory Goyle", "Luna Lovegood", "Remus Lupin", "Minerva McGonagall", "Peter Pettigrew",
            "Odolphus Lestrange", "Harry Potter", "Phineas Black", "Penelope Clearwater", "Pansy Parkinson",
            "Blaise Zabini", "Padma Patil", "Moaning Myrtle", "Millicent Bulstrode", "Gilderoy Lockhart",
            "Marcus Flint", "Anthony Goldstein", "Justin Finch-Fletchley", "Susan Bones", "Regulus Black",
            "Tom Riddle", "Romilda Vane", "Ernie Macmillan", "Bellatrix Lestrange", "Neville Longbottom",
            "Quirinus Quirrell", "Cormac McLaggen", "Rubeus Hagrid", "Cho Chang", "Filius Flitwick",
            "Sirius Black", "Pomona Sprout", "Horace Slughorn", "Ron Weasley", "Molly Weasley",
            "Michael Corner", "Vincent Crabbe", "Hermione Granger", "Zacharias Smith", "Cedric Diggory",
            "Severus Snape", "Draco Malfoy", "Angelina Johnson", "Marietta Edgecombe", "Lucius Malfoy"};

    private int[] arrResult = {4, 1, 1, 4, 1,
            2, 3, 1, 1, 1,
            2, 1, 2, 3, 2,
            2, 3, 3, 2, 3,
            2, 3, 4, 4, 2,
            2, 1, 4, 2, 1,
            3, 1, 1, 3, 3,
            1, 4, 2, 1, 1,
            3, 2, 1, 4, 4,
            2, 2, 1, 3, 2};
    //1-G,2-S,3-R,4-H

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level6);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        prefManager = new PrefManager(this);
        gameSound = new GameSound(this);
        isSound = prefManager.getPoints("GameSound") == 0 ? false : true;


        /*-----------------Setup Ads------------------*/
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7250925653938754/1480127827");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*---------Use an activity context to get the rewarded video instance.-------*/
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        genRanQuestion();
        initId();
        displayUI();
        startTimer(2);
    }

    private void displayUI() {
        if (checkQno()) {
            txtPoints.setText((cQno+1)+"/"+tQno);
            txtCharacter.setText(arrQuestions[arrQno[cQno]]);
            txtCharacter.startAnimation(animSequential);
        } else {
            prepareExit();
        }
    }

    private void initId() {
        txtPoints = (TextView) findViewById(R.id.txtScore);
        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        txtCharacter = (TextView) findViewById(R.id.txtCharacter);
        progressTimer = (ProgressBar) findViewById(R.id.progressTimer);
        btnG = (Button) findViewById(R.id.btnG);
        btnR = (Button) findViewById(R.id.btnR);
        btnS = (Button) findViewById(R.id.btnS);
        btnH = (Button) findViewById(R.id.btnH);

        btnAds = (ImageButton) findViewById(R.id.btnAds);
        btnAds.setOnClickListener(this);

        txtPoints.setText("Points: " + points);

        btnInfo.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnG.setOnClickListener(this);
        btnR.setOnClickListener(this);
        btnS.setOnClickListener(this);
        btnH.setOnClickListener(this);

        animSequential = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_up);
        animSequential.setDuration(900);
        tQno = arrQuestions.length;
    }

    private boolean checkQno() {
        if (cQno < tQno) {
            return true;
        }
        return false;
    }

    private void genRanQuestion() {
        arrQno = new Integer[arrQuestions.length];
        for (int i = 0; i < arrQno.length; i++) {
            arrQno[i] = i;
        }
        Collections.shuffle(Arrays.asList(arrQno));
    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-7250925653938754/6608331425", new AdRequest.Builder().addTestDevice("62157022C502ECE4B82BE08B1F2CE1EE").build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        points += 100;
        int totalPoints = prefManager.getPoints("total_points") + points;
        prefManager.setPoints("total_points", totalPoints);
        prefManager.setPoints("Level 6", points);
        prepareExit();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    private void displayInfo() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_levelsinfo);


        // set the custom dialog components - text, image and button

        TextView textInfo = (TextView) dialog.findViewById(R.id.txtInstructions);
        textInfo.setText("Sort the given characters into their respective houses." +
                "\n2 Points will be awarded for correct answer." +
                "\n1 Point will be deducted for incorrect answer." +
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHome:
                onBackPressed();
                break;

            case R.id.btnInfo:
                displayInfo();
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

            case R.id.btnG:
                checkAns(1);
                break;

            case R.id.btnR:
                checkAns(3);
                break;

            case R.id.btnS:
                checkAns(2);
                break;

            case R.id.btnH:
                checkAns(4);
                break;
        }
    }

    private void checkAns(int index) {
        if (arrResult[arrQno[cQno]] == index) {
            aCorrect++;
            if (isSound) {
                gameSound.playWin();
            }
        } else {
            aWrong++;
            if (isSound) {
                gameSound.playLoose();
            }
        }
        cQno++;
        displayUI();
    }

    private void startTimer(final int min) {
        progressTimer.setProgress(60 * min);
        progressTimer.setMax(60 * min);
        txtTimer.setText("2:00");

        countDownTimer = new CountDownTimer(60 * min * 1000, 500) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                progressTimer.setProgress((int) seconds);
                txtTimer.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
            }

            @Override
            public void onFinish() {
                txtTimer.setText("STOP");
                prepareExit();
                //timeOverTick();
            }
        }.start();
    }

    private void prepareExit() {
        findViewById(R.id.layoutGameButtons).setVisibility(View.GONE);
        //txtCharacter.setVisibility(View.GONE);
        findViewById(R.id.layoutQuestions).setVisibility(View.GONE);
        findViewById(R.id.layoutGameResult).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.txtCorrect)).setText("Correct : " + aCorrect);
        ((TextView) findViewById(R.id.txtCorrectPoints)).setText("" + aCorrect * 2);
        ((TextView) findViewById(R.id.txtInCorrect)).setText("Incorrect : " + aWrong);
        ((TextView) findViewById(R.id.txtInCorrectPoints)).setText("-" + aWrong);
        int tpoints = aCorrect - aWrong;
        int tCoins = aCorrect * 2 - aWrong;
        tpoints = tpoints < 0 ? 0 : tpoints;
        tCoins = tCoins < 0 ? 0 : tCoins;
        tCoins += points;
        ((TextView) findViewById(R.id.txtTCorrect)).setText("Total : " + tpoints);
        ((TextView) findViewById(R.id.txtTCorrectPoints)).setText("" + tCoins);
        prefManager.setPoints("Level 6", tCoins);


        CountDownTimer countDownTimer = new CountDownTimer(15000, 3000) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                animSequential = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                btnAds.startAnimation(animSequential);
            }

            @Override
            public void onFinish() {
                //prefManager.setPoints("Level 6", points);
                //onBackPressed();
            }
        }.start();
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //prefManager.setPoints("total_points", points);
        //prefManager.setPoints("Level 6", points);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAd.pause(this);
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
}
