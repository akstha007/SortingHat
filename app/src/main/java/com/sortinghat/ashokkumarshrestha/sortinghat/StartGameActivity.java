package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class StartGameActivity extends AppCompatActivity implements View.OnClickListener {

    private String[][] f_girlname = {
            {"Alice", "Alicia", "Amelia", "Andromeda", "Angelina", "Arabella", "Augusta", "Aurora"},
            {"Bathilda", "Bertha", "Bellatrix"},
            {"Charity", "Cho"},
            {"Demelza", "Dolores"},
            {"Emmeline", "Ernie", "Gabrielle", "Ginny", "Griselda"},
            {},
            {},
            {"Hannah", "Helena", "Helga", "Hermione"},
            {"Irma"},
            {},
            {"Katie"},
            {"Lavender", "Lily", "Luna"},
            {"Mafalda", "Marge", "Marietta", "Marjorie", "Mary", "Merope", "Minerva", "Molly", "Myrtle"}, {"Narcissa", "Nymphadora"},
            {},
            {"Olympe"},
            {"Padma", "Pansy", "Parvati", "Penelope", "Petunia", "Pomona", "Poppy"},
            {},
            {"Rita", "Rolanda", "Romilda", "Rose", "Rowena"},
            {"Septima", "Susan"},
            {},
            {},
            {},
            {"Wilhelmina"},
            {},
            {},
            {}
    };

    private String[][] f_guyname = {
            {"Aberforth", "Alastor", "Albert", "Albus", "Alecto", "Amos", "Amycus", "Anthony", "Antioch", "Antonin", "Argus", "Arthur", "Augustus"},
            {"Bartemius", "Barty", "Blaise", "Bill", "Bob"},
            {"Cadmus", "Cedric", "Charlie", "Colin", "Corban", "Cormac", "Cornelius", "Cuthbert"},
            {"Dean", "Dedalus", "Dennis", "Dirk", "Draco"},
            {"Elphias"},
            {"Fenrir", "Filius", "Fleur", "Florean", "Frank", "Fred"},
            {"Garrick", "Gellert", "George", "Gilderoy", "Godric", "Graham", "Gregorovitch", "Gregory"},
            {"Harry", "Hepzibah", "Horace"},
            {"Ignotus", "Igor"},
            {"James", "Justin"},
            {"Kingsley"},
            {"Lee", "Lucius", "Ludo"},
            {"Marcus", "Marvolo", "Michael", "Millicent", "Morfin", "Mundungus"},
            {"Neville", "Newt"},
            {"Oliver"},
            {"Percy", "Peter", "Phineas", "Pius"},
            {"Quirinus"},
            {"Rabastan", "Reginald", "Regulus", "Remus", "Rodolphus", "Roger", "Ron", "Ronald", "Rubeus", "Rufus"},
            {"Salazar", "Scorpius", "Seamus", "Severus", "Silvanus", "Sirius", "Stanley", "Sturgis", "Sybill"},
            {"Ted", "Terry", "Theodore", "Thomas", "Thorfinn", "Tom"},
            {},
            {"Vernon", "Viktor", "Vincent"},
            {"Walden", "Wilkie"},
            {"Xenophilius"},
            {},
            {"Zach", "Zacharias"}
    };

    private String[][] lname = {
            {"Abbott"},
            {"Bagman", "Bagshot", "Bell", "Binns", "Black", "Bones", "Boot", "Brown", "Bryce", "Bulstrode", "Burbage"},
            {"Carrow", "Cattermole", "Chang", "Clearwater", "Corner", "Crabbe", "Creevey", "Cresswell", "Crouch"},
            {"Davies", "Delacour", "Diggle", "Diggory", "Doge", "Dolohov", "Dumbledore", "Dursley"},
            {"Edgecombe"},
            {"Figg", "Filch", "Finch-Fletchley", "Finnigan", "Fletcher", "Flint", "Flitwick", "Fortescue", "Fudge"},
            {"Gaunt", "Goldstein", "Goyle", "Granger", "Greyback", "Grindelwald", "Grubbly", "Gryffindor"},
            {"Hagrid", "Hooch", "Hopkirk", "Hufflepuff"},
            {},
            {"Jane", "Johnson", "Jordan", "Jorkins"},
            {"Karkaroff", "Kettleburn", "Krum"},
            {"Lestrange", "Lockhart", "Longbottom", "Lovegood", "Lupin"},
            {"Macmillan", "Macnair", "Malfoy", "Malkin", "Marchbanks", "Marvolo", "Maxime", "McGonagall", "McLaggen", "Montague", "Moody"},
            {"Nott"},
            {"Ogden", "Ollivander"},
            {"Parkinson", "Patil", "Pettigrew", "Peverell", "Pince", "Podmore", "Pomfrey", "Potter"},
            {"Quirrell"},
            {"Ravenclaw", "Riddle", "Robins", "Rookwood", "Rowle", "Runcorn"},
            {"Scamander", "Scrimgeour", "Shacklebolt", "Shunpike", "Sinistra", "Skeeter", "Slughorn", "Slytherin", "Smith", "Snape", "Spinnet", "Sprout"},
            {"Thicknesse", "Thomas", "Tonks", "Trelawney", "Twycross"},
            {},
            {"Vance", "Vane", "Vector"},
            {"Warren", "Weasley", "Wood"},
            {},
            {"Yaxley"},
            {"Zabini"}
    };

    private LinearLayout layoutNameGenerator, layoutNameresult, layoutNameChoose;
    private RadioGroup radioGroupGender, radioGroupName;
    private EditText editTextFname, editTextLname;
    private Button btnGenerateName;
    private ImageButton btnTryAgain, btnContinue, btnContinueName;
    private TextView txtWizardName;
    private PrefManager prefManager;
    private ImageButton btnHome, btnInfo;
    private String wizardName, actualName;
    private boolean gender = false;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        prefManager = new PrefManager(this);

        initIds();
    }

    private void initIds() {
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        radioGroupName = (RadioGroup) findViewById(R.id.radioGroupName);
        editTextFname = (EditText) findViewById(R.id.editFName);
        editTextLname = (EditText) findViewById(R.id.editLName);
        txtWizardName = (TextView) findViewById(R.id.txtWizardName);
        btnGenerateName = (Button) findViewById(R.id.btnGenerateName);
        btnTryAgain = (ImageButton) findViewById(R.id.btnTryAgain);
        btnContinueName = (ImageButton) findViewById(R.id.btnContinueName);
        btnContinue = (ImageButton) findViewById(R.id.btnContinue);
        layoutNameGenerator = (LinearLayout) findViewById(R.id.layoutNameGenerator);
        layoutNameresult = (LinearLayout) findViewById(R.id.layoutNameResult);
        layoutNameChoose = (LinearLayout) findViewById(R.id.layoutNameChoose);
        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnHome = (ImageButton) findViewById(R.id.btnHome);

        btnHome.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnGenerateName.setOnClickListener(this);
        btnTryAgain.setOnClickListener(this);
        btnContinueName.setOnClickListener(this);
        btnContinue.setOnClickListener(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        animation.setDuration(900);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHome:
                onBackPressed();
                break;
            case R.id.btnGenerateName:
                generateWizardName();

                break;
            case R.id.btnTryAgain:
                tryAgain();

                break;
            case R.id.btnContinueName:
                continueName();
                break;
            case R.id.btnContinue:
                continueNextLevel();
                break;
            case R.id.btnInfo:
                displayInfo();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void displayInfo() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_levelsinfo);


        // set the custom dialog components - text, image and button
        TextView textInfo = (TextView) dialog.findViewById(R.id.txtInstructions);
        textInfo.setText("Generate your Wizard Name" +
                "Enter the details and generate your wizard name.\nThen choose your wizard name or actual name for the game." +
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

    private void continueNextLevel() {
        int selectedId = radioGroupName.getCheckedRadioButtonId();
        String namePrefix = "";
        if(gender){
            namePrefix = "Mr. ";
        }else{
            namePrefix = "Ms. ";
        }
        switch (selectedId) {
            case R.id.radioWizardName:
                prefManager.setStringValue("playerName",namePrefix + wizardName + ",");
                Toast.makeText(this, "Name: " + wizardName + " set.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioActualName:
                prefManager.setStringValue("playerName", namePrefix + actualName + ",");
                Toast.makeText(this, "Name: " + actualName + " set.", Toast.LENGTH_SHORT).show();
                break;
        }

        onBackPressed();
    }

    private void continueName() {
        //layoutNameGenerator.setVisibility(View.GONE);
        //layoutNameresult.setVisibility(View.GONE);
        layoutNameChoose.setVisibility(View.VISIBLE);
        layoutNameChoose.setAnimation(animation);
        animation.start();
    }

    private void tryAgain() {
        layoutNameGenerator.setVisibility(View.VISIBLE);
        layoutNameresult.setVisibility(View.GONE);
        layoutNameChoose.setVisibility(View.GONE);
    }

    private void generateWizardName() {
        String firstName = editTextFname.getText().toString().trim();
        String lastName = editTextLname.getText().toString().trim();

        actualName = firstName + " " + lastName;

        if(firstName.isEmpty()){
            Toast.makeText(this, "Enter First Name.", Toast.LENGTH_SHORT).show();
            return;
        }else if(lastName.isEmpty()){
            Toast.makeText(this, "Enter Last Name.", Toast.LENGTH_SHORT).show();
            return;
        }


        gender = false;
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radioMale:
                gender = true;
                break;
            case R.id.radioFemale:
                gender = false;
                break;
        }

        //Toast.makeText(this, "Gen: " + fName + lName + gender, Toast.LENGTH_SHORT).show();

        //layoutNameGenerator.setVisibility(View.GONE);
        layoutNameresult.setVisibility(View.VISIBLE);
        layoutNameChoose.setVisibility(View.GONE);

        layoutNameresult.setAnimation(animation);
        animation.start();

        String wizardName = getWizName(firstName, lastName, gender);

        txtWizardName.setText(wizardName);

        //hide virtual keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private String getWizName(String firstName, String lastName, boolean gender) {
        wizardName = "";
        int fIndex = (int) firstName.toLowerCase().charAt(0) - (int) 'a';
        int lIndex = (int) lastName.toLowerCase().charAt(0) - (int) 'a';

        if (!gender) {
            while (f_girlname[fIndex].length <= 0) {
                fIndex = (fIndex + 1) % f_girlname.length;
            }
            int nameIndex = firstName.length() % f_girlname[fIndex].length;
            wizardName = f_girlname[fIndex][nameIndex];
        } else {
            while (f_guyname[fIndex].length <= 0) {
                fIndex = (fIndex + 1) % f_guyname.length;
            }
            int nameIndex = firstName.length() % f_guyname[fIndex].length;
            wizardName = f_guyname[fIndex][nameIndex];
        }
        while (lname[lIndex].length <= 0) {
            lIndex = (lIndex + 1) % lname.length;
        }
        int nameIndex = lastName.length() % lname[lIndex].length;
        wizardName += " " + lname[lIndex][nameIndex];

        return wizardName;
    }
}
