package com.example.maricosurvey;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class check extends AppCompatActivity {

    String[] divisionList = new String[] {"Barisal", "Chittagong", "Dhaka", "Khulna", "Rajshahi", "Rangpur", "Sylhet"};
    String division = "";
    String[] districtList = new String[]{"Barguna", "Barisal", "Bhola", "Jhalokati", "Patuakhali", "Pirojpur", "Bandarban", "Brahmanbaria", "Chandpur", "Chittagong", "Comilla", "Cox's Bazar",
            "Feni", "Khagrachhari", "Lakshmipur", "Noakhali", "Rangamati", "Dhaka", "Faridpur", "Gazipur", "Gopalganj", "Jamalpur", "Kishoregonj", "Madaripur",
            "Manikganj", "Munshiganj", "Mymensingh", "Narayanganj", "Narsingdi", "Netrakona", "Rajbari", "Shariatpur", "Sherpur", "Tangail", "Bagerhat", "Chuadanga", "Jessore", "Jhenaidah", "Khulna",
            "Kushtia", "Magura", "Meherpur", "Narail", "Satkhira", "Bogra", "Joypurhat", "Naogaon", "Natore", "Chapai", "Nababganj", "Pabna", "Rajshahi", "Sirajganj", "Dinajpur", "Gaibandha", "Kurigram",
            "Lalmonirhat", "Nilphamari", "Zila", "Panchagarh", "Rangpur", "Thakurgaon", "Habiganj", "Maulvibazar", "Sunamganj", "Sylhet"};


    String[] thanaList = new String[] {"Amtali", "Bamna", "Barguna Sadar", "Betagi", "Patharghata", "Taltali", "Agailjhara", "Babuganj", "Bakerganj", "Banari Para", "Gaurnadi", "Hizla", "Barisal Sadar Kotwali", "Mhendiganj",
            "Muladi", "Wazirpur", "Bhola", "Burhanuddin", "Char", "Fasson", "Daulat", "Khan", "Lalmohan", "Manpura", "Tazumuddin", "Jhalokati", "Kanthalia", "Nalchity", "Rajapur",
            "Bauphal", "Dashmina", "Dumki", "Galachipa", "Kalapara", "Mirzaganj", "Patuakhali Sadar", "Rangabali", "Bhandaria", "Kawkhali", "Mathbaria", "Nazirpur", "Pirojpur Sadar", "Nesarabad (Swarupkati)", "Zianagar",
            "Alikadam", "Bandarban Sadar", "Lama", "Naikhongchhari", "Rowangchhari", "Ruma", "Thanchi", "Akhaura", "Banchharampur", "Bijoynagar", "Brahmanbaria Sadar", "Ashuganj", "Kasba", "Nabinagar", "Nasirnagar",
            "Sarail", "Chandpur Sadar", "Faridganj", "Haim", "Hajiganj", "Kachua", "Matlab Dakshi", "Matlab Uttar", "Shahrasti", "Anowara", "Bayejid Bostami", "Banshkhali", "Bakalia", "Boalkhali", "Chandanaish",
            "Chandgaon", "Chittagong Port", "Double Mooring", "Fatikchhari", "Halishahar", "Hathazari", "Kotwali", "Khulshi", "Lohagara", "Mirsharai", "Pahartali", "Panchlaish", "Patiya", "Patenga",
            "Rangunia", "Raozan", "Sandwip", "Satkania", "Sitakunda", "Barura", "Brahman Para", "Burichang", "Chandina", "Chauddagram", "Comilla Sadar Dakshin", "Daudkandi", "Debidwar", "Homna", "Comilla Adarsha Sadar", "Laksam", "Manoharganj",
            "Meghna", "Muradnagar", "Nangalkot", "Titas", "Chakaria", "Cox's Bazar Sadar", "Kutubdia", "Maheshkhali", "Pekua", "Ramu", "Teknaf", "Ukhia", "Chhagalnaiya", "Daganbhuiyan", "Feni", "Fulgazi", "Parshuram",
            "Sonagazi", "Dighinala", "Khagrachhari Sadar", "Lakshmichhari", "Mahalchhari", "Manikchhari", "Matiranga", "Panchhari", "Ramgarh", "Kamalnagar", "Lakshmipur Sadar", "Roypur", "Ramganj", "Ramgati", "Begumganj",
            "Chatkhil", "Companiganj", "Hatiya", "Kabirhat", "Senbagh", "Sonaimuri", "Subarnachar", "Noakhali Sadar", "Baghaichhari", "Barkal Upazila", "Kawkhali (Betbunia)", "Belai Chhari Upazi", "Kaptai Upazila", "Jurai Chhari Upazila",
            "Langadu Upazila", "Naniarchar Upazila", "Rajasthali Upazila", "Rangamati Sadar Upazila", "Adabor", "Badda", "Bangshal", "Biman Bandar", "Banani", "Cantonment", "Chak Bazar", "Dakshinkhan", "Darus Salam", "Demra", "Dhamrai",
            "Dhanmondi", "Dohar", "Bhasan Tek", "Bhatara", "Gendaria", "Gulshan", "Hazaribagh", "Jatrabari", "Kafrul", "Kadamtali", "Kalabagan", "Kamrangir Char", "Khilgaon", "Khilkhet", "Keraniganj", "Lalbagh",
            "Mirpur", "Mohammadpur", "Motijheel", "Mugda Para", "Nawabganj", "New Market", "Pallabi", "Paltan", "Ramna", "Rampura", "Sabujbagh", "Rupnagar", "Savar", "Shahjahanpur", "Shah Ali", "Shahbagh",
            "Shyampur", "Sher-e-bangla Nagar", "Sutrapur", "Tejgaon Ind.Area", "Turag", "Uttara Paschim", "Uttara Purba","Uttar Khan", "Wari", "Alfadanga", "Bhanga", "Boalmari", "Char Bhadrasan", "Faridpur Sadar", "Madhukhali",
            "Nagarkanda", "Sadarpur", "Saltha", "Gazipur Sadar", "Kaliakair", "Kaliganj", "Kapasia", "Sreepur", "Gopalganj Sadar", "Kashiani", "Kotalipara", "Muksudpur", "Tungipara", "Bakshiganj", "Dewanganj", "Islampur",
            "Jamalpur Sadar", "Madarganj", "Melandaha", "Sarishabari Upazila", "Austagram", "Bajitpur", "Bhairab", "Hossainpur", "Itna", "Karimganj", "Katiadi", "Kishoreganj Sadar", "Kuliar", "Mithamain", "Nikli", "Pakundia",
            "Tarail", "Kalkini", "Madaripur Sadar", "Rajoir", "Shibchar", "Daulatpur", "Ghior", "Harirampur", "Manikganj Sadar", "Saturia", "Shibalaya", "Singair", "Gazaria", "Lohajang", "Munshiganj Sadar", "Serajdikhan",
            "Sreenagar", "Tongibari", "Bhaluka", "Dhobaura", "Fulbaria", "Gaffargaon", "Gauripur", "Haluaghat", "Ishwarganj", "Mymensingh Sadar", "Muktagachha", "Nandail", "Phulpur", "Trishal", "Araihazar", "Sonargaon",
            "Narayanganj Sadar", "Rupganj", "Belabo", "Manohardi", "Narsingdi Sadar", "Palash", "Roypura", "Shibpur", "Atpara", "Barhatta", "Durgapur", "Khaliajuri", "Kalmakanda", "Kendua", "Madan", "Mohanganj", "Netrokona Sadar",
            "Purbadhala", "Baliakandi", "Goalanda", "Kalukhali", "Pangsha", "Rajbari Sadar", "Bhedarganj", "Damudya", "Gosairhat", "Naria", "Shariatpur Sadar", "Zanjira", "Jhenaigati", "Nakla", "Nalitabari", "Sherpur Sadar",
            "Sreebardi", "Basail", "Bhuapur", "Delduar", "Dhanbari", "Ghatail", "Gopalpur", "Kalihati", "Madhupur", "Mirzapur", "Nagarpur", "Sakhipur", "Tangail Sadar", "Bagerhat Sadar", "Chitalmari", "Fakirhat", "Mollahat",
            "Mongla", "Morrelganj", "Rampal", "Sarankhola", "Alamdanga", "Chuadanga Sadar", "Damurhuda", "Jiban", "Abhaynagar", "Bagher", "Chaugachha", "Jhikargachha", "Keshabpur", "Jessore", "Manirampur", "Sharsha",
            "Harinakunda", "Jhenaidah Sadar", "Kotchandpur", "Maheshpur", "Shailkupa", "Batiaghata", "Dacope", "Dumuria", "Dighalia", "Khalishpur", "Khan Jahan Ali", "Khulna Sadar", "Koyra", "Paikgachha", "Phultala", "Rupsa",
            "Sonadanga", "Terokhada", "Bheramara", "Khoksa", "Kumarkhali", "Kushtia Sadar", "Magura Sadar", "Shalikha", "Gangni", "Mujib Nagar", "Meherpur Sadar", "Kalia", "Narail Sadar", "Assasuni", "Debhata", "Kalaroa", "Satkhira Sadar",
            "Shyamnagar", "Tala", "Adamdighi", "Bogra Sadar", "Dhunat", "Dhupchanchia", "Gabtali", "Kahaloo", "Nandigram", "Sariakandi", "Shajahanpur", "Shibganj", "Sonatola", "Akkelpur", "Joypurhat Sadar", "Kalai",
            "Khetlal", "Panchbibi", "Atrai", "Badalgachhi", "Dhamoirhat", "Manda", "Mahadebpur", "Naogaon Sadar", "Niamatpur", "Patnitala", "Porsha", "Raninagar", "Sapahar", "Bagatipara", "Baraigram", "Gurudaspur",
            "Lalpur", "Natore Sadar", "Singra", "Bholahat", "Gomastapur", "Nachole", "Chapai Nababganj Sadar", "Atgharia", "Bera", "Bhangura", "Chatmohar", "Ishwardi", "Pabna", "Santhia", "Sujanagar", "Bagha", "Baghmara",
            "Boalia", "Charghat", "Godagari", "Matihar", "Mohanpur", "Paba", "Puthia", "Rajpara", "Shah Makhdum", "Tanore", "Belkuchi", "Chauhali", "Kamarkhanda", "Kazipur", "Royganj", "Shahjadpur", "Sirajganj Sadar",
            "Tarash", "Ullah Para", "Birampur", "Birganj", "Biral", "Bochaganj", "Chirirbandar", "Fulbari", "Ghoraghat", "Hakimpur", "Kaharole", "Khansama", "Dinajpur Sadar", "Parbatipur", "Fulchhari", "Gaibandha Sadar",
            "Gobindaganj", "Palashbari", "Sadullapur", "Saghata", "Sundarganj", "Bhurungamari", "Char Rajibpur", "Chilmari", "Phulbari", "Kurigram Sadar", "Nageshwari", "Rajarhat", "Raumari", "Ulipur", "Aditmari", "Hatibandha",
            "Lalmonirhat Sadar", "Patgram", "Dimla Upazila", "Domar Upazila", "Jaldhaka Upazila","Kishorganj Upazila", "Nilphamari Upazila", "Nilphamari Sadar Upazila", "Saidpur Upazila", "Atwari", "Boda", "Debiganj", "Panchagarh Sadar", "Tentulia", "Badarganj", "Gangachara", "Kaunia", "Rangpur", "Mitha",
            "Pukur", "Pirgachha", "Pirganj", "Taraganj", "Baliadangi", "Haripur", "Ranisankail", "Thakurgaon Sadar", "Ajmiriganj", "Bahubal", "Baniachong", "Chunarughat", "Habiganj Sadar", "Lakhai", "Madhabpur", "Nabiganj",
            "Barlekha", "Juri", "Kamalganj", "Kulaura", "Maulvibazar Sadar", "Rajnagar", "Sreemangal", "Bishwambarpur", "Chhatak", " Dakshin Sunamganj", "Derai", "Dharampasha", "Dowarabazar", "Jagannathpur", "Jamalganj", "Sulla", "Sunamganj Sadar",
            "Tahirpur", "Balaganj", "Beani Bazar", "Bishwanath", "Dakshin Surma", "Fenchuganj", "Golapganj", "Gowainghat", "Jaintiapur", "Kanaighat", "Sylhet Sadar", "Zakiganj", };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        Integer a = thanaList.length;
        Log.e("thana size","list"+a.toString());

        final AutoCompleteTextView autoDivision;
        final AutoCompleteTextView autoDistrict;


        autoDivision = findViewById(R.id.divisionList);
        autoDistrict = findViewById(R.id.districtList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, divisionList);
        autoDivision.setThreshold(1); //will start working from first character
        autoDivision.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, districtList);
        autoDistrict.setThreshold(1);
        autoDistrict.setAdapter(adapter1);

        autoDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                division = autoDivision.getOnItemSelectedListener().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
