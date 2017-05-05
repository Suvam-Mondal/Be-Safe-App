package com.example.shuvo.sosapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Setup extends AppCompatActivity implements AdapterView.OnItemClickListener {
    MyAdapter madapt;
    List<String> store= new ArrayList<String>();
    String s1,s2,s3,s4;
    String newline="\n";
    Button b;
    CheckBox cb;
    FileOutputStream out1;
    List<String> name1 = new ArrayList<String>();
    List<String> phone1 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
                .setMessage("This App requires GPS to work properly. Please keep the GPS enabled whenever you are using this App.")
        .show();

        getAllContacts(this.getContentResolver());
        ListView lv =(ListView)findViewById(R.id.lv);
        madapt = new MyAdapter();
        lv.setAdapter(madapt);
        lv.setOnItemClickListener(this);
        lv.setItemsCanFocus(false);
        lv.setTextFilterEnabled(true);
        b=(Button)findViewById(R.id.button);

        File file = new File(getFilesDir(),"contacts");
        try {
            out1 = openFileOutput("contacts", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) { }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!store.isEmpty())
                {
                        for (String s : store) {
                            s1 = s.replace("(", "");
                            s2 = s1.replace(")", "");
                            s3 = s2.replace(" ", "");
                            s4 = s3.replace("-", "");
                            try {
                                out1.write(s4.getBytes());
                                out1.write(newline.getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            startActivity(new Intent(Setup.this, Message.class));
                        }
                    }
                    else {
                        Toast.makeText(Setup.this, "Atleast 1 Contact must be selected !", Toast.LENGTH_SHORT).show();
                    }



            }
        });

    }

    public  void getAllContacts(ContentResolver cr) {

        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            System.out.println(".................."+phoneNumber);
            name1.add(name);
            phone1.add(phoneNumber);

        }

        phones.close();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    class MyAdapter extends BaseAdapter
    {
        LayoutInflater inf;

        MyAdapter() {
            inf = (LayoutInflater)Setup.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return name1.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi=convertView;
           // if(convertView!=null)
                vi = inf.inflate(R.layout.row, null);

            TextView tv = (TextView)vi.findViewById(R.id.tv);
            TextView textView = (TextView)vi.findViewById(R.id.textView);
            cb = (CheckBox)vi.findViewById(R.id.checkBox);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {

                        store.add(phone1.get(position));

                    }
                    else {
                        store.remove(phone1.get(position));
                    }
                }
            });
            tv.setText("Name: "+ name1.get(position));
            textView.setText("Number: "+phone1.get(position));
            return  vi;
        }


    }


}
