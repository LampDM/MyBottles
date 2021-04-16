package com.example.myfirstapp;

public class Bottle {

    private double energy_use = 0.0;
    private double emissions = 0.0;
    private double recycled = 0.0;
    private int ecof = 0;
    private int Transport = 0;

    //The key to the value thing
    private String transCode = "";

    private String city = "";

    private String soft_drink_prod = "";
    private String retail = "";

    public Bottle(double eu, double em,double re,int ec,int Tr,String trans,String ci,String so,String ret){
        energy_use = eu;
        emissions = em;
        recycled = re;
        ecof = ec;
        Transport = Tr;
        transCode =trans;
        city = ci;
        soft_drink_prod = so;
        retail = ret;
    }

}
