package com.mohammedsalah.salah;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mohammedsalah.AlertReciver;
import com.mohammedsalah.salah.notekeeper.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class chat extends AppCompatActivity {

    private EditText text_message ;
    private ImageView imag_delete ;
    private ListView listView;
    private ArrayAdapter adapter ;
    private String userName;
    private DatabaseReference root;
    private String tem_key ;
    private ArrayList<String> list;
    private FloatingActionButton  send_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

         setTitle("Chat Room");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         root = FirebaseDatabase.getInstance().getReference().child("ChatRoom");

        text_message= (EditText) findViewById(R.id.Edit_chatmessage);
        imag_delete = (ImageView) findViewById(R.id.delete);
        send_msg = (FloatingActionButton) findViewById(R.id.send);



        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        userName = pref.getString("user_display_name", "");


        list = new ArrayList<>();
        adapter = new ArrayAdapter(this , R.layout.list_itemchat , list);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);


        text_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (text_message.getText().toString().isEmpty()){
                    getSupportActionBar().setSubtitle("Searching for someone..");
                    send_msg.setVisibility(View.INVISIBLE);
                }else {
                    getSupportActionBar().setSubtitle(userName +" Typing...");
                    send_msg.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

         send_msg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Map <String , Object > map = new HashMap<>();
                  tem_key = root.push().getKey();
                  root.updateChildren(map);

                  DatabaseReference mess_ref = root.child(tem_key);
                 Map <String , Object > map1 = new HashMap<>();
                 String message = text_message.getText().toString();
                 map1.put("msg" , message );
                 mess_ref.updateChildren(map1);

             }
         });

         root.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 Add_chat(dataSnapshot);
             }


             @Override
             public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 Add_chat(dataSnapshot);
             }

             @Override
             public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });


    }

         String chat_msg ;
        int newSize;


    private void Add_chat(DataSnapshot dataSnapshot) {
          int currentSize  = list.size();
            Iterator i = dataSnapshot.getChildren().iterator();
            text_message.setText("");

        while (i.hasNext()){
            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            list.add(chat_msg);
            adapter.notifyDataSetChanged();
            listView.setSelection(list.size());

        }
           newSize = list.size();
        if (newSize > currentSize && currentSize != 0 ){

            Calendar calendar = Calendar.getInstance();

            Intent intent = new Intent(getApplicationContext() , AlertReciver.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP ,calendar.getTimeInMillis() , PendingIntent.getBroadcast(getApplicationContext() , 0
             , intent , PendingIntent.FLAG_UPDATE_CURRENT));

        }
    }

    public void delete(View view) {
        text_message.setText("");
    }
}
