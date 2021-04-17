package com.example.myfirstapp;

public class Bottle {

    private double energy_use = 0.0;

    public double getEnergy_use() {
        return energy_use;
    }

    public double getEmissions() {
        return emissions;
    }

    public double getRecycled() {
        return recycled;
    }

    public int getEcof() {
        return ecof;
    }

    public int getTransport() {
        return Transport;
    }

    public String getTransCode() {
        return transCode;
    }

    private double emissions = 0.0;
    private double recycled = 0.0;
    private int ecof = 0;
    private int Transport = 0;

    //The key to the value thing
    private String transCode = "";

    private String city = "";

    public String getCity() {
        return city;
    }

    public String getSoft_drink_prod() {
        return soft_drink_prod;
    }

    public String getRetail() {
        return retail;
    }

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
