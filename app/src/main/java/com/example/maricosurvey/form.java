package com.example.maricosurvey;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class form extends AppCompatActivity {

    String[] sDropList = new String[] {"Male", "Female"};
    String[] bcsDropList = new String[] {"N/A", "Yes", "No"};
    String[] chamberDropList = new String[] {"1","2","3"};
    String[] chamberTypesList = new String[] {"Pharmacy", "Hospital", "Clinic", "Stand Alone"};
    String[] offdayList = new  String[] {"Not applicable", "Saturday","Sunday","Monday","Tuesday","Wednesday", "Thursday","Friday"};
    String[] ownerList = new String[] {"Same", "Other"};
    String chamberNumber = "";
    String[] divisionList = new String[] {"Barisal", "Chittagong", "Dhaka", "Khulna", "Rajshahi", "Rangpur", "Sylhet"};
    EditText doctorName, doctorAge, organizationName, firstPhoneNumber, secondPhoneNumber, doctorEmail, patientPerDay, visitFees, reVisitFees, ownerName, ownerPhoneNumber, ownerEmail, dimension;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //all the spinner lists
        Spinner sdropdown = findViewById(R.id.sdropdown);
        Spinner bcsdropdown = findViewById(R.id.bcsdropdown);
        final Spinner chamberdropdown = findViewById(R.id.schambers);
        Spinner fchamberTypesdropdown = findViewById(R.id.firstchmbertype);
        Spinner fchamberOffdaydropdown = findViewById(R.id.firstoffday);
        Spinner schamberTypesdropdown = findViewById(R.id.secondchmbertype);
        Spinner schamberOffdaydropdown = findViewById(R.id.secondoffday);
        Spinner tchamberTypesdropdown = findViewById(R.id.thirdchmbertype);
        Spinner tchamberOffdaydropdown = findViewById(R.id.thirdoffday);
        Spinner ownerdropdown = findViewById(R.id.sowner);
        Spinner tradedropdown = findViewById(R.id.tradelicense);
        Spinner commsdropdown = findViewById(R.id.commagreed);
        Spinner brandinginterdropdown = findViewById(R.id.brandingInternal);
        Spinner brandingexterdropdown = findViewById(R.id.brandingExternal);
        Spinner shopfasciadropdown = findViewById(R.id.shopfascia);
        Spinner signboarddropdown = findViewById(R.id.dsignboard);

        //chambers layout
        final ConstraintLayout firstChamber = findViewById(R.id.firstChamber);
        final ConstraintLayout secondChamber = findViewById(R.id.secondChamber);
        final ConstraintLayout thirdChamber = findViewById(R.id.thirdChamber);

        //all edit text feilds
        doctorName = findViewById(R.id.doctorName);
        doctorAge = findViewById(R.id.doctorAge);
        organizationName = findViewById(R.id.owf);
        firstPhoneNumber = findViewById(R.id.fstphn);
        secondPhoneNumber = findViewById(R.id.scndphn);
        doctorEmail = findViewById(R.id.email);
        patientPerDay = findViewById(R.id.ppd);
        visitFees = findViewById(R.id.fees);
        reVisitFees = findViewById(R.id.refees);
        ownerName = findViewById(R.id.ownerName);
        ownerPhoneNumber = findViewById(R.id.ownerphn);
        ownerEmail = findViewById(R.id.owneremail);
        dimension = findViewById(R.id.dimension);

        //all spinner adapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sDropList);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bcsDropList);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, chamberDropList);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, chamberTypesList);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, offdayList);
        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ownerList);

        //spinner dropdown
        sdropdown.setAdapter(adapter1);
        bcsdropdown.setAdapter(adapter2);
        chamberdropdown.setAdapter(adapter3);
        fchamberTypesdropdown.setAdapter(adapter4);
        fchamberOffdaydropdown.setAdapter(adapter5);
        schamberTypesdropdown.setAdapter(adapter4);
        schamberOffdaydropdown.setAdapter(adapter5);
        tchamberTypesdropdown.setAdapter(adapter4);
        tchamberOffdaydropdown.setAdapter(adapter5);
        ownerdropdown.setAdapter(adapter6);
        tradedropdown.setAdapter(adapter2);
        commsdropdown.setAdapter(adapter2);
        brandinginterdropdown.setAdapter(adapter2);
        brandingexterdropdown.setAdapter(adapter2);
        shopfasciadropdown.setAdapter(adapter2);
        signboarddropdown.setAdapter(adapter2);

        chamberdropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chamberNumber = chamberdropdown.getSelectedItem().toString();
                if(chamberNumber.equals("1"))
                {
                    firstChamber.setVisibility(View.VISIBLE);
                    secondChamber.setVisibility(View.GONE);
                    thirdChamber.setVisibility(View.GONE);
                }
                else if(chamberNumber.equals("2"))
                {
                    firstChamber.setVisibility(View.VISIBLE);
                    secondChamber.setVisibility(View.VISIBLE);
                    thirdChamber.setVisibility(View.GONE);
                }
                else if(chamberNumber.equals("3")) {
                    firstChamber.setVisibility(View.VISIBLE);
                    secondChamber.setVisibility(View.VISIBLE);
                    thirdChamber.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

}
