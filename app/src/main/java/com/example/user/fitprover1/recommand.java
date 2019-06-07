package com.example.user.fitprover1;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by user on 19/11/2017.
 */

public class recommand extends Activity {

    TextView country,location,price,material;
    Button back,add,list;
    Spinner clothname,size,colour,type;
    ImageView photoview;
    String cartcolor,cartsize,cid,cname,clocation;
    Set<String> recommendcart = new HashSet<String>();
    ArrayList<String> catname = new ArrayList<String>();
    ArrayList<String> sizelist = new ArrayList<String>();
    ArrayList<String> colourlist = new ArrayList<String>();
    ArrayList<String> urllist = new ArrayList<String>();
    ArrayList<String> catname2 = new ArrayList<String>();
    ArrayList<String> sizelist2 = new ArrayList<String>();
    ArrayList<String> colourlist2 = new ArrayList<String>();
    ArrayList<String> urllist2 = new ArrayList<String>();
    ArrayList<cloth> recommendlist = new ArrayList<cloth>();
    ArrayList<cloth> catlist = new ArrayList<cloth>();
    ArrayList<String> typeview = new ArrayList<String>();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommandcloth);

        //ARRAYLIST THAT CONTAIN ALL THE CLOTHING INFORMATION
        final ArrayList<cloth> recommended = (ArrayList<cloth>) getIntent().getSerializableExtra("recommendlist");
        //ARRAYLIST THAT CONTAIN ONLY THE SCANNED CLOTHING
        final ArrayList<cloth> scan = (ArrayList<cloth>) getIntent().getSerializableExtra("scancloth");


        back = (Button) findViewById(R.id.BACK);
        add = (Button) findViewById(R.id.ADD);
        list = (Button) findViewById(R.id.cart2);
        country = (TextView) findViewById(R.id.coun);
        price = (TextView) findViewById(R.id.pric);
        location = (TextView) findViewById(R.id.loc);
        material = (TextView) findViewById(R.id.mat);
        clothname = (Spinner) findViewById(R.id.CLOTH2);
        size = (Spinner) findViewById(R.id.SIZE1);
        colour = (Spinner) findViewById(R.id.COLOR1);
        type = (Spinner) findViewById(R.id.TYPE);
        photoview = (ImageView) findViewById(R.id.CLOTHIMAGE);

        // the for loop will filter out the scanned clothing from the recommended arraylist
        for (int i = 0;i<recommended.size();i++){
            if (recommended.get(i).returnClothname().toString().trim().equals(scan.get(0).returnClothname().toString().trim())){
                recommended.remove(i);
            }

        }

        // the for loop will add the clothing with the same style with the scanned clothing into the recommendlist
        for (int i = 0;i<recommended.size();i++){
            if (recommended.get(i).returnClothstyle().toString().trim().equals(scan.get(0).returnClothstyle().toString().trim())){
                recommendlist.add(recommended.get(i));
            }

        }

        // the for loop will add the clothing collocation into the catlist
        for (int j = 0;j<recommended.size();j++){
            if (recommended.get(j).returnClothcategory().toString().trim().equals(scan.get(0).returnClothcategory().toString().trim())){
                catlist.add(recommended.get(j));
            }
        }

        //SET THE SELECTION FOR THE TYPE SPINNER
        //SIMILARITY SHOW THE CLOTHING WHICH HAVE SAME CATEGORY WITH THE SCANNED CLOTHING
        typeview.add("Similarity");
        //COLLOCATION SHOW THE CLOTHING WHICH HAVE SAME STYLE WITH THE SCANNED CLOTHING
        typeview.add("Collocation");
        //DISPLAY THE TYPES OF CLOTHING IN THE SPINNER
        //SET THE ARRAYLIST TYPEVIEW TO THE SPINNER
        ArrayAdapter<String> dataAdapter7= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeview);
        //SET THE STYLE OF THE SPINNER WHICH IS DROPDOWN
        dataAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //SET THE ADAPTER TO THE SPINNER
        type.setAdapter(dataAdapter7);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //IF THE CUSTOMER CHOOSE THE SIMILAR CLOTHING
                    if (type.getSelectedItem()==typeview.get(0)){
                            //EVERYTIME THE CUSTOMER CHOOSE THE TYPE SPINNER,CLEAR ALL THE ARRAYLIST TO PREVENT ERROR
                            catname.clear();
                            sizelist.clear();
                            colourlist.clear();
                            urllist.clear();

                            //RUN THE LOOP TO GET SIMILAR CLOTHING
                            for (int j = 0;j<catlist.size();j++){
                                catname.add(catlist.get(j).returnClothname());

                            }
                            //DISPLAY SIMILAR CLOTHING NAME
                            //SET THE ARRAYLIST CATNAME TO THE SPINNER
                           ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, catname);
                            //SET THE STYLE OF THE SPINNER WHICH IS DROPDOWN
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //SET THE ADAPTER TO THE SPINNER
                            clothname.setAdapter(dataAdapter);

                            //WHEN NAME SPINNER IS PRESS
                            clothname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                   for (int h =0;h<catname.size();h++){
                                       //THE MATCHING OF CUSTOMER SELECTION WITH THE CORRECT CLOTHING NAME
                                       if(clothname.getSelectedItem() == catname.get(h).toString().trim()){
                                           //CLEAR ALL THE ARRAYLIST TO PREVENT ERROR
                                           cname =catname.get(h);
                                           sizelist.clear();
                                           colourlist.clear();
                                           urllist.clear();

                                          //GET THE SELECTED CLOTHING SIZES
                                           for (int o=0;o<catlist.size();o++){
                                               //IF THE SELECTED CLOTHING NAME IS EQUAL WITH THE CLOTHING NAME IN THE CATLIST ARRAYLIST
                                               if(catname.get(h).toString().trim() == catlist.get(o).returnClothname().toString().trim()){

                                                   country.setText(catlist.get(o).returnClothcountry());
                                                   material.setText(catlist.get(o).returnClothmaterial());
                                                   price.setText(catlist.get(o).returnClothprice());
                                                   location.setText(catlist.get(o).returnClothlocation());
                                                   clocation = catlist.get(o).returnClothlocation();
                                                   cid = catlist.get(o).returnClothid();

                                                   //GET ALL THE AVAILABLE SIZES OF THE CLOTHING
                                                   if(!Objects.equals(catlist.get(o).returnClothsize1(),"")){
                                                       sizelist.add(catlist.get(o).returnClothsize1());
                                                   }
                                                   if(!Objects.equals(catlist.get(o).returnClothsize2(),"")){
                                                       sizelist.add(catlist.get(o).returnClothsize2());
                                                   }
                                                   if(!Objects.equals(catlist.get(o).returnClothsize3(),"")){
                                                       sizelist.add(catlist.get(o).returnClothsize3());
                                                   }
                                                   if(!Objects.equals(catlist.get(o).returnClothsize4(),"")){
                                                       sizelist.add(catlist.get(o).returnClothsize4());
                                                   }
                                                   if(!Objects.equals(catlist.get(o).returnClothsize5(),"")){
                                                       sizelist.add(catlist.get(o).returnClothsize5());
                                                   }
                                                   if(!Objects.equals(catlist.get(o).returnClothsize6(),"")){
                                                       sizelist.add(catlist.get(o).returnClothsize6());
                                                   }
                                               }
                                           }
                                          // GET THE SELECTED CLOTHING COLOURS AND IMAGES
                                           for (int o=0;o<catlist.size();o++){
                                               //IF THE SELECTED CLOTHING NAME IS EQUAL WITH THE CLOTHING NAME IN THE CATLIST ARRAYLIST
                                               if(catname.get(h).toString().trim() == catlist.get(o).returnClothname().toString().trim()){
                                                   //GET ALL THE AVAILABLE COLOURS AND IMAGES OF THE CLOTHING
                                                   // COLOURS VALUES WILL STORE IN COLOURLIST ARRAYLIST
                                                   // IMAGES URL WILL STORE IN URLLIST ARRAYLIST
                                                   if(!(Objects.equals(catlist.get(o).returnClothcolor1(),""))) {
                                                       colourlist.add(catlist.get(o).returnClothcolor1());
                                                       urllist.add(catlist.get(o).returnClothurl1());
                                                   }
                                                   if(!(Objects.equals(catlist.get(o).returnClothcolor2(),""))) {
                                                       colourlist.add(catlist.get(o).returnClothcolor2());
                                                       urllist.add(catlist.get(o).returnClothurl2());
                                                   }
                                                   if(!(Objects.equals(catlist.get(o).returnClothcolor3(),""))) {
                                                       colourlist.add(catlist.get(o).returnClothcolor3());
                                                       urllist.add(catlist.get(o).returnClothurl3());
                                                   }
                                                   if(!(Objects.equals(catlist.get(o).returnClothcolor4(),""))) {
                                                       colourlist.add(catlist.get(o).returnClothcolor4());
                                                       urllist.add(catlist.get(o).returnClothurl4());
                                                   }


                                               }
                                           }
                                           //DISPLAY THE CLOTHING ONCE THE CLOTHING HAD BEEN CHOOSE
                                           Glide.with(getApplicationContext()).load(urllist.get(0)).into(photoview);
                                          // DISPLAY THE SIZES OF THE CLOTHING
                                           //SET THE ARRAYLIST SIZELIST TO THE SPINNER
                                           ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, sizelist);
                                           //SET THE STYLE OF THE SPINNER WHICH IS DROPDOWN
                                           dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                           //SET THE ADAPTER TO THE SPINNER
                                           size.setAdapter(dataAdapter3);

                                           size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                   for (int j =0;j<sizelist.size(); j++)
                                                       //THE FOR LOOP IS CARRY OUT TO MATCH SELECTED SIZE WITH THE SIZES INSIDE THE SIZELIST
                                                       if (size.getSelectedItem() == sizelist.get(j)) {
                                                       //ASSIGN THE SELECTED SIZE TO THE CARTSIZE VARIABLE FOR ADD TO CLOTHING LIST PURPOSE
                                                           cartsize = sizelist.get(j);

                                                       }
                                               }

                                               @Override
                                               public void onNothingSelected(AdapterView<?> parent) {

                                               }
                                           });
                                          // DISPLAY THE COLOUR AND THE IMAGE OF THE SELECTED CLOTHING
                                           // SET THE COLOURLIST TO THE COLOUR SPINNER
                                           ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, colourlist);
                                           // SET THE STYLE OF THE SPINNER WHICH IS DROPDOWN
                                           dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                           //SET THE ADAPTER TO THE SPINNER
                                           colour.setAdapter(dataAdapter2);

                                           colour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                   for (int j =0;j<colourlist.size(); j++)
                                                       //MATCH THE SELECTED COLOUR WITH THE COLOUR INSIDE THE COLOURLIST
                                                       if (colour.getSelectedItem() == colourlist.get(j)) {
                                                       //ASSIGN THE SELECTED COLOUR VALUE TO THE CARTCOLOR VARIABLE FOR ADD TO CLOTHING LIST PURPOSE
                                                           cartcolor = colourlist.get(j);
                                                           //CHANGE THE IMAGE BASED ON COLOUR SELECTED BY THE CUSTOMER
                                                           Glide.with(getApplicationContext()).load(urllist.get(j)).into(photoview);
                                                       }
                                               }

                                               @Override
                                               public void onNothingSelected(AdapterView<?> parent) {

                                               }
                                           });




                                       }
                                   }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }

                        //IF THE CUSTOMER SELECT THE CLOTHING COLLOCATION
                        else{
                        //CLEAR ALL THE ARRAYLIST TO PREVENT ERROR
                             catname2.clear();
                             sizelist2.clear();
                             colourlist2.clear();
                             urllist2.clear();

                        // GET THE CLOTHING COLLOCATION INFORMATION
                        for (int j = 0;j<recommendlist.size();j++){
                                    catname2.add(recommendlist.get(j).returnClothname());
                                }

                              //DISPLAY THE CLOTHING NAME
                              //SET THE ARRAYLIST CATNAME2 TO THE SPINNER
                                ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, catname2);
                               // SET THE STYLE OF THE SPINNER WHICH IS DROPDOWN
                                dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //SET THE ADAPTER TO THE SPINNER
                                clothname.setAdapter(dataAdapter4);

                                //WHEN CLOTHING IS CHOOSEN
                                clothname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        for (int h =0;h<catname2.size();h++){
                                            //THE MATCHING OF CUSTOMER SELECTION WITH THE CORRECT CLOTHING NAME
                                            if(clothname.getSelectedItem().equals(catname2.get(h).toString().trim())){
                                                //ASSIGN THE SELECTED CLOTHING NAME VALUE TO THE CNAME VALUE FOR ADD TO CLOTHING LIST PURPOSE
                                                cname =catname2.get(h).toString().trim();

                                                //CLEAR ALL THE ARRAYLIST TO PREVENT ERROR

                                                sizelist2.clear();
                                                colourlist2.clear();
                                                urllist2.clear();

                                                //GET ALL THE COLOURS AND IMAGES OF THE SELECTED CLOTHING
                                                for (int o=0;o<recommendlist.size();o++){
                                                    //WHEN THE SELECTED CLOTHING NAME MATCH THE CLOTHING NAME IN THE RECOMMENDLIST
                                                    if(catname2.get(h).toString().trim() == recommendlist.get(o).returnClothname().toString().trim()){
                                                        country.setText(recommendlist.get(o).returnClothcountry());
                                                        material.setText(recommendlist.get(o).returnClothmaterial());
                                                        price.setText(recommendlist.get(o).returnClothprice());
                                                        location.setText(recommendlist.get(o).returnClothlocation());
                                                        clocation = recommendlist.get(o).returnClothlocation();
                                                        cid = recommendlist.get(o).returnClothid().toString();

                                                        //ADD THE COLOURS AND THE IMAGE URLS INTO TWO DIFFERENT ARRAYLISTS
                                                        if(!(Objects.equals(recommendlist.get(o).returnClothcolor1(),""))) {
                                                            colourlist2.add(recommendlist.get(o).returnClothcolor1());
                                                            urllist2.add(recommendlist.get(o).returnClothurl1());
                                                        }
                                                        if(!(Objects.equals(recommendlist.get(o).returnClothcolor2(),""))) {
                                                            colourlist2.add(recommendlist.get(o).returnClothcolor2());
                                                            urllist2.add(recommendlist.get(o).returnClothurl2());
                                                        }
                                                        if(!(Objects.equals(recommendlist.get(o).returnClothcolor3(),""))) {
                                                            colourlist2.add(recommendlist.get(o).returnClothcolor3());
                                                            urllist2.add(recommendlist.get(o).returnClothurl3());
                                                        }
                                                        if(!(Objects.equals(recommendlist.get(o).returnClothcolor4(),""))) {
                                                            colourlist2.add(recommendlist.get(o).returnClothcolor4());
                                                            urllist2.add(recommendlist.get(o).returnClothurl4());
                                                        }


                                                    }
                                                }
                                               //DISPLAY THE CLOTHING IMAGE ONCE THE CUSTOMER SELECT THE CLOTHING
                                                Glide.with(getApplicationContext()).load(urllist2.get(0)).into(photoview);

                                                //GET ALL THE SIZES OF THE SELECTED CLOTHING
                                                for (int o=0;o<recommendlist.size();o++){
                                                    //WHEN THE SELECTED CLOTHING NAME MATCH THE CLOTHING NAME IN THE RECOMMENDLIST
                                                    if(cname.toString().trim() == recommendlist.get(o).returnClothname().toString().trim()){
                                                        //ADD ALL THE AVAILABLE SIZES INTO THE SIZELIST2
                                                        if(!Objects.equals(recommendlist.get(o).returnClothsize1(),"")){
                                                            sizelist2.add(recommendlist.get(o).returnClothsize1());
                                                        }
                                                        if(!Objects.equals(recommendlist.get(o).returnClothsize2(),"")){
                                                            sizelist2.add(recommendlist.get(o).returnClothsize2());
                                                        }
                                                        if(!Objects.equals(recommendlist.get(o).returnClothsize3(),"")){
                                                            sizelist2.add(recommendlist.get(o).returnClothsize3());
                                                        }
                                                        if(!Objects.equals(recommendlist.get(o).returnClothsize4(),"")){
                                                            sizelist2.add(recommendlist.get(o).returnClothsize4());
                                                        }
                                                        if(!Objects.equals(recommendlist.get(o).returnClothsize5(),"")){
                                                            sizelist2.add(recommendlist.get(o).returnClothsize5());
                                                        }
                                                        if(!Objects.equals(recommendlist.get(o).returnClothsize6(),"")){
                                                            sizelist2.add(recommendlist.get(o).returnClothsize6());
                                                        }
                                                    }
                                                }

                                                //DISPLAY THE SIZES OF THE SELECTED CLOTHING
                                                //SET THE SIZELIST2 TO THE SIZE SPINNER
                                                ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, sizelist2);
                                                //SET THE STYLE OF THE SPINNER WHICH IS DROPDOWN
                                                dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                //SET THE ADAPTER INTO THE SPINNER
                                                size.setAdapter(dataAdapter5);

                                                size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        for (int j =0;j<sizelist2.size(); j++)
                                                            //THE FOR LOOP WILL CARRY OUT TO MATCH THE SELECTED SIZE WITH THE SIZES INSIDE THE SIZELIST2
                                                            if (size.getSelectedItem() == sizelist2.get(j)) {
                                                            //ASSIGN THE SIZE VALUE TO THE CARTLIST VARIABLE FOR ADD TO CLOTHING LIST PURPOSE
                                                                cartsize = sizelist2.get(j);

                                                            }
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                    }
                                                });

                                                //DISPLAY THE IMAGES AND THE COLOUR OF THE SELECTED CLOTHING
                                                //SET THE COLOURLIST2 TO THE COLOUR SPINNER
                                                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, colourlist2);
                                                //SET THE STYLE OF THE SPINNER WHICH IS DROPDOWN
                                                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                //SET THE ADAPTER TO THE COLOUR SPINNER
                                                colour.setAdapter(dataAdapter2);

                                                //WHEN THE COLOUR SPINNER IS PRESS
                                                colour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        for (int j =0;j<colourlist2.size(); j++)
                                                            //THE FOR LOOP WILL MATCH THE SELECTED COLOUR WITH THE COLOUR INSIDE THE COLOURLIST2
                                                            if (colour.getSelectedItem() == colourlist2.get(j)) {
                                                            //ASSIGN THE SELECTED COLOUR VALUE TO CARTCOLOR VARIABLE FOR ADD TO CLOTHING LIST PURPOSE
                                                                cartcolor = colourlist2.get(j);
                                                                //CHANGE THE CLOTHING IMAGE ONCE THE COLOUR OF THE CLOTHING IS SELECTED
                                                                Glide.with(getApplicationContext()).load(urllist2.get(j)).into(photoview);

                                                            }
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                    }
                                                });




                                            }
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                        }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                String data = "Name :"+cname +"   ID :"+cid +"   Colour :"+cartcolor +"   Size :"+cartsize+"   Location :"+clocation;
                recommendcart.add(data);
                editor.putStringSet("recommend",recommendcart);
                editor.commit();

            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(getApplicationContext(),cart.class);
                startActivity(cart);
            }
        });
    }
}
