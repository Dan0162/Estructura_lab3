package com.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("c:\\Users\\Dany\\Desktop\\olaputs.txt");

        try (Scanner scan = new Scanner(file)) {
            while(scan.hasNextLine())        {
                String line = scan.nextLine();
                System.out.println(line);
            }
        }
    }
}