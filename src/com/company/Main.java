package com.company;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("data.csv");
        WeatherAnalyzer myAnalyzer = new WeatherAnalyzer();
        myAnalyzer.makeResults(file);
    }
}