package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.Dialog;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class PlayActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private RewardedVideoAd mAd;
    private Dialog mDialog;
    PrefManager prefManager;
    String[][] arrayLevel = {
            {"StartGame", "com.sortinghat.ashokkumarshrestha.sortinghat.StartGameActivity", "40"},
            {"Level 2", "com.sortinghat.ashokkumarshrestha.sortinghat.Level2Activity", "40"},
            {"Level 3", "com.sortinghat.ashokkumarshrestha.sortinghat.Level3Activity", "40"},
            {"Level 4", "com.sortinghat.ashokkumarshrestha.sortinghat.Level4Activity", "40"},
            {"Level 5", "com.sortinghat.ashokkumarshrestha.sortinghat.Level5Activity", "40"},
            {"Level 6", "com.sortinghat.ashokkumarshrestha.sortinghat.Level6Activity", "40"},
            {"Level 7", "com.sortinghat.ashokkumarshrestha.sortinghat.Level7Activity", "40"},
            {"Level 8", "com.sortinghat.ashokkumarshrestha.sortinghat.Level8Activity", "40"},
            {"Level 9", "com.sortinghat.ashokkumarshrestha.sortinghat.Level9Activity", "40"},
            {"Level 10", "com.sortinghat.ashokkumarshrestha.sortinghat.Level10Activity", "40"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        prefManager = new PrefManager(this);

        /*if (prefManager.getPoints("IsFirstTime") == 0) {
            prefManager.setPoints("IsFirstTime", 1);
            prefManager.setPoints("total_points", 100);
            prefManager.setPoints("GameSound", 1);
        }*/
        createUIs();

        /*---------Use an activity context to get the rewarded video instance.-------*/
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();


    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-7250925653938754/1825162070", new AdRequest.Builder().addTestDevice("62157022C502ECE4B82BE08B1F2CE1EE").build());
    }

    private void createUIs() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutPlay);

        //remove previous layout elements
        if (layout.getChildCount() > 0) {
            layout.removeAllViews();
        }

        for (int i = 0; i < arrayLevel.length; i++) {
            final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
                    R.layout.activity_playrows, layout, false);

            ImageView image = (ImageView) newView.findViewById(R.id.imgLevel);
            ImageView coin = (ImageView) newView.findViewById(R.id.imgCoin);
            TextView title = (TextView) newView.findViewById(R.id.txtLevelName);
            TextView score = (TextView) newView.findViewById(R.id.txtScore);

            //title.setText(arrayLevel[i][0]);
            title.setText("Game " + (i + 1));
            int points = prefManager.getPoints(arrayLevel[i][0]);

            if (points <= 0) {
                image.setImageResource(R.drawable.ic_ticke);
                score.setVisibility(View.INVISIBLE);
                coin.setVisibility(View.INVISIBLE);
            } else {
                image.setImageResource(R.drawable.ic_tickd);
                score.setVisibility(View.VISIBLE);
                coin.setVisibility(View.VISIBLE);
                //score.setText(points+"/"+arrayLevel[i][2]);
                score.setText("" + points);
            }

            final int finalI = i;
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(new Intent(PlayActivity.this, Class.forName(arrayLevel[finalI][1])));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

            layout.addView(newView);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
            animation.setDuration(900 + 200 * i);
            newView.setAnimation(animation);
            animation.start();
        }
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
        //startActivity(new Intent(PlayActivity.this, MainActivity.class));
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
    protected void onPause() {
        mAd.pause(this);
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onResume() {
        mAd.resume(this);
        createUIs();
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
