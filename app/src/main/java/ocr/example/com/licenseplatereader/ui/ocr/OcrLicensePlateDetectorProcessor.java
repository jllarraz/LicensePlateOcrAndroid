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

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.List;

import ocr.example.com.licenseplatereader.utils.camera.FrameMetadata;
import ocr.example.com.licenseplatereader.utils.camera.GraphicOverlay;
import ocr.example.com.licenseplatereader.utils.camera.OcrLicensePlateListener;
import ocr.example.com.licenseplatereader.utils.camera.VisionProcessorBase;
import ocr.example.com.licenseplatereader.utils.ocr.ILicensePlate;
import ocr.example.com.licenseplatereader.utils.ocr.LicensePlateManager;


/**
 * A very simple Processor which receives detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrLicensePlateDetectorProcessor extends VisionProcessorBase<FirebaseVisionText> {

    private static final String TAG = OcrLicensePlateDetectorProcessor.class.getSimpleName();

    private OcrLicensePlateListener licenseListener;
    private final FirebaseVisionTextRecognizer detector;

    OcrLicensePlateDetectorProcessor(OcrLicensePlateListener licenseListener) {
        this.licenseListener= licenseListener;
        detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
    }


    @Override
    public void stop() {
        try {
            detector.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception thrown while trying to close Text Detector: " + e);
        }
    }

    @Override
    protected Task<FirebaseVisionText> detectInImage(FirebaseVisionImage image) {
        return detector.processImage(image);
    }

    @Override
    protected void onSuccess(
            @NonNull FirebaseVisionText results,
            @NonNull FrameMetadata frameMetadata,
            @NonNull GraphicOverlay graphicOverlay) {
        graphicOverlay.clear();
        String fullRead="";
        List<FirebaseVisionText.TextBlock> blocks = results.getTextBlocks();
        for (int i = 0; i < blocks.size(); i++) {
            String temp="";
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++) {
                //extract scanned text lines here
                //temp+=lines.get(j).getText().trim()+"-";
                temp+=lines.get(j).getText()+"-";
            }
            temp=temp.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
            fullRead+=temp+"-";
        }
        try {
            ILicensePlate iLicensePlate = LicensePlateManager.getInstance().extractLicense(fullRead);
            if(licenseListener!=null){
                licenseListener.onLicensePlate(iLicensePlate);
            }
        }catch (Exception e){
        }

    }

    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.w(TAG, "Text detection failed." + e);
    }
}
