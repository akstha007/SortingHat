package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class WizardActivity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {

    private RewardedVideoAd mAd;
    private Dialog mDialog;
    private PrefManager prefManager;
    private ImageButton btnHouse,btnWand, btnPatronus;
    private TextView txtHouse, txtWand, txtPatronus;
    Animation animSequential;
    private Button btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        prefManager = new PrefManager(this);

        /*---------Use an activity context to get the rewarded video instance.-------*/
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        initIDs();
        display();
    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-7250925653938754/1825162070", new AdRequest.Builder().addTestDevice("62157022C502ECE4B82BE08B1F2CE1EE").build());
    }

    private void display() {
        String prefNames[][] = {
                {"House", "House:\n"},
                {"Wand", "Wand:\n"},
                {"Patronus", "Patronus:\n"}
        };
        int[] ids = {R.id.txtHouse, R.id.txtWand, R.id.txtPatronus};

        int[] profiles = {R.drawable.ic_g, R.drawable.ic_s, R.drawable.ic_r, R.drawable.ic_h, R.drawable.ic_hogwarts};

        String title = "";
        for (int i = 0; i < 3; i++) {
            title = prefManager.getStringValue(prefNames[i][0]);
            if (!title.isEmpty()) {
                if(i==0){
                    //change house icon
                    int index = 4;
                    switch (title){
                        case "Gryffindor":
                            index = 0;
                            break;
                        case "Slytherin":
                            index = 1;
                            break;
                        case "Ravenclaw":
                            index = 2;
                            break;
                        case "Hufflepuff":
                            index = 3;
                            break;
                        default:
                            index = 4;
                            break;

                    }
                    btnHouse.setImageResource(profiles[index]);

                }
                //title = prefNames[i][1] + title;
                ((TextView) findViewById(ids[i])).setText(title);
            }
        }

        //display profile name
        String wizname = prefManager.getStringValue("playerName");
        if(!wizname.isEmpty()){
            btnProfile.setText(wizname);
        }

    }

    private void initIDs() {
        btnHouse = (ImageButton)findViewById(R.id.btnHouse);
        btnWand = (ImageButton)findViewById(R.id.btnWand);
        btnPatronus = (ImageButton)findViewById(R.id.btnPatronus);

        txtHouse = (TextView)findViewById(R.id.txtHouse);
        txtWand = (TextView)findViewById(R.id.txtWand);
        txtPatronus = (TextView)findViewById(R.id.txtPatronus);

        btnHouse.setOnClickListener(this);
        btnWand.setOnClickListener(this);
        btnPatronus.setOnClickListener(this);

        btnProfile = (Button)findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(this);

        animSequential = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnHouse:
                btnHouse.startAnimation(animSequential);
                startActivity(new Intent(WizardActivity.this, HouseActivity.class));
                break;

            case R.id.btnWand:
                btnWand.startAnimation(animSequential);
                startActivity(new Intent(WizardActivity.this, WandActivity.class));
                break;
            case R.id.btnPatronus:
                btnPatronus.startAnimation(animSequential);
                startActivity(new Intent(WizardActivity.this, PatronusActivity.class));
                break;
            case R.id.btnProfile:
                btnPatronus.startAnimation(animSequential);
                startActivity(new Intent(WizardActivity.this, StartGameActivity.class));
                break;

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
        textTitle.setText(""+prefManager.getPoints("total_points"));

        ImageButton dialogButtonAds = (ImageButton) dialog.findViewById(R.id.btnVidAds);
        dialogButtonAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //display ads and update points
                mDialog = dialog;
                if (mAd.isLoaded()) {
                    mAd.show();
                }else {
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
        //createUIs();
        display();
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
        textTitle.setText(""+prefManager.getPoints("total_points"));
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
