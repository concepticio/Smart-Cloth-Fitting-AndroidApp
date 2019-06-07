package com.example.user.fitprover1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 18/11/2017.
 */

public class cloth implements Parcelable{

    String qrid;
    String name;
    String colour1;
    String colour2;
    String colour3;
    String colour4;
    String size1;
    String size2;
    String size3;
    String size4;
    String size5;
    String size6;
    String url1;
    String url2;
    String url3;
    String url4;
    String style;
    String category;
    String material;
    String country;
    String price;
    String location;


   public cloth(){}
   public cloth( String qrid,String name,String colour1,String colour2,String colour3, String colour4,String size1,String size2,
                 String size3,String size4,String size5,String size6,String url1,String url2,String url3,String url4,String style,
                 String category,String material,String country,String price, String location){
       this.qrid = qrid;
       this.name = name;
       this.colour1 = colour1;
       this.colour2 = colour2;
       this.colour3 = colour3;
       this.colour4 = colour4;
       this.size1 = size1;
       this.size2 = size2;
       this.size3 = size3;
       this.size4 = size4;
       this.size5 = size5;
       this.size6 = size6;
       this.url1 = url1;
       this.url2 = url2;
       this.url3 = url3;
       this.url4 = url4;
       this.style = style;
       this.category = category;
       this.material = material;
       this.country = country;
       this.price = price;
       this.location = location;

   }

    protected cloth(Parcel in) {
        qrid = in.readString();
        name = in.readString();
        colour1 = in.readString();
        colour2 = in.readString();
        colour3 = in.readString();
        colour4 = in.readString();
        size1 = in.readString();
        size2 = in.readString();
        size3 = in.readString();
        size4 = in.readString();
        size5 = in.readString();
        size6 = in.readString();
        url1 = in.readString();
        url2 = in.readString();
        url3 = in.readString();
        url4 = in.readString();
        style = in.readString();
        category = in.readString();
        material = in.readString();
        country = in.readString();
        price = in.readString();
        location = in.readString();

    }

    public static final Creator<cloth> CREATOR = new Creator<cloth>() {
        @Override
        public cloth createFromParcel(Parcel in) {
            return new cloth(in);
        }

        @Override
        public cloth[] newArray(int size) {
            return new cloth[size];
        }
    };

    public String returnClothid(){
        return qrid;
    }

    public String returnClothname(){
        return name;
    }


    public String returnClothcolor1(){
        return colour1;
    }
    public String returnClothcolor2(){
        return colour2;
    }
    public String returnClothcolor3(){
        return colour3;
    }
    public String returnClothcolor4(){
        return colour4;
    }

    public String returnClothsize1(){
        return size1;
    }
    public String returnClothsize2(){
        return size2;
    }
    public String returnClothsize3(){
        return size3;
    }
    public String returnClothsize4(){
        return size4;
    }
    public String returnClothsize5(){
        return size5;
    }
    public String returnClothsize6(){
        return size6;
    }


    public String returnClothurl1(){
        return url1;
    }
    public String returnClothurl2(){
        return url2;
    }
    public String returnClothurl3(){
        return url3;
    }
    public String returnClothurl4(){
        return url4;
    }

    public String returnClothstyle(){
        return style;
    }
    public String returnClothcategory(){
        return category;
    }
    public String returnClothmaterial(){return material;}
    public String returnClothcountry(){
        return country;
    }
    public String returnClothprice(){return price;}
    public String returnClothlocation(){
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(qrid);
        parcel.writeString(name);
        parcel.writeString(colour1);
        parcel.writeString(colour2);
        parcel.writeString(colour3);
        parcel.writeString(colour4);
        parcel.writeString(size1);
        parcel.writeString(size2);
        parcel.writeString(size3);
        parcel.writeString(size4);
        parcel.writeString(size5);
        parcel.writeString(size6);
        parcel.writeString(url1);
        parcel.writeString(url2);
        parcel.writeString(url3);
        parcel.writeString(url4);
        parcel.writeString(style);
        parcel.writeString(category);
        parcel.writeString(material);
        parcel.writeString(country);
        parcel.writeString(price);
        parcel.writeString(location);

    }
}
