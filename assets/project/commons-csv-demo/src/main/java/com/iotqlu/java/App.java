package com.iotqlu.java;

import java.io.*;
import org.apache.commons.csv.*;

public class App {

    public void parseExcelCSV(String filePath) {
        try {
            Reader in = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader("name", "sex", "age", "height", "weight")
                    .parse(in);
            for (CSVRecord record : records) {
                String name = record.get("name");
                String sex = record.get(2); // 1-based
                String age = record.get("age");
                String height = record.get("height");
                String weight = record.get("weight");

                System.out.println(name);
                System.out.println(sex);
                System.out.println(age);
                System.out.println(height);
                System.out.println(weight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.parseExcelCSV(args[0]);
    }
}
