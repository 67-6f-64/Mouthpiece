
package com.omega.mouthpiece;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class Converter extends AppCompatActivity{

    AnimationDrawable MouthAnimation;
    //Uri uriStreamImage = Uri.parse(this.getFilesDir() + "/MouthpiecesTest/mouth1.jpg");

    //------------------------RECORDING VAR----------------------------------
    private MediaRecorder recorder = null;
    private static final String LOG_TAG = "AudioRecordTest";
    private static String fileName = null;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    //-----------------------------------------------------------------------

    //------------------------HANDLER VAR--------------------------------------
    Handler h2 = new Handler();
    //-----------------------------------------------------------------------

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

    private void startRecording() {

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording()
    {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    //----------------------------------MEASURE AMP------------------------------
    public double getAmplitude() {
        if (recorder != null)
            return  (recorder.getMaxAmplitude());
        else
            return 0;

    }


    //-----------------------------------------------------------------------------
    //-----------------------TIMER FUNCTIONS---------------------------------------
    Runnable measure = new Runnable() {

        @Override
        public void run() {
            ImageView mouthImage = findViewById(R.id.img_mouth);
            double amp = getAmplitude();
            double db = 20 * Math.log10(amp / 0.447);
            if(db >= 71.5) {
            }
            else {
                mouthImage.setBackgroundResource(R.drawable.close_mouth);
            }
            MouthAnimation = (AnimationDrawable) mouthImage.getBackground();
            MouthAnimation.start();

            h2.postDelayed(this, 30);
        }
    };
    //---------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_landing);
        //---------------------------KEEP SCREEN ON------------------------------
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //---------------------------ANIMATION INIT------------------------------
        ImageView mouthImage = findViewById(R.id.img_mouth);
        mouthImage.setBackgroundResource(R.drawable.open_mouth);
        MouthAnimation = (AnimationDrawable) mouthImage.getBackground();

        //---------------------------RECORDING SYSTEM-----------------------------
        // Record to the external cache directory for visibility
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/voiceprofile_audio.3gp";

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        Button volume_based_btn = findViewById(R.id.btn_record);
        //Click on Volume button to start recording
        volume_based_btn.setOnClickListener(new View.OnClickListener() {
            boolean mStartRecording = true;
            public void onClick(View view) {

                if (mStartRecording) {//click to record

                    startRecording();
                    h2.postDelayed(measure,0);
                } else {//click again to stop recording

                    stopRecording();
                    h2.removeCallbacks(measure);
                }
                mStartRecording = !mStartRecording;
            }
        });

        //-------------------------TESTING--------------------------------
        //Click On image to check animation
        mouthImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });

    }
}
