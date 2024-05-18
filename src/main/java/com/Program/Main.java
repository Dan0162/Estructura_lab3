package com.Program;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        File auctions = new File("c:\\Users\\Dany\\Downloads\\input_auctions_challenge_lab_3.jsonl");
        File customers = new File("c:\\Users\\Dany\\Downloads\\input_customer_challenge_lab_3.jsonl");
        ObjectMapper defaultObjectMapper = new ObjectMapper();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Dany\\Desktop\\outputLab3.txt"));
             Scanner scanAuctions = new Scanner(auctions))
              {

            while (scanAuctions.hasNextLine()) {
                PriorityQueue<Customer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
                JsonNode auctionsNode = defaultObjectMapper.readTree(scanAuctions.nextLine());

                int prio = 0;
                for (JsonNode customer : auctionsNode.get("customers")) {
                    maxHeap.add(new Customer(customer.get("dpi").asText(), customer.get("budget").asInt(), customer.get("date").asText(), prio));
                    prio++;
                }

                for (int i = 0; i < auctionsNode.get("rejection").asInt(); i++) {
                    maxHeap.poll();
                }

                int winnerBudget = maxHeap.peek().budget;
                Customer winner = new Customer(null, winnerBudget, null, prio);
                Vector<Customer> possibleWinners = new Vector<Customer>();
                int lowestPrio = Integer.MAX_VALUE;

                while(maxHeap.size()>0){
                    if (maxHeap.peek().budget == winnerBudget){

                        if (maxHeap.peek().prio < lowestPrio){
                            lowestPrio = maxHeap.peek().prio;
                        }
                        possibleWinners.add(maxHeap.poll());
                    }
                    else{
                        maxHeap.poll();
                    }
                }

                for (Customer currCst:possibleWinners){
                    if (currCst.prio == lowestPrio){
                        winner = currCst;
                    }
                }


                try (Scanner scanCustomers = new Scanner(customers)) {
                    while(scanCustomers.hasNextLine()){
                        JsonNode customersNode = defaultObjectMapper.readTree(scanCustomers.nextLine());
                        if (winner.dpi.equals(customersNode.get("dpi").asText())) {
                            try {
                                MessageDigest md = MessageDigest.getInstance("MD5");
                                byte[] messageDigest = md.digest(customersNode.toString().getBytes());
                                BigInteger bigInt = new BigInteger(1, messageDigest);
                                String hash = bigInt.toString(16);

                                System.out.println(customersNode.get("firstName").asText());

                                writer.write("{\"dpi\":" + customersNode.get("dpi").asText());
                                writer.write(",\"budget\":" + winner.budget);
                                writer.write(",\"date\":\"" + winner.date + "\"");
                                writer.write(",\"firstName\":" + customersNode.get("firstName"));
                                writer.write(",\"lastName\":" + customersNode.get("lastName"));
                                writer.write(",\"birthDate\":" + customersNode.get("birthDate"));
                                writer.write(",\"job\":" + customersNode.get("job"));
                                writer.write(",\"placeJob\":" + customersNode.get("placeJob"));
                                writer.write(",\"salary\":" + customersNode.get("salary").asText());
                                writer.write(",\"property\":" + auctionsNode.get("property"));
                                writer.write(",\"signature\":" + hash + "}\n");

                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                            break;
                        }

                    }
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
