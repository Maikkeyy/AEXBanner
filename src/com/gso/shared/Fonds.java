package com.gso.shared;

import com.gso.IFonds;

import java.io.Serializable;

/**
 * Created by Maikkeyy on 4-4-2017.
 */
public class Fonds implements IFonds, Serializable {
    private String naam;
    private double koers;

    public Fonds(String naam, double koers) {
        this.naam = naam;
        this.koers = koers;
    }

    public String getNaam() {
        return this.naam;
    }

    public double getKoers() {
        return this.koers;
    }

    public void setKoers(double koers) {
        this.koers = koers;
    }
}
