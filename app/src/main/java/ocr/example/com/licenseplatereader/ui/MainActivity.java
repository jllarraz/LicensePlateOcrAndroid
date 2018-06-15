package ocr.example.com.licenseplatereader.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.CommonStatusCodes;

import ocr.example.com.licenseplatereader.R;
import ocr.example.com.licenseplatereader.common.IntentData;
import ocr.example.com.licenseplatereader.ui.ocr.OcrLicensePlateCaptureActivity;
import ocr.example.com.licenseplatereader.utils.ocr.ILicensePlate;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int RC_OCR_CAPTURE = 9003;

    AppCompatImageButton buttonCamera;
    AppCompatTextView textViewLicensePlate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewLicensePlate = findViewById(R.id.licensePlateScanned);
        buttonCamera = findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch Ocr capture activity.
                textViewLicensePlate.setText("");
                Intent intent = new Intent(MainActivity.this, OcrLicensePlateCaptureActivity.class);
                intent.putExtra(OcrLicensePlateCaptureActivity.AutoFocus, true);
                intent.putExtra(OcrLicensePlateCaptureActivity.UseFlash, false);

                startActivityForResult(intent, RC_OCR_CAPTURE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    ILicensePlate license = data.getParcelableExtra(IntentData.KEY_LICENSE_PLATE);
                    textViewLicensePlate.setText(license.getLicensePlate());
                    Log.d(TAG, "Text read: " + license);
                } else {
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
