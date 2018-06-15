package ocr.example.com.licenseplatereader.utils.ocr.filters.license_plate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ocr.example.com.licenseplatereader.utils.ocr.ILicensePlate;
import ocr.example.com.licenseplatereader.utils.ocr.LicensePlate;


public class UKDiplomaticLicensePlateFilter implements ILicensePlateFilter {

    private static String UK_LICENSE_PLATE ="([0-9]{3} [A-Z]{1} [0-9]{3})";
    private static String UK_LICENSE_PLATE_TEMP ="([0-9ILDS]{3} [A-Z]{1} [0-9ILDS]{3})";

    @Override
    public ILicensePlate extract(String src) throws IllegalArgumentException {

        String tempLicense="";
        Pattern patternPre = Pattern.compile(UK_LICENSE_PLATE_TEMP);
        Matcher matcher1 = patternPre.matcher(src);
        if(matcher1.find()){
            String group = matcher1.group(0);

            String[] split = group.split(" ");
            String init = split[0];
            init=init.replaceAll("I", "1");
            init=init.replaceAll("L", "1");
            init=init.replaceAll("D", "0");
            init=init.replaceAll("S", "5");

            String end = split[2];
            end=end.replaceAll("I", "1");
            end=end.replaceAll("L", "1");
            end=end.replaceAll("D", "0");
            end=end.replaceAll("S", "5");

            tempLicense=init+" "+split[1]+" "+end;
        }
       // Log.e("PostProcess UK", tempLicense);
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
