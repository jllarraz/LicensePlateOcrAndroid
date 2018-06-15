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

import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.util.regex.Pattern;

import ocr.example.com.licenseplatereader.utils.camera.GraphicOverlay;
import ocr.example.com.licenseplatereader.utils.camera.OcrLicensePlateListener;
import ocr.example.com.licenseplatereader.utils.ocr.ILicensePlate;
import ocr.example.com.licenseplatereader.utils.ocr.LicensePlateManager;


/**
 * A very simple Processor which receives detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrLicensePlateDetectorProcessor implements Detector.Processor<TextBlock> {

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private OcrLicensePlateListener licenseListener;

    OcrLicensePlateDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, OcrLicensePlateListener licenseListener) {
        mGraphicOverlay = ocrGraphicOverlay;
        this.licenseListener= licenseListener;
    }

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();

        String fullRead="";
        for (int i = 0; i < items.size(); ++i) {
            TextBlock textBlock = items.valueAt(i);
            String temp="";
            for (Text line : textBlock.getComponents()) {
                //extract scanned text lines here
                temp+=line.getValue().trim()+"-";
            }
            temp=temp.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
            fullRead+=temp+"-";
            Log.e("PreProcess", temp);

        }
        fullRead=fullRead.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");

        try {
            ILicensePlate iLicensePlate = LicensePlateManager.getInstance().extractLicense(fullRead);
            if(licenseListener!=null){
                licenseListener.onLicensePlate(iLicensePlate);
            }
        }catch (Exception e){
        }
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        mGraphicOverlay.clear();
    }
}
