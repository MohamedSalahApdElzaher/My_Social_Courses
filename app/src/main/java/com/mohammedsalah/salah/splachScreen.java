package com.mohammedsalah.salah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mohammedsalah.salah.notekeeper.MainActivity;
import com.mohammedsalah.salah.notekeeper.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class splachScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);

        CircleImageView imageView = (CircleImageView) findViewById(R.id.image_splach);
        imageView.animate().alpha(0f).setDuration(7000);

        TextView text1 = (TextView) findViewById(R.id.text_1);
        text1.animate().alpha(0f).setDuration(6000);

        TextView text2 = (TextView) findViewById(R.id.text_2);
        text2.animate().alpha(0f).setDuration(4000);

        TextView text3 = (TextView) findViewById(R.id.text_3);
        text3.animate().alpha(0f).setDuration(3000);

        TextView text4 = (TextView) findViewById(R.id.text_4);
        text4.animate().alpha(0f).setDuration(5000);

        Thread thread = new Thread(){

            @Override
            public void run() {
                try {
                    sleep(6500);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };

        thread.start();
    }
}
