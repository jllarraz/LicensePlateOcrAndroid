package ocr.example.com.licenseplatereader.utils.ocr;

import android.os.Parcelable;

public interface ILicensePlate extends Parcelable {
    /**
     * Country code
     * @return ISO 3166-1 alpha-3
     */
    String getCountryCode();
    String getLicensePlate();
}
