package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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


public class HouseActivity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {

    private AdView mAdView;
    private RewardedVideoAd mAd;
    private PrefManager prefManager;
    private GameSound gameSound;
    private boolean isSound;
    private TextView txtQuestion, txtQnos;
    private int cQno = 0, tQno = 8;
    private int[] arrPts = {0, 0, 0, 0};
    private LinearLayout flayout;
    private ImageButton btnAds, btnHome, btnInfo;
    private Integer[] arrQno;
    private MediaPlayer mp;

    private String[][] arrOptions = {
            {"What do you want to be remembered as?", "Wise", "Good", "Great", "Bold"},
            {"What you don’t want to be remembered as?", "Selfish", "Ignorant", " Cowardly", "Ordinary"},
            {"Choose one potion.", "Power Potion", "Love Potion", "Wise Potion", "Glory Potion"},
            {"What do you don’t want to be called?", "Weak", "Ignorant", "Unkind", "Boring"},
            {"In a dual battle, which one will be your first spell.", "Expelliarmus", "Protego", "Stupefy", "Crucio"},
            {"You got caught cheating. You would want ...", "Detention with Hagrid in Forbidden Forest.", "Detention with Gilderoy answering his fan letters.", "Detention with  Filch cleaning all trophies.", "Detention with Umbridge writing lines with black quil."},
            {"Can you solve rubik’s cube?", "No, But I will give it a try.", "Yes, in 30 seconds.", "Never heard of it.", "I have better things to do."},
            {"You got less score in a test. What would be your thought?", "That’s impossible. I should have got perfect score.", "That’s alright. At Least I passed.", "That test was rigged.", "I am just glad test is over."},
            {"What would you do if you see two of your friends fighting?", "Try to stop them fighting.", "Let them fight and see who wins.", "Pretend you don’t know them and move away from the scene.", "Take a stand with your close friend."},
            {"You see the student cheating in exam. What would you do?", "You also cheat.", "Inform the guard immediately.", "Pretend you didn’t see anything.", "Just concentrate in your test."},
            {"Choose an element.", "Fire", "Water", "Wind", "Earth"},
            {"Choose one.", "Left", "Right"},
            {"Choose one.", "Up", "Down"},
            {"Choose one.", "Sun", "Moon"},
            {"Choose one.", "New Moon", "Full Moon"},
            {"Choose one.", "Black", "White"},
            {"Choose one.", "Heads", "Tails"},
            {"Choose one.", "Dusk", "Dawn"},
            {"Choose one.", "Forest", "River"},
            {"If you win the lottery, what would you do?", "Buy a car you have always wanted.", "Invest in market and double it.", "Put it in saving account and spend as per needed.", "Throw a feast."},
            {"You see your classmate eating alone in dining hall. You would…", "Ask him/her to join your group.", "You go to his/her table and ask if you can join.", "Make a comment on him/her and laugh with your friends.", "Don’t do anything."},
            {"If someone tries to rob you, what would you do?", "Give everything you have without asking anything.", "Refuse to give any money and tell him what he is doing is wrong.", "Threaten him to call the police.", "Give some amount and hide the rest."},
            {"Your friend has played prank on you infront of whole school. You would...", "Laugh with the crowd.", "Laugh now and plan how you can get back at him/her.", "Scold your friend for pranking you.", "Run away as fast as you can."},
            {"If you find the wallet on the street, you would…", "Take the money and throw away the rest.", "Find the nearest police station and leave it there.", "Pick the wallet and  ask around people about its owner.", "Leave it there, the owner might come back looking for it."},
            {"Someone hid your dress-robe so you became late for the class. Professor starts scolding you.", "Explain him the case and try to reason with him.", "You pretend like you are listening.", "Yell right back at him.", "You admit your mistake and endure his yelling."},
            {"Which skill do you possess?", "Ability to make new friends.", "Ability to keep secrets.", "Ability to get what you want.", "Ability to absorb new information quickly."},
            {"In a quidditch match what do you want to be?", "Seeker", "Beater", "Chaser", "Keeper"},
            {"In your free time, where do you want to go?", "Forbidden forest", "Library", "Kitchen", "Room of requirements"},
            {"Choose a Deathly hallow.", "Elder wand", "Resurrection stone", "Cloak of Invisibility", "All"},
            {"What do you see in the mirror of Erised?", "Surrounded by loving family and friends.", "Surrounded by riches and your accomplishments.", "Surrounded by scholar certificates and accolades.", "Travelling the around the world in adventure."},
            {"The boy stole all your money you have been saving for your new car. You chase the boy and find out he stole for his sick mother. You would…", "Hand him over to police.", "Give all your money.", "Don’t give any money but nurse his sick mother.", "Give all your money and nurse his sick mother."},
            {"Finally, the Sorting hat also considers your opinion. Do you have a preference?", "Yes", "No"},
            {"Which house you want to be in.", "Gryffindor", "Slytherin", "Hufflepuff", "Ravenclaw"}
    };

    private String[][] arrAnswers = {
            {"Gryffindor", "Courage\nBravery\n Determination"},
            {"Slytherin", "Cunning\nAmbitious\nResourceful"},
            {"Ravenclaw", "Cleverness\nWisdom\nCreativity"},
            {"Hufflepuff", "Hard work\nPatience \nLoyalty"}
    };

    private int[] arrImages = {R.drawable.ic_g, R.drawable.ic_s, R.drawable.ic_r, R.drawable.ic_h};

    //G-1,S-2,R-3,H-4
    private int[][] arrPoints = {
            {3, 4, 2, 1},
            {4, 3, 1, 2},
            {1, 4, 3, 2},
            {2, 3, 4, 1},
            {4, 3, 1, 2},
            {1, 3, 4, 2},
            {1, 3, 4, 2},
            {3, 4, 2, 1},
            {1, 2, 4, 3},
            {2, 1, 4, 3},
            {1, 2, 3, 4},
            {3, 4, 1, 2},//two options 4 values
            {1, 3, 2, 4},//
            {1, 4, 2, 3},//
            {2, 3, 1, 4},//
            {2, 3, 1, 4},//
            {1, 2, 3, 4},//
            {1, 3, 2, 4},//
            {1, 3, 2, 4},//
            {2, 3, 4, 1},
            {1, 4, 2, 3},
            {4, 1, 2, 3},
            {1, 3, 2, 4},
            {2, 3, 1, 4},
            {3, 1, 2, 4},
            {1, 4, 2, 3},
            {1, 2, 3, 4},
            {1, 3, 4, 2},
            {1, 4, 3, 2},
            {4, 2, 3, 1},
            {2, 1, 3, 4},
            {1, 2, 4, 3},
            {1, 2, 4, 3}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        prefManager = new PrefManager(this);
        gameSound = new GameSound(this);
        isSound = prefManager.getPoints("GameSound") == 0 ? false : true;

        /*-----------------Setup Ads------------------*/
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7250925653938754/6250881428");
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
        mAd.loadAd("ca-app-pub-7250925653938754/7727614622", new AdRequest.Builder().addTestDevice("62157022C502ECE4B82BE08B1F2CE1EE").build());
    }

    private void displayInfo() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_levelsinfo);


        // set the custom dialog components - text, image and button
        TextView textInfo = (TextView) dialog.findViewById(R.id.txtInstructions);
        textInfo.setText("Get Sorted into one of the Hogwarts House. Remember, Sorting hat also considers your opinion while sorting into a House." +
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

    private boolean playSound(final int houseId) {
        ((ScrollView) findViewById(R.id.viewImage)).setVisibility(View.GONE);
        findViewById(R.id.scrollGameButtons).setVisibility(View.GONE);
        findViewById(R.id.layoutSortingHat).setVisibility(View.VISIBLE);

        int[] arrSound = {R.raw.sound_g, R.raw.sound_s, R.raw.sound_r, R.raw.sound_h};
        mp = MediaPlayer.create(HouseActivity.this, arrSound[houseId]);
        final int[] colorsStatusBar = this.getResources().getIntArray(R.array.array_house_title);
        final int[] colorsTitleBar = this.getResources().getIntArray(R.array.array_house_color);

        try {
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp1) {
                    // TODO Auto-generated method stub
                    mp1.stop();
                    mp1.release();
                    findViewById(R.id.layoutSortingHat).setVisibility(View.GONE);
                    findViewById(R.id.layoutGameResult).setVisibility(View.VISIBLE);
                    changeStatusBarColor(colorsTitleBar[houseId],colorsStatusBar[houseId]);
                }
            });
        }catch (Exception e){
            Log.d("Audio Error",e.getMessage());
            mp.stop();
            mp.release();
            findViewById(R.id.layoutSortingHat).setVisibility(View.GONE);
            findViewById(R.id.layoutGameResult).setVisibility(View.VISIBLE);
            changeStatusBarColor(colorsTitleBar[houseId],colorsStatusBar[houseId]);
        }finally {
            //prepareExit();
            return true;
        }
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
            btn.setPadding(15, 15, 15, 15);
            btn.setBackgroundResource(R.drawable.bg_games);
            btn.setTextSize(16);
            btn.setAllCaps(false);

            final int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int index = arrQno[cQno];
                    if (isSound) {
                        gameSound.playLoose();
                    }

                    int zindex = arrPoints[index][finalI-1] - 1;
                    arrPts[zindex]++;
                    if (arrOptions[index].length < 4) {
                        int jindex = arrPoints[index][finalI*2-1] - 1;
                        arrPts[jindex]++;
                    }
                    if (index == (arrOptions.length - 2)) {
                        //if user choose no go to prepare exit for second last question
                        if(finalI == 2){
                            prepareExit();
                        }
                    }
                    if (index == (arrOptions.length - 1)) {
                        //y2/(y-2x)
                        arrPts[zindex] += arrOptions.length * 2;
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
            //playSound();
        } else {
            int currQno = cQno + 1;
            txtQnos.setText(currQno + "/" + tQno);
            flayout.removeAllViews();
            createUI();
        }
    }

    private void prepareExit() {
        int maxPts = 0, maxIndex = 0, total = 0;
        for (int i = 0; i < 4; i++) {
            total += arrPts[i];
            if (maxPts < arrPts[i]) {
                maxPts = arrPts[i];
                maxIndex = i;
            }
        }

        ((ScrollView) findViewById(R.id.viewImage)).setVisibility(View.GONE);
        findViewById(R.id.scrollGameButtons).setVisibility(View.GONE);

        playSound(maxIndex);

        int match = (maxPts * 100) / total;
        int[] colors = this.getResources().getIntArray(R.array.array_house_color);

        ((TextView) findViewById(R.id.txtHouse)).setText(arrAnswers[maxIndex][0]);
        ((TextView) findViewById(R.id.txtHouse)).setTextColor(colors[maxIndex]);
        //save house answer
        prefManager.setStringValue("House",arrAnswers[maxIndex][0]);

        ((TextView) findViewById(R.id.txtMatching)).setText("Matching: " + match + " %");
        ((TextView) findViewById(R.id.txtMatching)).setTextColor(colors[maxIndex]);

        ((TextView) findViewById(R.id.txtHouseDesc)).setText(arrAnswers[maxIndex][1]);
        ((ImageView) findViewById(R.id.imgHouse)).setImageResource(arrImages[maxIndex]);
        ((LinearLayout) findViewById(R.id.layoutResultDetail)).setBackgroundColor(colors[maxIndex]);
        //((LinearLayout) findViewById(R.id.layoutActionBar)).setBackgroundColor(colors[maxIndex]);

        /*int[] colorsTitleBar = this.getResources().getIntArray(R.array.array_house_title);
        changeStatusBarColor(colorsTitleBar[maxIndex]);*/


        //save patronus details
        //prefManager.setPoints("Level 1", tCoins);

        /*CountDownTimer countDownTimer = new CountDownTimer(60000, 3000) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                //btnAds.startAnimation(animSequential);
            }

            @Override
            public void onFinish() {

                //onBackPressed();
            }
        }.start();*/
    }

    private void genRanQuestion() {
        arrQno = new Integer[arrOptions.length];
        Integer[] myArr = new Integer[arrOptions.length-2];
        for (int i = 0; i < myArr.length; i++) {
            myArr[i] = i;
        }
        Collections.shuffle(Arrays.asList(myArr));
        for (int i = 0; i < arrQno.length-2; i++) {
            arrQno[i] = myArr[i];
        }
        arrQno[arrQno.length - 2] = arrQno.length - 2;
        arrQno[arrQno.length - 1] = arrQno.length - 1;
    }

    private boolean checkQno() {
        if (cQno < tQno) {
            return false;
        }
        return true;
    }

    private void changeStatusBarColor(int colorTitle, int colorStatus) {
        ((LinearLayout) findViewById(R.id.layoutActionBar)).setBackgroundColor(colorTitle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorStatus);
        }
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
