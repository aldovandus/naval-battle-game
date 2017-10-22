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
public class Giocatore1 extends Giocatore{

  

    Giocatore1() {
        this.setNumero(1);
        System.out.println("Provola" + this.getNumero());
    }
    
    /**
     *
     */
    public void inviaPronto()
    {
        try {
            System.out.println("Invia pronto");
            
            this.getGameClient().setStatoG1(true);
            this.getGameClient().getOs().writeObject("Pronto");
            
            String risposta;
            this.getGameClient().getProntoBtn().setEnabled(false);
            risposta = (String)this.getGameClient().getIs().readObject();
            //risposta = this.gameClient.getIn().readLine();
            if(risposta.equals("Pronto"))
            {
                this.getGameClient().setStatoG2(true);
            }
            System.out.println("Fine Invia pronto");
            
                   
            this.getGameClient().iniziaPartita();

        } catch (IOException ex) {
            //Logger.getLogger(Giocatore2.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Connessione Interrotta", "Attenzione",JOptionPane.WARNING_MESSAGE);
            this.getGameClient().dispose();
            //Thread attesa = new Thread(this.gameClient.listaUtenti);
            //attesa.start();
            this.getGameClient().getListaUtenti().startUtentiThread();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Giocatore1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
}
