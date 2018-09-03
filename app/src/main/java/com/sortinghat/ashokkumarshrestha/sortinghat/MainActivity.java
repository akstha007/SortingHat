package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PrefManager prefManager;
    ImageButton btnRate, btnShare, btnPlay, btnSettings, btnMoreGames, btnQuote, btnWizard;
    Animation animSequential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefManager = new PrefManager(this);

        if (prefManager.getPoints("IsFirstTime") == 0) {
            prefManager.setPoints("IsFirstTime", 1);
            prefManager.setPoints("total_points", 100);
            prefManager.setPoints("GameSound", 1);
        }

        btnRate = (ImageButton) findViewById(R.id.btnRateUs);
        btnShare = (ImageButton) findViewById(R.id.btnShareUs);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        btnMoreGames = (ImageButton) findViewById(R.id.btnMoreGames);
        btnQuote = (ImageButton) findViewById(R.id.btnQuote);
        btnWizard = (ImageButton) findViewById(R.id.btnWizard);

        btnRate.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnMoreGames.setOnClickListener(this);
        btnQuote.setOnClickListener(this);
        btnWizard.setOnClickListener(this);

        animSequential = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        //animateUI();

        //show new game info when activity starts
        int showNewGameInfo = prefManager.getPoints("NewGameInfo");
        if (showNewGameInfo != 1) {
            displayNewGameInfo();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                btnPlay.startAnimation(animSequential);
                startActivity(new Intent(MainActivity.this, PlayActivity.class));
                break;
            case R.id.btnRateUs:
                btnRate.startAnimation(animSequential);
                rateApp();
                break;
            case R.id.btnShareUs:
                btnShare.startAnimation(animSequential);
                shareApp();
                break;
            case R.id.btnMoreGames:
                btnMoreGames.startAnimation(animSequential);
                newApp();
                break;
            case R.id.btnSettings:
                btnSettings.startAnimation(animSequential);
                displayInfo();
                break;
            case R.id.btnQuote:
                btnQuote.startAnimation(animSequential);
                Intent intent = new Intent(MainActivity.this, QuoteActivity.class);
                intent.putExtra("Quote", "");
                startActivity(intent);
                finish();
                break;
            case R.id.btnWizard:
                btnWizard.startAnimation(animSequential);
                startActivity(new Intent(MainActivity.this, WizardActivity.class));
                break;
        }
    }


    public void newAppInfo() {
        Uri uri = Uri.parse("market://details?id=com.developers.meraki.sortinghatpro");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=com.developers.meraki.sortinghatpro")));
        }
    }

    public void newApp() {
        Uri uri = Uri.parse("market://developer?id=Ashok+Kumar+Shrestha");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=Ashok+Kumar+Shrestha")));
        }
    }

    public void shareApp() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Sorting Hat");
        String text = "Must for every Potterheads! \nLet the Magic begin!\n\n";
        text += "http://play.google.com/store/apps/details?id=" + this.getPackageName();
        i.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(i, "Share via"));

    }

    public void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }

    private void displayNewGameInfo() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_newgameinfo);

        ImageButton btnClose = (ImageButton) dialog.findViewById(R.id.btnClose);
        final CheckBox checkBoxDontShow = (CheckBox) dialog.findViewById(R.id.checkboxDontshow);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxDontShow.isChecked()) {
                    //hide the message if checked
                    prefManager.setPoints("NewGameInfo",1);
                    //Toast.makeText(getApplicationContext(), "Checkbox checked!!!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        Button btnTryNewGame = (Button) dialog.findViewById(R.id.btnTryNewgame);
        btnTryNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to new game
                if (checkBoxDontShow.isChecked()) {
                    //hide the message if checked
                    prefManager.setPoints("NewGameInfo",1);
                }
                newAppInfo();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void displayInfo() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_settings);

        ImageButton btnClose = (ImageButton) dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Switch toggleSound = (Switch) dialog.findViewById(R.id.toggleSound);
        boolean isSound = prefManager.getPoints("GameSound") == 0 ? false : true;
        toggleSound.setChecked(isSound);

        toggleSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    prefManager.setPoints("GameSound", 1);
                } else {
                    prefManager.setPoints("GameSound", 0);
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //animateUI();
    }

    private void animateUI() {
        findViewById(R.id.imgTitle).startAnimation(animSequential);
    }

    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.setNegativeButton("No", null);

        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setTitle("Sorting Hat");
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        doExit();
    }
}
