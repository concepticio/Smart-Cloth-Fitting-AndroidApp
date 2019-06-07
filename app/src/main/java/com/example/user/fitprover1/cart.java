package com.example.user.fitprover1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Created by user on 27/11/2017.
 */

public class cart extends Activity {

    Button back,cancel,notify;
    ListView simpleList;
    DatabaseReference sendrequest;
    ArrayList<String> listview = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // set<String> that get the clothing list from the scanned clothing interface
         final Set<String> clothinglist  = prefs.getStringSet("cart", new HashSet<String>());
        // set<String> that get the clothing list from the recommended clothing interface
         final Set<String> clothinglist2  = prefs.getStringSet("recommend", new HashSet<String>());


        // the two if statements are use to combine the two clothing list into one
        if(clothinglist != null){

            Iterator<String> iterator = clothinglist.iterator();

            while(iterator.hasNext()) {
                String name = iterator.next();

                listview.add(name);
            }

        }

        if(clothinglist2 != null){

            Iterator<String> iterator2 = clothinglist2.iterator();

            while(iterator2.hasNext()) {
                String name2 = iterator2.next();

                listview.add(name2);
            }

        }
        back = (Button) findViewById(R.id.BACK);
        cancel = (Button) findViewById(R.id.CANCEL);
        notify = (Button) findViewById(R.id.NOTIFY);
        simpleList = (ListView) findViewById(R.id.itemlist);

        //Set the clothing list to the listview
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(this,R.layout.listview,R.id.View,listview);
        //Set the arrayadapter to the listview
        simpleList.setAdapter(ArrayAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs.edit().clear().apply();

                if(listview != null){
                    listview.clear();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Your Clothing List is Empty",Toast.LENGTH_LONG).show();
                }
                ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview,R.id.View,listview);
                simpleList.setAdapter(ArrayAdapter);
            }
        });

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Objects.equals(listview,null)){
                    Toast.makeText(getApplicationContext(), "Your Clothing List is Empty", Toast.LENGTH_LONG).show();
                }
                else {
                    //create a variable to connect to the firebase
                    sendrequest = FirebaseDatabase.getInstance().getReference("message");
                    // create a new hashmap
                    HashMap<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
                    //store the clothing list into the hashmap
                    data.put("messageinfo", listview);
                    //store the clothing list into firebase
                    sendrequest.child("requestlist").setValue(listview);
                    Toast.makeText(getApplicationContext(),"Your Clothing List had Been Sent",Toast.LENGTH_LONG).show();
                    clothinglist.clear();
                    clothinglist2.clear();
                    prefs.edit().clear().commit();
                    listview.clear();
                }
            }
        });

    }
}