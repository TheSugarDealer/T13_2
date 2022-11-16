package com.example.t13;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class T15 extends AppCompatActivity {

    private VideoView V1;
    private Button btn_play, btn_adelantar, btn_atrasar, btn_stop;
    private TextView tvVideo1, texv1;
    private SeekBar skVideo1;
    private Handler sk_handlerV = new Handler();
    private String listavideo[] = new String[5];
    private int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t15);


        // --- BOTONES --- //

        btn_play = (Button) findViewById(R.id.butn_play);
        btn_adelantar = (Button) findViewById(R.id.butn_adelantar);
        btn_stop = (Button) findViewById(R.id.butn_stop);
        btn_atrasar = (Button) findViewById(R.id.butn_atrasar);
        tvVideo1 = (TextView) findViewById(R.id.tvVideo1);
        skVideo1 = (SeekBar) findViewById(R.id.skVideo1);
        texv1 = (TextView) findViewById(R.id.texv1);

        listavideo[0] = "android.resource://" + getPackageName() + "/" + R.raw.vid1;
        listavideo[1] = "android.resource://" + getPackageName() + "/" + R.raw.vid2;
        listavideo[2] = "android.resource://" + getPackageName() + "/" + R.raw.vid3;
        listavideo[3] = "android.resource://" + getPackageName() + "/" + R.raw.vid4;
        listavideo[4] = "android.resource://" + getPackageName() + "/" + R.raw.vid5;



        // --- VIDEO --- //

        V1 = (VideoView) findViewById(R.id.video1);
        V1.setVideoURI(Uri.parse(listavideo[posicion]));



        // --- FUNCIONES DE BOTONES --- //

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(V1.isPlaying()){
                    V1.pause();
                    btn_play.setBackgroundResource(R.drawable.reproducir);
                }
                else {
                    V1.start();
                    btn_play.setBackgroundResource(R.drawable.pausa);
                }

            }
        });



        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (V1.isPlaying()  &&  V1 != null) {

                    btn_play.setBackgroundResource(R.drawable.reproducir);
                    V1.stopPlayback();
                    V1.setVideoURI(Uri.parse(listavideo[posicion]));

                }
            }
        });

        btn_adelantar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean yes = false;
                int posicion_sig = posicion;
                if(V1.isPlaying()){
                    yes = true;
                }
                if(posicion == 3){

                    posicion = 0;
                }
                else
                {
                    posicion++;
                }
                V1.stopPlayback();
                V1.setVideoURI(Uri.parse(listavideo[posicion]));

                if(yes){
                    V1.start();

                }

            }
        });



        btn_atrasar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posicionn_video = V1.getCurrentPosition();
                V1.seekTo(posicionn_video-10000);
                skVideo1.setProgress( V1.getCurrentPosition());

            }

        });
        tvVideo1.setText(getRHM(V1.getDuration()));
        skVideo1.setMax(V1.getDuration());
        skVideo1.setProgress(V1.getCurrentPosition());
        sk_handlerV.postDelayed(UpdateVideo, 1000);
    }



    // --- hh:mm:ss --- //
    private String getRHM(int milliseconds)
    {
        int seconds = (int) (milliseconds/1000) % 60;
        int minutes = (int) (milliseconds/(60*1000)) % 60;
        int hours = (int) (milliseconds/(1000*60*60)) % 24;

        return  ""  + ((hours < 10)?"0"+hours:hours) +
                ":" + ((minutes < 10)?"0"+minutes:minutes) +
                ":" + ((seconds < 10)?"0"+seconds:seconds);
    }

    // --- BARRA --- //
    Runnable UpdateVideo = new Runnable() {
        @Override
        public void run() {
            skVideo1.setProgress(V1.getCurrentPosition());
            tvVideo1.setText(getRHM(V1.getDuration()) + " - " + getRHM(V1.getCurrentPosition()));
            sk_handlerV.postDelayed(UpdateVideo, 1000);
            }
        };


}
