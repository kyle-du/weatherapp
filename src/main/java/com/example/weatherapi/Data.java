package com.example.weatherapi;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    public static ArrayList formData(Double[] longLat) throws IOException {

        double longitude = longLat[0];
        double latitude = longLat[1];

      URL outerData = new URL("https://api.weather.gov/points/" + longitude + "," + latitude);

        InputStreamReader myReader = new InputStreamReader(outerData.openStream());

        Gson gson = new Gson();
        HashMap hmap1 = (HashMap) gson.fromJson(myReader, HashMap.class);
        LinkedTreeMap tmap1 = (LinkedTreeMap) hmap1.get("properties");
        myReader.close();

        URL innerData = new URL("" + tmap1.get("forecast"));
        InputStreamReader myReader2 = new InputStreamReader(innerData.openStream());

        HashMap hmap2 = (HashMap) gson.fromJson(myReader2, HashMap.class);
        LinkedTreeMap tmap2 = (LinkedTreeMap) hmap2.get("properties");
        ArrayList alist2 = (ArrayList) tmap2.get("periods");

        ArrayList<PeriodData> dataMap = new ArrayList<>();
        myReader2.close();

        for (int i = 0; i < 14; i++) {
            LinkedTreeMap tempTMap = (LinkedTreeMap) alist2.get(i);
            PeriodData tempPeriodData = new PeriodData();
            tempPeriodData.setName((String) tempTMap.get("name"));
            tempPeriodData.setTemperature((Double) tempTMap.get("temperature"));
            dataMap.add(tempPeriodData);
        }

        return (dataMap);
    }
}

class PeriodData {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getShortForecast() {
        return shortForecast;
    }

    public void setShortForecast(String shortForecast) {
        this.shortForecast = shortForecast;
    }

    private String name;
    private String startTime;
    private String endTime;
    private Double temperature;
    private String windSpeed;
    private String shortForecast;
}