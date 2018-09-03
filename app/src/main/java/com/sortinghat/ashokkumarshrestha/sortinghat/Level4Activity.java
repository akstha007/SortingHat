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


public class Level4Activity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {

    private AdView mAdView;
    private RewardedVideoAd mAd;
    private PrefManager prefManager;
    private GameSound gameSound;
    private boolean isSound;
    private TextView txtQuestion, txtQnos;
    private int cQno = 0, tQno = 10, aCorrect = 0, aWrong = 0;
    private FlexboxLayout flayout;
    private ImageButton imageButton, btnAds, btnHome, btnInfo;
    private Animation animSequential;
    private Integer[] arrQno;
    private String[][] arrQuestions = {
            {"Well, let's start wit an easy one. Which creature got healing tears and is reborn on burning days?", "Phoenix"},
            {"Good! Find the pest yeh shoulda been able to handle in yer 2nd yea, if \'Peskipiski Pesternomi\' hadn\'t been too much for that Lockhart bloke.", "Pixie"},
            {"Can yeh remember the creature Harry had to face in the first Triwizard task?", "Hungarian Horntail"},
            {"So... next one got me expelled when ev\'ryone thought it was Slytherin\'s monster. I had to let \'im go in the forest.", "Acromantula"},
            {"Nicely done! Yeh should remember meetin\' these in yer fifth year - only folks who\'ve seen someone die are able to see \'em ", "Thestral"},
            {"Next one\'s the proud beast I showed yeh on my first ever day as a teacher, Err... didn\'t go so well.", "Hippogriff"},
            {"Yer doin\' great! Now, Hermione in particular oughtta remember this creature and its petrifyin\' gaze.", "Basilisk"},
            {"Jus\' a few more, now. Can yeh find the creature that lives in trees with wand-quality wood?", "Bowtruckle"},
            {"Next creature\'s a useful one! I don't care how much it\'s worth, I use their hair for bindin\' bandages.", "Unicorn"},
            {"Last one, now! Two o\' the creatures left were professors here at Hogwarts, Which one taught in yer third year, before resignin\'", "Werewolf"},
            {"Yeh got it! Thanks fer being\' so smart, I thought yeh\'d all fail and I\'d be sacked.", "Centaur"},
            {"Rudy ol\' bird. Yeh should have listen\' when I taugh\'. Try again.", "Giant"},
            {"", "Troll"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelgame2);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        prefManager = new PrefManager(this);
        gameSound = new GameSound(this);
        isSound = prefManager.getPoints("GameSound") == 0 ? false : true;
        animSequential = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);

        /*-----------------Setup Ads------------------*/
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-7250925653938754/7526661421");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
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
        mAd.loadAd("ca-app-pub-7250925653938754/9502922228", new AdRequest.Builder().build());
    }

    private void displayInfo() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_levelsinfo);


        // set the custom dialog components - text, image and button
        TextView textInfo = (TextView) dialog.findViewById(R.id.txtInstructions);
        textInfo.setText("Test your knowledge in Magical Creatures!" +
                " Select appropriate magical creatures for given questions." +
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
        txtQuestion = (TextView) findViewById(R.id.txtGameQuestion);
        txtQnos = (TextView) findViewById(R.id.txtQno);
        flayout = (FlexboxLayout) findViewById(R.id.layoutGameButtons);

        imageButton = (ImageButton) findViewById(R.id.imgGamePerson);
        imageButton.setImageResource(R.drawable.ic_hagrid);

        btnAds = (ImageButton) findViewById(R.id.btnAds);
        btnAds.setOnClickListener(this);

        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(this);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
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
        }
    }

    private void createUI() {
        for (int j = 0; j < arrQuestions.length; j++) {
            int i = arrQno[j];
            final Button btn = new Button(this);
            btn.setText(arrQuestions[i][1]);
            btn.setPadding(10, 0, 10, 0);
            btn.setBackgroundResource(R.drawable.bg_games);
            btn.setAllCaps(false);

            final int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int index = arrQno[cQno];
                    if (arrQuestions[cQno][1] == arrQuestions[finalI][1]) {
                        //set visibility of this button gone
                        if (isSound) {
                            gameSound.playWin();
                        }
                        aCorrect++;
                        btn.setVisibility(View.GONE);
                    } else {
                        if (isSound) {
                            gameSound.playLoose();
                        }
                        aWrong++;
                        btn.startAnimation(animSequential);
                    }

                    cQno++;
                    updateDisplay();
                }
            });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
            //int index = arrQno[cQno];
            txtQuestion.setText(arrQuestions[cQno][0]);
        }
    }

    private void prepareExit() {
        flayout.removeAllViews();
        findViewById(R.id.scrollGameButtons).setVisibility(View.GONE);
        findViewById(R.id.layoutGameResult).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.txtCorrect)).setText("Correct : " + aCorrect);
        ((TextView) findViewById(R.id.txtCorrectPoints)).setText("" + aCorrect * 2);
        ((TextView) findViewById(R.id.txtInCorrect)).setText("Incorrect : " + aWrong);
        ((TextView) findViewById(R.id.txtInCorrectPoints)).setText("-" + aWrong);
        int tpoints = aCorrect - aWrong;
        int tCoins = aCorrect * 2 - aWrong;
        tpoints = tpoints < 0 ? 0 : tpoints;
        tCoins = tCoins < 0 ? 0 : tCoins;
        String msg = (tpoints >= (tQno / 2)) ? arrQuestions[10][0] : arrQuestions[11][0];
        txtQuestion.setText(msg);

        ((TextView) findViewById(R.id.txtTCorrect)).setText("Total : " + tpoints);
        ((TextView) findViewById(R.id.txtTCorrectPoints)).setText("" + tCoins);
        prefManager.setPoints("Level 4", tCoins);

        CountDownTimer countDownTimer = new CountDownTimer(15000, 3000) {
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
        //Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +reward.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.
        int tCoins = aCorrect * 2 - aWrong + 100;
        int tpoints = aCorrect - aWrong;
        tCoins = tCoins < 0 ? 0 : tCoins;
        tpoints = tpoints < 0 ? 0 : tpoints;

        ((TextView) findViewById(R.id.txtTCorrect)).setText("Total : " + tpoints);
        ((TextView) findViewById(R.id.txtTCorrectPoints)).setText("" + tCoins);

        int points = prefManager.getPoints("total_points") + tCoins;
        prefManager.setPoints("total_points", points);
        prefManager.setPoints("Level 4", tCoins);
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
