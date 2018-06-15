package ocr.example.com.licenseplatereader.utils.ocr.filters.license_plate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ocr.example.com.licenseplatereader.utils.ocr.ILicensePlate;
import ocr.example.com.licenseplatereader.utils.ocr.LicensePlate;


public class UK1983LicensePlateFilter implements ILicensePlateFilter {

    private static String UK_LICENSE_PLATE ="([A-Z]{1}[0-9]{1,3} [A-Z]{3})";
    private static String UK_LICENSE_PLATE_TEMP ="([A-Z]{1}[0-9ILDS]{1,3} [A-Z]{3})";

    @Override
    public ILicensePlate extract(String src) throws IllegalArgumentException {

        String tempLicense="";
        Pattern patternPre = Pattern.compile(UK_LICENSE_PLATE_TEMP);
        Matcher matcher1 = patternPre.matcher(src);
        if(matcher1.find()){
            String group = matcher1.group(0);
            String init = group.substring(0, 1);
            String middle="";
            String end = group.substring(group.length()-3, group.length());
            Pattern patternNumbers = Pattern.compile("[0-9ILDS]");
            Matcher matcher = patternNumbers.matcher(group);
            if(matcher.find()){
                middle = matcher.group(0);
                middle=middle.replaceAll("I", "1");
                middle=middle.replaceAll("L", "1");
                middle=middle.replaceAll("D", "0");
                middle=middle.replaceAll("S", "5");
                middle+=" ";
            }

            tempLicense=init+middle+end;
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
