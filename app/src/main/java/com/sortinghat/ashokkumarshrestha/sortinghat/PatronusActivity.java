package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Arrays;
import java.util.Collections;


public class PatronusActivity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {

    private AdView mAdView;
    private RewardedVideoAd mAd;
    private PrefManager prefManager;
    private GameSound gameSound;
    private boolean isSound;
    private TextView txtQuestion, txtQnos;
    private int cQno = 0, tQno = 8, i1 = 0, i2 = 0;
    private LinearLayout flayout;
    private ImageButton btnAds, btnHome, btnInfo;
    private Integer[] arrQno;

    private String[][] arrOptions = {
            {"Choose an element", "Fire", "Water", "Air", "Earth"},
            {"Which one do you prefer?", "Bright", "Dark"},
            {"Which one do you like?", "Sun", "Moon"},
            {"What is the temperature of your morning coffee?", "Hot", "Mild", "Cold"},
            {"Your shoe size", "Large", "Medium", "Small"},
            {"Are you?", "Keeper", "Breaker"},
            {"What do you consider yourself as?", "Follower", "Rebel", "Leader"},
            {"Which power do you choose?", "Protector", "Destroyer"},
            {"Which one do you believe in?", "Luck", "Hard Work"},
            {"Which color do you prefer?", "Black", "White", "Grey"},
            {"What do you feel?", "Free", "Bound"},
            {"Do you ... ", "Command", "Obey"},
            {"Choose ...", "Wood", "Rock", "Sand"},
            {"Which one do you like?", "Day", "Night"},
            {"Choose one", "Beauty", "Intelligence", "Beast"},
            {"Which one do you prefer?", "Light", "Shadow"},
            {"Would you rather be ...", "Squib", "Muggle"},
            {"Choose your house", "Gryffindor", "Slytherin", "Ravenclaw", "Hufflepuff"},
            {"Do you believe in magic?", "Sometimes", "Never", "Always"},
            {"Choose your action in wizarding war?", "Fight", "Lead", "Save", "Escape"},
            {"Which one do you prefer?", "Alone", "Together"},
            {"Choose one", "Elder wand", "Resurrection stone", "Invisibility cloak"}
    };

    private String[][] arrAnswers = {
            {"Dragon", "Griffin ", "Phoenix", "Wolf", "Giant", "Mammoth", "Abraxan", "Hippogriff", "Occamy", "Thestral", "Dragonfly"},
            {"Eagle", "Raven", "Sparrow", "Owl", "Hummingbird", "Crane", "Swan", "Kingfisher", "Peacock", "Bat", "Swift", "Vulture", "Albatros", "Hawk", "Parrot", "Pigeon"},
            {"Mermaids", "Python", "Cobra", "Lizard", "Badger", "Kelpie", "Kappa", "Seal", "Otter", "Orca", "Python", "Runespoor", "Salmon", "Seal", "Shark"},
            {"Yeti", "Unicorn", "Stag", "Deer", "Dog", "Rabbit", "Cat", "Mouse", "Goat", "Fox", "Orangutan", "Monkey", "Oryx", "Boar", "Lion", "Impala", "Squirrel"}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patronus);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        prefManager = new PrefManager(this);
        gameSound = new GameSound(this);
        isSound = prefManager.getPoints("GameSound") == 0 ? false : true;

        /*-----------------Setup Ads------------------*/
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7250925653938754/3654865028");
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
        mAd.loadAd("ca-app-pub-7250925653938754/9502922228", new AdRequest.Builder().addTestDevice("62157022C502ECE4B82BE08B1F2CE1EE").build());
    }

    private void displayInfo() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_levelsinfo);


        // set the custom dialog components - text, image and button
        TextView textInfo = (TextView) dialog.findViewById(R.id.txtInstructions);
        textInfo.setText("Discover your patronus. Always remember the happiest memory before casting the Patronus charm." +
                "\nSelect the best answer that describes you." +
                "" +
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
        flayout = (LinearLayout) findViewById(R.id.layoutGameButtons);
        txtQuestion = (TextView) findViewById(R.id.txtQues);

        //btnAds = (ImageButton) findViewById(R.id.btnAds);
        //btnAds.setOnClickListener(this);

        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(this);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);

        tQno = arrOptions.length;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHome:
                onBackPressed();
                break;
            /*case R.id.btnAds:
                //Double the points on ads viewed if successful in viewing ad increase points with sound
                if (mAd.isLoaded()) {
                    mAd.show();
                } else {
                    loadRewardedVideoAd();
                    if (mAd.isLoaded()) {
                        mAd.show();
                    }
                }
                break;*/
            case R.id.btnInfo:
                displayInfo();
                break;
        }
    }

    private void createUI() {
        final int index = arrQno[cQno];
        //imgQues.setImageDrawable(getResources().getDrawable(arrPics[index]));
        txtQuestion.setText(arrOptions[index][0]);

        for (int i = 1; i < arrOptions[index].length; i++) {
            final Button btn = new Button(this);
            btn.setText(arrOptions[index][i]);
            btn.setPadding(10, 25, 10, 25);
            btn.setBackgroundResource(R.drawable.bg_games);
            btn.setTextSize(18);
            btn.setAllCaps(false);

            final int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int index = arrQno[cQno];
                    if (isSound) {
                        gameSound.playLoose();
                    }
                    if (index == 0) {
                        i1 = finalI-1;
                    } else {
                        i2 += finalI-1;
                    }

                    cQno++;
                    updateDisplay();
                }
            });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 15);
            flayout.addView(btn, lp);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
            animation.setDuration(900 + 200 * i);
            btn.setAnimation(animation);
            animation.start();
        }
    }

    private void updateDisplay() {
        if (checkQno()) {
            prepareExit();
        } else {
            int currQno = cQno + 1;
            txtQnos.setText(currQno + "/" + tQno);
            flayout.removeAllViews();
            createUI();
        }
    }

    private void prepareExit() {
        ((ScrollView) findViewById(R.id.viewImage)).setVisibility(View.GONE);
        findViewById(R.id.scrollGameButtons).setVisibility(View.GONE);
        findViewById(R.id.layoutGameResult).setVisibility(View.VISIBLE);

        i2 = i2 % arrAnswers[i1].length;
        ((TextView) findViewById(R.id.txtHouseDesc)).setText(arrAnswers[i1][i2]);

        //save patronus details
        prefManager.setStringValue("Patronus", arrAnswers[i1][i2]);

        CountDownTimer countDownTimer = new CountDownTimer(60000, 3000) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                //btnAds.startAnimation(animSequential);
            }

            @Override
            public void onFinish() {

                //onBackPressed();
            }
        }.start();
    }

    private void genRanQuestion() {
        arrQno = new Integer[arrOptions.length];
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
        /*// Reward the user.
        int tCoins = aCorrect * 2 - aWrong + 100;
        int tpoints = aCorrect - aWrong;
        tCoins = tCoins < 0 ? 0 : tCoins;
        tpoints = tpoints < 0 ? 0 : tpoints;

        ((TextView) findViewById(R.id.txtTCorrect)).setText("Total : " + tpoints);
        ((TextView) findViewById(R.id.txtTCorrectPoints)).setText("" + tCoins);

        int points = prefManager.getPoints("total_points") + tCoins;
        prefManager.setPoints("total_points", points);
        prefManager.setPoints("Level 1", tCoins);*/
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
