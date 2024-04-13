package com.example;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.jsonparsing.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("c:\\Users\\Dany\\Downloads\\input_lab_2_example(2).jsonl");

        try (Scanner scan = new Scanner(file)) {
            while(scan.hasNextLine()){

                String line = scan.nextLine();

                JsonNode node = json.parse(line);
                
            }
        }
    }
}