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


public class Level5Activity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {

    private AdView mAdView;
    private RewardedVideoAd mAd;
    private PrefManager prefManager;
    private GameSound gameSound;
    private boolean isSound;
    private TextView txtQuestion, txtQnos;
    private int cQno = 0, tQno = 30, aCorrect = 0, aWrong = 0;
    private FlexboxLayout flayout;
    private ImageButton imageButton, btnAds, btnHome, btnInfo;
    private Animation animSequential;
    private Integer[] arrQno;
    private String[][] arrQuestions = {
            {"Summoning Charm", "Accio"},
            {"Charm that causes a locked door to open", "Alohomora"},
            {"The Killing Curse", "Avada Kedavra"},
            {"Conjures a flock of small birds", "Avis"},
            {"Locking a door, preventing Muggle opening", "Colloportus"},
            {"Creates confusion in an opponent", "Confundo"},
            {"Causes the victim to suffer almost intolerable pain", "Crucio"},
            {"Causes the victim\'s teeth to enlarge grotesquely", "Densaugeo"},
            {"Spell that cuts something open", "Diffindo"},
            {"Patronus Charm", "Expecto Patronum"},
            {"Disarming Spell", "Expelliarmus"},
            {"Stops an object or slows it down", "Impedimenta"},
            {"Gives the caster complete control over their victim", "Imperio"},
            {"Makes something waterproof", "Impervius"},
            {"Starts a fire", "Incendio"},
            {"Glues the subject\'s tongue to the roof of their mouth", "Langlock"},
            {"Dangles a person upside-down by the ankle", "Levicorpus"},
            {"Creates a beam of light from the end of one\'s wand", "Lumos"},
            {"Fills a surrounding area with an unidentifiable buzzing", "Muffliato"},
            {"Turns off the light produced by one of the other spells", "Nox"},
            {"Turns the entire body of the victim rigid", "Petrificus Totalus"},
            {"Shield Charm", "Protego"},
            {"A powerful shield charm against dark magic", "Protego Horribilis"},
            {"Undoes damage to an object", "Reparo"},
            {"Repelling Spell", "Repello"},
            {"Perpetual Tickling Charm", "Rictusempra"},
            {"Boggart banishing spell", "Riddikulus"},
            {"Makes the caster\'s voice carry over long distances", "Sonorus"},
            {"Stunning Spell", "Stupefy"},
            {"Levitation Charm", "Wingardium Leviosa"}
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
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-7250925653938754/9003394627");
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
        mAd.loadAd("ca-app-pub-7250925653938754/4933121824", new AdRequest.Builder().build());
    }

    private void displayInfo() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_levelsinfo);


        // set the custom dialog components - text, image and button
        TextView textInfo = (TextView) dialog.findViewById(R.id.txtInstructions);
        textInfo.setText("Test your knowledge in charms!" +
                " Select appropriate charms for given questions." +
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
        imageButton.setImageResource(R.drawable.ic_madeye);

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
        txtQuestion.setText("Well done! Let's see your progress.");
        ((TextView) findViewById(R.id.txtTCorrect)).setText("Total : " + tpoints);
        ((TextView) findViewById(R.id.txtTCorrectPoints)).setText("" + tCoins);
        prefManager.setPoints("Level 5", tCoins);

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
        prefManager.setPoints("Level 5", tCoins);
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
