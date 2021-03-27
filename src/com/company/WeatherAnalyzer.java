package com.company;

import java.io.*;
import java.util.*;


public class WeatherAnalyzer {

    public void makeResults(File file) throws IOException {
        ArrayList<WeatherString> data = new ArrayList<>();

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        HashMap<String, Double> averages = new HashMap<>();
        HashMap<String, Integer> cardinalPoints = new HashMap<>();
        String city = "";

        averages.put("Temperature", 0.0);
        averages.put("Humidity", 0.0);
        averages.put("Wind Speed", 0.0);

        cardinalPoints.put("North", 0);
        cardinalPoints.put("Northeast", 0);
        cardinalPoints.put("East", 0);
        cardinalPoints.put("Southeast", 0);
        cardinalPoints.put("South", 0);
        cardinalPoints.put("Southwest", 0);
        cardinalPoints.put("West", 0);
        cardinalPoints.put("Northwest", 0);

        String result;
        int numberOfString = 0;

        while((result = bufferedReader.readLine()) != null) {
            if(numberOfString == 0){
                city = result.split(",")[1];
            }

            if(numberOfString >= 10){
                List<String> datas = Arrays.asList(result.split(","));
                WeatherString weatherString = new WeatherString(toConvertToNormalDate(datas.get(0)),
                        datas.get(1), datas.get(2), datas.get(3), datas.get(4),
                        toConvertToNormalTime(datas.get(0)));

                data.add(weatherString);
            }

            numberOfString++;
        }

        bufferedReader.close();

        for (WeatherString datum : data) {
            averages.put("Temperature", averages.get("Temperature") + datum.getTemperature());
            averages.put("Humidity", averages.get("Humidity") + datum.getHumidity());
            averages.put("Wind Speed", averages.get("Wind Speed") + datum.getWindSpeed());

            String detectedSide = detectSideOfTheWorld(datum);
            cardinalPoints.put(detectedSide, cardinalPoints.get(detectedSide) + 1);
        }


        averages.put("Temperature", (double) Math.round((averages.get("Temperature") / data.size())));
        averages.put("Humidity", (double) Math.round((averages.get("Humidity") / data.size())));
        averages.put("Wind Speed", (double) Math.round((averages.get("Wind Speed") / data.size())));

        WeatherString mostLowTemperatureResult = detectMostLowTemperature(data);
        WeatherString mostHighTemperatureResult = detectMostHighTemperature(data);
        WeatherString mostStrongWind = detectMostStrongWind(data);
        WeatherString mostLowHumidity = detectMostLowHumidity(data);

        HashMap<Integer, String> reversedCardinalPoints = toReverseMap(cardinalPoints);
        Set<Integer> sortedKeysSet = reversedCardinalPoints.keySet();
        ArrayList<Integer> sortedKeysList = new ArrayList<>(sortedKeysSet);
        Collections.sort(sortedKeysList);
        String oftenWind = reversedCardinalPoints.get(sortedKeysList.get(sortedKeysList.size() - 1)) + ", " +
                sortedKeysList.get(sortedKeysList.size() - 1);

        writeDataToFile(oftenWind, mostHighTemperatureResult, mostLowTemperatureResult, mostStrongWind,
                mostLowHumidity, averages, city);
    }

    private String toConvertToNormalDate(String d){
        String date = d.split("T")[0];

        return date.substring(0, 4) + "." + date.substring(4, 6) + "." + date.substring(6, 8);
    }

    private String toConvertToNormalTime(String d){
        String date = d.split("T")[1];

        return date.substring(0, 2) + ":" + date.substring(2, 4);
    }

    private WeatherString detectMostLowTemperature(List<WeatherString> data){
        WeatherString result = data.get(0);

        for(WeatherString elem : data){
            if(elem.getTemperature() < result.getTemperature()){
                result = elem;
            }
        }

        return result;
    }

    private WeatherString detectMostHighTemperature(List<WeatherString> data){
        WeatherString result = data.get(0);

        for(WeatherString elem : data){
            if(elem.getTemperature() > result.getTemperature()){
                result = elem;
            }
        }

        return result;
    }

    private WeatherString detectMostStrongWind(List<WeatherString> data){
        WeatherString result = data.get(0);

        for(WeatherString elem : data){
            if(elem.getHumidity() < result.getHumidity()){
                result = elem;
            }
        }

        return result;
    }

    private WeatherString detectMostLowHumidity(List<WeatherString> data){
        WeatherString result = data.get(0);

        for(WeatherString elem : data){
            if(elem.getWindSpeed() > result.getWindSpeed()){
                result = elem;
            }
        }

        return result;
    }

    private String detectSideOfTheWorld(WeatherString data){
        String[] sides = {"North", "Northeast", "East", "Southeast", "South", "Southwest", "West", "Northwest", "North"};
        double degrees = data.getWindDirection();
        int result = (int) Math.round(degrees / 45);

        return sides[result];
    }

    private HashMap<Integer, String> toReverseMap(Map<String, Integer> data){
        HashMap<Integer, String> result = new HashMap<>();

        for(String elem : data.keySet()){
            result.put(data.get(elem), elem);
        }

        return result;
    }

    private void writeDataToFile(String oftenWind, WeatherString mostHighTemperature, WeatherString mostLowTemperature,
                                 WeatherString mostStrongWind, WeatherString mostLowHumidity,
                                 Map<String, Double> averages, String city) throws IOException {
        FileWriter  fileWriter = new FileWriter("result.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(city.toUpperCase() + "\n");
        bufferedWriter.write("\n");
        bufferedWriter.write("Average temperature: " + averages.get("Temperature") + "\n");
        bufferedWriter.write("Average humidity: " + averages.get("Humidity") + "\n");
        bufferedWriter.write("Average wind speed: " + averages.get("Wind Speed") + "\n");
        bufferedWriter.write("\n");
        bufferedWriter.write("The most high temperature: " + mostHighTemperature.getTemperature() + " " +
                mostHighTemperature.getDate() + " at " + mostHighTemperature.getTime() + "\n");
        bufferedWriter.write("The most low temperature: " + mostLowTemperature.getTemperature() + " " +
                mostLowTemperature.getDate() + " at " + mostLowTemperature.getTime() + "\n");
        bufferedWriter.write("The most strong wind: " + mostStrongWind.getWindSpeed() + " " +
                mostStrongWind.getDate() + " at " + mostStrongWind.getTime() + "\n");
        bufferedWriter.write("The most low humidity: " + mostLowHumidity.getHumidity() + " " +
                mostLowHumidity.getDate() + " at " + mostLowHumidity.getTime() + "\n");
        bufferedWriter.write("\n");
        bufferedWriter.write("Most frequent wind direction: " + oftenWind + "\n");

        bufferedWriter.close();
    }

}
