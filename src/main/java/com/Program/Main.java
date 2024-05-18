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
        //Se abren los archivos para las problemáticas y la base de datos
        File auctions = new File("c:\\Users\\Dany\\Downloads\\input_auctions_challenge_lab_3.jsonl");
        File customers = new File("c:\\Users\\Dany\\Downloads\\input_customer_challenge_lab_3.jsonl");
        ObjectMapper defaultObjectMapper = new ObjectMapper();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Dany\\Desktop\\outputLab3.txt"));
            //Se dividó el archivo linea por linea
             Scanner scanAuctions = new Scanner(auctions))
              {

            //Se hace la lógica por cada línea
                while (scanAuctions.hasNextLine()) {
                    //Se crea un maxheap
                PriorityQueue<Customer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
                JsonNode auctionsNode = defaultObjectMapper.readTree(scanAuctions.nextLine());

                //Se ingresan todos los customers dentro del maxheap usando como parámetros el budget y la prioridad de entrada.
                int prio = 0;
                for (JsonNode customer : auctionsNode.get("customers")) {
                    maxHeap.add(new Customer(customer.get("dpi").asText(), customer.get("budget").asInt(), customer.get("date").asText(), prio));
                    prio++;
                }

                for (int i = 0; i < auctionsNode.get("rejection").asInt(); i++) {
                    maxHeap.poll();
                }

                //Se obtiene el budget ganador del primer valor del maxheap
                int winnerBudget = maxHeap.peek().budget;
                Customer winner = new Customer(null, winnerBudget, null, prio);
                Vector<Customer> possibleWinners = new Vector<Customer>();
                int lowestPrio = Integer.MAX_VALUE;

                //Se revisa cual es el customer con la menor prio
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

                //Se obtiene el objeto del customer con la menor prio dentro de la lista de possibleWinners
                for (Customer currCst:possibleWinners){
                    if (currCst.prio == lowestPrio){
                        winner = currCst;
                    }
                }


                //Se crea un documento txt donde se añaden los datos requeridos
                try (Scanner scanCustomers = new Scanner(customers)) {
                    while(scanCustomers.hasNextLine()){
                        JsonNode customersNode = defaultObjectMapper.readTree(scanCustomers.nextLine());
                        //Se recorre la bd y se encuentra el dpi que es el mismo que el ganador
                        if (winner.dpi.equals(customersNode.get("dpi").asText())) {
                            //Se crea un hash usando MD5 como base
                            try {
                                MessageDigest md = MessageDigest.getInstance("MD5");
                                byte[] messageDigest = md.digest(customersNode.toString().getBytes());
                                BigInteger bigInt = new BigInteger(1, messageDigest);
                                String hash = bigInt.toString(16);

                                System.out.println(customersNode.get("firstName").asText());

                                //Se ingresan los datos al archivo
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
