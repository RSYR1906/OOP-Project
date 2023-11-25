package org.sc2002.utils;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {

    public static void main(String[] args) {
        ArrayList<String> committees = new ArrayList<>();
        committees.add("Committee1");
        committees.add("Committee2");
        committees.add("Committee3");

        String staffID = "123"; // 假设这是 staffID 的值

        writeCSV(committees, "test.csv");
    }

    public static void writeCSV(String string, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))) {
            writer.write(string);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCSV(List<String> strings, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))) {
            // 写入标题行
            for (String string : strings) {
                writer.write(string);
                writer.write(",");
                writer.newLine();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCSV() {
    }
}
