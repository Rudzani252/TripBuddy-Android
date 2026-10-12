package com.example.tripbuddyv10;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tripbuddyv10.R;

public class memories extends AppCompatActivity {

    ImageView image1, image2, image3;
    MediaPlayer mediaPlayer;
    Uri musicUri;

    // Launchers for images
    private ActivityResultLauncher<Intent> pickImage1Launcher;
    private ActivityResultLauncher<Intent> pickImage2Launcher;
    private ActivityResultLauncher<Intent> pickImage3Launcher;

    // Launcher for music
    private ActivityResultLauncher<Intent> pickMusicLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);

        Button addMem1 = findViewById(R.id.addMem1);
        Button addMem2 = findViewById(R.id.addMem2);
        Button addMem3 = findViewById(R.id.addMem3);

        Button selectMusic = findViewById(R.id.selectMusic);
        Button play = findViewById(R.id.play);
        Button pause = findViewById(R.id.pause);
        Button stopMusic = findViewById(R.id.stopMusic);

        // --- Register Launchers ---
        pickImage1Launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        image1.setImageURI(uri);
                    }
                });

        pickImage2Launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        image2.setImageURI(uri);
                    }
                });

        pickImage3Launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        image3.setImageURI(uri);
                    }
                });

        pickMusicLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        musicUri = result.getData().getData();
                    }
                });

        // --- Button Listeners ---
        addMem1.setOnClickListener(v -> pickImage1Launcher.launch(pickImageIntent()));
        addMem2.setOnClickListener(v -> pickImage2Launcher.launch(pickImageIntent()));
        addMem3.setOnClickListener(v -> pickImage3Launcher.launch(pickImageIntent()));

        selectMusic.setOnClickListener(v -> pickMusicLauncher.launch(pickMusicIntent()));

        play.setOnClickListener(v -> {
            if (musicUri != null) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, musicUri);
                }
                mediaPlayer.start();
            }
        });

        pause.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        });

        stopMusic.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });
    }

    private Intent pickImageIntent() {
        return new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    private Intent pickMusicIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        return intent;
    }




}














