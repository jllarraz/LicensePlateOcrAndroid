package ocr.example.com.licenseplatereader.utils.ocr.filters.license_plate;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ocr.example.com.licenseplatereader.utils.ocr.ILicensePlate;
import ocr.example.com.licenseplatereader.utils.ocr.LicensePlate;


public class IrishLicensePlateFilter implements ILicensePlateFilter {

    private static String IRISH_LICENSE_PLATE ="([0-9]{2,3}-[A-Z]{1,2}-[0-9]{1,})";
    private static String IRISH_LICENSE_PLATE_TEMP ="([0-9ILD]{2,3}-[A-Z]{1,2}-[0-9ILD]{1,})";

    @Override
    public ILicensePlate extract(String src) throws IllegalArgumentException {

        String tempLicense="";
        Pattern patternPre = Pattern.compile(IRISH_LICENSE_PLATE_TEMP);
        Matcher matcher1 = patternPre.matcher(src);
        if(matcher1.find()){
            String group = matcher1.group(0);
            String[] split = group.split("-");
            String first = split[0];
            first=first.replaceAll("I", "1");
            first=first.replaceAll("L", "1");
            first=first.replaceAll("D", "0");
            split[0]=first;

            String last = split[2];
            last=last.replaceAll("I", "1");
            last=last.replaceAll("L", "1");
            last=last.replaceAll("D", "0");
            split[2]=last;

            tempLicense="";
            for(String str: split){
                tempLicense+=str+"-";
            }

        }
        Log.e("PostProcess Irish", tempLicense);
        Pattern pattern = Pattern.compile(IRISH_LICENSE_PLATE);
        // String text = graphic.getTextBlock().getValue().trim();
        Matcher matcher = pattern.matcher(tempLicense);
        if(matcher.find()){
            ILicensePlate license = new LicensePlate("IRL", matcher.group(0));
            return license;
        }
        throw new IllegalArgumentException("Irish License not valid");
    }
}
