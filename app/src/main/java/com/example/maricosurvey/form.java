package com.example.maricosurvey;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

public class form extends AppCompatActivity {

    public LocationManager locationManager;
    public GPSLocationListener listener;
    public Location previousBestLocation = null;
    private static final int TWO_MINUTES = 1000 * 60 * 1;
    public static final String BROADCAST_ACTION = "gps_data";
    Intent intent;


    String division, district, thana;
    Integer divisionStartIndex, divisionEndIndex;

    public static String presentLat = "", presentLon = "", presentAcc = "";


    String[] sDropList = new String[] {"Male", "Female"};
    String[] bcsDropList = new String[] {"N/A", "Yes", "No"};
    String[] chamberTypesList = new String[] {"Pharmacy", "Hospital", "Clinic", "Stand Alone"};
    String[] offdayList = new  String[] {"Not applicable", "Saturday","Sunday","Monday","Tuesday","Wednesday", "Thursday","Friday"};
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
            "Sreebardi", "Basail", "Bhuapur", "Delduar", "Dhanbari", "Ghatail", "Gopalpur", "Kalihati", "Madhupur", "Mirzapur", "Nagarpur", "Sakhipur", "Tangail Sadar", "Bagerhat Sadar", "Chitalmari", "Fakirhat", "Kachua", "Mollahat",
            "Mongla", "Morrelganj", "Rampal", "Sarankhola", "Alamdanga", "Chuadanga Sadar", "Damurhuda", "Jiban Nagar", "Abhaynagar", "Bagher Para", "Chaugachha", "Jhikargachha", "Keshabpur", "Jessore Sadar", "Manirampur", "Sharsha",
            "Harinakunda", "Jhenaidah Sadar", "Kaliganj", "Kotchandpur", "Maheshpur", "Shailkupa", "Batiaghata", "Dacope", "Daulatpur", "Dumuria", "Dighalia", "Khalishpur", "Khan Jahan Ali", "Khulna Sadar", "Koyra", "Paikgachha", "Phultala", "Rupsa",
            "Sonadanga", "Terokhada", "Bheramara", "Dualatpur", "Khoksa", "Kumarkhali", "Kushtia Sadar", "Mirpur", "Magura Sadar", "Mohammadpur", "Shalikha", "Sreepur", "Gangni", "Mujib Nagar", "Meherpur Sadar", "Kalia", "Lohagara", "Narail Sadar",
            "Assasuni", "Debhata", "Kalaroa", "Kaliganj", "Satkhira Sadar", "Shyamnagar", "Tala", "Adamdighi", "Bogra Sadar", "Dhunat", "Dhupchanchia", "Gabtali", "Kahaloo", "Nandigram", "Sariakandi", "Shajahanpur", "Sherpur", "Shibganj",
            "Sonatola", "Akkelpur", "Joypurhat Sadar", "Kalai", "Khetlal", "Panchbibi", "Atrai", "Badalgachhi", "Dhamoirhat", "Manda", "Mahadebpur", "Naogaon Sadar", "Niamatpur", "Patnitala", "Porsha", "Raninagar", "Sapahar", "Bagatipara",
            "Baraigram", "Gurudaspur", "Lalpur", "Natore Sadar", "Singra", "Bholahat", "Gomastapur", "Nachole", "Chapai Nababganj Sadar", "Shibgonj", "Atgharia", "Bera", "Bhangura", "Chatmohar", "Faridpur", "Ishwardi", "Pabna Sadar", "Santhia",
            "Sujanagar", "Bagha", "Baghmara", "Boalia", "Charghat", "Durgapur", "Godagari", "Matihar", "Mohanpur", "Paba", "Puthia", "Rajpara", "Shah Makhdum", "Tanore", "Belkuchi", "Chauhali", "Kamarkhanda", "Kazipur", "Royganj", "Shahjadpur",
            "Sirajganj Sadar", "Tarash", "Ullah Para", "Birampur", "Birganj", "Biral", "Bochaganj", "Chirirbandar", "Fulbari", "Ghoraghat", "Hakimpur", "Kaharole", "Khansama", "Dinajpur Sadar", "Nawabganj", "Parbatipur", "Fulchhari", "Gaibandha Sadar",
            "Gobindaganj", "Palashbari", "Sadullapur", "Saghata", "Sundarganj", "Bhurungamari", "Char Rajibpur", "Chilmari", "Phulbari", "Kurigram Sadar", "Nageshwari", "Rajarhat", "Raumari", "Ulipur", "Aditmari", "Hatibandha",
            "Kaliganj", "Lalmonirhat Sadar", "Patgram", "Dimla Upazila", "Domar Upazila", "Jaldhaka Upazila","Kishorganj Upazila", "Nilphamari Sadar Upazila", "Saidpur Upazila", "Atwari", "Boda", "Debiganj", "Panchagarh Sadar", "Tentulia", "Badarganj",
            "Gangachara", "Kaunia", "Rangpur Sadar", "Mitha Pukur", "Pirgachha", "Pirganj", "Taraganj", "Baliadangi", "Haripur", "Pirganj", "Ranisankail", "Thakurgaon Sadar",  "Ajmiriganj", "Bahubal", "Baniachong", "Chunarughat", "Habiganj Sadar", "Lakhai",
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

    EditText doctorName, doctorAge, organizationName, firstPhoneNumber, secondPhoneNumber, doctorEmail, patientPerDay, visitFees, reVisitFees, ownerName, ownerPhoneNumber, ownerEmail, dimension;
    TextView location;
    AutoCompleteTextView autoDivision1, autoDistrict1, autoThana1, autoDivision2, autoDistrict2, autoThana2, autoDivision3, autoDistrict3, autoThana3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


        intent = new Intent(BROADCAST_ACTION);

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


        //all radio group
        RadioGroup chamberGroup = findViewById(R.id.chamberNo);
        RadioGroup pharmacyOwnerGroup = findViewById(R.id.sowner);
        RadioButton rb;

        //all the spinner lists
        Spinner sdropdown = findViewById(R.id.sdropdown);
        Spinner fchamberTypesdropdown = findViewById(R.id.firstchmbertype);
        Spinner fchamberOffdaydropdown = findViewById(R.id.firstoffday);
        Spinner schamberTypesdropdown = findViewById(R.id.secondchmbertype);
        Spinner schamberOffdaydropdown = findViewById(R.id.secondoffday);
        Spinner tchamberTypesdropdown = findViewById(R.id.thirdchmbertype);
        Spinner tchamberOffdaydropdown = findViewById(R.id.thirdoffday);

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

        location = findViewById(R.id.gps);

        //all spinner adapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sDropList);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bcsDropList);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, chamberTypesList);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, offdayList);

        //spinner dropdown
        sdropdown.setAdapter(adapter1);
        fchamberTypesdropdown.setAdapter(adapter4);
        fchamberOffdaydropdown.setAdapter(adapter5);
        schamberTypesdropdown.setAdapter(adapter4);
        schamberOffdaydropdown.setAdapter(adapter5);
        tchamberTypesdropdown.setAdapter(adapter4);
        tchamberOffdaydropdown.setAdapter(adapter5);


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
        GPS_Start();




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
                Log.e("division",autoDistrict1.getText().toString());
            }
        });

        autoThana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("division",autoThana1.getText().toString());
            }
        });


        autoDivision2.setThreshold(1); //will start working from first character
        autoDivision2.setAdapter(adapter7);

        autoDivision2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                division = autoDivision1.getText().toString();
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
                Log.e("division",autoDistrict2.getText().toString());
            }
        });

        autoThana2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("division",autoThana2.getText().toString());
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


        autoDistrict1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("division",autoDistrict1.getText().toString());
            }
        });

        autoThana1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("division",autoThana1.getText().toString());
            }
        });

        pharmacyOwnerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.sownerYes:
                        ownerName.setText(doctorName.getText().toString());
                        ownerEmail.setText(doctorEmail.getText().toString());
                        ownerPhoneNumber.setText(firstPhoneNumber.getText());
                        break;
                    case R.id.sownerNo:
                        ownerName.setText("");
                        ownerEmail.setText("");
                        ownerPhoneNumber.setText("");
                        break;
                }
            }
        });
















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
