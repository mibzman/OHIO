package com.isotope.OrgView;

import android.content.Intent;
import com.isotope.OrgView.MyActivity;
import com.isotope.OrgView.CameraPreview;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Button;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;

import android.widget.TextView;
import android.graphics.ImageFormat;

/* Import ZBar Class files */
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;

public class MyActivity extends Activity
{
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    TextView scanText;
    Button scanButton;

    ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //scannerStartupStuff();
        Button recents = (Button) findViewById(R.id.RecentButton);
        recents.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecents();
            }
        });

        Button home = (Button) findViewById(R.id.HomeButton);
        home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

    }
    public void startRecents(){
        Intent adventure = new Intent(this, RecentsPage.class);
        startActivity(adventure);
    }
    public void goHome(){
        Intent adventure2 = new Intent(this, HomePage.class);
        startActivity(adventure2);
    }

    @Override
    public void onStop(){
        super.onStop();

    }

    public void scannerStartupStuff(){
        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        //scanText = (TextView)findViewById(R.id.scanText);


/*
        scanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    //scanText.setText("Scanning...");
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });
        */
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    public void onResume(){
        super.onResume();
        scannerStartupStuff();

    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
        mPreview.getHolder().removeCallback(mPreview);
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    PreviewCallback previewCb = new PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                    for (Symbol sym : syms) {
                        //scanText.setText("barcode result " + sym.getData());
                        sendResult(sym.getData());
                    barcodeScanned = true;
                }
            }
        }
    };

    public void sendResult(String input){
        Intent startIntent = getIntent();
        Intent startScan = getIntent();
        boolean isStart = startIntent.getBooleanExtra("start", false);
        boolean isAgain = startScan.getBooleanExtra("again", false);
        if(isStart || isAgain){
            Intent intent20 = new Intent(this, Display.class);
            intent20.putExtra("barcodeResult", input);    //sends who won
            intent20.putExtra("orgTag", true);
            startActivity(intent20);
        }else {
            Intent intent20 = new Intent(this, Display.class);
            intent20.putExtra("barcodeResult", input);    //sends who won
            intent20.putExtra("orgTag", false);
            startActivity(intent20);
        }
    }

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
}