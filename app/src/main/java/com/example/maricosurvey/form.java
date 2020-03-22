package com.example.maricosurvey;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class form extends AppCompatActivity {

    public LocationManager locationManager;
    public GPSLocationListener listener;
    public Location previousBestLocation = null;
    private static final int TWO_MINUTES = 1000 * 60 * 1;
    public static final String BROADCAST_ACTION = "gps_data";
    Intent intent;

    Button premisePhoto, cardPhoto, signboardPhoto,chamberPhoto;
    String premisePhotoPath = "", cardPhotPath = "", signboardPhotPath= "", chamberPhotoPath= "";
    static String photoName = "", photoName1 = "", photoName2 = "",photoName3 = "", photoName4 = "";
    static String imageString1 = "",imageString2 = "",imageString3 = "",imageString4 = "";
    static Bitmap bitmap1,bitmap2, bitmap3, bitmap4;
    Bundle IDbundle;
    String currentPhotoPath = "";
    Uri photoURI1, photoURI2, photoURI3, photoURI4;
    static final int REQUEST_IMAGE_CAPTURE = 99;

    ProgressDialog progressDialog;
    JSONObject jsonObject;
    JSONArray jsonArray;

    String division, district, thana;
    Integer divisionStartIndex, divisionEndIndex, thanaCode1=-1, thanaCode2=-1, thanaCode3=-1;

    public static String presentLat = "", presentLon = "", presentAcc = "";


    String[] sDropList = new String[] {"Male", "Female"};
    String[] yesnoDropList = new String[] {"N/A", "Yes", "No"};
    String[] chamberTypesList = new String[] {"N/A", "Pharmacy", "Hospital", "Clinic", "Stand Alone", "Other"};
    String[] tittleList = new String[] {"N/A","DR", "OTHERS"};
    String[] categoryList = new String[] {"N/A", "PEDIATRICIAN","CARDIOLOGIST","DERMATOLOGIST","GENERAL PRACTITIONER","GYNECOLOGIST","MEDICINE SPECIALIST","PEDIATRIC SURGERY","OTHERS"};
    String[] designationList = new String[] {"N/A","PROFESSOR", "ASSOCIATE PROFESSOR", "ASSISTANT PROFESSOR", "REGISTER", "SENIOR REGISTER","ASSISTANT REGISTER",
    "CONSULTANT","CONSULTANT HEAD", "SENIOR CONSULTANT","ASSOCIATE CONSULTANT","CHILD SPECIALIST","DEPARTMENTAL HEAD","DEPUTY CO-ORDINATOR, CHRF","EMERGENCY MEDICAL OFFICER",
            "EX-PRINCIPAL & DIRECTOR","PRINCIPAL","DIRECTOR","DEPARTMENT HEAD","IN CHARGE","JUNIOR CONSULTANT","MD RESIDENT","MEDICAL OFFICER","SENIOR MEDICAL OFFICER","PEDIATRIC SURGEON",
            "RESIDENTIAL MEDICAL OFFICER","RESIDENTIAL MEDICAL OFFICER (ENT)","UHFPO","OTHER"};
    String[] offdayList = new  String[] {"N/A", "Saturday","Sunday","Monday","Tuesday","Wednesday", "Thursday","Friday"};
    String chamberNumber = "";
    String[] divisionList = new String[] {"Barisal", "Chittagong", "Dhaka", "Khulna", "Rajshahi", "Rangpur", "Sylhet"};

    String[] districtList = new String[]{"Barguna", "Barisal", "Bhola", "Jhalokati", "Patuakhali", "Pirojpur", "Bandarban", "Brahmanbaria", "Chandpur", "Chittagong", "Comilla", "Cox's Bazar",
            "Feni", "Khagrachhari", "Lakshmipur", "Noakhali", "Rangamati", "Dhaka", "Faridpur", "Gazipur", "Gopalganj", "Jamalpur", "Kishoregonj", "Madaripur",
            "Manikganj", "Munshiganj", "Mymensingh", "Narayanganj", "Narsingdi", "Netrakona", "Rajbari", "Shariatpur", "Sherpur", "Tangail", "Bagerhat", "Chuadanga", "Jessore", "Jhenaidah", "Khulna",
            "Kushtia", "Magura", "Meherpur", "Narail", "Satkhira", "Bogra", "Joypurhat", "Naogaon", "Natore", "Chapai", "Nababganj", "Pabna", "Rajshahi", "Sirajganj", "Dinajpur", "Gaibandha", "Kurigram",
            "Lalmonirhat", "Nilphamari", "Zila", "Panchagarh", "Rangpur", "Thakurgaon", "Habiganj", "Maulvibazar", "Sunamganj", "Sylhet"};


    String[] thanaList = new String[] {"Amtali", "Bamna", "Barguna Sadar", "Betagi", "Patharghata", "Taltali", "Agailjhara", "Babuganj", "Bakerganj", "Banari Para", "Gaurnadi", "Hizla", "Barisal Sadar (Kotwali)", "Mhendiganj",
            "Muladi", "Wazirpur", "Bhola Sadar", "Burhanuddin", "Char Fasson", "Daulat Khan", "Lalmohan", "Manpura", "Tazumuddin", "Jhalokati Sadar", "Kanthalia", "Nalchity", "Rajapur",
            "Bauphal", "Dashmina", "Dumki", "Galachipa", "Kalapara", "Mirzaganj", "Patuakhali Sadar", "Rangabali", "Bhandaria", "Kawkhali", "Mathbaria", "Nazirpur", "Pirojpur Sadar", "Nesarabad (Swarupkati)", "Zianagar",
            "Alikadam", "Bandarban Sadar", "Lama", "Naikhongchhari", "Rowangchhari", "Ruma", "Thanchi", "Akhaura", "Banchharampur", "Bijoynagar", "Brahmanbaria Sadar", "Ashuganj", "Kasba", "Nabinagar", "Nasirnagar",
            "Sarail", "Chandpur Sadar", "Faridganj", "Haim Char", "Hajiganj", "Kachua", "Matlab Dakshi", "Matlab Uttar", "Shahrasti", "Anowara", "Bayejid Bostami", "Banshkhali", "Bakalia", "Boalkhali", "Chandanaish",
            "Chandgaon", "Chittagong Port", "Double Mooring", "Fatikchhari", "Halishahar", "Hathazari", "Kotwali", "Khulshi", "Lohagara", "Mirsharai", "Pahartali", "Panchlaish", "Patiya", "Patenga",
            "Rangunia", "Raozan", "Sandwip", "Satkania", "Sitakunda", "Barura", "Brahman Para", "Burichang", "Chandina", "Chauddagram", "Comilla Sadar Dakshin", "Daudkandi", "Debidwar", "Homna", "Comilla Adarsha Sadar", "Laksam", "Manoharganj",
            "Meghna", "Muradnagar", "Nangalkot", "Titas", "Chakaria", "Cox's Bazar Sadar", "Kutubdia", "Maheshkhali", "Pekua", "Ramu", "Teknaf", "Ukhia", "Chhagalnaiya", "Daganbhuiyan", "Feni Sadar", "Fulgazi", "Parshuram",
            "Sonagazi", "Dighinala", "Khagrachhari Sadar", "Lakshmichhari", "Mahalchhari", "Manikchhari", "Matiranga", "Panchhari", "Ramgarh", "Kamalnagar", "Lakshmipur Sadar", "Roypur", "Ramganj", "Ramgati", "Begumganj",
            "Chatkhil", "Companiganj", "Hatiya", "Kabirhat", "Senbagh", "Sonaimuri", "Subarnachar", "Noakhali Sadar", "Baghaichhari", "Barkal Upazila", "Kawkhali (Betbunia)", "Belai Chhari Upazila", "Kaptai Upazila", "Jurai Chhari Upazila",
            "Langadu Upazila", "Naniarchar Upazila", "Rajasthali Upazila", "Rangamati Sadar Upazila",
            "Adabor", "Badda", "Bangshal", "Biman Bandar", "Banani", "Cantonment", "Chak Bazar", "Dakshinkhan", "Darus Salam", "Demra", "Dhamrai",
            "Dhanmondi", "Dohar", "Bhasan Tek", "Bhatara", "Gendaria", "Gulshan", "Hazaribagh", "Jatrabari", "Kafrul", "Kadamtali", "Kalabagan", "Kamrangir Char", "Khilgaon", "Khilkhet", "Keraniganj", "Kotwali", "Lalbagh",
            "Mirpur", "Mohammadpur", "Motijheel", "Mugda Para", "Nawabganj", "New Market", "Pallabi", "Paltan", "Ramna", "Rampura", "Sabujbagh", "Rupnagar", "Savar", "Shahjahanpur", "Shah Ali", "Shahbagh",
            "Shyampur", "Sher-e-bangla Nagar", "Sutrapur", "Tejgaon", "Tejgaon Ind.Area", "Turag", "Uttara Paschim", "Uttara Purba","Uttar Khan", "Wari", "Alfadanga", "Bhanga", "Boalmari", "Char Bhadrasan", "Faridpur Sadar", "Madhukhali",
            "Nagarkanda", "Sadarpur", "Saltha", "Gazipur Sadar", "Kaliakair", "Kaliganj", "Kapasia", "Sreepur", "Gopalganj Sadar", "Kashiani", "Kotalipara", "Muksudpur", "Tungipara", "Bakshiganj", "Dewanganj", "Islampur",
            "Jamalpur Sadar", "Madarganj", "Melandaha", "Sarishabari Upazila", "Austagram", "Bajitpur", "Bhairab", "Hossainpur", "Itna", "Karimganj", "Katiadi", "Kishoreganj Sadar", "Kuliar Char", "Mithamain", "Nikli", "Pakundia",
            "Tarail", "Kalkini", "Madaripur Sadar", "Rajoir", "Shibchar", "Daulatpur", "Ghior", "Harirampur", "Manikganj Sadar", "Saturia", "Shibalaya", "Singair", "Gazaria", "Lohajang", "Munshiganj Sadar", "Serajdikhan",
            "Sreenagar", "Tongibari", "Bhaluka", "Dhobaura", "Fulbaria", "Gaffargaon", "Gauripur", "Haluaghat", "Ishwarganj", "Mymensingh Sadar", "Muktagachha", "Nandail", "Phulpur", "Trishal", "Araihazar", "Sonargaon", "Bandar",
            "Narayanganj Sadar", "Rupganj", "Belabo", "Manohardi", "Narsingdi Sadar", "Palash", "Roypura", "Shibpur", "Atpara", "Barhatta", "Durgapur", "Khaliajuri", "Kalmakanda", "Kendua", "Madan", "Mohanganj", "Netrokona Sadar",
            "Purbadhala", "Baliakandi", "Goalanda", "Kalukhali", "Pangsha", "Rajbari Sadar", "Bhedarganj", "Damudya", "Gosairhat", "Naria", "Shariatpur Sadar", "Zanjira", "Jhenaigati", "Nakla", "Nalitabari", "Sherpur Sadar",
            "Sreebardi", "Basail", "Bhuapur", "Delduar", "Dhanbari", "Ghatail", "Gopalpur", "Kalihati", "Madhupur", "Mirzapur", "Nagarpur", "Sakhipur", "Tangail Sadar",
            "Bagerhat Sadar", "Chitalmari", "Fakirhat", "Kachua", "Mollahat",
            "Mongla", "Morrelganj", "Rampal", "Sarankhola", "Alamdanga", "Chuadanga Sadar", "Damurhuda", "Jiban Nagar", "Abhaynagar", "Bagher Para", "Chaugachha", "Jhikargachha", "Keshabpur", "Jessore Sadar", "Manirampur", "Sharsha",
            "Harinakunda", "Jhenaidah Sadar", "Kaliganj", "Kotchandpur", "Maheshpur", "Shailkupa", "Batiaghata", "Dacope", "Daulatpur", "Dumuria", "Dighalia", "Khalishpur", "Khan Jahan Ali", "Khulna Sadar", "Koyra", "Paikgachha", "Phultala", "Rupsa",
            "Sonadanga", "Terokhada", "Bheramara", "Dualatpur", "Khoksa", "Kumarkhali", "Kushtia Sadar", "Mirpur", "Magura Sadar", "Mohammadpur", "Shalikha", "Sreepur", "Gangni", "Mujib Nagar", "Meherpur Sadar", "Kalia", "Lohagara", "Narail Sadar",
            "Assasuni", "Debhata", "Kalaroa", "Kaliganj", "Satkhira Sadar", "Shyamnagar", "Tala",
            "Adamdighi", "Bogra Sadar", "Dhunat", "Dhupchanchia", "Gabtali", "Kahaloo", "Nandigram", "Sariakandi", "Shajahanpur", "Sherpur", "Shibganj",
            "Sonatola", "Akkelpur", "Joypurhat Sadar", "Kalai", "Khetlal", "Panchbibi", "Atrai", "Badalgachhi", "Dhamoirhat", "Manda", "Mahadebpur", "Naogaon Sadar", "Niamatpur", "Patnitala", "Porsha", "Raninagar", "Sapahar", "Bagatipara",
            "Baraigram", "Gurudaspur", "Lalpur", "Natore Sadar", "Singra", "Bholahat", "Gomastapur", "Nachole", "Chapai Nababganj Sadar", "Shibgonj", "Atgharia", "Bera", "Bhangura", "Chatmohar", "Faridpur", "Ishwardi", "Pabna Sadar", "Santhia",
            "Sujanagar", "Bagha", "Baghmara", "Boalia", "Charghat", "Durgapur", "Godagari", "Matihar", "Mohanpur", "Paba", "Puthia", "Rajpara", "Shah Makhdum", "Tanore", "Belkuchi", "Chauhali", "Kamarkhanda", "Kazipur", "Royganj", "Shahjadpur",
            "Sirajganj Sadar", "Tarash", "Ullah Para",
            "Birampur", "Birganj", "Biral", "Bochaganj", "Chirirbandar", "Fulbari", "Ghoraghat", "Hakimpur", "Kaharole", "Khansama", "Dinajpur Sadar", "Nawabganj", "Parbatipur", "Fulchhari", "Gaibandha Sadar",
            "Gobindaganj", "Palashbari", "Sadullapur", "Saghata", "Sundarganj", "Bhurungamari", "Char Rajibpur", "Chilmari", "Phulbari", "Kurigram Sadar", "Nageshwari", "Rajarhat", "Raumari", "Ulipur", "Aditmari", "Hatibandha",
            "Kaliganj", "Lalmonirhat Sadar", "Patgram", "Dimla Upazila", "Domar Upazila", "Jaldhaka Upazila","Kishorganj Upazila", "Nilphamari Sadar Upazila", "Saidpur Upazila", "Atwari", "Boda", "Debiganj", "Panchagarh Sadar", "Tentulia", "Badarganj",
            "Gangachara", "Kaunia", "Rangpur Sadar", "Mitha Pukur", "Pirgachha", "Pirganj", "Taraganj", "Baliadangi", "Haripur", "Pirganj", "Ranisankail", "Thakurgaon Sadar",
            "Ajmiriganj", "Bahubal", "Baniachong", "Chunarughat", "Habiganj Sadar", "Lakhai",
            "Madhabpur", "Nabiganj", "Barlekha", "Juri", "Kamalganj", "Kulaura", "Maulvibazar Sadar", "Rajnagar", "Sreemangal", "Bishwambarpur", "Chhatak", " Dakshin Sunamganj", "Derai", "Dharampasha", "Dowarabazar", "Jagannathpur", "Jamalganj", "Sulla", "Sunamganj Sadar",
            "Tahirpur", "Balaganj", "Beani Bazar", "Bishwanath", "Companiganj", "Dakshin Surma", "Fenchuganj", "Golapganj", "Gowainghat", "Jaintiapur", "Kanaighat", "Sylhet Sadar", "Zakiganj"};

    String[] barisalThanaList = new String[] {"Amtali", "Bamna", "Barguna Sadar", "Betagi", "Patharghata", "Taltali", "Agailjhara", "Babuganj", "Bakerganj", "Banari Para", "Gaurnadi", "Hizla", "Barisal Sadar (Kotwali)", "Mhendiganj",
            "Muladi", "Wazirpur", "Bhola Sadar", "Burhanuddin", "Char Fasson", "Daulat Khan", "Lalmohan", "Manpura", "Tazumuddin", "Jhalokati Sadar", "Kanthalia", "Nalchity", "Rajapur",
            "Bauphal", "Dashmina", "Dumki", "Galachipa", "Kalapara", "Mirzaganj", "Patuakhali Sadar", "Rangabali", "Bhandaria", "Kawkhali", "Mathbaria", "Nazirpur", "Pirojpur Sadar", "Nesarabad (Swarupkati)", "Zianagar"};

    String[] chittagongThanaList = new String[] {"Alikadam", "Bandarban Sadar", "Lama", "Naikhongchhari", "Rowangchhari", "Ruma", "Thanchi", "Akhaura", "Banchharampur", "Bijoynagar", "Brahmanbaria Sadar", "Ashuganj", "Kasba", "Nabinagar", "Nasirnagar",
            "Sarail", "Chandpur Sadar", "Faridganj", "Haim Char", "Hajiganj", "Kachua", "Matlab Dakshi", "Matlab Uttar", "Shahrasti", "Anowara", "Bayejid Bostami", "Banshkhali", "Bakalia", "Boalkhali", "Chandanaish",
            "Chandgaon", "Chittagong Port", "Double Mooring", "Fatikchhari", "Halishahar", "Hathazari", "Kotwali", "Khulshi", "Lohagara", "Mirsharai", "Pahartali", "Panchlaish", "Patiya", "Patenga",
            "Rangunia", "Raozan", "Sandwip", "Satkania", "Sitakunda", "Barura", "Brahman Para", "Burichang", "Chandina", "Chauddagram", "Comilla Sadar Dakshin", "Daudkandi", "Debidwar", "Homna", "Comilla Adarsha Sadar", "Laksam", "Manoharganj",
            "Meghna", "Muradnagar", "Nangalkot", "Titas", "Chakaria", "Cox's Bazar Sadar", "Kutubdia", "Maheshkhali", "Pekua", "Ramu", "Teknaf", "Ukhia", "Chhagalnaiya", "Daganbhuiyan", "Feni Sadar", "Fulgazi", "Parshuram",
            "Sonagazi", "Dighinala", "Khagrachhari Sadar", "Lakshmichhari", "Mahalchhari", "Manikchhari", "Matiranga", "Panchhari", "Ramgarh", "Kamalnagar", "Lakshmipur Sadar", "Roypur", "Ramganj", "Ramgati", "Begumganj",
            "Chatkhil", "Companiganj", "Hatiya", "Kabirhat", "Senbagh", "Sonaimuri", "Subarnachar", "Noakhali Sadar", "Baghaichhari", "Barkal Upazila", "Kawkhali (Betbunia)", "Belai Chhari Upazila", "Kaptai Upazila", "Jurai Chhari Upazila",
            "Langadu Upazila", "Naniarchar Upazila", "Rajasthali Upazila", "Rangamati Sadar Upazila"};

    String[] dhakaThanaList = new String[] {"Adabor", "Badda", "Bangshal", "Biman Bandar", "Banani", "Cantonment", "Chak Bazar", "Dakshinkhan", "Darus Salam", "Demra", "Dhamrai",
            "Dhanmondi", "Dohar", "Bhasan Tek", "Bhatara", "Gendaria", "Gulshan", "Hazaribagh", "Jatrabari", "Kafrul", "Kadamtali", "Kalabagan", "Kamrangir Char", "Khilgaon", "Khilkhet", "Keraniganj", "Kotwali", "Lalbagh",
            "Mirpur", "Mohammadpur", "Motijheel", "Mugda Para", "Nawabganj", "New Market", "Pallabi", "Paltan", "Ramna", "Rampura", "Sabujbagh", "Rupnagar", "Savar", "Shahjahanpur", "Shah Ali", "Shahbagh",
            "Shyampur", "Sher-e-bangla Nagar", "Sutrapur", "Tejgaon", "Tejgaon Ind.Area", "Turag", "Uttara Paschim", "Uttara Purba","Uttar Khan", "Wari", "Alfadanga", "Bhanga", "Boalmari", "Char Bhadrasan", "Faridpur Sadar", "Madhukhali",
            "Nagarkanda", "Sadarpur", "Saltha", "Gazipur Sadar", "Kaliakair", "Kaliganj", "Kapasia", "Sreepur", "Gopalganj Sadar", "Kashiani", "Kotalipara", "Muksudpur", "Tungipara", "Bakshiganj", "Dewanganj", "Islampur",
            "Jamalpur Sadar", "Madarganj", "Melandaha", "Sarishabari Upazila", "Austagram", "Bajitpur", "Bhairab", "Hossainpur", "Itna", "Karimganj", "Katiadi", "Kishoreganj Sadar", "Kuliar Char", "Mithamain", "Nikli", "Pakundia",
            "Tarail", "Kalkini", "Madaripur Sadar", "Rajoir", "Shibchar", "Daulatpur", "Ghior", "Harirampur", "Manikganj Sadar", "Saturia", "Shibalaya", "Singair", "Gazaria", "Lohajang", "Munshiganj Sadar", "Serajdikhan",
            "Sreenagar", "Tongibari", "Bhaluka", "Dhobaura", "Fulbaria", "Gaffargaon", "Gauripur", "Haluaghat", "Ishwarganj", "Mymensingh Sadar", "Muktagachha", "Nandail", "Phulpur", "Trishal", "Araihazar", "Sonargaon", "Bandar",
            "Narayanganj Sadar", "Rupganj", "Belabo", "Manohardi", "Narsingdi Sadar", "Palash", "Roypura", "Shibpur", "Atpara", "Barhatta", "Durgapur", "Khaliajuri", "Kalmakanda", "Kendua", "Madan", "Mohanganj", "Netrokona Sadar",
            "Purbadhala", "Baliakandi", "Goalanda", "Kalukhali", "Pangsha", "Rajbari Sadar", "Bhedarganj", "Damudya", "Gosairhat", "Naria", "Shariatpur Sadar", "Zanjira", "Jhenaigati", "Nakla", "Nalitabari", "Sherpur Sadar",
            "Sreebardi", "Basail", "Bhuapur", "Delduar", "Dhanbari", "Ghatail", "Gopalpur", "Kalihati", "Madhupur", "Mirzapur", "Nagarpur", "Sakhipur", "Tangail Sadar"};

    String[] khulnaThanaList = new String[] {"Bagerhat Sadar", "Chitalmari", "Fakirhat", "Kachua", "Mollahat",
            "Mongla", "Morrelganj", "Rampal", "Sarankhola", "Alamdanga", "Chuadanga Sadar", "Damurhuda", "Jiban Nagar", "Abhaynagar", "Bagher Para", "Chaugachha", "Jhikargachha", "Keshabpur", "Jessore Sadar", "Manirampur", "Sharsha",
            "Harinakunda", "Jhenaidah Sadar", "Kaliganj", "Kotchandpur", "Maheshpur", "Shailkupa", "Batiaghata", "Dacope", "Daulatpur", "Dumuria", "Dighalia", "Khalishpur", "Khan Jahan Ali", "Khulna Sadar", "Koyra", "Paikgachha", "Phultala", "Rupsa",
            "Sonadanga", "Terokhada", "Bheramara", "Dualatpur", "Khoksa", "Kumarkhali", "Kushtia Sadar", "Mirpur", "Magura Sadar", "Mohammadpur", "Shalikha", "Sreepur", "Gangni", "Mujib Nagar", "Meherpur Sadar", "Kalia", "Lohagara", "Narail Sadar",
            "Assasuni", "Debhata", "Kalaroa", "Kaliganj", "Satkhira Sadar", "Shyamnagar", "Tala"};

    String[] rajshahiThanaList = new String[] {"Adamdighi", "Bogra Sadar", "Dhunat", "Dhupchanchia", "Gabtali", "Kahaloo", "Nandigram", "Sariakandi", "Shajahanpur", "Sherpur", "Shibganj",
            "Sonatola", "Akkelpur", "Joypurhat Sadar", "Kalai", "Khetlal", "Panchbibi", "Atrai", "Badalgachhi", "Dhamoirhat", "Manda", "Mahadebpur", "Naogaon Sadar", "Niamatpur", "Patnitala", "Porsha", "Raninagar", "Sapahar", "Bagatipara",
            "Baraigram", "Gurudaspur", "Lalpur", "Natore Sadar", "Singra", "Bholahat", "Gomastapur", "Nachole", "Chapai Nababganj Sadar", "Shibgonj", "Atgharia", "Bera", "Bhangura", "Chatmohar", "Faridpur", "Ishwardi", "Pabna Sadar", "Santhia",
            "Sujanagar", "Bagha", "Baghmara", "Boalia", "Charghat", "Durgapur", "Godagari", "Matihar", "Mohanpur", "Paba", "Puthia", "Rajpara", "Shah Makhdum", "Tanore", "Belkuchi", "Chauhali", "Kamarkhanda", "Kazipur", "Royganj", "Shahjadpur",
            "Sirajganj Sadar", "Tarash", "Ullah Para"};

    String[] rangpurThanaList = new String[] {"Birampur", "Birganj", "Biral", "Bochaganj", "Chirirbandar", "Fulbari", "Ghoraghat", "Hakimpur", "Kaharole", "Khansama", "Dinajpur Sadar", "Nawabganj", "Parbatipur", "Fulchhari", "Gaibandha Sadar",
            "Gobindaganj", "Palashbari", "Sadullapur", "Saghata", "Sundarganj", "Bhurungamari", "Char Rajibpur", "Chilmari", "Phulbari", "Kurigram Sadar", "Nageshwari", "Rajarhat", "Raumari", "Ulipur", "Aditmari", "Hatibandha",
            "Kaliganj", "Lalmonirhat Sadar", "Patgram", "Dimla Upazila", "Domar Upazila", "Jaldhaka Upazila","Kishorganj Upazila", "Nilphamari Sadar Upazila", "Saidpur Upazila", "Atwari", "Boda", "Debiganj", "Panchagarh Sadar", "Tentulia", "Badarganj",
            "Gangachara", "Kaunia", "Rangpur Sadar", "Mitha Pukur", "Pirgachha", "Pirganj", "Taraganj", "Baliadangi", "Haripur", "Pirganj", "Ranisankail", "Thakurgaon Sadar"};

    String[] sylthetThanaList = new String[] {"Ajmiriganj", "Bahubal", "Baniachong", "Chunarughat", "Habiganj Sadar", "Lakhai",
            "Madhabpur", "Nabiganj", "Barlekha", "Juri", "Kamalganj", "Kulaura", "Maulvibazar Sadar", "Rajnagar", "Sreemangal", "Bishwambarpur", "Chhatak", " Dakshin Sunamganj", "Derai", "Dharampasha", "Dowarabazar", "Jagannathpur", "Jamalganj", "Sulla", "Sunamganj Sadar",
            "Tahirpur", "Balaganj", "Beani Bazar", "Bishwanath", "Companiganj", "Dakshin Surma", "Fenchuganj", "Golapganj", "Gowainghat", "Jaintiapur", "Kanaighat", "Sylhet Sadar", "Zakiganj"};

    String[] barisalDistricList = new String[] {"Barguna", "Barisal", "Bhola", "Jhalokati", "Patuakhali", "Pirojpur"};
    String[] chittagongDistricList = new String[] {"Bandarban", "Brahmanbaria", "Chandpur", "Chittagong", "Comilla", "Cox's Bazar",
            "Feni", "Khagrachhari", "Lakshmipur", "Noakhali", "Rangamati"};
    String[] dhakaDistricList = new String[] { "Dhaka", "Faridpur", "Gazipur", "Gopalganj", "Jamalpur", "Kishoregonj", "Madaripur",
            "Manikganj", "Munshiganj", "Mymensingh", "Narayanganj", "Narsingdi", "Netrakona", "Rajbari", "Shariatpur", "Sherpur", "Tangail"};
    String[] khulnaDistricList = new String[] {"Bagerhat", "Chuadanga", "Jessore", "Jhenaidah", "Khulna",
            "Kushtia", "Magura", "Meherpur", "Narail", "Satkhira"};
    String[] rajshahiDistricList = new String[] {"Bogra", "Joypurhat", "Naogaon", "Natore", "Chapai", "Nababganj", "Pabna", "Rajshahi", "Sirajganj"};
    String[] rangpurDistricList = new String[] {"Dinajpur", "Gaibandha", "Kurigram",
            "Lalmonirhat", "Nilphamari", "Zila", "Panchagarh", "Rangpur", "Thakurgaon"};
    String[] sylthetDistricList = new String[] {"Habiganj", "Maulvibazar", "Sunamganj", "Sylhet"};

    EditText editdoctorName, editdoctorAge, editorganizationName, editfirstPhoneNumber, editsecondPhoneNumber, editdoctorEmail, editdoctorOtherdegree, editAriaZipcode, editpatientPerDay, editvisitFees, editreVisitFees, editownerName, editownerPhoneNumber, editownerEmail, editdimension,
            editfirstAdd, editsecondAdd, editthirdAdd, editRemarks;
    TextView location;
    AutoCompleteTextView autoDivision1, autoDistrict1, autoThana1, autoDivision2, autoDistrict2, autoThana2, autoDivision3, autoDistrict3, autoThana3;

    String doctorName = "", doctorSex = "", doctorAge="", organizationName="", firstPhoneNumber="", secondPhoneNumber="", doctorEmail="", doctorOtherdegree = "", designation= "", category= "", privatePractice= "", tittle= "", ariaZipcode= "", patientPerDay="", visitFees="",
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Log.e("thana list size", String.valueOf(thanaList.length));

        intent = new Intent(BROADCAST_ACTION);

        photoName1 = LoginActivity.userid + "_premise"+CustomUtility.getTimeStamp("yyyyMMddhhmmss") + ".jpeg";
        photoName2 = LoginActivity.userid + "_doctorcard"+CustomUtility.getTimeStamp("yyyyMMddhhmmss") + ".jpeg";
        photoName3 = LoginActivity.userid + "_signboard"+CustomUtility.getTimeStamp("yyyyMMddhhmmss") + ".jpeg";
        photoName4 = LoginActivity.userid + "_chamber"+CustomUtility.getTimeStamp("yyyyMMddhhmmss") + ".jpeg";

        //auto views;
        autoDivision1 = findViewById(R.id.firstdivisionList);
        autoDistrict1 = findViewById(R.id.firstdistrictList);
        autoThana1 = findViewById(R.id.firstthanaList);

        autoDivision2 = findViewById(R.id.seconddivisionList);
        autoDistrict2 = findViewById(R.id.seconddistrictList);
        autoThana2 = findViewById(R.id.secondthanaList);

        autoDivision3 = findViewById(R.id.thirddivisionList);
        autoDistrict3 = findViewById(R.id.thirddistrictList);
        autoThana3 = findViewById(R.id.thirdthanaList);


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
        editAriaZipcode = findViewById(R.id.zipcode);
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


        rd = findViewById(R.id.ch1);

        rd.setChecked(true);

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


        pharmacyOwnerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.sownerYes:
                        pharmacyOwnerType = "Same";
                        break;
                    case R.id.sownerNo:
                        pharmacyOwnerType = "Other";
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
                        break;
                    case R.id.ch2:
                        firstChamber.setVisibility(View.VISIBLE);
                        secondChamber.setVisibility(View.VISIBLE);
                        thirdChamber.setVisibility(View.GONE);
                        break;
                    case R.id.ch3:
                        firstChamber.setVisibility(View.VISIBLE);
                        secondChamber.setVisibility(View.VISIBLE);
                        thirdChamber.setVisibility(View.VISIBLE);
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
                   setDistrictThanaList(barisalDistricList, barisalThanaList, autoDistrict1, autoThana1);
                   //Log.e("division",division + thanaList1.length);
               }
               else if (division.equals("Chittagong"))
               {
                   divisionStartIndex = 42;
                   divisionEndIndex = 152;
                   setDistrictThanaList(chittagongDistricList, chittagongThanaList, autoDistrict1, autoThana1);
               }
               else if (division.equals("Dhaka"))
               {
                   divisionStartIndex = 153;
                   divisionEndIndex = 321;
                   setDistrictThanaList(dhakaDistricList, dhakaThanaList, autoDistrict1, autoThana1);
               }
               else if (division.equals("Khulna"))
               {
                   divisionStartIndex = 322;
                   divisionEndIndex = 387;
                   setDistrictThanaList(khulnaDistricList, khulnaThanaList, autoDistrict1, autoThana1);
               }
               else if (division.equals("Rajshahi"))
               {
                   divisionStartIndex = 388;
                   divisionEndIndex = 457;
                   setDistrictThanaList(rajshahiDistricList, rajshahiThanaList, autoDistrict1, autoThana1);
               }
               else if (division.equals("Rangpur"))
               {
                   divisionStartIndex = 458;
                   divisionEndIndex = 515;
                   setDistrictThanaList(rangpurDistricList, rangpurThanaList, autoDistrict1, autoThana1);
               }
               else if (division.equals("Sylhet"))
               {
                   divisionStartIndex = 516;
                   divisionEndIndex = 553;
                   setDistrictThanaList(sylthetDistricList, sylthetThanaList, autoDistrict1, autoThana1);
               }
           }
       });


        autoDistrict1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("district",autoDistrict1.getText().toString());
            }
        });

        autoThana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("thana",autoThana1.getText().toString());
                String selection = (String) parent.getItemAtPosition(position);
                Integer pos = -1,i;

                for (i = divisionStartIndex; i <= divisionEndIndex; i++) {
                    if (thanaList[i].equals(selection)) {
                        pos = i+1;
                        break;
                    }
                }
                thanaCode1 = pos;
                Log.e("Position", pos.toString());
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
                    setDistrictThanaList(barisalDistricList, barisalThanaList, autoDistrict2, autoThana2);
                    //Log.e("division",division + thanaList1.length);
                }
                else if (division.equals("Chittagong"))
                {
                    divisionStartIndex = 42;
                    divisionEndIndex = 152;
                    setDistrictThanaList(chittagongDistricList, chittagongThanaList, autoDistrict2, autoThana2);
                }
                else if (division.equals("Dhaka"))
                {
                    divisionStartIndex = 153;
                    divisionEndIndex = 321;
                    setDistrictThanaList(dhakaDistricList, dhakaThanaList, autoDistrict2, autoThana2);
                }
                else if (division.equals("Khulna"))
                {
                    divisionStartIndex = 322;
                    divisionEndIndex = 387;
                    setDistrictThanaList(khulnaDistricList, khulnaThanaList, autoDistrict2, autoThana2);
                }
                else if (division.equals("Rajshahi"))
                {
                    divisionStartIndex = 388;
                    divisionEndIndex = 457;
                    setDistrictThanaList(rajshahiDistricList, rajshahiThanaList, autoDistrict2, autoThana2);
                }
                else if (division.equals("Rangpur"))
                {
                    divisionStartIndex = 458;
                    divisionEndIndex = 515;
                    setDistrictThanaList(rangpurDistricList, rangpurThanaList, autoDistrict2, autoThana2);
                }
                else if (division.equals("Sylhet"))
                {
                    divisionStartIndex = 516;
                    divisionEndIndex = 553;
                    setDistrictThanaList(sylthetDistricList, sylthetThanaList, autoDistrict2, autoThana2);
                }
            }
        });


        autoDistrict2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("district",autoDistrict2.getText().toString());
            }
        });

        autoThana2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("thana2",autoThana2.getText().toString());
                String selection = (String) parent.getItemAtPosition(position);
                Integer pos = -1,i;

                for (i = divisionStartIndex; i <= divisionEndIndex; i++) {
                    if (thanaList[i].equals(selection)) {
                        pos = i+1;
                        break;
                    }
                }
                thanaCode2 = pos;
                Log.e("Position2", pos.toString());
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
                    setDistrictThanaList(barisalDistricList, barisalThanaList, autoDistrict3, autoThana3);
                    //Log.e("division",division + thanaList1.length);
                }
                else if (division.equals("Chittagong"))
                {
                    divisionStartIndex = 42;
                    divisionEndIndex = 152;
                    setDistrictThanaList(chittagongDistricList, chittagongThanaList, autoDistrict3, autoThana3);
                }
                else if (division.equals("Dhaka"))
                {
                    divisionStartIndex = 153;
                    divisionEndIndex = 321;
                    setDistrictThanaList(dhakaDistricList, dhakaThanaList, autoDistrict3, autoThana3);
                }
                else if (division.equals("Khulna"))
                {
                    divisionStartIndex = 322;
                    divisionEndIndex = 387;
                    setDistrictThanaList(khulnaDistricList, khulnaThanaList, autoDistrict3, autoThana3);
                }
                else if (division.equals("Rajshahi"))
                {
                    divisionStartIndex = 388;
                    divisionEndIndex = 457;
                    setDistrictThanaList(rajshahiDistricList, rajshahiThanaList, autoDistrict3, autoThana3);
                }
                else if (division.equals("Rangpur"))
                {
                    divisionStartIndex = 458;
                    divisionEndIndex = 515;
                    setDistrictThanaList(rangpurDistricList, rangpurThanaList, autoDistrict3, autoThana3);
                }
                else if (division.equals("Sylhet"))
                {
                    divisionStartIndex = 516;
                    divisionEndIndex = 553;
                    setDistrictThanaList(sylthetDistricList, sylthetThanaList, autoDistrict3, autoThana3);
                }
            }
        });


        autoDistrict3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("district",autoDistrict3.getText().toString());
            }
        });

        autoThana3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("thana",autoThana3.getText().toString());
                String selection = (String) parent.getItemAtPosition(position);
                Integer pos = -1,i;

                for (i = divisionStartIndex; i <= divisionEndIndex; i++) {
                    if (thanaList[i].equals(selection)) {
                        pos = i+1;
                        break;
                    }
                }
                thanaCode3 = pos;
                Log.e("Position3", pos.toString());
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
                        break;
                    case R.id.sownerNo:
                        editownerName.setText("");
                        editownerEmail.setText("");
                        editownerPhoneNumber.setText("");
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
                    }
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorName = editdoctorName.getText().toString();
                Log.e("name",doctorName);
                doctorAge = editdoctorAge.getText().toString();
                organizationName = editorganizationName.getText().toString();
                firstPhoneNumber = editfirstPhoneNumber.getText().toString();
                secondPhoneNumber = editsecondPhoneNumber.getText().toString();
                doctorEmail = editdoctorEmail.getText().toString();
                doctorOtherdegree = editdoctorOtherdegree.getText().toString();
                ariaZipcode = editAriaZipcode.getText().toString();
                patientPerDay = editpatientPerDay.getText().toString();
                visitFees = editvisitFees.getText().toString();
                reVisitFees = editreVisitFees.getText().toString();
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
                //Integer flag = checkAllfields();
                Integer flag = 0;
                if(flag == 1 | flag == 2)
                {
                    CustomUtility.showAlert(form.this,"Please fill the fields" , "Error");
                }
                else if(flag == 3)
                {
                    CustomUtility.showAlert(form.this,"Pleas take the photos","Error");
                }
                else{
                    if (ContextCompat.checkSelfPermission(form.this, Manifest.permission.INTERNET)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Permission is not granted
                        Log.e("DXXXXXXXXXX", "Not Granted");
                        CustomUtility.showAlert(form.this, "Permission not granted", "Permission");
                    } else {
                        progressDialog = new ProgressDialog(form.this);
                        progressDialog.setTitle("Please wait...");
                        progressDialog.setMessage("Uploading");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        upload();
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
            bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri1);
            bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri2);
            bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri3);
            bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri4);
        } catch (IOException e) {
            progressDialog.dismiss();
            CustomUtility.showAlert(form.this, e.getMessage(), "Creating Bitmap at Submit");
            return;
        }
        imageString1 = CustomUtility.imageToString(bitmap1);
        imageString2 = CustomUtility.imageToString(bitmap2);
        imageString3 = CustomUtility.imageToString(bitmap3);
        imageString4 = CustomUtility.imageToString(bitmap4);
        String upLoadServerUri = "https://deenal.com/api/doctor/insert_doctor.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upLoadServerUri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            Log.e("upload response","["+response+"]");
                            jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            CustomUtility.showAlert(form.this, message, code);
                        } catch (JSONException e) {
                            CustomUtility.showAlert(form.this, e.getMessage(), "Getting Response");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    progressDialog.dismiss();
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    String code = data.getString("success");
                    String message = data.getString("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    CustomUtility.showAlert(form.this, message, "Upload failed");
                } catch (JSONException e) {
                    CustomUtility.showAlert(form.this, e.getMessage(), "Exception e");

                } catch (UnsupportedEncodingException errorr) {
                    CustomUtility.showAlert(form.this, errorr.getMessage(), "Exception error");

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Tittle", tittle);
                params.put("DoctorName", doctorName);
                params.put("Age", doctorAge);
                params.put("Sex", doctorSex);
                params.put("IsMBBS", isMbbs);
                params.put("IsFCPS", isFcps);
                params.put("IsFRCS", isFrcs);
                params.put("IsMD", isMd);
                params.put("IsDiploma", isDiploma);
                params.put("IsBcs", isBcs);
                params.put("Category", category);
                params.put("Designation", designation);
                params.put("IsPrivatePractice", privatePractice);
                params.put("AriaZipCode", ariaZipcode);
                params.put("Organization", organizationName);
                params.put("DoctorPhone01", firstPhoneNumber);
                params.put("DoctorPhone02", secondPhoneNumber);
                params.put("DoctorEmail", doctorEmail);
                params.put("PatientPerDay", patientPerDay);
                params.put("DoctorFees", visitFees);
                params.put("RevisitFees", reVisitFees);
                params.put("PharmacyOwnerType", pharmacyOwnerType);
                params.put("Cham01Address", fstchmberAdd);
                params.put("Cham01ThanId", thanaCode1.toString());
                params.put("Cham01TimeStart", fstchmberStarttime);
                params.put("Cham01TimeEnd", fstchmberEndtime);
                params.put("Cham01OffDay", fstchmberOffday);
                params.put("Cham01PremiseType", fstchmberType);
                params.put("Cham01PharmacyOwnerName", ownerName);
                params.put("Cham01PharmacyOwnerPhone01", ownerPhoneNumber);
                params.put("Cham01PharmacyOwnerMail", ownerEmail);
                params.put("Cham01HasTradeLicense", isTradelicense);
                params.put("Cham01ChmaberDimension", dimension);
                params.put("Cham01HasBrandingOpportunityInternal", isbrandingInternal);
                params.put("Cham01HasBrandingOpportunityExternal", isbrandingExternal);
                params.put("Cham01HasShopFascia", isfascia);
                params.put("Cham01HasDoctorSignboard", isSignboard);
                params.put("Cham02Address", scndchmberAdd);
                params.put("Cham02ThanId", thanaCode2.toString());
                params.put("Cham02TimeStart", scndchmberStarttime);
                params.put("Cham02TimeEnd", scndchmberEndtime);
                params.put("Cham02OffDay", scndchmberOffday);
                params.put("Cham02PremiseType", scndchmberType);
                params.put("Cham03Address", thirdchmberAdd);
                params.put("Cham03ThanId", thanaCode3.toString());
                params.put("Cham03TimeStart", thirdchmberStarttime);
                params.put("Cham03TimeEnd", thirdchmberEndtime);
                params.put("Cham03OffDay", thirdchmberOffday);
                params.put("Cham03PremiseType", thirdchmberType);
                params.put("IsAgreedToCommuication", isCommsagreed);



                params.put("LatValue", presentLat);
                params.put("LonValue", presentLon);
                params.put("GeoAccuracy", presentAcc);

                params.put("image_name_premise", photoName1);
                params.put("image_premise", imageString1);
                params.put("image_name_doctorcard", photoName2);
                params.put("image_doctorcard", imageString2);
                params.put("image_name_signboard", photoName3);
                params.put("image_signboard", imageString3);
                params.put("image_name_chamber", photoName4);
                params.put("image_chamber", imageString4);
                return params;
            }
        };

        MySingleton.getInstance(form.this).addToRequestQue(stringRequest);
    }


    public Integer checkAllfields()
    {
        //Log.e("name",doctorName);
        if( (doctorName.equals("") | doctorAge.equals("") | doctorSex.equals("") | isBcs.equals("") | designation.equals("") |
                category.equals("") | privatePractice.equals("") | tittle.equals("") | ariaZipcode.equals("") | organizationName.equals("")| firstPhoneNumber.equals("") | doctorEmail.equals("") | patientPerDay.equals("")
                | visitFees.equals("") | reVisitFees.equals("") | ownerName.equals("") | ownerPhoneNumber.equals("") | ownerEmail.equals("") | isTradelicense.equals("") | isCommsagreed.equals("") | dimension.equals("")| isbrandingInternal.equals("") |
                isbrandingExternal.equals("") | isfascia.equals("")| isSignboard.equals("") | (isMbbs.equals("") & isFcps.equals("") & isMd.equals("") & isFrcs.equals("") & isDiploma.equals("") & doctorOtherdegree.equals(""))) )
        {
            return 1;
        }
        else if((thanaCode1 == -1 | fstchmberType.equals("")))
        {
            return 2;
        }
        else if(premisePhotoPath.equals("") | cardPhotPath.equals("") | signboardPhotPath.equals(""))
        {
            return 3;
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


    public void setDistrictThanaList(String[] districtlst, String[] thanalst, AutoCompleteTextView autodivision,AutoCompleteTextView autothana)
    {
        ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, districtlst);
        autodivision.setThreshold(1);
        autodivision.setAdapter(adapter8);

        ArrayAdapter<String> adapter9 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, thanalst);
        autothana.setThreshold(1);
        autothana.setAdapter(adapter9);
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
            Toast.makeText(getApplicationContext(), "Status Changed", Toast.LENGTH_SHORT).show();
        }
    }


}
