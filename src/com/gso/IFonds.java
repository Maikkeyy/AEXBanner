package com.gso;

/**
 * Created by Maikkeyy on 28-3-2017.
 */
public interface IFonds {
    String getNaam();
    double getKoers();
    void setKoers(double koers); // anders kan je de koers nooit aanpassen
}
