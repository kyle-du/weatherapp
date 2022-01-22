package com.example.weatherapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@SpringBootApplication
@RestController
public class WeatherAPI {

    public static void main(String[] args) {
        SpringApplication.run(WeatherAPI.class, args);
    }

    @GetMapping("/")
    public String home() {
        String htmlHeader = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>Weather Service</title>" +
                "<meta name = 'viewport'content = 'width = device-width, initial-scale = 1.0'>" +
                "<link rel = 'stylesheet' href = 'https://www.w3schools.com/w3css/4/w3.css'>" +
                "<style>" +
                "#div1 {" +
                "background-color: white;" +
                "width: 300px;" +
                "border: 5px solid lightgrey;" +
                "padding: 15px;" +
                "margin: auto;" +
                "margin-top: 15px;" +
                "margin-bottom: 15px;" +
                "font-family: Trebuchet MS, sans-serif;" +
                "border-radius: 15px;" +
                "}" +
                "table {" +
                "margin: auto;" +
                "width: 280px;" +
                "border-collapse: collapse;" +
                "font-size: 15px;" +
                "}" +
                "body {" +
                "background: rgb(2,0,36);" +
                "background: linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(24,103,101,1) 68%, rgba(0,212,255,1) 100%);" +
                "}" +
                "select {" +
                "margin: auto;" +
                "}" +
                "input {" +
                "text-align: center;" +
                "}" +
                "form {" +
                "margin: auto;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h2 style='border-radius: 15px;color:blue;text-align:center;background-color: white;" +
                "width:500px;border:5px solid lightgrey;padding:15px;margin:auto;font-family:Trebuchet MS, sans-serif;margin-top:20px;'>" +
                "U.S. City Weather" +
                "</h2>";
        String htmlFooter = "</body>" +
                "</html>";
        String input = "<div id = 'div1'>" +
                "<form action ='weather'>" +
                "<div style = 'text-align: center'>" +
                "<select name = 'city'>" +
                "<option value = 'Pleasanton'>Pleasanton</option>" +
                "<option value = 'Seattle'>Seattle</option>" +
                "<option value = 'Atlanta'>Atlanta</option>" +
                "<option value = 'Washington D.C.'>Washington D.C.</option>" +
                "<option value = 'New York'>New York</option>" +
                "<option value = 'Houston'>Houston</option>" +
                "<option value = 'Los Angeles'>Los Angeles</option>" +
                "<option value = 'Chicago'>Chicago</option>" +
                "<option value = 'San Francisco'>San Francisco</option>" +
                "</select>" +
                "</div>" +
                "<br>" +
                "<div style = 'text-align: center'><input type ='submit'></div>" +
                "</form>" +
                "</div>";

        return htmlHeader + input + htmlFooter;
    }

    @GetMapping("weather")
    public String weather(@RequestParam(value = "city") String city) {
        Double[] pleasantonCoords = {37.6604, -121.8758};
        Double[] seattleCoords = {47.6062, -122.3321};
        Double[] atlantaCoords = {33.7490, -84.3880};
        Double[] nyCoords = {40.7128, -74.0060};
        Double[] wdcCoords = {38.9072, -77.0369};
        Double[] houstonCoords = {29.7604, -95.3698};
        Double[] laCoords = {34.0522, -118.2437};
        Double[] chicagoCoords = {41.8781, -87.6298};
        Double[] sfCoords = {37.7749, -122.4194};

        HashMap<String, Double[]> longLat = new HashMap<>();
        longLat.put("Pleasanton", pleasantonCoords);
        longLat.put("Seattle", seattleCoords);
        longLat.put("Atlanta", atlantaCoords);
        longLat.put("New York", nyCoords);
        longLat.put("Washington D.C.", wdcCoords);
        longLat.put("Houston", houstonCoords);
        longLat.put("Los Angeles", laCoords);
        longLat.put("Chicago", chicagoCoords);
        longLat.put("San Francisco", sfCoords);

        ArrayList<PeriodData> weatherDataAL = null;
        String htmlReturn = null;
        int flag = 0;
        try {
            weatherDataAL = Data.formData(longLat.get(city));
        } catch (IOException e) {
            flag = 1;
        }
        if (flag == 0) {
            ArrayList<String> dataTextAL = new ArrayList<>();
            String dataText = "";
            if (!weatherDataAL.get(0).getName().equals("Tonight")) {
                for (int i = 0; i < 7; i++) {
                    int highTemp = (int) (double) weatherDataAL.get(i * 2).getTemperature();
                    int lowTemp = (int) (double) weatherDataAL.get(i * 2 + 1).getTemperature();
                    String name = weatherDataAL.get(i * 2).getName();
                    String name2 = "" + name.charAt(0) + name.charAt(1) + name.charAt(2);
                    if (i == 0) {
                        name2 = "Today";
                    }
                    String html = "<div id = 'rcorners1'>" +
                            "<table>" +
                            "<colgroup>" +
                            "<col span='1' style='width:50%'>" +
                            "<col span='1' style='width: 25%'>" +
                            "<col span='1' style='width: 25%'>" +
                            "</colgroup>" +
                            "<tr width>" +
                            "<td>" + name2 + "</td>" +
                            "<td>H: " + highTemp + "°</td>" +
                            "<td>L: " + lowTemp + "°</td>" +
                            "</tr>" +
                            "</table>" +
                            "</div>";
                    dataTextAL.add(html);
                    dataText += dataTextAL.get(i);
                }
            } else {
                for (int i = 0; i < 7; i++) {
                    int lowTemp = -1;
                    String highTemp = "Error";
                    String name2 = "";
                    if (i != 0) {
                        highTemp = String.valueOf((int) (double) weatherDataAL.get(i * 2 - 1).getTemperature());
                        lowTemp = (int) (double) weatherDataAL.get(i * 2).getTemperature();
                        String name = weatherDataAL.get(i * 2 - 1).getName();
                        name2 = "" + name.charAt(0) + name.charAt(1) + name.charAt(2);
                    } else {
                        lowTemp = (int) (double) weatherDataAL.get(0).getTemperature();
                        highTemp = "N/A";
                        name2 = "Today";
                    }
                    String html = "<div id = 'rcorners1'>" +
                            "<table>" +
                            "<colgroup>" +
                            "<col span='1' style='width:50%'>" +
                            "<col span='1' style='width: 25%'>" +
                            "<col span='1' style='width: 25%'>" +
                            "</colgroup>" +
                            "<tr width>" +
                            "<td>" + name2 + "</td>" +
                            "<td>H: " + highTemp + "°</td>" +
                            "<td>L: " + lowTemp + "°</td>" +
                            "</tr>" +
                            "</table>" +
                            "</div>";
                    dataTextAL.add(html);
                    dataText += dataTextAL.get(i);
                }
            }

            weatherDataAL.clear();
            String htmlHeader = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<title>Weather Service</title>" +
                    "<meta name = 'viewport'content = 'width = device-width, initial-scale = 1.0'>" +
                    "<link rel = 'stylesheet' href = 'https://www.w3schools.com/w3css/4/w3.css'>" +
                    "<style>" +
                    "div {" +
                    "background-color: white;" +
                    "width: 300px;" +
                    "border: 5px solid lightgrey;" +
                    "padding: 15px;" +
                    "margin: auto;" +
                    "margin-top: 15px;" +
                    "margin-bottom: 15px;" +
                    "font-family: Trebuchet MS, sans-serif;" +
                    "}" +
                    "table {" +
                    "margin: auto;" +
                    "width: 280px;" +
                    "border-collapse: collapse;" +
                    "font-size: 15px;" +
                    "}" +
                    "body {" +
                    "background: rgb(2,0,36);" +
                    "background: linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(24,103,101,1) 68%, rgba(0,212,255,1) 100%);" +
                    "}" +
                    "#rcorners1 {" +
                    "  border-radius: 15px;" +
                    "}" +
                    "input {" +
                    "background-color: white;" +
                    "border: 5px solid lightgrey;" +
                    "font-family: Trebuchet MS, sans-serif;" +
                    "font-size: 10px;" +
                    "width: 50;" +
                    "height: 40px;" +
                    "}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<input id = 'rcorners1' class='w3-display-topright w3-padding' type = 'button' onclick = 'document.location = \"/\"' value = 'Go Back'>";
            String htmlBody = "<h2 id = 'rcorners1' style='color:blue;text-align:center;background-color: white;" +
                    "width:500px;border:5px solid lightgrey;padding:15px;margin:auto;font-family:Trebuchet MS, sans-serif;margin-top:20px;'>" +
                    city + " Weather" +
                    "</h2>" +
                    dataText;
            String htmlFooter = "</body>" +
                    "</html>";
            htmlReturn = htmlHeader + htmlBody + htmlFooter;
        } else {
            htmlReturn = "Site is down, please try again later.";
        }
        return htmlReturn;
    }
}
            