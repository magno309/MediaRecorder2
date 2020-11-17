package com.melecio.mediarecorder2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.melecio.mediarecorder2.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnGrabar;
    Button btnReproducir;
    boolean grabar = true;
    boolean reproducir = true;
    private static String fileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/audiorecordtest.3gp";

        btnGrabar = (Button) findViewById(R.id.btnGrabar);
        btnReproducir = (Button) findViewById(R.id.btnReproducir);

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaRecorder mediaRecorder = new MediaRecorder();
                if(grabar){
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setOutputFile(fileName);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    try{
                        mediaRecorder.prepare();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Algo salió mal!", Toast.LENGTH_SHORT).show();
                    }
                    mediaRecorder.start();
                    grabar = false;
                }else{
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                }
            }
        });

        btnReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = new MediaPlayer();
                if(reproducir){
                    try {
                        btnReproducir.setText("Detener audio");
                        player.setDataSource(fileName);
                        player.prepare();
                        player.start();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Algo salió mal!", Toast.LENGTH_SHORT).show();
                        btnReproducir.setText("Iniciar audio");
                    }
                }else{
                    btnReproducir.setText("Iniciar audio");
                    player.release();
                    player = null;
                }
            }
        });
    }
}