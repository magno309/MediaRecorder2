package com.melecio.mediarecorder2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
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

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

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
                        reproducir = false;
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Algo salió mal!", Toast.LENGTH_SHORT).show();
                        btnReproducir.setText("Iniciar audio");
                    }
                }else{
                    btnReproducir.setText("Iniciar audio");
                    player.stop();
                    player.release();
                    reproducir = true;
                }
            }
        });
    }
}