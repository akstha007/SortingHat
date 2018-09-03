package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class Level8Activity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {

    private RewardedVideoAd mAd;
    private PrefManager prefManager;
    private GameSound gameSound;
    private TextView txtCorrect;
    private ScrollView sv;
    private LinearLayout layout;
    private ImageButton btnSend, btnHome, btnInfo;
    private boolean showHint, lastChat, isSound;
    private EditText txtSend;
    private int cQno = 0, tQno = 55, aCorrect = 10;
    private String[][] arrQuestions = {
            {"The boy who lived?", "Harry Potter"},
            {"is best friends with?", "Ron Weasley"},
            {"who ends up marrying?", "Hermione Granger"},
            {"who goes to the Yule Ball with?", "Viktor Krum"},
            {"whose headmaster is ?", "Igor Karkaroff"},
            {"who is interrogated in prison by?", "Barty Crouch Sr."},
            {"whose son impersonates?", "Mad-Eye / Alastor Moody "},
            {"who dies while flying with ?", "Mundungus Fletcher"},
            {"who steals from the house of ?", "Sirius Black"},
            {"who helped create this secret document?", "Marauder's Map"},
            {"which fell into the hands of?", "Fred and George Weasley"},
            {"whose sister is ?", "Ginny Weasley"},
            {"whose friend in Ravenclaw is ?", "Luna Lovegood"},
            {"whose father is the editor of ?", "Quibbler"},
            {"which featured a Harry Potter article written by ?", "Rita Skeeter"},
            {"who was a reporter at the this event?", "Triwizard Tournament"},
            {"which was hosted by ?", "Hogwarts"},
            {"whose headmaster is ?", "Albus Dumbledore"},
            {"who is killed by?", "Severus Snape"},
            {"who pretends he works for?", "Lord Voldemort"},
            {"whose loyal animagus follower is ?", "Wormtail Peter Pettigrew"},
            {"who was discovered to be alive by this professor?", "Remus Lupin"},
            {"who was bitten by?", "Fenrir Greyback"},
            {"who also attacked this Weasley?", "Bill Weasley"},
            {"who is married to ?", "Fleur Delacour"},
            {"whose wedding is interrupted by the patronus of?", "Kingsley Shacklebolt"},
            {"who at that time worked for this man?", "Rufus Scrimgeour"},
            {"who is the successor of?", "Cornelius Fudge"},
            {"who instituted himself at Hogwarts by means of this person?", "Dolores Umbridge"},
            {"who elects this person as head of her Inquisitorial Squad?", "Draco Malfoy"},
            {"whose mother is ?", "Narcissa Malfoy"},
            {"whose death eater sister is?", "Bellatrix Lestrange"},
            {"who tortures this students parents?", "Neville Longbottom"},
            {"who gets tortured by this professor?", "Amycus Carrow"},
            {"who teaches this subject?", "Defence Against Dark Arts"},
            {"which was taught by this professor in Harry's first year?", "Quirinus Quirrell"},
            {"who meets Harry for the first time in this place?", "Leaky Cauldron"},
            {"which lies at the entrance to?", "Diagon Alley"},
            {"in which lies this wand man's wand shop?", "Ollivander"},
            {"who gets rescued from Malfoy Manor by this house elf?", "Dobby"},
            {"whose master was?", "Lucius Malfoy"},
            {"who secretly gives Ginny this in Diagon Alley?", "Tom Riddle's diary"},
            {"which hits this character on the head in the bathroom?", "Moaning Myrtle"},
            {"whose bathroom lies at the entrance to?", "The Chamber of Secrets"},
            {"which contains this creature?", "Basilisk"},
            {"who petrifies this Hufflepuff student?", "Justin Finch-Fletchley"},
            {"who joins this secret organization?", "Dumbledore's Army"},
            {"whose meetings take place in?", "The Room of Requirement"},
            {"which is the home of this Horcrux?", "Rowena Ravenclaw's diadem"},
            {"whose location was revealed to Tom Riddle by?", "The Grey Lady"},
            {"who was killed by ?", "The Bloody Baron"},
            {"who is the ghost of this house?", "Slytherin"},
            {"whose Head in the seventh book is?", "Horace Slughorn"},
            {"whose favorite Muggle born student was ?", "Lily Evans"},
            {"whose son is?", "Harry Potter"}
    };

    private String[][] arrAnswers = {
            {"Harry Potter", "Harry", "Harry James Potter", "Harry J Potter", "Harry J. Potter", "Potter"},
            {"Ron Weasley", "Ronald Weasley", "Ronald Bilius Weasley", "Ron", "Ronald", "Weasley"},
            {"Hermione Granger", "Hermione Jean Granger", "Hermione", "Granger"},
            {"Viktor Krum", "Viktor", "Krum"},
            {"Igor Karkaroff", "Igor", "Karkaroff"},
            {"Barty Crouch Sr.", "Barty Crouch", "Barty", "Crouch"},
            {"Mad-Eye Alastor Moody", "Mad-Eye Alastor", "Alastor Moody", "Mad-Eye", "MadEye", "Mad Eye", "Mad-Eye Moody", "MadEye Moody", "Mad Eye Moody", "Alastor", "Moody"},
            {"Mundungus Fletcher", "Mundungus", "Fletcher"},
            {"Sirius Black", "Sirius", "Black"},
            {"Marauder's Map", "Marauder's", "Marauders", "Marauder", "Map"},
            {"Fred and George Weasley", "Fred and George", "Fred Weasley", "George Weasley", "Fred", "George", "Weasleys", "Weasley's", "Weasley"},
            {"Ginny Weasley", "Ginevra Molly", "Ginevra", "Ginny", "Weasley"},
            {"Luna Lovegood", "Luna", "Lovegood"},
            {"Quibblers", "Quibbler"},
            {"Rita Skeeter", "Rita", "Skeeter"},
            {"Triwizard Tournament", "Triwizard Tournament championship", "Triwizard championship", "Triwizard"},
            {"Hogwart", "Hogwarts"},
            {"Albus Dumbledore", "Albus Percival Wulfric Brian Dumbledore", "Albus Percival", "Albus", "Dumbledore"},
            {"Severus Snape", "Severus", "Halfblood prince","Half blood prince", "Half-blood prince", "Snape"},
            {"Lord Voldemort", "Lord Voldy", "Tom Marvolo Riddle", "Tom Riddle", "Riddle", "Voldy", "Voldemort"},
            {"Wormtail Peter Pettigrew", "Wormtail", "Peter", "Pettigrew"},
            {"Remus Lupin", "Moony", "Remus", "Lupin"},
            {"Fenrir Greyback", "Fenrir", "Greyback"},
            {"Bill Weasley", "Bill", "Weasley"},
            {"Fleur Delacour", "Fleur", "Delacour"},
            {"Kingsley Shacklebolt", "Kingsley", "Shacklebolt"},
            {"Rufus Scrimgeour", "Rufus", "Scrimgeour"},
            {"Cornelius Fudge", "Cornelius", "Fudge"},
            {"Dolores Umbridge", "Dolores", "Umbridge"},
            {"Draco Malfoy", "Draco", "Malfoy"},
            {"Narcissa Malfoy", "Narcissa", "Malfoy"},
            {"Bellatrix Lestrange", "Bellatrix", "Lestrange"},
            {"Neville Longbottom", "Neville", "Longbottom"},
            {"Amycus Carrow", "Amycus", "Carrow"},
            {"Defence Against the Dark Arts", "Defence Against Dark Arts", "Defence Against Dark Art", "Defence Against the Dark Art", "Defence Against", "Defence Against Dark", "Defence"},
            {"Quirinus Quirrell", "Quirinus", "Quirrell"},
            {"Leaky Cauldron", "Leaky", "Cauldron"},
            {"Diagon Alley", "Diagon", "Alley"},
            {"Ollivanders", "Ollivander"},
            {"Dobby", "Dobby"},
            {"Lucius Malfoy", "Lucius", "Malfoy"},
            {"Tom Riddle's diary", "Tom Riddles diary", "Tom Riddle diary", "Tom diary", "Tom's diary", "Toms diary", "Riddle's diary", "Riddles diary", "Riddle diary", "diary"},
            {"Moaning Myrtle", "Myrtle Elizabeth Warren", "Myrtle Warren", "Moaning", "Myrtle"},
            {"The Chamber of Secrets", "The Chamber of Secret", "Chamber of Secrets", "Chamber of Secret", "Chamber"},
            {"Basilisks", "Basilisk"},
            {"Justin Finch-Fletchley", "Justin", "Finch-Fletchley", "Finch", "Fletchley"},
            {"Dumbledore's Army", "Dumbledores Army", "Dumbledore Army", "Dumbledore's", "Dumbledores", "Dumbledore", "DA", "D.A", "D.A.", "D A"},
            {"The Room of Requirement", "Room of Requirement", "Room Requirement", "The Room of Requirements", "Room of Requirements", "Room Requirements", "Room", "Requirement", "Requirements"},
            {"Rowena Ravenclaw's diadem", "Rowena", "Ravenclaw's", "Ravenclaws", "Ravenclaw", "diadem"},
            {"The Grey Lady", "Grey Lady", "Grey", "Lady"},
            {"The Bloody Baron", "Bloody Baron", "Bloody", "Baron"},
            {"Slytherins", "Slytherin"},
            {"Horace Slughorn", "Horace", "Slughorn"},
            {"Lily Evans", "Lily", "Lily Potter", "Lily J. Potter", "Potter", "Evans"},
            {"Harry Potter", "Harry James Potter", "Harry J Potter", "Harry J. Potter", "Harry", "Potter"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level8);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        prefManager = new PrefManager(this);
        gameSound = new GameSound(this);
        isSound = prefManager.getPoints("GameSound") == 0 ? false : true;
        lastChat = true;
        aCorrect = prefManager.getPoints("total_points");
        initId();
        display();

        /*---------Use an activity context to get the rewarded video instance.-------*/
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-7250925653938754/6549455828", new AdRequest.Builder().addTestDevice("62157022C502ECE4B82BE08B1F2CE1EE").build());
    }

    private void display() {
        if (checkQno()) {
            prepareExit();
        } else {
            updateChat(false, "", false);
        }
    }

    private void prepareExit() {
        prefManager.setPoints("total_points", aCorrect);
        prefManager.setPoints("Level 8", aCorrect);
        updateChat(false, "Congratulations!!!", false);
        updateChat(false, "You have completed the level!", false);
        updateChat(false, "You have earned " + aCorrect + " Points for your house!", false);
        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {

            }

            @Override
            public void onFinish() {
                onBackPressed();
            }
        }.start();
    }

    private void updateChat(boolean isMe, final String msg, boolean isCorrect) {
        cQno = cQno < tQno ? cQno : 0;
        if (isMe) {
            //answer send by the user
            TextView txt = new TextView(this);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams.gravity = Gravity.END;
            if (lastChat == isMe) {
                txt.setBackgroundResource(R.drawable.msg_edit);
                lparams.setMargins(80, 0, 7, 5);
            } else {
                txt.setBackgroundResource(R.drawable.msgedit);
                lparams.setMargins(80, 3, 7, 5);
            }
            txt.setLayoutParams(lparams);
            if (isCorrect) {
                //txt.setBackgroundResource(R.drawable.msgsend);
                if (msg.equalsIgnoreCase("")) {
                    //display proper answer when correct
                    txt.setText(arrQuestions[cQno][1]);
                } else {
                    txt.setText(msg);
                }
            } else {
                //txt.setBackgroundResource(R.drawable.msgsenderror);
                if (isSound) {
                    gameSound.playLoose();
                }
                txt.setText(msg);
                txt.setPaintFlags(txt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            txt.setPadding(15, 10, 30, 10);
            txt.setMinimumWidth(150);
            txt.setTextColor(Color.parseColor("#ffffff"));
            //txt.setTextSize(16);
            layout.addView(txt);
        } else {
            //question asked
            final TextView txt = new TextView(this);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams.gravity = Gravity.LEFT;
            if (lastChat == isMe) {
                txt.setBackgroundResource(R.drawable.msg_rec);
                lparams.setMargins(7, 0, 80, 5);
            } else {
                txt.setBackgroundResource(R.drawable.msgreceiving);
                lparams.setMargins(7, 3, 80, 5);
            }
            txt.setLayoutParams(lparams);
            txt.setPadding(30, 10, 10, 15);

            //txt.setText("...");
            if (msg.equalsIgnoreCase("")) {
                txt.setText("...");
            } else {
                txt.setText(msg);
            }

            txt.setTextColor(Color.parseColor("#000000"));
            txt.setMinimumWidth(130);
            //txt.setTextSize(16);
            layout.addView(txt);


            if (msg.equalsIgnoreCase("")) {
                CountDownTimer countDownTimer = new CountDownTimer(1200, 800) {
                    // 500 means, onTick function will be called at every 500 milliseconds

                    @Override
                    public void onTick(long leftTimeInMilliseconds) {
                        //txt.setText("...");
                    }

                    @Override
                    public void onFinish() {
                        txt.setText(arrQuestions[cQno][0]);
                        focusOnView();
                    }
                }.start();
            }
        }
        lastChat = isMe;
        focusOnView();
    }

    private void initId() {
        sv = (ScrollView) findViewById(R.id.scrollChat);
        layout = (LinearLayout) findViewById(R.id.layoutChat);
        btnSend = (ImageButton) findViewById(R.id.btnSend);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        txtSend = (EditText) findViewById(R.id.txtSend);
        txtCorrect = (TextView) findViewById(R.id.txtScore);
        txtCorrect.setText("Points: "+aCorrect);

        btnSend.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        txtSend.setOnClickListener(this);


        txtSend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    focusOnView();
                }
            }
        });

    }

    private boolean checkQno() {
        if (cQno < tQno) {
            return false;
        }
        return true;
    }

    private final void focusOnView() {
        sv.post(new Runnable() {
            @Override
            public void run() {
                sv.smoothScrollTo(0, layout.getBottom());
            }
        });
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
            case R.id.btnSend:
                String ans = txtSend.getText().toString().trim();
                txtSend.setText("");
                if (ans.equalsIgnoreCase("")) {

                } else if (ans.equalsIgnoreCase("question") || ans.equalsIgnoreCase("repeat") || ans.equalsIgnoreCase("again") || ans.equalsIgnoreCase("r") || ans.equalsIgnoreCase("a") || ans.equalsIgnoreCase("q")) {
                    //repeat the question
                    //isCorrect = true;
                    updateChat(true, ans, true);
                    //isCorrect = false;
                    updateChat(false, "", false);
                } else if (ans.equalsIgnoreCase("hint") || ans.equalsIgnoreCase("help") || ans.equalsIgnoreCase("h")) {
                    //display hint-- reduce points
                    //isCorrect = true;
                    showHint = true;
                    updateChat(true, ans, true);
                    //int hintPoints = ((cQno + 1) / 10) * 5 + 5;
                    int hintPoints = 50;
                    updateChat(false, "Type", false);
                    updateChat(false, "1 for ads hint", false);
                    updateChat(false, "2 for " + hintPoints + " points hint", false);
                    //isCorrect = false;
                } else if (ans.equalsIgnoreCase("1") || ans.equalsIgnoreCase("2") && showHint) {
                    //isCorrect = true;
                    updateChat(true, ans, true);
                    //isCorrect = false;
                    if (ans.equalsIgnoreCase("1")) {
                        if (mAd.isLoaded()) {
                            mAd.show();
                        } else {
                            loadRewardedVideoAd();
                            if (mAd.isLoaded()) {
                                mAd.show();
                            } else {
                                updateChat(false, "Unable to load ads.", false);
                                updateChat(false, "Please try again.", false);
                            }
                        }

                    } else if (ans.equalsIgnoreCase("2")) {

                        //int hintPoints = ((cQno + 1) / 10) * 5 + 5;
                        int hintPoints = 50;
                        if (aCorrect >= hintPoints) {
                            showHint = false;
                            aCorrect -= hintPoints;
                            txtCorrect.setText("Points: " + aCorrect);
                            int len = arrAnswers[cQno].length - 1;
                            updateChat(false, "Give this a try!", false);
                            updateChat(false, arrAnswers[cQno][len], false);
                        } else {
                            updateChat(false, "You don\'t have sufficient points.", false);
                            updateChat(false, "Require " + hintPoints + " points.", false);
                        }
                    }
                } else {
                    boolean iCorrect = false;
                    for (int i = 0; i < arrAnswers[cQno].length; i++) {
                        if (ans.equalsIgnoreCase(arrAnswers[cQno][i].toString().trim())) {
                            //string matches the answer then display
                            iCorrect = true;
                            break;
                        }
                    }

                    if (iCorrect) {
                        //display correct answer
                        updateChat(true, "", iCorrect);
                        if (isSound) {
                            gameSound.playWin();
                        }
                        aCorrect += 5;
                        txtCorrect.setText("Points: " + aCorrect);
                        cQno++;
                        display();
                    } else {
                        updateChat(true, ans, iCorrect);
                        //aWrong++;
                        //txtWrong.setText("W " + aWrong);
                    }
                }


                break;
            case R.id.txtSend:
                focusOnView();
                break;

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
        textInfo.setText("Test yor knowledge in Harry Potter characters!" +
                " Type the part/full correct answer to the question mentioned." +
                " Current questions clue is in previous question." +
                "\nType help/hint/h for hints." +
                "\nType question/repeat/again/q/a/r for repeating the question.");


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
        prefManager.setPoints("total_points", aCorrect);
        prefManager.setPoints("Level 8", aCorrect);
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
        updateChat(false, "Watch video ads to unluck the clue.", false);
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        showHint = false;
        int len = arrAnswers[cQno].length - 1;
        updateChat(false, "Give this a try!", false);
        updateChat(false, arrAnswers[cQno][len], false);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        //Toast.makeText(this, "Sorry! Could not load the video.", Toast.LENGTH_SHORT).show();
    }
}
