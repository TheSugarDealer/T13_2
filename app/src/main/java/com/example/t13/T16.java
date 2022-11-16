package com.example.t13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class T16 extends AppCompatActivity {
    private MediaRecorder grabacion;
    private MediaPlayer mediaPlayer;
    private String archivoSalida = null;
    private Button btn_recorder ,btn_play_gr, btn_stop_gr;
    private TextView textosalida;

    private String ruta_grabaciones = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/AudiosMicro/";
    private File carpeta = new File(ruta_grabaciones);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t16);
        btn_recorder = (Button) findViewById(R.id.btn_recorder);
        btn_play_gr = (Button) findViewById(R.id.btn_play_gr);
        btn_stop_gr = (Button) findViewById(R.id.btn_stop_gr);
        textosalida = (TextView) findViewById(R.id.nombre_audio);
        mediaPlayer = new MediaPlayer();
        carpeta.mkdirs();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(T16.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }

        btn_recorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grabacion == null) {
                    // archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + momento() + ".mp3";
                    archivoSalida = ruta_grabaciones + "/" + momento() + ".mp3";
                    textosalida.setText(archivoSalida);
                    grabacion = new MediaRecorder();
                    grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
                    grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    grabacion.setOutputFile(archivoSalida);
                    grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    try {
                        grabacion.prepare();
                        textosalida.setTextColor(Color.parseColor("red"));
                        textosalida.setText("Grabando....");
                        grabacion.start();
                    } catch (IOException e) {
                        Log.e("Error", "Error" + e);
                    }

                } else if (grabacion != null) {
                    textosalida.setTextColor(Color.parseColor("white"));
                    textosalida.setText("Grabación Finalizada");
                    textosalida.setText(archivoSalida);
                    grabacion.stop();
                    grabacion.release();
                    grabacion = null;
                }
            }
        });


        btn_play_gr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer.setDataSource(archivoSalida);
                    mediaPlayer.prepare();

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        btn_play_gr.setBackgroundResource(R.drawable.reproducir);
                        textosalida.setText("Escuchar audio");
                        textosalida.setTextColor(Color.parseColor("red"));
                    } else {

                        mediaPlayer.start();
                        btn_play_gr.setBackgroundResource(R.drawable.pausa);
                        textosalida.setText("pausar audio");
                        textosalida.setTextColor(Color.parseColor("white"));

                    }
                } catch (IOException e) {
                    Log.e("Error", "Error");
                }

            }
        });


        btn_stop_gr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {

                    textosalida.setTextColor(Color.parseColor("white"));
                    mediaPlayer.stop();
                    mediaPlayer.release();

                }

            }
        });


    }


    // --- AÑADE LA FECHA Y HORA AL ARCHIVO --- //
    private String momento() {
        SimpleDateFormat formatofh = new
                SimpleDateFormat("yyMMddhhmmss");
        String formato = formatofh.format(new Date());
        String audio = "Grab_" + formato;
        return audio;
    }
}


