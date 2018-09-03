package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.AdView;

import java.util.Arrays;
import java.util.Collections;
import java.util.zip.Inflater;


public class Level7Activity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {

    private Integer[] arrQno;
    private AdView mAdView;
    private RewardedVideoAd mAd;
    private PrefManager prefManager;
    private GameSound gameSound;
    private FlexboxLayout flayout, layoutAns;
    private LinearLayout playout, layoutSuccess;
    private boolean showHint, isSound, isVidAds;
    private TextView txtHint, txtPoints;
    private Button btnHorcrux, btnNoHorcrux, btnSaveLife;
    private ImageButton btnInfo, btnHome, btnBack, btnHint;
    private LinearLayout btnLayout;
    private int currButton = 0, prevButton = 0, points = 10, life = 1, blockIndex = 0;
    private Dialog mDialog;
    private Button[] buttons;
    private Level7Block[] blocks;
    private int[] arrId;
    private int cQno = 0, tQno = 47;
    private int[][] arrPics = {
            {R.drawable.ic_lightning, R.drawable.ic_glass},
            {R.drawable.ic_dumb, R.drawable.ic_bell, R.drawable.ic_door},
            {R.drawable.ic_lock, R.drawable.ic_heart},
            {R.drawable.ic_run},
            {R.drawable.ic_head, R.drawable.ic_hair},
            {R.drawable.ic_u,R.drawable.ic_m,R.drawable.ic_bridge},
            {R.drawable.ic_angry, R.drawable.ic_eye},
            {R.drawable.ic_ginie},
            {R.drawable.ic_moon},
            {R.drawable.ic_heart, R.drawable.ic_good},
            {R.drawable.ic_moon, R.drawable.ic_wolf},
            {R.drawable.ic_howl_wolf},
            {R.drawable.ic_black_dog},
            {R.drawable.ic_ruby, R.drawable.ic_yes},
            {R.drawable.ic_bill},
            {R.drawable.ic_hats, R.drawable.ic_mustache},
            {R.drawable.ic_olive, R.drawable.ic_wood},
            {R.drawable.ic_cat, R.drawable.ic_bell},
            {R.drawable.ic_car, R.drawable.ic_mac},
            {R.drawable.ic_frog},
            {R.drawable.ic_stag},
            {R.drawable.ic_grey_lady, R.drawable.ic_crown},
            {R.drawable.ic_squirrel},
            {R.drawable.ic_sprout},
            {R.drawable.ic_bone},
            {R.drawable.ic_slug, R.drawable.ic_horn},
            {R.drawable.ic_dragon},
            {R.drawable.ic_bell, R.drawable.ic_trick},
            {R.drawable.ic_crab},
            {R.drawable.ic_car, R.drawable.ic_arrow},
            {R.drawable.ic_rook, R.drawable.ic_wood},
            {R.drawable.ic_car, R.drawable.ic_car, R.drawable.ic_off},
            {R.drawable.ic_shackel, R.drawable.ic_lightning},
            {R.drawable.ic_sock},
            {R.drawable.ic_cat, R.drawable.ic_wizard_hat},
            {R.drawable.ic_spider},
            {R.drawable.ic_rats},
            {R.drawable.ic_fire, R.drawable.ic_lightning},
            {R.drawable.ic_bag,R.drawable.ic_mans},
            {R.drawable.ic_bag,R.drawable.ic_musket},
            {R.drawable.ic_fin,R.drawable.ic_musket},
            {R.drawable.ic_butterbeer},
            {R.drawable.ic_green,R.drawable.ic_doll,R.drawable.ic_wall},
            {R.drawable.ic_raven,R.drawable.ic_claw},
            {R.drawable.ic_phoenix},
            {R.drawable.ic_horse,R.drawable.ic_mans},
            {R.drawable.ic_black_dogs,R.drawable.ic_doghead,R.drawable.ic_doghead}

    };
    private String[][] arrWords = {
            {"Harry Potter", "Harry Pott res", "HarryPotter"},
            {"Dumbledore", "Dumbledore hpm", "Dumbledore"},
            {"Gilderoy Lockhart", "Gildy Lockhart", "Lockhart"},
            {"Ron Weasley", "Ron Weasleyad", "RonWeasley"},
            {"Hedwig", "Hedwig poter", "Hedwig"},
            {"Dolores Umbridge", "Doles Umbridge", "Umbridge"},
            {"Madeye Moody", "Madeye moody", "Madeye"},
            {"Ginny Weasley", "Ginny wesly", "Ginny"},
            {"Luna Lovegood", "Luna lovegood", "Luna"},
            {"Luna Lovegood", "Luna lovegood", "Lovegood"},
            {"Remus Lupin", "Lupin remus pz", "RemusLupin"},
            {"Fenrir Greyback", "Fenir Greyback", "Greyback"},
            {"Sirius  Black", "Sirius  black dog", "SiriusBlack"},
            {"Rubeus hagrid", "Rubeus hagrid me", "Rubeus"},
            {"Bill weasley", "Bill weasley ius", "Bill"},
            {"Charlie Weasley", "Charlie easley", "Charlie"},
            {"Oliver Wood", "Oliver wood er", "Oliverwood"},
            {"Katie Bell", "Katie bell cy", "KatieBell"},
            {"Cormac Mclaggen", "Cormac mclaggen K", "Cormac"},
            {"Trevor", "Trevor nevil", "Trevor"},
            {"James Potter", "James Potter G", "JamesPotter"},
            {"The grey lady", "grey lady the", "Thegreylady"},
            {"Quirinus Quirrell", "Quirnus Quirrell", "Quirrell"},
            {"Pomona Sprout", "Pomna Sprout", "Sprout"},
            {"Susan bones", "Susana bones", "Bones"},
            {"Horace slughorn", "Hace slughorn", "Slughorn"},
            {"Draco malfoy", "Draco mlfoy", "Draco"},
            {"Bellatrix lestrange", "Bellatrix lesng", "Bellatrix"},
            {"Vincent crabbe", "Vinct crabbe", "crabbe"},
            {"Carrow", "Carrow amycus", "Carrow"},
            {"Augustus rookwood", "Augs rookwood", "rookwood"},
            {"Igor karkaroff", "Ig karkaroff", "Karkaroff"},
            {"Kingsley shacklebolt", "Kingy shacklebolt", "Shacklebolt"},
            {"Dobby", "Dobby daie", "Dobby"},
            {"Minerva Mcgonagall", "iervMcgonagall", "Mcgonagall"},
            {"Aragog", "Aragog azz", "Aragog"},
            {"Peter Pettigrew", "Pettigrew pet", "Pettigrew"},
            {"Firebolt", "Firebolt sam", "Firebolt"},
            {"Ludo Bagman","Ludo Bagman sa","Bagman"},
            {"Bathilda Bagshot","Bild Bagshot","Bagshot"},
            {"Seamus finnigan","Semus finnigan","finnigan"},
            {"Butterbeer","Butterbeer asa","Butterbeer"},
            {"Gellert Grindelwald","Get Grindelwald","Grindelwald"},
            {"Ravenclaw","ravenclaw roea","ravenclaw"},
            {"Fawkes","Fawkes phoenix","Fawkes"},
            {"Firenze","Firenze centaur","Firenze"},
            {"Fluffy","Fluffy dogs","Fluffy"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level7);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        prefManager = new PrefManager(this);
        gameSound = new GameSound(this);
        isSound = prefManager.getPoints("GameSound") == 0 ? false : true;
        points = prefManager.getPoints("total_points");

        genRanQuestion();
        initId();
        displayUI();

        /*-----------------Setup Ads------------------*/
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7250925653938754/2956861020");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*---------Use an activity context to get the rewarded video instance.-------*/
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-7250925653938754/5072722626", new AdRequest.Builder().addTestDevice("62157022C502ECE4B82BE08B1F2CE1EE").build());
    }

    private void displayUI() {

        tQno = arrWords.length;
        flayout.removeAllViews();
        layoutAns.removeAllViews();
        //Display pics

        playout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        playout.setVerticalGravity(Gravity.CENTER_VERTICAL);
        int index = arrQno[cQno];
        for (int i = 0; i < arrPics[index].length; i++) {
            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams.setMargins(1, 1, 1, 1);
            img.setLayoutParams(lparams);
            img.setPadding(10, 10, 10, 10);
            img.setMaxHeight(80);
            img.setMaxWidth(80);
            img.setBackgroundResource(R.drawable.bg_game);
            img.setImageDrawable(getResources().getDrawable(arrPics[index][i]));

            playout.addView(img);

            if (i < (arrPics[index].length - 1)) {
                //add plus sign between images
                ImageView imgPlus = new ImageView(this);
                LinearLayout.LayoutParams lparamsPlus = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lparams.setMargins(5, 1, 5, 1);
                imgPlus.setLayoutParams(lparamsPlus);
                imgPlus.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));

                playout.addView(imgPlus);
            }
        }

        //Display answer fields
        int len = arrWords[index][2].replace(" ", "").length();
        blocks = new Level7Block[len];
        int offset = 10;

        for (int i = 0; i < len; i++) {
            addAns("_", i + offset);
            blocks[i] = new Level7Block();
            blocks[i].setLetter("_");
            blocks[i].setAnsId(i + offset);
        }

        //Display Buttons
        final String str = arrWords[index][1].trim().replace(" ", "");
        final Integer[] arr = new Integer[str.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr));
        for (int i = 0; i < str.length(); i++) {

            final Button btn = new Button(this);
            final int ioffset = 99;
            int dx = (int)getResources().getDisplayMetrics().widthPixels;
            //Toast.makeText(Level7Activity.this,"a"+dx,Toast.LENGTH_SHORT).show();
            int xwidth = 100,yheight=110;
            if(dx<=480){
                xwidth = 60;
                yheight = 80;
            }
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(xwidth,
                    yheight);
            lparams.setMargins(1, 1, 1, 1);
            btn.setLayoutParams(lparams);
            btn.setId(i + ioffset);
            //btn.setPadding(10,10,10,10);
            btn.setText("" + str.charAt(arr[i]));
            //btn.setTextSize(16);
            btn.setBackgroundResource(R.drawable.bg_game);
            btn.setAllCaps(true);

            final int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkAns()) {
                        btn.setVisibility(View.INVISIBLE);
                        updateAns(finalI + ioffset, "" + str.charAt(arr[finalI]));
                    }
                }
            });

            //Animation
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_up);
            animation.setDuration(900 + 300 * i);
            btn.setAnimation(animation);
            animation.start();

            flayout.addView(btn);

        }
    }

    private boolean checkAns() {
        int index = arrQno[cQno];
        if (blockIndex < arrWords[index][2].length()) {
            return true;
        } else {
            return false;
        }
    }

    private void updateAns(int blockId, String letter) {
        if (checkAns()) {
            int index = blockIndex++;
            blocks[index].setBlockId(blockId);
            blocks[index].setLetter(letter);
            int id = blocks[index].getAnsId();
            ((TextView) findViewById(id)).setText(letter.toUpperCase());
            if (!checkAns()) {
                //check if ans is correct or not
                //Toast.makeText(Level10Activity.this,"check out",Toast.LENGTH_SHORT).show();
                verifyAns();
            }
        }
    }

    private void verifyAns() {
        String ans = "";
        int index = arrQno[cQno];
        for (int i = 0; i < arrWords[index][2].length(); i++) {
            ans += blocks[i].getLetter();
        }
        if (ans.equalsIgnoreCase(arrWords[index][2].replace(" ", ""))) {
            //correct answer
            //Toast.makeText(Level10Activity.this,"Correct Answer",Toast.LENGTH_SHORT).show();
            //change the question no. display success message
            updatePoints(5);
            //remove block objects
            for (int i = 0; i < arrWords[index][2].length(); i++) {
                blocks[i] = null;
            }
            displaySuccess();
            updateDisplay();

        } else {
            //Toast.makeText(Level10Activity.this,"Incorrect: " + ans+"<>"+arrWords[cQno][2],Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDisplay() {
        btnBack.setEnabled(false);
        int index = arrQno[cQno];
        if (cQno < tQno-1) {
            CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
                // 500 means, onTick function will be called at every 500 milliseconds

                @Override
                public void onTick(long leftTimeInMilliseconds) {
                }

                @Override
                public void onFinish() {
                    layoutSuccess.setVisibility(View.GONE);
                    playout.setVisibility(View.VISIBLE);
                    cQno++;
                    blockIndex = 0;
                    btnBack.setEnabled(true);
                    displayUI();
                }
            }.start();

        } else {
            //game completed -> exit
            TextView txtSuccess = (TextView) findViewById(R.id.txtCorrect);
            txtSuccess.setText("All Levels completed!");
            prepareExit();
        }
    }

    private void displaySuccess() {
        playout.removeAllViews();
        playout.setVisibility(View.GONE);
        layoutSuccess.setVisibility(View.VISIBLE);
        TextView txtSuccess = (TextView) findViewById(R.id.txtCorrect);
        int index = arrQno[cQno];
        txtSuccess.setText(arrWords[index][0]);
    }

    private void addAns(String str, int id) {
        /*layoutAns.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        layoutAns.setVerticalGravity(Gravity.CENTER_VERTICAL);*/
        TextView textView = new TextView(this);
        textView.setId(id);
        textView.setText(str.toUpperCase());
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.setMargins(1, 1, 1, 1);
        textView.setLayoutParams(lparams);
        textView.setTextSize(20);
        textView.setTextColor(getResources().getColor(R.color.colorBlack));
        textView.setPadding(15, 5, 15, 5);
        textView.setBackgroundResource(R.drawable.bg_games);
        layoutAns.addView(textView);

    }

    private void initId() {
        txtPoints = (TextView) findViewById(R.id.txtScore);
        flayout = (FlexboxLayout) findViewById(R.id.layoutGameButtons);
        layoutAns = (FlexboxLayout) findViewById(R.id.layoutAns);
        playout = (LinearLayout) findViewById(R.id.layoutBox);
        layoutSuccess = (LinearLayout) findViewById(R.id.layoutSuccess);

        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnHint = (ImageButton) findViewById(R.id.btnHint);

        txtPoints.setText("Points: " + points);

        btnInfo.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnHint.setOnClickListener(this);
    }

    private void genRanQuestion() {
        arrQno = new Integer[arrWords.length];
        for (int i = 0; i < arrQno.length; i++) {
            arrQno[i] = i;
        }
        Collections.shuffle(Arrays.asList(arrQno));
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
            case R.id.btnHint:
                displayHint();
                break;
            case R.id.btnBack:
                //delete answers and display blocks if blockindex >0 and block is not set
                if (blockIndex > 0 && !blocks[blockIndex - 1].getFlag()) {
                    blockIndex--;
                    //display block
                    int id = blocks[blockIndex].getBlockId();
                    ((Button) findViewById(id)).setVisibility(View.VISIBLE);

                    //delete answer
                    updateAns(0, "_");
                    blockIndex--;
                }
                break;
        }
    }

    public void removeAns(int len) {
        for (int i = 0; i < Math.min(len,blockIndex); i++) {
            int id = blocks[i].getBlockId();
            if(id>-1) {
                ((Button) findViewById(id)).setVisibility(View.VISIBLE);
            }
        }
    }

    private void updatePoints(int point) {
        points += point;
        txtPoints.setText("Points: " + points);
        //ring the sound
        if (isSound) {
            gameSound.playWin();
        }
    }

    private void prepareExit() {
        CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {

            }

            @Override
            public void onFinish() {
                prefManager.setPoints("Level 7", points);
                onBackPressed();
            }
        }.start();
    }

    private void displayHint() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_level7hint);

        ImageButton dialogButtonClose = (ImageButton) dialog.findViewById(R.id.btnClose);
        Button dialogButtonFirst = (Button) dialog.findViewById(R.id.btnHintFirst);
        Button dialogButtonMore = (Button) dialog.findViewById(R.id.btnHintMore);
        Button dialogButtonAll = (Button) dialog.findViewById(R.id.btnHintAll);

        // if button is clicked, close the custom dialog
        dialogButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintAns(1);
                dialog.dismiss();
            }
        });
        dialogButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintAns(3);
                dialog.dismiss();
            }
        });
        dialogButtonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = arrQno[cQno];
                hintAns(arrWords[index][2].length());
                dialog.dismiss();
            }
        });

        ImageButton dialogButtonAds = (ImageButton) dialog.findViewById(R.id.btnVidAds);
        dialogButtonAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //display ads and update points
                dialog.dismiss();
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

        dialog.show();
    }

    public void hintAns(int len) {
        if (points >= 30 || points >= len * 5) {
            
            switch (len) {
                case 1:
                    removeAns(1);
                    if (blockIndex < len) {
                        blockIndex = len;
                    }
                    updatePoints(-5);
                    break;
                case 3:
                    removeAns(3);
                    if (blockIndex < len) {
                        blockIndex = len;
                    }
                    updatePoints(-(5 * len));
                    break;
                default:
                    blockIndex += len;
                    updatePoints(-35);
                    //verifyAns();
            }

            for (int i = 0; i < len; i++) {
                int index = arrQno[cQno];
                blocks[i].setLetter("" + arrWords[index][2].charAt(i));
                blocks[i].setFlag(true);
                blocks[i].setBlockId(-1);
                int id = blocks[i].getAnsId();
                ((TextView) findViewById(id)).setText(("" + arrWords[index][2].charAt(i)).toUpperCase());
            }

            verifyAns();
            
        } else {
            Toast.makeText(Level7Activity.this, "Insufficient Points! Watch Ads to get Points.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayInfo() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_levelsinfo);


        // set the custom dialog components - text, image and button

        TextView textInfo = (TextView) dialog.findViewById(R.id.txtInstructions);
        textInfo.setText("Solve the riddle based on the clues provided." +
                " Clues include combination of pictures." +
                "\n5 Points will be awarded for correct answer." +
                "\nYou can revive the life at the expense of 50 points or by watching ads." +
                "\n\nLet the magic begin!");

        ImageButton dialogButtonClose = (ImageButton) dialog.findViewById(R.id.btnClose);
        dialogButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ImageButton dialogButtonAds = (ImageButton) dialog.findViewById(R.id.btnVidAds);
        dialogButtonAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //display ads and update points
                dialog.dismiss();
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

        dialog.show();
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
        //super.onBackPressed();
        prefManager.setPoints("total_points", points);
        prefManager.setPoints("Level 7", points);
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
        /*isVidAds = false;
        updateSavePoints(mDialog, "Sorry! Could not revive. Need to watch ads for charm to work");*/
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        updatePoints(100);
        /*isVidAds = true;
        updatePoints(50);
        updateSavePoints(mDialog, "Sorry! Could not revive. Need to watch ads for charm to work");*/
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }
}
