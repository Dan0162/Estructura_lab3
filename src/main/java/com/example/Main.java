package com.example;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.jsonparsing.*;



public class Main {
    public static void main(String[] args) throws IOException {
        File auctions = new File("c:\\Users\\Dany\\Downloads\\input_auctions_example_lab_3(1).jsonl");
        File customers = new File("c:\\Users\\Dany\\Downloads\\input_customer_example_lab_3(1).jsonl");

        try (Scanner scanAunctions = new Scanner(auctions); Scanner scanCustomers = new Scanner(customers)) {
            while(scanAunctions.hasNextLine()){

                String auctionsLine = scanAunctions.nextLine();
                String customersLine = scanCustomers.nextLine();

                

                JsonNode auctionsNode = json.parse(auctionsLine);
                JsonNode customersNode = json.parse(customersLine);


                System.out.println(auctionsNode);
                System.out.println(customersNode);


            }
        }
    }
}