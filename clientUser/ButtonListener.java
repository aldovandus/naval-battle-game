/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 *
 * @author aldo
 */
public class ButtonListener implements MouseListener,Runnable {

    private int id;
    private Runnable giocatore;
    private GameClient gameClient;
    
    public ButtonListener(int id) {
        this.id = id;
    }
    
    public ButtonListener(int id,GameClient gameClient)
    {
        this.gameClient = gameClient;
        this.id = id;
        
    }
    
    
   /*
    void inviaAttacco()
    {
        
        Attacco attaccoInviato = new Attacco(id,0,0);
        Attacco attaccoRicevuto;
      
        
        
        
        System.out.println("Invia Attacco");
        try {
            
            this.gameClient.getOs().writeObject(attaccoInviato);
            this.gameClient.setTurno(0);
          
           // this.gameClient.getOs().reset();
            this.gameClient.getOs().flush();
            
            
            attaccoRicevuto = (Attacco) this.gameClient.getIs().readObject();
            if(attaccoRicevuto.getEsitoAttacco() == 0)
            {
                this.gameClient.setTurno(0);
                this.gameClient.messaggio.setText("Turno Avversario");
                Thread thread = new Thread(this.gameClient);
                thread.start();
            }
            //this.gameClient.getIs().reset();
            //this.gameClient.label.setText(attaccoRicevuto.toString());
            //JOptionPane.showConfirmDialog(null, attaccoRicevuto.toString(), "Invito", JOptionPane.YES_NO_OPTION);
            
          
        } catch (IOException ex) {
            Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    */

    @Override
    public void mouseClicked(MouseEvent me) {

        if(this.gameClient.getTurno() == 1)
        {
       
            if(!((Cella)this.gameClient.getGriglia2().getComponent(id)).isClicked())
            {
                Thread t1 = new Thread(this);
                t1.start();
                ((Cella)this.gameClient.getGriglia2().getComponent(id)).setClicked(true);
            }
            
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Non è il tuo turno.", "Mossa non consentita", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
        if(this.gameClient.getTurno() == 1)
        {
            
            Cella cella = (Cella)this.gameClient.getGriglia2().getComponent(id);
            Border border = BorderFactory.createLineBorder(Color.BLUE, 2);
            cella.setBorder(border);
            
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        Cella cella = (Cella)this.gameClient.getGriglia2().getComponent(id);
        Border border = BorderFactory.createLineBorder(Color.lightGray, 1);
        cella.setBorder(border);
    }

    @Override
    public void run() {
        
        this.gameClient.getGiocatore().inviaAttacco(id);
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Runnable getGiocatore() {
        return giocatore;
    }

    public void setGiocatore(Runnable giocatore) {
        this.giocatore = giocatore;
    }

    public GameClient getGameClient() {
        return gameClient;
    }

    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;
    }
}
