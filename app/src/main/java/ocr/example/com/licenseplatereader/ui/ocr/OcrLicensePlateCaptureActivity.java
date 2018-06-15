/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ocr.example.com.licenseplatereader.ui.ocr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import ocr.example.com.licenseplatereader.R;
import ocr.example.com.licenseplatereader.common.IntentData;
import ocr.example.com.licenseplatereader.utils.camera.OcrLicensePlateListener;
import ocr.example.com.licenseplatereader.utils.ocr.ILicensePlate;


/**
 * Activity for the multi-tracker app.  This app detects text and displays the value with the
 * rear facing camera. During detection overlay graphics are drawn to indicate the position,
 * size, and contents of each TextBlock.
 */
public final class OcrLicensePlateCaptureActivity extends OcrCaptureActivity {
    private static final String TAG = OcrLicensePlateCaptureActivity.class.getSimpleName();

    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout layoutTutorial;

    /**
     * Initializes the UI and creates the detector pipeline.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.ocr_capture_license_plate);
        onCreateInit(icicle);
        layoutTutorial = findViewById(R.id.tutorialLayout);
        bottomSheetBehavior = BottomSheetBehavior.from(layoutTutorial);
        setActionBarTitle(getString(R.string.scan_plate));
        setBackButton();
    }

    @Override
    protected Detector.Processor<TextBlock> getTextProcessor() {
        return new OcrLicensePlateDetectorProcessor(mGraphicOverlay, new OcrLicensePlateListener() {
            @Override
            public void onLicensePlate(ILicensePlate licensePlate) {
                Intent data = new Intent();
                data.putExtra(IntentData.KEY_LICENSE_PLATE, licensePlate);
                setResult(CommonStatusCodes.SUCCESS, data);
                finish();
            }
        });
    }
}
