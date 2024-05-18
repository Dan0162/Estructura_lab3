package com.Program;

public class Customer implements Comparable<Customer>{

    String dpi;
    int budget;
    String date;
    int prio;

    public Customer (String dpi, int budget, String date, int prio){ //Constructor
        this.budget = budget;
        this.dpi = dpi;
        this.date = date;
        this.prio = prio;
    }

    //Usado para comparar en el maxheap, primero por budget y después si hay budgets iguales, se ordena por prioridades.
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
            if(prio < arg0.prio){
                return 1;
            }
            else if (prio > arg0.prio){
                return -1;
            }
            else{
                return 0;
            }
        }

    }

}
