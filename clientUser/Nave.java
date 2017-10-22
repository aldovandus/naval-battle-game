/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Color;

/**
 *
 * @author Kotss
 */
public class Nave {
    private int colpiRimanenti;
    private Color colore;
    private int rotazione;
    
    Nave()
    {
        this.rotazione = 0;
    }
    
    public int getColpiRimanenti() {
        return colpiRimanenti;
    }

    public void setColpiRimanenti(int colpiRimanenti) {
        this.colpiRimanenti = colpiRimanenti;
    }
    
    public void colpito()
    {
        this.colpiRimanenti--;
    }
    
    public boolean naveDistrutta()
    {
        if(this.colpiRimanenti > 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Color getColore() {
        return colore;
    }

    public void setColore(Color colore) {
        this.colore = colore;
    }

    public int getRotazione() {
        return rotazione;
    }

    public void setRotazione(int rotazione) {
        this.rotazione = rotazione;
    }
    
    
    
}
