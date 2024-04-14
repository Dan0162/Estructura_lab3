package com.Program;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        File auctions = new File("c:\\Users\\Dany\\Downloads\\input_auctions_example_lab_3(1).jsonl");
        File customers = new File("c:\\Users\\Dany\\Downloads\\input_customer_example_lab_3(1).jsonl");
        ObjectMapper defaultObjectMapper = new ObjectMapper();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Dany\\Desktop\\output.txt"));
             Scanner scanAuctions = new Scanner(auctions))
              {

            while (scanAuctions.hasNextLine()) {
                PriorityQueue<Customer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
                JsonNode auctionsNode = defaultObjectMapper.readTree(scanAuctions.nextLine());

                for (JsonNode customer : auctionsNode.get("customers")) {
                    maxHeap.add(new Customer(customer.get("dpi").asText(), customer.get("budget").asInt(), customer.get("date").asText()));
                }

                for (int i = 0; i < auctionsNode.get("rejection").asInt(); i++) {
                    maxHeap.poll();
                }

                Customer winner = maxHeap.peek();
                Scanner scanCustomers = new Scanner(customers);

                //System.out.println(winner.dpi);
                int i = 0;
                while(scanCustomers.hasNextLine()){
                    JsonNode customersNode = defaultObjectMapper.readTree(scanCustomers.nextLine());
                    if (winner.dpi.equals(customersNode.get("dpi").asText())) {
                        try {
                            MessageDigest md = MessageDigest.getInstance("MD5");
                            byte[] messageDigest = md.digest(customersNode.toString().getBytes());
                            BigInteger bigInt = new BigInteger(1, messageDigest);
                            String hash = bigInt.toString(16);

                            writer.write("{\"dpi\":\"" + customersNode.get("dpi").asText());
                            writer.write("\",\"budget\":\"" + winner.budget);
                            writer.write("\"date\":\"" + winner.date);
                            writer.write("\"firstName\":\"" + customersNode.get("firstName").asText());
                            writer.write("\"lastName\":\"" + customersNode.get("lastName").asText());
                            writer.write("\"birthDate\":\"" + customersNode.get("birthDate").asText());
                            writer.write("\"job\":\"" + customersNode.get("job").asText());
                            writer.write("\"placeJob\":\"" + customersNode.get("placeJob").asText());
                            writer.write("\"salary\":\"" + customersNode.get("salary").asText());
                            writer.write("\"property\":\"" + auctionsNode.get("property").asText());
                            writer.write("\"signature\":\"" + hash + "\n");

                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                }
                System.out.println(i);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
