package com.example.user.fitprover1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class QRscan extends Activity {

    private Button scans;
    DatabaseReference md,record;
    cloth item = new cloth( "qrid","name","colour1", "colour2","colour3","colour4","size1",
              "size2","size3","size4","size5","size6","url1","url2","url3","url4","style",
            "category","material","country","price","location");

    ArrayList<cloth> clothList = new ArrayList<cloth>();
    ArrayList<cloth> selectedcloth = new ArrayList<cloth>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscan);
        final Activity activity = this;

        scans = (Button) findViewById(R.id.scan);
        scans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CODE TO RUN THE QR SCANNER
                //This code declare the new intentintegrator.
                IntentIntegrator integrator = new IntentIntegrator(activity);
                //This code allow the scanner can only scan the code with QR type only.
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                //This code set a text “scan” on the scanners screen when the scan button is press.
                integrator.setPrompt("Scan");
               // This code enable the camera on the android device to function to scan the QR code.
                integrator.setCameraId(0);
                //This code enable the camera to focus on the three dots on the QR code.
                integrator.setBeepEnabled(false);
               // This code enable the camera to recognise the QR code.
                integrator.setBarcodeImageEnabled(false);
               // This code start the camera to scan.
                integrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if(result.getContents() == null){
                Toast.makeText(this,"You canceled the scanning", Toast.LENGTH_LONG).show();
            }
            else{


                Toast.makeText(this,"Scan Successfully", Toast.LENGTH_SHORT).show();
                //READ FIREBASE AND STORE CLOTHING OBJECT
                md = FirebaseDatabase.getInstance().getReference("cloth");
                //This code shows the variable link to the “cloth” inside the firebase database.
                md.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()) {
                           // This code show that the system will continue search and store clothing information
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                                item = dataSnapshot1.getValue(cloth.class);
                                //STORE ALL CLOTHING INTO AN ARRAYLIST
                                clothList.add(item);

                            }
                        }
                        for (int i = 0;i<clothList.size();i++) {
                            //compare QR Code with the Clothing ID
                            if (clothList.get(i).returnClothid().toString().trim().equals(result.getContents().toString().trim())) {
                              //PAST THE CLOTHING INFORMATION TO THE SCANNED CLOTHING INTERFACE
                               selectedcloth.add(clothList.get(i));
                              //RECORD THE SCANNED CLOTHING
                               // this code creates a variable which connect to the “Scanned Clothing” in the firebase database.
                                record = FirebaseDatabase.getInstance().getReference("Scanned Clothing");
                               // This code create a variable to store the clothing name.
                                String name = selectedcloth.get(0).returnClothname();
                               // This code create a variable to store the clothing id.
                                String id = selectedcloth.get(0).returnClothid();
                               // This code creates a variable to store the clothing colour.
                                String colour = selectedcloth.get(0).returnClothcolor1();
                                //This code creates a variable to store the clothing size.
                                String size = selectedcloth.get(0).returnClothsize1();
                               // This code creates a variable to store the clothing location.
                                String location = selectedcloth.get(0).returnClothlocation();
                               // This code creates a HashMap to store the clothing all the clothing information.
                                HashMap<String,String> data =new HashMap<String,String>();
                               // This code put the clothing name value into the hashmap.
                                data.put("name",name);
                               // This code put the clothing id value into the hashmap.
                                data.put("id",id);
                               // This code put the clothing colour value into the hashmap.
                                data.put("colour",colour);
                               // This code put the clothing size value into the hashmap.
                                data.put("size",size);
                                //This code put the clothing location value into the hashmap.
                                data.put("location",location);
                                //This code store the scanned clothing to the firebase database.
                                record.push().setValue(data);

                               // This code generates new intent.
                                Intent intentSystem = new Intent(getApplicationContext(), main.class);
                                //This code passes the scanned clothing information to the scanned clothing information interface.
                                intentSystem.putParcelableArrayListExtra("mylist",selectedcloth);
                               // This code passes all the clothing information to the scanned clothing information interface.
                                intentSystem.putParcelableArrayListExtra("alllist",clothList);
                               // This code starts the scanned clothing information interface.
                                startActivity(intentSystem);

                           }


                        }
                    }
                    // }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
            }

        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

