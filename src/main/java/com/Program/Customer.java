package com.Program;

public class Customer implements Comparable<Customer>{

    String dpi;
    int budget;
    String date;

    public Customer (String dpi, int budget, String date){ //Constructor
        this.budget = budget;
        this.dpi = dpi;
        this.date = date;
    }

    @Override
    public int compareTo(Customer arg0) {

        if(budget < arg0.budget)
        {
            return -1;
        }
        else if (budget > arg0.budget){
            return 1;
        }
        else{
            return 0;
        }

    }

}
