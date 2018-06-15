package ocr.example.com.licenseplatereader.utils.ocr.filters.license_plate;


import ocr.example.com.licenseplatereader.utils.ocr.ILicensePlate;

public interface ILicensePlateFilter {
    ILicensePlate extract(String src) throws IllegalArgumentException;
}
