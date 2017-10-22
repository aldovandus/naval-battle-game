/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author aldo
 */
public class Griglia extends JPanel{
    
    private Nave naveCorrente;
    
    Griglia()
    {
         this.setPreferredSize(new Dimension(400,400));
         this.setLayout(new GridLayout(10, 10));
         this.naveCorrente = null;
    }
    
    void aggiungiCella(Cella cella,MouseListener listener)
    {
  
        cella.addMouseListener(listener);
        this.add(cella);
    }

   
    
    public boolean verificaCellaOccupata(int id,boolean esito)
    {
       
        if(this.naveCorrente.getRotazione()==0)
        {
            for(int i=0;i<this.naveCorrente.getColpiRimanenti();i++)
            {
                if(((Cella)this.getComponent(id+i)).getNave() != null && ((Cella)this.getComponent(id+i)).getNave() != this.naveCorrente)
                {
                    esito = false;
                }
            }
        }
        else
        {
            for(int i=0;i<this.naveCorrente.getColpiRimanenti();i++)
            {
                if(((Cella)this.getComponent(id+i*10)).getNave() != null && ((Cella)this.getComponent(id+i*10)).getNave() != this.naveCorrente)
                {
                    esito = false;
                }
            }
        }
        return esito;
    }
   
    
    public boolean verificaCella(int id,int rotazione)
    {
        int riga = id / 10;
        int colonna = id - (riga * 10);
        
        boolean esito = true;
        
        if(rotazione == 0)
        {
            if(colonna + this.naveCorrente.getColpiRimanenti() > 10)
            {
                esito = false;
            }
        }
        else 
        {
            if(riga + this.naveCorrente.getColpiRimanenti()-1 >= 10)
            {
                esito = false;
            }
        }
        
        esito = verificaCellaOccupata(id,esito);
        
        
        return esito;
    }
    
    public void posizionaNave(Nave nave,int idPosizione)
    {
        if(this.verificaCella(idPosizione, nave.getRotazione()))
        {
            if(nave.getRotazione()==0)
            {
                for(int i=0;i<nave.getColpiRimanenti();i++)
                {
                    this.getComponent(idPosizione+i).setBackground(nave.getColore());
                    ((Cella)this.getComponent(idPosizione+i)).setNave(nave);
                }
            }
            else
            {
                for(int i=0;i<nave.getColpiRimanenti();i++)
                {
                    this.getComponent(idPosizione+i*10).setBackground(nave.getColore());
                    ((Cella)this.getComponent(idPosizione+i*10)).setNave(nave);
                }
            }
            
        }
        
    }
    
    public void rimuoviNave(Nave nave,int idPosizione)
    {
        if(this.verificaCella(idPosizione, nave.getRotazione()))
        {
            if(nave.getRotazione()==0)
            {
                for(int i=0;i<nave.getColpiRimanenti();i++)
                {
                    this.getComponent(idPosizione+i).setBackground(Color.WHITE);
                    ((Cella)this.getComponent(idPosizione+i)).setNave(null);
                }
            }
            else
            {
                for(int i=0;i<nave.getColpiRimanenti();i++)
                {
                    this.getComponent(idPosizione+i*10).setBackground(Color.WHITE);
                    ((Cella)this.getComponent(idPosizione+i*10)).setNave(null);
                }
            }
            
        }
    }

    public Nave getNaveCorrente() {
        return naveCorrente;
    }

    public void setNaveCorrente(Nave naveCorrente) {
        this.naveCorrente = naveCorrente;
    }
       
}
