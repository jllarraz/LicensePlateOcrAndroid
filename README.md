# LicensePlateAndroid

Sample project to read license plates. Currently it's able to read Irish, United Kingdom and Northern Ireland license plates.

You can use the example images stored under `examples` to test the application.

![alt text](https://github.com/jllarraz/LicensePlateOcrAndroid/blob/master/examples/ireland.png)

This project is based in the information and tutorials found in (https://developers.google.com/vision/)

## Youtube

You can see how the application works here

https://youtu.be/eA4fmuZumyE

## Build & Run

```
    1. Clone Repository
    2. Open with Android Studio
    3. Configure Android SDK
    4. Launch application
```

## Customisation

If you want to add or remove a new license plate, you can do it creating a new filter under `ocr.example.com.licenseplatereader.utils.ocr.filters`. You can use as example `IrishLicensePlateFilter` and then adding it to `LicensePlateManager`.

For example


```
//Adding the license plate filters
private LicensePlateManager(){
        filters= new ArrayList<>();
        filters.add(new IrishLicensePlateFilter());
        filters.add(new NortherIrelandLicensePlateFilter());
        filters.add(new UKCurrentLicensePlateFilter());       
        filters.add(new UKDiplomaticLicensePlateFilter());
    }
```

## License

Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

