package ocr.example.com.licenseplatereader.utils.ocr.filters.license_plate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ocr.example.com.licenseplatereader.utils.ocr.ILicensePlate;
import ocr.example.com.licenseplatereader.utils.ocr.LicensePlate;

public class UKCurrentLicensePlateFilter implements ILicensePlateFilter {

    private static String UK_LICENSE_PLATE ="([A-Z]{2}[0-9]{2} [A-Z]{3})";
    private static String UK_LICENSE_PLATE_TEMP ="([A-Z]{2}[0-9ILDS]{2} [A-Z]{3})";

    @Override
    public ILicensePlate extract(String src) throws IllegalArgumentException {

        String tempLicense="";
        Pattern patternPre = Pattern.compile(UK_LICENSE_PLATE_TEMP);
        Matcher matcher1 = patternPre.matcher(src);
        if(matcher1.find()){
            String group = matcher1.group(0);
            String areaCode = group.substring(0, 2);

            String year = group.substring(2, 4);
            year=year.replaceAll("I", "1");
            year=year.replaceAll("L", "1");
            year=year.replaceAll("D", "0");
            year=year.replaceAll("S", "5");

            String random = group.substring(4, group.length());

            tempLicense=areaCode+year+random;
        }
        //Log.e("PostProcess UK", tempLicense);
        Pattern pattern = Pattern.compile(UK_LICENSE_PLATE);
        // String text = graphic.getTextBlock().getValue().trim();
        Matcher matcher = pattern.matcher(tempLicense);
        if(matcher.find()){
            ILicensePlate license = new LicensePlate("GBR", matcher.group(0));
            return license;
        }
        throw new IllegalArgumentException("UK License not valid");
    }
}
