package com.example.maricosurvey;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class form extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 0;
    public LocationManager locationManager;
    public GPSLocationListener listener;
    public Location previousBestLocation = null;
    private static final int TWO_MINUTES = 1000 * 60 * 1;
    public static final String BROADCAST_ACTION = "gps_data";
    Intent intent;

    Button btnDisclaimer, premisePhoto, cardPhoto, signboardPhoto,chamberPhoto;
    String premisePhotoPath = "", cardPhotPath = "", signboardPhotPath= "", chamberPhotoPath= "";
    static String photoName = "", photoName1 = "", photoName2 = "",photoName3 = "", photoName4 = "";
    static String imageString1 = "",imageString2 = "",imageString3 = "",imageString4 = "";
    static Bitmap bitmap1,bitmap2, bitmap3, bitmap4;
    Bundle IDbundle;
    String currentPhotoPath = "";
    Uri photoURI1, photoURI2, photoURI3, photoURI4;
    static final int REQUEST_IMAGE_CAPTURE = 99;

    Integer premise=0,card=0,sign=0,chamber=0;

    ProgressDialog progressDialog;
    JSONObject jsonObject;
    JSONArray jsonArray;
    JSONObject jsonObj;
    JSONArray jsonArr;
    String Json_String;
    String division, district, thana;
    Integer divisionStartIndex;
    Integer divisionEndIndex;
    String thanaCode1="";
    String thanaCode2="";
    String thanaCode3="";

    public static String presentLat = "", presentLon = "", presentAcc = "";
    String imageString = "";
    String temp = "", NumberOfChamber = "1";
    String[] sDropList = new String[] {"Male", "Female"};
    String[] yesnoDropList = new String[] {"N/A", "Yes", "No"};
    String[] chamberTypesList = new String[] {"N/A", "Pharmacy", "Hospital", "Clinic", "Stand Alone", "Other"};
    String[] tittleList = new String[] {"N/A","DR", "OTHERS"};
    String[] categoryList = new String[] {"N/A", "PEDIATRICIAN","CARDIOLOGIST","DERMATOLOGIST","GENERAL PRACTITIONER","GYNECOLOGIST","MEDICINE SPECIALIST","PEDIATRIC SURGERY","OTHERS"};
    String[] designationList = new String[] {"N/A","PROFESSOR", "ASSOCIATE PROFESSOR", "ASSISTANT PROFESSOR", "REGISTRAR", "SENIOR REGISTRAR","ASSISTANT REGISTRAR",
    "CONSULTANT","CONSULTANT HEAD", "SENIOR CONSULTANT","ASSOCIATE CONSULTANT","CHILD SPECIALIST","DEPARTMENTAL HEAD","DEPUTY CO-ORDINATOR, CHRF","EMERGENCY MEDICAL OFFICER",
            "EX-PRINCIPAL & DIRECTOR","PRINCIPAL","DIRECTOR","DEPARTMENT HEAD","IN CHARGE","JUNIOR CONSULTANT","MD RESIDENT","MEDICAL OFFICER","SENIOR MEDICAL OFFICER","PEDIATRIC SURGEON",
            "RESIDENTIAL MEDICAL OFFICER","RESIDENTIAL MEDICAL OFFICER (ENT)","UHFPO","OTHER"};
    String[] offdayList = new  String[] {"N/A", "Saturday","Sunday","Monday","Tuesday","Wednesday", "Thursday","Friday"};
    String[] divisionList = new String[] {"Barisal", "Chittagong", "Dhaka", "Khulna", "Rajshahi", "Rangpur", "Sylhet"};



    String[] barisalDistricList = new String[] {"Barguna", "Barisal", "Bhola", "Jhalokati", "Patuakhali", "Pirojpur"};
    String[] chittagongDistricList = new String[] {"Bandarban", "Brahmanbaria", "Chandpur", "Chittagong", "Comilla", "Cox's Bazar",
            "Feni", "Khagrachhari", "Lakshmipur", "Noakhali", "Rangamati"};
    String[] dhakaDistricList = new String[] { "Dhaka", "Faridpur", "Gazipur", "Gopalganj", "Jamalpur", "Kishoregonj", "Madaripur",
            "Manikganj", "Munshiganj", "Mymensingh", "Narayanganj", "Narsingdi", "Netrakona", "Rajbari", "Shariatpur", "Sherpur", "Tangail"};
    String[] khulnaDistricList = new String[] {"Bagerhat", "Chuadanga", "Jessore", "Jhenaidah", "Khulna",
            "Kushtia", "Magura", "Meherpur", "Narail", "Satkhira"};
    String[] rajshahiDistricList = new String[] {"Bogra", "Joypurhat", "Naogaon", "Natore", "Chapai Nababganj", "Pabna", "Rajshahi", "Sirajganj"};
    String[] rangpurDistricList = new String[] {"Dinajpur", "Gaibandha", "Kurigram",
            "Lalmonirhat", "Nilphamari", "Zila", "Panchagarh", "Rangpur", "Thakurgaon"};
    String[] sylthetDistricList = new String[] {"Habiganj", "Maulvibazar", "Sunamganj", "Sylhet"};

    EditText editdoctorName, editdoctorAge, editorganizationName, editfirstPhoneNumber, editsecondPhoneNumber, editdoctorEmail, editdoctorOtherdegree, editAriaZipcode,editAriaZipcode1,editAriaZipcode2,editAriaZipcode3, editpatientPerDay, editvisitFees, editreVisitFees, editownerName, editownerPhoneNumber, editownerEmail, editdimension,
            editfirstAdd, editsecondAdd, editthirdAdd, editRemarks;
    TextView location,todayVisit,totalVisit,textId;
    AutoCompleteTextView autoDivision1, autoDistrict1, autoDivision2, autoDistrict2, autoDivision3, autoDistrict3;

    Spinner thanaSpinner1, thanaSpinner2, thanaSpinner3;

    String doctorDisclaimer = "", doctorName = "", doctorSex = "Male", doctorAge="", organizationName="", firstPhoneNumber="", secondPhoneNumber="", doctorEmail="", doctorOtherdegree = "", designation= "", category= "", privatePractice= "", tittle= "", fstariaZipcode= "",scndariaZipcode= "",thirdariaZipcode= "", patientPerDay="", visitFees="",
            reVisitFees="", pharmacyOwnerType = "", ownerName="", ownerPhoneNumber="", ownerEmail="", dimension="", fstchmberAdd= "", scndchmberAdd= "", thirdchmberAdd= "", remarks= "";

    String isFcps = "", isMbbs = "", isFrcs = "", isMd = "", isDiploma = "", isBcs = "";

    EditText edtfstStart, edtfstEnd, edtscndStart, edtscndEnd, edtthirdStart, edtthirdEnd;
    String fstchmberStarttime="", fstchmberEndtime="", scndchmberStarttime="", scndchmberEndtime="", thirdchmberStarttime="", thirdchmberEndtime="";
    String fstchmberOffday = "N/A", scndchmberOffday = "N/A", thirdchmberOffday = "N/A";
    String fstchmberType = "N/A", scndchmberType = "N/A", thirdchmberType = "N/A";
    CheckBox fcps, mbbs, frcs, md, diploma;

    RadioGroup chamberGroup, pharmacyOwnerGroup, bcsGroup, tradeGroup, commsGroup, brandingInternalGroup, brandingExternalGroup, fasciaGroup, signboardGroup;
    RadioButton rd;
    String isTradelicense= "", isCommsagreed= "", isbrandingInternal= "", isbrandingExternal= "", isfascia= "", isSignboard= "";

    TextView textPremise, textCard, textSignboard, textChamber;
    String user = "",id = "";
    String thanaName1 = "", thanaName2 = "", thanaName3;
    Map<String, String> thanaMap1 ;
    Map<String, String> thanaMap2 ;
    Map<String, String> thanaMap3 ;

    int flag = 0;  // if activity restarts it will go 1

// saving instances in case of activity restarts for camera intent
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        flag = flag + 1;
        outState.putInt("flag",flag);
        outState.putString("id",id);
        outState.putString("IsConsent",doctorDisclaimer);
        outState.putString("IsMBBS",isMbbs);
        outState.putString("IsFCPS",isFcps);
        outState.putString("IsFRCS",isFrcs);
        outState.putString("IsMD",isMd);
        outState.putString("IsDiploma",isDiploma);
        outState.putString("IsBCS",isBcs);
        outState.putString("Designation",designation);
        outState.putString("Category",category);
        outState.putString("IsPrivatePractice",privatePractice);
        outState.putString("Title",tittle);
        outState.putString("NumberOfChamber",NumberOfChamber);
        outState.putString("ThanaName1",thanaName1);
        outState.putString("ThanaName2",thanaName2);
        outState.putString("ThanaName3",thanaName3);
        outState.putString("ThanaCode1",thanaCode1);
        outState.putString("ThanaCode2",thanaCode2);
        outState.putString("ThanaCode3",thanaCode3);
        outState.putString("Cham01OffDay",fstchmberOffday);
        outState.putString("Cham01Type",fstchmberType);
        outState.putString("Cham02OffDay",scndchmberOffday);
        outState.putString("Cham02Type",scndchmberType);
        outState.putString("Cham03OffDay",thirdchmberOffday);
        outState.putString("Cham03Type",thirdchmberType);
        outState.putString("PharmacyOwnerType",pharmacyOwnerType);
        outState.putString("IsTradeLicense",isTradelicense);
        outState.putString("IsAgreed",isCommsagreed);
        outState.putString("IsBrandingExternal",isbrandingExternal);
        outState.putString("IsBrandingInternal",isbrandingInternal);
        outState.putString("IsShopFascia",isfascia);
        outState.putString("IsSignboard",isSignboard);
        outState.putString("PremisePhotoPath",premisePhotoPath);
        outState.putString("CardPhotoPath",cardPhotPath);
        outState.putString("SignboardPhotoPath",signboardPhotPath);
        outState.putString("ChamberPhotoPath",chamberPhotoPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_form);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.e("Permission","Storage permission already granted");
        }
        else {
            //Permission is not granted so you have to request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
            Log.e("Permission","Requesting for storage permission");
        }

        intent = new Intent(BROADCAST_ACTION);
        IDbundle = getIntent().getExtras();
        id = IDbundle.getString("id");
        textId = findViewById(R.id.id);
        todayVisit = findViewById(R.id.todayVisit);
        totalVisit = findViewById(R.id.totalVisit);

        // setting the default chamber number to 1
        rd = findViewById(R.id.ch1);
        rd.setChecked(true);


        textPremise = findViewById(R.id.textPremise);
        textCard = findViewById(R.id.textCard);
        textSignboard = findViewById(R.id.textSignboard);
        textChamber = findViewById(R.id.textChamber);


        autoDivision1 = findViewById(R.id.firstdivisionList);
        autoDistrict1 = findViewById(R.id.firstdistrictList);
        thanaSpinner1 = findViewById(R.id.firstThanaList);

        autoDivision2 = findViewById(R.id.seconddivisionList);
        autoDistrict2 = findViewById(R.id.seconddistrictList);
        thanaSpinner2 = findViewById(R.id.secondThanaList);

        autoDivision3 = findViewById(R.id.thirddivisionList);
        autoDistrict3 = findViewById(R.id.thirddistrictList);
        thanaSpinner3 = findViewById(R.id.thirdThanaList);


// if activity restarts then restore those instances
        try{
            if(s != null)
            {
                flag = s.getInt("flag");
                id = s.getString("id");
                doctorDisclaimer = s.getString("IsConsent");
                isMbbs = s.getString("IsMBBS");
                isFcps = s.getString("IsFCPS");
                isFrcs = s.getString("IsFRCS");
                isMd = s.getString("IsMD");
                isDiploma = s.getString("IsDiploma");
                isBcs = s.getString("IsBCS");
                designation = s.getString("Designation");
                category = s.getString("Category");
                privatePractice = s.getString("IsPrivatePractice");
                tittle = s.getString("Title");
                NumberOfChamber = s.getString("NumberOfChamber");
                if(NumberOfChamber.equals("2"))
                {
                    rd = findViewById(R.id.ch2);
                    rd.setChecked(true);
                }
                else if(NumberOfChamber.equals("3"))
                {
                    rd = findViewById(R.id.ch3);
                    rd.setChecked(true);
                }
                thanaCode1 = s.getString("ThanaCode1");
                thanaName1 = s.getString("ThanaName1");
                if(!thanaCode1.equals("") & !thanaName1.equals(""))
                {
                    List<String> lst = new ArrayList<String>();
                    lst.add(thanaName1);
                    showThanaList(lst,1);
                }
                thanaCode2 = s.getString("ThanaCode2");
                thanaName2 = s.getString("ThanaName2");
                if(!thanaCode2.equals("") & !thanaName2.equals(""))
                {
                    List<String> lst = new ArrayList<String>();
                    lst.add(thanaName2);
                    showThanaList(lst,2);
                }
                thanaCode3 = s.getString("ThanaCode3");
                thanaName3 = s.getString("ThanaName3");
                if(!thanaCode3.equals("") & !thanaName3.equals(""))
                {
                    List<String> lst = new ArrayList<String>();
                    lst.add(thanaName3);
                    showThanaList(lst,3);
                }
                fstchmberOffday = s.getString("Cham01OffDay");
                fstchmberType = s.getString("Cham01Type");
                scndchmberOffday = s.getString("Cham02OffDay");
                scndchmberType = s.getString("Cham02Type");
                thirdchmberOffday = s.getString("Cham03OffDay");
                thirdchmberType = s.getString("Cham03Type");
                pharmacyOwnerType = s.getString("PharmacyOwnerType");
                isTradelicense = s.getString("IsTradeLicense");
                isCommsagreed = s.getString("IsAgreed");
                isbrandingExternal = s.getString("IsBrandingExternal");
                isbrandingInternal = s.getString("IsBrandingInternal");
                isfascia = s.getString("IsFascia");
                isSignboard = s.getString("IsSignboard");
                premisePhotoPath = s.getString("PremisePhotoPath");
                cardPhotPath = s.getString("CardPhotoPath");
                signboardPhotPath = s.getString("SignboradPhotoPath");
                chamberPhotoPath = s.getString("ChamberPhotoPath");
                if(!premisePhotoPath.equals(""))
                {
                    textPremise.setBackgroundResource(R.drawable.ok_button_square);
                    temp = textPremise.getText().toString() + "Ok";
                    textPremise.setText(temp);
                }
                if(!cardPhotPath.equals(""))
                {
                    textCard.setBackgroundResource(R.drawable.ok_button_square);
                    temp = textCard.getText().toString() + "Ok";
                    textCard.setText(temp);
                }
                if(!signboardPhotPath.equals(""))
                {
                    textSignboard.setBackgroundResource(R.drawable.ok_button_square);
                    temp = textSignboard.getText().toString() + "Ok";
                    textSignboard.setText(temp);
                }
                if(!chamberPhotoPath.equals(""))
                {
                    textChamber.setBackgroundResource(R.drawable.ok_button_square);
                    temp = textChamber.getText().toString() + "Ok";
                    textChamber.setText(temp);
                }
                try {
                    Process process = Runtime.getRuntime().exec("logcat -d");
                    process = Runtime.getRuntime().exec( "logcat -f " + "/storage/emulated/0/"+"Logging.txt");
                }catch(Exception ee)
                {
                    ee.printStackTrace();
                }
            }

        }catch (Exception e)
        {
            try {
                Process process = Runtime.getRuntime().exec("logcat -d");
                process = Runtime.getRuntime().exec( "logcat -f " + "/storage/emulated/0/MaricoLog"+"LogCat.txt");
            }catch(Exception ee)
            {
                ee.printStackTrace();
            }
        }


        textId.setText(id);

        new getTargetAchievement().execute();

        btnDisclaimer = findViewById(R.id.disclaimer);

        photoName1 = "premise_" + LoginActivity.userid + "_"+CustomUtility.getTimeStamp("yyyyMMddhhmmss") + ".jpg";
        photoName2 = LoginActivity.userid + "_doctorcard"+CustomUtility.getTimeStamp("yyyyMMddhhmmss") + ".jpg";
        photoName3 = LoginActivity.userid + "_signboard"+CustomUtility.getTimeStamp("yyyyMMddhhmmss") + ".jpg";
        photoName4 = LoginActivity.userid + "_chamber"+CustomUtility.getTimeStamp("yyyyMMddhhmmss") + ".jpg";

        //auto views;



        //all checkbox
        fcps = findViewById(R.id.chkfcps);
        frcs = findViewById(R.id.chkfrcs);
        mbbs = findViewById(R.id.chkmbbs);
        md = findViewById(R.id.chkmd);
        diploma = findViewById(R.id.chkdiploma);

        //all radio group
        bcsGroup = findViewById(R.id.bcsGroup);
        chamberGroup = findViewById(R.id.chamberNo);
        pharmacyOwnerGroup = findViewById(R.id.sowner);
        tradeGroup = findViewById(R.id.tradelicense);
        commsGroup = findViewById(R.id.commagreed);
        brandingInternalGroup = findViewById(R.id.brandingInternal);
        brandingExternalGroup = findViewById(R.id.brandingExternal);
        fasciaGroup = findViewById(R.id.shopfascia);
        signboardGroup = findViewById(R.id.dsignboard);


        //all the spinner lists
        final Spinner sdropdown = findViewById(R.id.sdropdown);
        final Spinner designationDropdown = findViewById(R.id.designationDropdown);
        final Spinner categoryDropdown = findViewById(R.id.categorydropdown);
        final Spinner privatepracticeDropdown = findViewById(R.id.privatepracticeDropdown);
        final Spinner tittleDropdown = findViewById(R.id.tittleDropdown);
        final Spinner fchamberTypesdropdown = findViewById(R.id.firstchmbertype);
        final Spinner fchamberOffdaydropdown = findViewById(R.id.firstoffday);
        final Spinner schamberTypesdropdown = findViewById(R.id.secondchmbertype);
        final Spinner schamberOffdaydropdown = findViewById(R.id.secondoffday);
        final Spinner tchamberTypesdropdown = findViewById(R.id.thirdchmbertype);
        final Spinner tchamberOffdaydropdown = findViewById(R.id.thirdoffday);

        //chambers layout
        final ConstraintLayout firstChamber = findViewById(R.id.firstChamber);
        final ConstraintLayout secondChamber = findViewById(R.id.secondChamber);
        final ConstraintLayout thirdChamber = findViewById(R.id.thirdChamber);

        //all edit text feilds
        editdoctorName = findViewById(R.id.doctorName);
        editdoctorAge = findViewById(R.id.doctorAge);
        editorganizationName = findViewById(R.id.owf);
        editfirstPhoneNumber = findViewById(R.id.fstphn);
        editsecondPhoneNumber = findViewById(R.id.scndphn);
        editdoctorEmail = findViewById(R.id.email);
        editdoctorOtherdegree = findViewById(R.id.otherDegree);
        editpatientPerDay = findViewById(R.id.ppd);
        editAriaZipcode1 = findViewById(R.id.fstzipcode);
        editAriaZipcode2 = findViewById(R.id.scndzipcode);
        editAriaZipcode3 = findViewById(R.id.thirdzipcode);

        editvisitFees = findViewById(R.id.fees);
        editreVisitFees = findViewById(R.id.refees);
        editfirstAdd = findViewById(R.id.firstadd);
        editsecondAdd = findViewById(R.id.secondadd);
        editthirdAdd = findViewById(R.id.thirdadd);
        editownerName = findViewById(R.id.ownerName);
        editownerPhoneNumber = findViewById(R.id.ownerphn);
        editownerEmail = findViewById(R.id.owneremail);
        editdimension = findViewById(R.id.dimension);
        edtfstStart = findViewById(R.id.firstchamerStarttime);
        edtfstEnd = findViewById(R.id.firstchamerEndtime);
        edtscndStart = findViewById(R.id.secondchamerStarttime);
        edtscndEnd = findViewById(R.id.secondchamerEndtime);
        edtthirdStart = findViewById(R.id.thirdchamerStarttime);
        edtthirdEnd = findViewById(R.id.thirdchamerEndtime);
        editRemarks = findViewById(R.id.remarks);

        premisePhoto = findViewById(R.id.selfieWithPremise);
        cardPhoto = findViewById(R.id.picDoctorsCard);
        signboardPhoto = findViewById(R.id.picSignboard);
        chamberPhoto = findViewById(R.id.picChamber);



        location = findViewById(R.id.gps);

        Button submit = findViewById(R.id.submitbutton);

        //Bundle extras = getIntent().getExtras();
        //user = extras.getString("id");

        //all spinner adapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sDropList);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, designationList);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, chamberTypesList);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, offdayList);
        ArrayAdapter<String> adapter11 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, categoryList);
        ArrayAdapter<String> adapter10 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, yesnoDropList);
        ArrayAdapter<String> adapter12 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, tittleList);

        //spinner dropdown
        sdropdown.setAdapter(adapter1);
        designationDropdown.setAdapter(adapter2);
        categoryDropdown.setAdapter(adapter11);
        privatepracticeDropdown.setAdapter(adapter10);
        tittleDropdown.setAdapter(adapter12);
        fchamberTypesdropdown.setAdapter(adapter4);
        fchamberOffdaydropdown.setAdapter(adapter5);
        schamberTypesdropdown.setAdapter(adapter4);
        schamberOffdaydropdown.setAdapter(adapter5);
        tchamberTypesdropdown.setAdapter(adapter4);
        tchamberOffdaydropdown.setAdapter(adapter5);

        GPS_Start();


        btnDisclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(form.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Consent")
                        .setMessage("All the data that is being collected for this process will be shared with Marico on a real time basis.\n" +
                                "Upon completion of project, Marico shall audit the authenticity of the data by any means considered appropriate by Marico and only upon ensuring that the data shared are correct and authentic, Interspeed will be entitled to receive the payment.\n" +
                                "In addition to real time sharing of data, all the data collected during the process will be shared with Marico in a consolidated form once the survey is complete.\n" +
                                "Marico Bangladesh Limited shall have right over this data during and upon completion of the project and this data cannot be shared with any other party by Interspeed.\n" +
                                "Marico holds the right to use the information from the data collected for any future project it deems relevant.\"")
                        .setPositiveButton("Agree", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doctorDisclaimer = "Agree";
                            }

                        })
                        .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doctorDisclaimer = "Disagree";
                            }
                        })
                        .show();
            }
        });



        sdropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doctorSex = sdropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        designationDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                designation = designationDropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        categoryDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = categoryDropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        privatepracticeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                privatePractice = privatepracticeDropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tittleDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tittle = tittleDropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mbbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mbbs.isChecked())
                    isMbbs = "Yes";
                else
                    isMbbs = "No";
            }
        });
        fcps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fcps.isChecked())
                    isFcps = "Yes";
                else
                    isFcps = "No";
            }
        });
        frcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frcs.isChecked())
                    isFrcs = "Yes";
                else
                    isFrcs = "No";
            }
        });
        md.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(md.isChecked())
                    isMd = "Yes";
                else
                    isMd = "No";
            }
        });
        diploma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(diploma.isChecked())
                    isDiploma = "Yes";
                else
                    isDiploma = "No";
            }
        });

        bcsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.bcsyes:
                        isBcs = "Yes";
                        break;
                    case R.id.bcsno:
                        isBcs = "No";
                        break;
                }
            }
        });




        chamberGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.ch1:
                        firstChamber.setVisibility(View.VISIBLE);
                        secondChamber.setVisibility(View.GONE);
                        thirdChamber.setVisibility(View.GONE);
                        NumberOfChamber = "1";
                        break;
                    case R.id.ch2:
                        firstChamber.setVisibility(View.VISIBLE);
                        secondChamber.setVisibility(View.VISIBLE);
                        thirdChamber.setVisibility(View.GONE);
                        NumberOfChamber = "2";
                        break;
                    case R.id.ch3:
                        firstChamber.setVisibility(View.VISIBLE);
                        secondChamber.setVisibility(View.VISIBLE);
                        thirdChamber.setVisibility(View.VISIBLE);
                        NumberOfChamber = "3";
                        break;
                }
            }
        });

        fchamberTypesdropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fstchmberType = fchamberTypesdropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        schamberTypesdropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scndchmberType = schamberTypesdropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tchamberTypesdropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                thirdchmberType = tchamberTypesdropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fchamberOffdaydropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fstchmberOffday = fchamberOffdaydropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        schamberOffdaydropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scndchmberOffday = schamberOffdaydropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tchamberOffdaydropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                thirdchmberOffday = tchamberOffdaydropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter7 = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, divisionList);
        autoDivision1.setThreshold(1); //will start working from first character
        autoDivision1.setAdapter(adapter7);

       autoDivision1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               division = autoDivision1.getText().toString();
               if(division.equals("Barisal"))
               {
                   divisionStartIndex = 0; //thana list starts
                   divisionEndIndex = 41; //thana list ends
                   setDistrictList(barisalDistricList, autoDistrict1);
                   //Log.e("division",division + thanaList1.length);
               }
               else if (division.equals("Chittagong"))
               {
                   divisionStartIndex = 42;
                   divisionEndIndex = 152;
                   setDistrictList(chittagongDistricList, autoDistrict1);
               }
               else if (division.equals("Dhaka"))
               {
                   divisionStartIndex = 153;
                   divisionEndIndex = 321;
                   setDistrictList(dhakaDistricList, autoDistrict1);
               }
               else if (division.equals("Khulna"))
               {
                   divisionStartIndex = 322;
                   divisionEndIndex = 387;
                   setDistrictList(khulnaDistricList, autoDistrict1);
               }
               else if (division.equals("Rajshahi"))
               {
                   divisionStartIndex = 388;
                   divisionEndIndex = 457;
                   setDistrictList(rajshahiDistricList, autoDistrict1);
               }
               else if (division.equals("Rangpur"))
               {
                   divisionStartIndex = 458;
                   divisionEndIndex = 515;
                   setDistrictList(rangpurDistricList, autoDistrict1);
               }
               else if (division.equals("Sylhet"))
               {
                   divisionStartIndex = 516;
                   divisionEndIndex = 553;
                   setDistrictList(sylthetDistricList, autoDistrict1);
               }
           }
       });


        autoDistrict1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dis = autoDistrict1.getText().toString();
                Log.e("district",dis);
                progressDialog = new ProgressDialog(form.this);
                progressDialog.setTitle("Please wait...");
                progressDialog.setMessage("");
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();
                flag = 0;
                getThanaList(dis,1);
            }
        });

        thanaSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(flag == 0)
                {
                    thanaName1 = thanaSpinner1.getSelectedItem().toString();
                    thanaCode1 = thanaMap1.get(thanaName1);
                    Log.e("Position", thanaCode1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        autoDivision2.setThreshold(1); //will start working from first character
        autoDivision2.setAdapter(adapter7);

        autoDivision2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                division = autoDivision2.getText().toString();
                if(division.equals("Barisal"))
                {
                    divisionStartIndex = 0; //thana list starts
                    divisionEndIndex = 41; //thana list ends
                    setDistrictList(barisalDistricList, autoDistrict2);
                    //Log.e("division",division + thanaList1.length);
                }
                else if (division.equals("Chittagong"))
                {
                    divisionStartIndex = 42;
                    divisionEndIndex = 152;
                    setDistrictList(chittagongDistricList, autoDistrict2);
                }
                else if (division.equals("Dhaka"))
                {
                    divisionStartIndex = 153;
                    divisionEndIndex = 321;
                    setDistrictList(dhakaDistricList, autoDistrict2);
                }
                else if (division.equals("Khulna"))
                {
                    divisionStartIndex = 322;
                    divisionEndIndex = 387;
                    setDistrictList(khulnaDistricList, autoDistrict2);
                }
                else if (division.equals("Rajshahi"))
                {
                    divisionStartIndex = 388;
                    divisionEndIndex = 457;
                    setDistrictList(rajshahiDistricList, autoDistrict2);
                }
                else if (division.equals("Rangpur"))
                {
                    divisionStartIndex = 458;
                    divisionEndIndex = 515;
                    setDistrictList(rangpurDistricList, autoDistrict2);
                }
                else if (division.equals("Sylhet"))
                {
                    divisionStartIndex = 516;
                    divisionEndIndex = 553;
                    setDistrictList(sylthetDistricList, autoDistrict2);
                }
            }
        });


        autoDistrict2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dis = autoDistrict2.getText().toString();
                Log.e("district",dis);
                progressDialog = new ProgressDialog(form.this);
                progressDialog.setTitle("Please wait...");
                progressDialog.setMessage("");
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();
                flag = 0;
                getThanaList(dis,2);
            }
        });

        thanaSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(flag == 0)
                {
                    thanaName2 = thanaSpinner2.getSelectedItem().toString();
                    thanaCode2 = thanaMap2.get(thanaName2);
                    Log.e("Position", thanaCode2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        autoDivision3.setThreshold(1); //will start working from first character
        autoDivision3.setAdapter(adapter7);

        autoDivision3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                division = autoDivision3.getText().toString();
                if(division.equals("Barisal"))
                {
                    divisionStartIndex = 0; //thana list starts
                    divisionEndIndex = 41; //thana list ends
                    setDistrictList(barisalDistricList, autoDistrict3);
                    //Log.e("division",division + thanaList1.length);
                }
                else if (division.equals("Chittagong"))
                {
                    divisionStartIndex = 42;
                    divisionEndIndex = 152;
                    setDistrictList(chittagongDistricList, autoDistrict3);
                }
                else if (division.equals("Dhaka"))
                {
                    divisionStartIndex = 153;
                    divisionEndIndex = 321;
                    setDistrictList(dhakaDistricList, autoDistrict3);
                }
                else if (division.equals("Khulna"))
                {
                    divisionStartIndex = 322;
                    divisionEndIndex = 387;
                    setDistrictList(khulnaDistricList, autoDistrict3);
                }
                else if (division.equals("Rajshahi"))
                {
                    divisionStartIndex = 388;
                    divisionEndIndex = 457;
                    setDistrictList(rajshahiDistricList, autoDistrict3);
                }
                else if (division.equals("Rangpur"))
                {
                    divisionStartIndex = 458;
                    divisionEndIndex = 515;
                    setDistrictList(rangpurDistricList, autoDistrict3);
                }
                else if (division.equals("Sylhet"))
                {
                    divisionStartIndex = 516;
                    divisionEndIndex = 553;
                    setDistrictList(sylthetDistricList, autoDistrict3);
                }
            }
        });


        autoDistrict3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dis = autoDistrict3.getText().toString();
                Log.e("district",dis);
                progressDialog = new ProgressDialog(form.this);
                progressDialog.setTitle("Please wait...");
                progressDialog.setMessage("");
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();
                flag = 0;
                getThanaList(dis,3);
            }
        });

        thanaSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(flag == 0)
                {
                    thanaName3 = thanaSpinner3.getSelectedItem().toString();
                    thanaCode3 = thanaMap3.get(thanaName3);
                    Log.e("Position", thanaCode3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pharmacyOwnerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.sownerYes:
                        editownerName.setText(editdoctorName.getText().toString());
                        editownerEmail.setText(editdoctorEmail.getText().toString());
                        editownerPhoneNumber.setText(editfirstPhoneNumber.getText());
                        pharmacyOwnerType = "Same";
                        break;
                    case R.id.sownerNo:
                        editownerName.setText("");
                        editownerEmail.setText("");
                        editownerPhoneNumber.setText("");
                        pharmacyOwnerType = "Other";
                        break;
                }
            }
        });


        tradeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tradeYes:
                        isTradelicense = "Yes";
                        break;
                    case R.id.tradeNo:
                        isTradelicense = "No";
                        break;
                }
            }
        });

        commsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.commsYes:
                        isCommsagreed = "Yes";
                        break;
                    case R.id.commsNo:
                        isCommsagreed = "No";
                        break;
                }
            }
        });

        brandingInternalGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.brandingInternalYes:
                        isbrandingInternal = "Yes";
                        break;
                    case R.id.brandingInternalNo:
                        isbrandingInternal = "No";
                        break;
                }
            }
        });

        brandingExternalGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.brandingExternalYes:
                        isbrandingExternal = "Yes";
                        break;
                    case R.id.brandingExternalNo:
                        isbrandingExternal = "No";
                        break;
                }
            }
        });

        fasciaGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.shopfasciaYes:
                        isfascia = "Yes";
                        break;
                    case R.id.shopfasciaNo:
                        isfascia = "No";
                        break;
                }
            }
        });

        signboardGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.dsignboardYes:
                        isSignboard = "Yes";
                        break;
                    case R.id.dsignboardNo:
                        isSignboard = "No";
                        break;
                }
            }
        });

        premisePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(1);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        CustomUtility.showAlert(form.this, ex.getMessage(), "Creating Image");
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI1 = FileProvider.getUriForFile(getApplicationContext(),
                                "com.example.maricosurvey.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI1);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        if(premise == 0)
                        {
                            textPremise.setBackgroundResource(R.drawable.ok_button_square);
                            temp = textPremise.getText().toString() + " Ok";
                            textPremise.setText(temp);
                            premise++;
                        }

                    }
                }
            }
        });

        cardPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(2);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        CustomUtility.showAlert(form.this, ex.getMessage(), "Creating Image");
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI2 = FileProvider.getUriForFile(getApplicationContext(),
                                "com.example.maricosurvey.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI2);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        if(card==0) // checking it will not add "ok" for other time
                        {
                            textCard.setBackgroundResource(R.drawable.ok_button_square);
                            temp = textCard.getText().toString() + " Ok";
                            textCard.setText(temp);
                            card++;
                        }

                    }
                }
            }
        });

        signboardPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(3);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        CustomUtility.showAlert(form.this, ex.getMessage(), "Creating Image");
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI3 = FileProvider.getUriForFile(getApplicationContext(),
                                "com.example.maricosurvey.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI3);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        if(sign==0)
                        {
                            textSignboard.setBackgroundResource(R.drawable.ok_button_square);
                            temp = textSignboard.getText().toString() + " Ok";
                            textSignboard.setText(temp);
                            sign++;
                        }

                    }
                }
            }
        });

        chamberPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(4);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        CustomUtility.showAlert(form.this, ex.getMessage(), "Creating Image");
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI4 = FileProvider.getUriForFile(getApplicationContext(),
                                "com.example.maricosurvey.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI4);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        if(chamber==0)
                        {
                            textChamber.setBackgroundResource(R.drawable.ok_button_square);
                            temp = textChamber.getText().toString() + " Ok";
                            textChamber.setText(temp);
                        }
                    }
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorName = editdoctorName.getText().toString();
                Log.e("designation",designation);
                doctorAge = editdoctorAge.getText().toString();
                organizationName = editorganizationName.getText().toString();
                firstPhoneNumber = editfirstPhoneNumber.getText().toString();
                secondPhoneNumber = editsecondPhoneNumber.getText().toString();
                doctorEmail = editdoctorEmail.getText().toString();
                doctorOtherdegree = editdoctorOtherdegree.getText().toString();
                fstariaZipcode = editAriaZipcode1.getText().toString();
                scndariaZipcode = editAriaZipcode2.getText().toString();
                thirdariaZipcode = editAriaZipcode3.getText().toString();
                patientPerDay = editpatientPerDay.getText().toString();
                visitFees = editvisitFees.getText().toString();
                reVisitFees = editreVisitFees.getText().toString();
                fstchmberAdd = editfirstAdd.getText().toString();
                scndchmberAdd = editsecondAdd.getText().toString();
                thirdchmberAdd = editthirdAdd.getText().toString();
                fstchmberStarttime = edtfstStart.getText().toString();
                fstchmberEndtime = edtfstEnd.getText().toString();
                scndchmberStarttime = edtscndStart.getText().toString();
                scndchmberEndtime = edtscndEnd.getText().toString();
                thirdchmberStarttime = edtthirdStart.getText().toString();
                thirdchmberEndtime = edtthirdEnd.getText().toString();
                ownerName = editownerName.getText().toString();
                ownerEmail = editownerEmail.getText().toString();
                ownerPhoneNumber = editownerPhoneNumber.getText().toString();
                dimension = editdimension.getText().toString();
                remarks = editRemarks.getText().toString();
                Log.e("remarks",remarks);
                Log.e("chamber no",NumberOfChamber);
                Integer flag = checkAllfields();
                firstPhoneNumber = "+88" + firstPhoneNumber;
                Log.e("first phone", firstPhoneNumber);
                if(secondPhoneNumber.length() != 0 & ownerPhoneNumber.length() == 11) secondPhoneNumber = "+88" + secondPhoneNumber;
                if(ownerPhoneNumber.length() != 0 & ownerPhoneNumber.length() == 11) ownerPhoneNumber = "+88" + ownerPhoneNumber;
                if(flag == 0 ) {
                    if (ContextCompat.checkSelfPermission(form.this, Manifest.permission.INTERNET)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Permission is not granted
                        Log.e("DXXXXXXXXXX", "Not Granted");
                        CustomUtility.showAlert(form.this, "Permission not granted", "Permission");
                    } else {
                        new AlertDialog.Builder(form.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Are You Sure?")
                                .setMessage("Are you sure you want to submit?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog = new ProgressDialog(form.this);
                                        progressDialog.setTitle("Please wait...");
                                        progressDialog.setMessage("Uploading");
                                        progressDialog.setIndeterminate(true);
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();
                                        upload();
                                    }

                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                }
            }
        });





    }

    private void upload() {
        Uri uri1 = Uri.fromFile(new File(premisePhotoPath));
        Uri uri2 = Uri.fromFile(new File(cardPhotPath));
        Uri uri3 = Uri.fromFile(new File(signboardPhotPath));
        Uri uri4 = null;
        if(!chamberPhotoPath.equals(""))
            uri4 = Uri.fromFile(new File(chamberPhotoPath));
        try {
            //bitmap1 = CustomUtility.scaleFile(form.this, uri1);
            bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri1);
            bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri2);
            bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri3);
            if(uri4 != null)
            bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri4);
        } catch (IOException e) {
            progressDialog.dismiss();
            String err = e.getMessage() + " May be storage full please uninstall then install the app again";
            CustomUtility.showAlert(form.this, e.getMessage(), "Problem Creating Bitmap at Submit");
            return;
        }
        imageString1 = CustomUtility.imageToString(bitmap1);
        imageString2 = CustomUtility.imageToString(bitmap2);
        imageString3 = CustomUtility.imageToString(bitmap3);
        if(uri4 != null)
        imageString4 = CustomUtility.imageToString(bitmap4);
        String upLoadServerUri = "https://atmdbd.com/api/doctor/insert_doctor.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upLoadServerUri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("response",response);
                        try {
                            jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(code.equals("true"))
                            {
                                code = "Uploading done";
                                new AlertDialog.Builder(form.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Your upload is done")
                                        .setMessage("Refreshing this form")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                                startActivity(getIntent());
                                            }

                                        })
                                        //.setNegativeButton("No", null)
                                        .show();
                            }
                            else
                            {
                                code = "Uploading fails";
                                CustomUtility.showAlert(form.this,message,code);
                            }


                        } catch (JSONException e) {
                            CustomUtility.showAlert(form.this, e.getMessage(), "Getting Response");
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("response","onerrorResponse");
                CustomUtility.showAlert(form.this, "Network slow, try again", "Getting Error Response");
                NetworkResponse response = error.networkResponse;
                String errorMsg = "";
                Log.e("response", String.valueOf(response));
                if(response != null && response.data != null){
                    String errorString = new String(response.data);
                    Log.i("log error", errorString);
                    CustomUtility.showAlert(form.this, errorString + " try again", "Getting Error Response");
                }
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Title", tittle);
                params.put("IsConsent", doctorDisclaimer);
                params.put("DoctorName", doctorName);
                params.put("Age", doctorAge);
                params.put("Sex", doctorSex);
                params.put("IsMBBS", isMbbs);
                params.put("IsFCPS", isFcps);
                params.put("IsFRCS", isFrcs);
                params.put("IsMD", isMd);
                params.put("IsDiploma", isDiploma);
                params.put("IsBCS", isBcs);
                params.put("OtherDegree", doctorOtherdegree);
                params.put("Category", category);
                params.put("Designation", designation);
                params.put("IsPrivatePractice", privatePractice);
                params.put("AriaZipCode", fstariaZipcode);
                params.put("Organization", organizationName);
                params.put("DoctorPhone01", firstPhoneNumber);
                params.put("DoctorPhone02", secondPhoneNumber);
                params.put("DoctorEmail", doctorEmail);
                params.put("PatientPerDay", patientPerDay);
                params.put("DoctorFees", visitFees);
                params.put("RevisitFees", reVisitFees);
                params.put("PharmacyOwnerType", pharmacyOwnerType);
                params.put("NumberOfChamber",NumberOfChamber);
                params.put("Cham01Address", fstchmberAdd);
                params.put("Cham01Name",organizationName);
                params.put("Cham01ZipCode",fstariaZipcode);
                params.put("Cham01ThanaId", thanaCode1);
                params.put("Cham01TimeStart", fstchmberStarttime);
                params.put("Cham01TimeEnd", fstchmberEndtime);
                params.put("Cham01OffDay", fstchmberOffday);
                params.put("Cham01PremiseType", fstchmberType);
                params.put("Cham01PharmacyOwnerName", ownerName);
                params.put("Cham01PharmacyOwnerPhone01", ownerPhoneNumber);
                params.put("Cham01PharmacyOwnerMail", ownerEmail);
                params.put("Cham01HasTradeLicence", isTradelicense);
                params.put("Cham01ChamberDimension", dimension);
                params.put("Cham01HasBrandingOpportunityInternal", isbrandingInternal);
                params.put("Cham01HasBrandingOpportunityExternal", isbrandingExternal);
                params.put("Cham01HasShopFascia", isfascia);
                params.put("Cham01HasDoctorSignboard", isSignboard);
                params.put("Cham02Address", scndchmberAdd);
                params.put("Cham02ThanaId", thanaCode2);
                params.put("Cham02ZipCode",scndariaZipcode);
                params.put("Cham02TimeStart", scndchmberStarttime);
                params.put("Cham02TimeEnd", scndchmberEndtime);
                params.put("Cham02OffDay", scndchmberOffday);
                params.put("Cham02PremiseType", scndchmberType);
                params.put("Cham03Address", thirdchmberAdd);
                params.put("Cham03ThanaId", thanaCode3);
                params.put("Cham03ZipCode",thirdariaZipcode);
                params.put("Cham03TimeStart", thirdchmberStarttime);
                params.put("Cham03TimeEnd", thirdchmberEndtime);
                params.put("Cham03OffDay", thirdchmberOffday);
                params.put("Cham03PremiseType", thirdchmberType);
                params.put("IsAgreedToCommunication", isCommsagreed);
                params.put("LatValue", presentLat);
                params.put("LonValue", presentLon);
                params.put("GeoAccuracy", presentAcc);
                params.put("PictureSelfieData", imageString1);
                params.put("PictureDoctorCardData", imageString2);
                params.put("PicturePremiseFrontData", imageString3);
                params.put("PictureChamberData", imageString4);
                params.put("Remark", remarks);
                params.put("CreatedBy",id);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(form.this).addToRequestQue(stringRequest);
    }

    public void getThanaList(final String disName, final Integer chamberId)
    {
        String upLoadServerUri="https://atmdbd.com/api/CommThana/get_thana_by_district_name.php";
        //String upLoadServerUri="https://deenal.com/app/CommThana/get_thana_by_district_name.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, upLoadServerUri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            Log.e("Server Response",response);
                            JSONObject j;
                            JSONObject jo = new JSONObject(response);
                            String code = jo.getString("success");
                            String message = jo.getString("message");

                            if(code.equals("true"))
                            {
                                JSONArray ja = jo.getJSONArray("resultThanaList");
                                //Log.e("ThanaList", String.valueOf(ja));
                                List<String> thList = new ArrayList<String>();
                                if(chamberId == 1)
                                {
                                    thanaMap1 = new HashMap<String, String>();
                                    for(int i = 0; i<ja.length(); i++)
                                    {
                                        j = ja.getJSONObject(i);
                                        //Log.e("thana", String.valueOf(j));
                                        thanaMap1.put(j.getString("ThanaName"),j.getString("ThanaId"));
                                        thList.add(j.getString("ThanaName"));
                                    }
                                }
                                else if(chamberId == 2)
                                {
                                    thanaMap2 = new HashMap<String, String>();
                                    for(int i = 0; i<ja.length(); i++)
                                    {
                                        j = ja.getJSONObject(i);
                                        //Log.e("thana", String.valueOf(j));
                                        thanaMap2.put(j.getString("ThanaName"),j.getString("ThanaId"));
                                        thList.add(j.getString("ThanaName"));
                                    }
                                }
                                else if(chamberId == 3)
                                {
                                    thanaMap3 = new HashMap<String, String>();
                                    for(int i = 0; i<ja.length(); i++)
                                    {
                                        j = ja.getJSONObject(i);
                                        //Log.e("thana", String.valueOf(j));
                                        thanaMap3.put(j.getString("ThanaName"),j.getString("ThanaId"));
                                        thList.add(j.getString("ThanaName"));
                                    }
                                }

                                //Log.e("lst", String.valueOf(thList));
                                showThanaList(thList, chamberId);

                            }else {
                                CustomUtility.showAlert(form.this,"No thana list found by district id",message);
                            }

                        } catch (JSONException e) {
                            CustomUtility.showAlert(form.this,e.getMessage(),"Getting Response");
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    progressDialog.dismiss();
                    Log.e("error response",error.toString());
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    JSONArray errors = data.getJSONArray("server_response");
                    JSONObject jsonMessage = errors.getJSONObject(0);
                    String code = jsonMessage.getString("code");
                    String message = jsonMessage.getString("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    CustomUtility.showAlert(form.this,message,"Get Thana List Failed");
                } catch (JSONException e) {
                    CustomUtility.showAlert(form.this, e.getMessage(), "Exception e");

                } catch (UnsupportedEncodingException errorr) {
                    CustomUtility.showAlert(form.this, errorr.getMessage(), "Exception error");
                }


            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("DistrictName",disName);
                return params;
            }
        };

        MySingleton.getInstance(form.this).addToRequestQue(stringRequest);
    }

    public void showThanaList(List<String> lst, Integer chId)
    {
        ArrayAdapter<String> adapter20 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, lst);
        if(chId == 1)
        {
            thanaSpinner1.setAdapter(adapter20);
        }
        else if(chId == 2)
        {
            thanaSpinner2.setAdapter(adapter20);
        }
        else if(chId == 3)
        {
            thanaSpinner3.setAdapter(adapter20);
        }

    }

    public Integer checkAllfields()
    {
        //Log.e("name",doctorName);
        if(doctorDisclaimer.equals(""))
        {
            CustomUtility.showAlert(this,"Doctor must see the consent","Fill the feilds");
            return 1;
        }
        else if (doctorName.equals(""))
        {
            CustomUtility.showAlert(this, "Please fill doctor's name","Fill the feilds");
            return 1;
        }
        else if(doctorAge.equals(""))
        {
            CustomUtility.showAlert(this, "Please fill doctor's age","Fill the feilds");
            return 1;
        }
        else if(doctorSex.equals(""))
        {
            CustomUtility.showAlert(this, "Please select doctor's sex","Select the feilds");
            return 1;
        }
        else if((isMbbs.equals("") & isFcps.equals("") & isMd.equals("") & isFrcs.equals("") & isDiploma.equals("") & doctorOtherdegree.equals("")))
        {
            CustomUtility.showAlert(this, "Please select doctor's degree","Fill the feilds");
            return 1;
        }else if(isBcs.equals(""))
        {
            CustomUtility.showAlert(this, "Please fill doctor has done bcs or not","Fill the feilds");
            return 1;
        }
        else if(designation.equals("N/A"))
        {
            CustomUtility.showAlert(this, "Please fill doctor's designation","Fill the feilds");
            return 1;
        }
        else if(category.equals("N/A"))
        {
            CustomUtility.showAlert(this, "Please fill doctor's category","Fill the feilds");
            return 1;
        }
        else if(privatePractice.equals("N/A"))
        {
            CustomUtility.showAlert(this, "Please select doctor doing private practice or not","Fill the feilds");
            return 1;
        }
        else if(organizationName.equals(""))
        {
            CustomUtility.showAlert(this, "Please fill doctor's organization name","Fill the feilds");
            return 1;
        }
        else if(firstPhoneNumber.length() != 11)
        {
            CustomUtility.showAlert(this, "Please fill doctor's 1st phone number correctly","Fill the feilds");
            return 1;
        }
        else if(secondPhoneNumber.length() != 0 & secondPhoneNumber.length() != 11)
        {
            CustomUtility.showAlert(this, "Please fill doctor's 2nd phone number correctly","Fill the feilds");
            return 1;
        }
        else if(patientPerDay.equals(""))
        {
            CustomUtility.showAlert(this, "Please fill doctor's patient/day","Fill the feilds");
            return 1;
        }
        else if(visitFees.equals(""))
        {
            CustomUtility.showAlert(this, "Please fill doctor's visit fees","Fill the feilds");
            return 1;
        }
        else if(reVisitFees.equals(""))
        {
            CustomUtility.showAlert(this, "Please fill doctor's revisit fees","Fill the feilds");
            return 1;
        }
        else if(thanaCode1.equals(""))
        {
            CustomUtility.showAlert(this, "Please fill 1st chmaber's address","Fill the feilds");
            return 1;
        }
        else if(fstchmberAdd.equals(""))
        {
            CustomUtility.showAlert(this, "Please fill 1st chamber's address(road no)","Fill the feilds");
            return 1;
        }
        else if(fstariaZipcode.equals(""))
        {
            CustomUtility.showAlert(this, "Please fill 1st chamber's aria zipcode","Fill the feilds");
            return 1;
        }
        else if(!fstchmberStarttime.equals("") & fstchmberStarttime.indexOf(':') == -1)
        {
            CustomUtility.showAlert(this, "Please fill the first chamber start time in 10:00 and 24h format","Fill the feilds");
            return 1;
        }
        else if(!fstchmberEndtime.equals("") & fstchmberEndtime.indexOf(':') == -1)
        {
            CustomUtility.showAlert(this, "Please fill the first chamber end time in 17:00 and 24h format","Fill the feilds");
            return 1;
        }
        else if(!scndchmberStarttime.equals("") & scndchmberStarttime.indexOf(':') == -1)
        {
            CustomUtility.showAlert(this, "Please fill the first chamber second time in 10:00 and 24h format","Fill the feilds");
            return 1;
        }
        else if(!scndchmberEndtime.equals("") & scndchmberEndtime.indexOf(':') == -1)
        {
            CustomUtility.showAlert(this, "Please fill the second chamber end time in 17:00 and 24h format","Fill the feilds");
            return 1;
        }
        else if(!thirdchmberStarttime.equals("") & thirdchmberStarttime.indexOf(':') == -1)
        {
            CustomUtility.showAlert(this, "Please fill the third chamber start time in 10:00 and 24h format","Fill the feilds");
            return 1;
        }
        else if(!thirdchmberEndtime.equals("") & thirdchmberEndtime.indexOf(':') == -1)
        {
            CustomUtility.showAlert(this, "Please fill the third chamber end time in 17:00 and 24h format","Fill the feilds");
            return 1;
        }
        else if(isCommsagreed.equals(""))
        {
            CustomUtility.showAlert(this, "Please select doctor comms agreed or not","Fill the feilds");
            return 1;
        }
        else if(isbrandingExternal.equals(""))
        {
            CustomUtility.showAlert(this, "Please select pharmacy has branding scope external or not","Fill the feilds");
            return 1;
        }
        else if(isfascia.equals(""))
        {
            CustomUtility.showAlert(this, "Please select pharmacy has shop fascia or not","Fill the feilds");
            return 1;
        }
        else if(isSignboard.equals(""))
        {
            CustomUtility.showAlert(this, "Please select doctor has signboard or not","Fill the feilds");
            return 1;
        }
        else if(premisePhotoPath.equals(""))
        {
            CustomUtility.showAlert(this, "Please take a selfie with the premise","Take the picture");
            return 1;
        }
        else if(cardPhotPath.equals(""))
        {
            CustomUtility.showAlert(this, "Please take a photo of doctor's visiting card","Take the picture");
            return 1;
        }
        else if(signboardPhotPath.equals(""))
        {
            CustomUtility.showAlert(this, "Please take a picture of signboard","Take the picture");
            return 1;
        }
        return 0;
    }

    private File createImageFile(Integer id) throws IOException {

        File storageDir = getExternalFilesDir("Maricosurvey/Photos");

        if(id == 1) photoName = photoName1;
        else if(id==2) photoName = photoName2;
        else if(id==3) photoName = photoName3;
        else if (id==4) photoName = photoName4;
        File image = new File(storageDir.getAbsolutePath() + File.separator + photoName);
        try {
            image.createNewFile();
        } catch (IOException e) {
            CustomUtility.showAlert(form.this, "Image Creation Failed. Please contact administrator", "Error");
        }
        if(id==1) premisePhotoPath = image.getAbsolutePath();
        else if(id==2) cardPhotPath = image.getAbsolutePath();
        else if(id==3) signboardPhotPath = image.getAbsolutePath();
        else if(id==4) chamberPhotoPath = image.getAbsolutePath();
        currentPhotoPath = image.getAbsolutePath();
        Log.e("image path",currentPhotoPath);
        return image;
    }


    public void setDistrictList(String[] districtlst, AutoCompleteTextView autodivision)
    {
        ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, districtlst);
        autodivision.setThreshold(1);
        autodivision.setAdapter(adapter8);

    }


    private void GPS_Start() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            listener = new GPSLocationListener();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
        } catch (Exception ex) {

        }
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    public class GPSLocationListener implements LocationListener {
        public void onLocationChanged(final Location loc) {
            Log.i("**********", "Location changed");
            if (isBetterLocation(loc, previousBestLocation)) {


                loc.getAccuracy();
                location.setText("Location Accuracy: " + loc.getAccuracy());

                presentLat = String.valueOf(loc.getLatitude());
                presentLon = String.valueOf(loc.getLongitude());
                presentAcc = String.valueOf(loc.getAccuracy());


//                Toast.makeText(context, "Latitude" + loc.getLatitude() + "\nLongitude" + loc.getLongitude(), Toast.LENGTH_SHORT).show();
                intent.putExtra("Latitude", loc.getLatitude());
                intent.putExtra("Longitude", loc.getLongitude());
                intent.putExtra("Provider", loc.getProvider());
                sendBroadcast(intent);
            }
        }

        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
        }

        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            //Toast.makeText(getApplicationContext(), "Status Changed", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseJson(String json) {
        if (json == null) {
            Log.e("Json", "First Get Json\n" + json);
        } else {
            try {
                Log.e("Json", "Json\n" + json);
                jsonObj = new JSONObject(json);
                String success = jsonObj.getString("success");

                if(success.equals("true"))
                {
                    String today_count = jsonObj.getString("todayCount");
                    String total_count = jsonObj.getString("totalCount");
                    Log.e("total count",total_count);
                    Log.e("today count",today_count);
                    todayVisit.setText(today_count);
                    totalVisit.setText(total_count);
                }
                else
                {
                    CustomUtility.showAlert(this, "Error finding your visit count", "Error");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class getTargetAchievement extends AsyncTask<String, Void, String> {

        String json_url;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            json_url = "https://atmdbd.com/api/doctor/user_status.php";
            progressDialog = new ProgressDialog(form.this);
            progressDialog.setTitle("Please wait...");
            progressDialog.setMessage("");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(LoginActivity.id, "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
//                inputStream.close();btnAttendance
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            //Toast.makeText(getApplicationContext(), "First Get Json\n"+result, Toast.LENGTH_SHORT).show();
            parseJson(result);
        }
    }



   @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing the application")
                .setMessage("Are you sure you want to close this application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


}
