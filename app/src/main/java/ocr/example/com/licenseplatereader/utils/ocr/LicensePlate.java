package ocr.example.com.licenseplatereader.utils.ocr;

import android.os.Parcel;

public class LicensePlate implements ILicensePlate {

    private String countryCode;
    private String drivingLicense;

    public LicensePlate(){
        this(null, null);
    }

    public LicensePlate(String countryCode, String drivingLicense){
        this.countryCode=countryCode;
        this.drivingLicense=drivingLicense;
    }



    @Override
    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String getLicensePlate() {
        return drivingLicense;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(drivingLicense!=null?1:0);
        if(drivingLicense!=null){
            dest.writeString(drivingLicense);
        }

        dest.writeInt(countryCode!=null?1:0);
        if(countryCode!=null){
            dest.writeString(countryCode);
        }
    }

    public static final Creator<ILicensePlate> CREATOR = new Creator<ILicensePlate>() {
        public ILicensePlate createFromParcel(Parcel in) {
            return new LicensePlate(in);
        }

        public ILicensePlate[] newArray(int size) {
            return new LicensePlate[size];
        }
    };

    private LicensePlate(Parcel in){
        try {
            boolean isDrivingLicenseNull = in.readInt() == 1;
            if(isDrivingLicenseNull){
                drivingLicense= in.readString();
            }

            boolean isCountryCodeNull = in.readInt() == 1;
            if(isCountryCodeNull){
                countryCode= in.readString();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
