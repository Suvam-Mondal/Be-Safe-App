package com.example.shuvo.sosapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shuvo on 5/3/2017.
 */
public class Send extends Activity{
    String infomsg,message="",postalcode,locality,admin,num,finalMessage,temp;
    double longitude,latitude;
    List<String> contacts = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);

        Bundle p = getIntent().getExtras();
         postalcode=p.getString("postal");
         locality=p.getString("locality");
        //String thouroghfare=p.getString("thoroughfare");
         admin=p.getString("admin");
         longitude = (double) p.get("longitude");
         latitude = (double) p.get("latitude");

       /* Toast.makeText(Send.this, "Postal Code: "+postalcode
                +"\nLocality: "+locality
                +"\nAdmin: "+admin
                +"\nLongitude: "+longitude
                +"\nLatitude: "+latitude, Toast.LENGTH_SHORT).show();*/

         infomsg="Postal Code: "+postalcode +"\n"+"Locality: "+locality+","+admin+"\n"+"Longitude: "+longitude+"\n"+"Latitude: "+latitude;


        try {
            FileInputStream msg = openFileInput("message");
            FileInputStream con = openFileInput("contacts");

            BufferedReader br = new BufferedReader(new InputStreamReader(msg));
            String linemsg;
            while ((linemsg=br.readLine()) != null) {
                 message += linemsg;
            }
            String linecon="";
            BufferedReader cr = new BufferedReader(new InputStreamReader(con));
            while ((linecon=cr.readLine())!=null)
            {
                contacts.add(linecon);
            }
            //Toast.makeText(Send.this, contacts.toString(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(Send.this, "No emergency contacts or message found. Please Setup your emergency contacts and message.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Send.this,MainActivity.class));

        }

        finalMessage = message+"\n\n"+"Location info of the Sender:"+"\n"+infomsg;
        int i=1;
        for (String c: contacts) {
            sendsms(c,finalMessage);
        }
        startActivity(new Intent(Send.this,MainActivity.class));
    }
   void sendsms(String phone,String text)
   {
       SmsManager sms = SmsManager.getDefault();
       try {

           PendingIntent sentPI;
           String SENT = "SMS_SENT";
           sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
           sms.sendTextMessage(phone, null, text, sentPI, null);
           Toast.makeText(Send.this, "Sent", Toast.LENGTH_SHORT).show();
       }
       catch (Exception e)
       {
           Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();

       }


   }
}
