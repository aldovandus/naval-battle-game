/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.Serializable;

/**
 *
 * @author aldo
 */
public class Attacco implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private int id;
    private int esitoAttacco;
    private int esitoPartita;

    Attacco(int id,int esitoAttacco,int esitoPartita)
    {
        this.id = id;
        this.esitoAttacco = esitoAttacco;
        this.esitoPartita = esitoPartita;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEsitoAttacco(int esitoAttacco) {
        this.esitoAttacco = esitoAttacco;
    }

    public void setEsitoPartita(int esitoPartita) {
        this.esitoPartita = esitoPartita;
    }

    public int getId() {
        return id;
    }

    public int getEsitoAttacco() {
        return esitoAttacco;
    }

    public int getEsitoPartita() {
        return esitoPartita;
    }
    
    @Override
    public String toString() {
        return "id " + this.id + "esito attacco : " + this.esitoAttacco + "esito partita : " + this.esitoPartita;
    }
    
    
}
