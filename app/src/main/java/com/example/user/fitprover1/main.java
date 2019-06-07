package com.example.user.fitprover1;

/**
 * Created by user on 16/11/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class main extends Activity {
    Button scan1, recommand, addcart,cartlist;
    Spinner color, size;
    ImageView cloth;
    TextView cart,clothn,clothmat,clothcat,clothcount,clothstyle,clothprice,clothloc;

    final Activity activity = this;
    cloth list = new cloth( "qrid","name","colour1", "colour2","colour3","colour4","size1",
            "size2","size3","size4","size5","size6","url1","url2","url3","url4","style",
            "category","material","country","price","location");

    ArrayList<cloth> scancloth = new ArrayList<>();
    ArrayList<cloth> selectedcloth = new ArrayList<>();
    Set<String> cartcloth = new HashSet<String>();

    String cartcolor,cartsize,cid,cname,ccategory,cmaterial,cstyle,ccount,cprice,clocation;
    DatabaseReference md,record;


protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Bundle bundle = getIntent().getExtras();

    //receive cloth after scan from firebase
    final ArrayList<cloth> myList = (ArrayList<cloth>) getIntent().getSerializableExtra("mylist");
    final ArrayList<cloth> forrecommended = (ArrayList<cloth>) getIntent().getSerializableExtra("alllist");
    //variables link to the layout
    clothn = (TextView) findViewById(R.id.CLOTHNAME);
    clothcat = (TextView) findViewById(R.id.CLOTHCAT);
    clothcount = (TextView) findViewById(R.id.CLOTHCOUNT);
    clothmat = (TextView) findViewById(R.id.CLOTHMAT);
    clothstyle = (TextView) findViewById(R.id.STYLE);
    clothprice = (TextView) findViewById(R.id.price);
    clothloc = (TextView) findViewById(R.id.location);
    cloth = (ImageView) findViewById(R.id.CLOTHIMAGE);
    scan1 = (Button) findViewById(R.id.SCAN1);
    recommand = (Button) findViewById(R.id.RECOMMAND);
    addcart = (Button) findViewById(R.id.ADDCART);
    cartlist = (Button) findViewById(R.id.CART);
    color = (Spinner) findViewById(R.id.COLOR);
    size = (Spinner) findViewById(R.id.SIZE);

    //STORE SCANNED CLOTHING ID INTO VARIABLE
    cid = myList.get(0).returnClothid();
    //STORE SCANNED CLOTHING NAME INTO VARIABLE
    cname = myList.get(0).returnClothname();
    //STORE SCANNED CLOTHING MATERIAL INTO VARIABLE
    cmaterial = myList.get(0).returnClothmaterial();
    //STORE SCANNED CLOTHING STYLE VARIABLE
    cstyle = myList.get(0).returnClothstyle();
    //STORE SCANNED CLOTHING CATEGORY INTO VARIABLE
    ccategory = myList.get(0).returnClothcategory();
    //STORE SCANNED CLOTHING COUNTRY VARIABLE
    ccount = myList.get(0).returnClothcountry();
    //STORE SCANNED CLOTHING PRICE VARIABLE
    cprice = myList.get(0).returnClothprice();
    //STORE SCANNED CLOTHING LOCATION VARIABLE
    clocation =myList.get(0).returnClothlocation();

    //DISPLAY CLOTHING NAME
    clothn.setText(cname);
    //DISPLAY CLOTHING MATERIAL
    clothmat.setText(cmaterial);
    //DISPLAY CLOTHING CATEGORY
    clothcat.setText(ccategory);
    //DISPLAY CLOTHING COUNTRY
    clothcount.setText(ccount);
    //DISPLAY CLOTHING STYLE
    clothstyle.setText(cstyle);
    //DISPLAY CLOTHING PRICE
    clothprice.setText(cprice);
    //DISPLAY CLOTHING LOCATION
    clothloc.setText(clocation);
    //DISPLAY CLOTHING IMAGE
    Glide.with(getApplicationContext()).load(myList.get(0).returnClothurl1()).into(cloth);

    // ARRAYLIST TO SHOW CLOTHING SIZE
    final List<String> sizelist = new ArrayList<String>();
    // ADD CLOTHING SIZE INTO SPINNER
    if (!(Objects.equals(myList.get(0).returnClothsize1(),""))){
        sizelist.add(myList.get(0).returnClothsize1());
    }
    if (!(Objects.equals(myList.get(0).returnClothsize2(),""))){
        sizelist.add(myList.get(0).returnClothsize2());
    }
    if (!(Objects.equals(myList.get(0).returnClothsize3(),""))){
        sizelist.add(myList.get(0).returnClothsize3());
    }
    if (!(Objects.equals(myList.get(0).returnClothsize4(),""))){
        sizelist.add(myList.get(0).returnClothsize4());
    }
    if (!(Objects.equals(myList.get(0).returnClothsize5(),""))){
        sizelist.add(myList.get(0).returnClothsize5());
    }
    if (!(Objects.equals(myList.get(0).returnClothsize6(),""))){
        sizelist.add(myList.get(0).returnClothsize6());
    }
     //DISPLAY CLOTHING SIZE
    //This code set the arraylist<String> sizelist to the spinner
    final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sizelist);
    //this code set the style of the spinner which is dropdown style.
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   // this code set the dataAdapter to the spinner.
    size.setAdapter(dataAdapter);

    size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            for (int j =0;j<sizelist.size(); j++)
            if (size.getSelectedItem() == sizelist.get(j)) {
                cartsize = sizelist.get(j);

    }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });


    //DECLARE AN ARRAYLIST FOR CLOTHING COLOUR
    final List<String> colorlist = new ArrayList<String>();
    //DECLARE AN ARRAYLIST FOR CLOTHING IMAGE
    final List<String> url = new ArrayList<String>();
    if(!(Objects.equals(myList.get(0).returnClothcolor1(),""))) {
        colorlist.add(myList.get(0).returnClothcolor1());
        url.add(myList.get(0).returnClothurl1());
    }
    //GET THE CLOTHING COLOURS AND IMAGES OF THE CLOTHING
    if(!(Objects.equals(myList.get(0).returnClothcolor2(), ""))){
         colorlist.add(myList.get(0).returnClothcolor2());
         url.add(myList.get(0).returnClothurl2());
    }
    if(!(Objects.equals(myList.get(0).returnClothcolor3(), ""))){
        colorlist.add(myList.get(0).returnClothcolor3());
        url.add(myList.get(0).returnClothurl3());
    }
    if(!Objects.equals(myList.get(0).returnClothcolor4(), "")){
        colorlist.add(myList.get(0).returnClothcolor4());
        url.add(myList.get(0).returnClothurl4());}

    //INSERT THE COLOURS INTO THE SPINNER
    //This code set the arraylist<String> colourlist to the spinner
    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colorlist);
    // this code set the style of the spinner which is dropdown style.
    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    //this code set the dataAdapter2 to the spinner.
    color.setAdapter(dataAdapter2);
    color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
           for(int m =0 ;m<colorlist.size();m++){
               if(color.getSelectedItem() == colorlist.get(m)){
                  //DISPLAY THE IMAGE
                   Glide.with(getApplicationContext()).load(url.get(m)).into(cloth);
                   cartcolor = colorlist.get(0);
               }
           }

       }

       @Override
       public void onNothingSelected(AdapterView<?> adapterView) {
       }
   });

    scan1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IntentIntegrator integrator = new IntentIntegrator(activity);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }
    });


    recommand.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intentSystem=new Intent(getApplicationContext(),recommand.class);
            intentSystem.putParcelableArrayListExtra("scancloth",myList);
            intentSystem.putParcelableArrayListExtra("recommendlist",forrecommended);
            startActivity(intentSystem);
        }
    });

    // ADD THE SELECTED CLOTHING TO CLOTHING LIST
    addcart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();
            //SET THE WANTED CLOTHING NAME,ID,COLOUR,SIZE AND LOCATION INTO ONE STRING
            String data = "Name :"+cname +"   ID :"+cid +"   Colour :"+cartcolor +"   Size :"+cartsize+"   Location:"+clocation;
            // ADD THE DATA INTO SET CARTCLOTH WITH STRING TYPE
            cartcloth.add(data);
            //PASS THE SET TO THE CLOTHING LIST INTERFACE
            editor.putStringSet("cart",cartcloth);
            editor.apply();


        }
    });

    cartlist.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // create new intent
            Intent cart = new Intent(getApplicationContext(),cart.class);
            // go to the clothing list interface
            startActivity(cart);
        }
    });

}
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data){
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You canceled the scanning", Toast.LENGTH_LONG).show();
            }
            else {

                Toast.makeText(this, "Scanned Successfully", Toast.LENGTH_SHORT).show();
                md = FirebaseDatabase.getInstance().getReference("cloth");
                md.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                                list = dataSnapshot1.getValue(cloth.class);
                                scancloth.add(list);

                            }

                        }
                        for (int i = 0;i<scancloth.size();i++) {
                            if (Objects.equals(scancloth.get(i).returnClothid(), result.getContents().toString())) {
                                selectedcloth.add(scancloth.get(i));
                                record = FirebaseDatabase.getInstance().getReference("Scanned Clothing");
                                String name = selectedcloth.get(0).returnClothname();
                                String id = selectedcloth.get(0).returnClothid();
                                String colour = selectedcloth.get(0).returnClothcolor1();
                                String size = selectedcloth.get(0).returnClothsize1();
                                String location = selectedcloth.get(0).returnClothlocation();
                                HashMap<String,String> data =new HashMap<String,String>();
                                data.put("name",name);
                                data.put("id",id);
                                data.put("colour",colour);
                                data.put("size",size);
                                data.put("location",location);
                                record.push().setValue(data);

                                Intent intentSystem = new Intent(getApplicationContext(), main.class);
                                intentSystem.putParcelableArrayListExtra("mylist", selectedcloth);
                                intentSystem.putParcelableArrayListExtra("alllist",scancloth);
                                startActivity(intentSystem);

                            }
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }

          else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
