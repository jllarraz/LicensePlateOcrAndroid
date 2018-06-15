package ocr.example.com.licenseplatereader.utils.ocr.filters.license_plate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ocr.example.com.licenseplatereader.utils.ocr.ILicensePlate;
import ocr.example.com.licenseplatereader.utils.ocr.LicensePlate;


public class NortherIrelandLicensePlateFilter implements ILicensePlateFilter {

    private static String NORTHERN_IRELAND_LICENSE_PLATE ="([A-Z]{3} [0-9]{4})";
    private static String UK_LICENSE_PLATE_TEMP ="([A-Z]{3} [0-9ILDS]{4})";

    @Override
    public ILicensePlate extract(String src) throws IllegalArgumentException {

        String tempLicense="";
        Pattern patternPre = Pattern.compile(UK_LICENSE_PLATE_TEMP);
        Matcher matcher1 = patternPre.matcher(src);
        if(matcher1.find()){
            String group = matcher1.group(0);
            String areaCode = group.substring(0, 3);

            String year = group.substring(3, group.length());
            year=year.replaceAll("I", "1");
            year=year.replaceAll("L", "1");
            year=year.replaceAll("D", "0");
            year=year.replaceAll("S", "5");

            tempLicense=areaCode+year;
        }
        //Log.e("PostProcess NI", tempLicense);
        Pattern pattern = Pattern.compile(NORTHERN_IRELAND_LICENSE_PLATE);
        // String text = graphic.getTextBlock().getValue().trim();
        Matcher matcher = pattern.matcher(tempLicense);
        if(matcher.find()){
            ILicensePlate license = new LicensePlate("GBR", matcher.group(0));
            return license;
        }
        throw new IllegalArgumentException("NI License not valid");
    }
}
