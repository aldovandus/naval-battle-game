/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author aldo
 */
public class Cella extends JLabel {
    
    private  int id;
    private  Color colore;
    private  Nave nave;
    private  boolean clicked;
    
    Cella()
    {
       // this.setPreferredSize(new Dimension(30,30));
        
    }
    
    Cella(int i)
    { 
        //super(label);
        this.clicked = false;
        this.setPreferredSize(new Dimension(50,50));
        Border border = new LineBorder(Color.LIGHT_GRAY, 1);
        this.setOpaque(true);
        this.colore=Color.white;
        this.setBackground(this.colore);
        this.setBorder(border);
        //this.setBorderPainted(false);
        //this.addActionListener(new ButtonListener(i));
        //this.addMouseListener(new ButtonListener(i,giocatore));
        
        
    }

    public int getId() {
        return id;
    }

    public Color getColore() {
        return colore;
    }

    public Nave getNave() {
        return nave;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

}
