/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author aldo
 */
public class Giocatore2 extends Giocatore {

    public Giocatore2(GameClient gameClient) {
        super(gameClient);
    }

    Giocatore2() {
        this.setNumero(2);
        System.out.println("Provola" + this.getNumero());
    }
    
    public void inviaPronto()
    {
        this.getGameClient().setStatoG2(true);
        this.getGameClient().getProntoBtn().setEnabled(false);
        
        if(this.getGameClient().getStatoG1() && this.getGameClient().getStatoG2())
        {
            try {
                this.getGameClient().getOs().writeObject("Pronto");
                this.getGameClient().iniziaPartita();
            } catch (IOException ex) {
                Logger.getLogger(Giocatore2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void attesaPronto() 
    {
      
        try {
            String risposta;
            System.out.println("Sto in pronto-attesa");
            //risposta = this.gameClient.getIn().readLine();
            risposta = (String)this.getGameClient().getIs().readObject();
            if(risposta.equals("Pronto"))
            {
                this.getGameClient().setStatoG1(true);
            }
            
            System.out.println("Sto in fine-pronto-attesa");
            
            if(this.getGameClient().getStatoG1() && this.getGameClient().getStatoG2())
            {
                this.getGameClient().getOs().writeObject("Pronto");
                this.getGameClient().iniziaPartita();
            }
        } catch (IOException ex) {
            //Logger.getLogger(Giocatore2.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Connessione Interrotta", "Attenzione",JOptionPane.WARNING_MESSAGE);
            this.getGameClient().dispose();
            this.getGameClient().getListaUtenti().startUtentiThread();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Giocatore2.class.getName()).log(Level.SEVERE, null, ex);
        }
            
       
    }

   
    
}
