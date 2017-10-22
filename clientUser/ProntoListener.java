/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Kotss
 */
public class ProntoListener implements ActionListener,Runnable {

    private GameClient gameClient;
    
    public ProntoListener(GameClient gameClient) {
        
        this.gameClient = gameClient;
        
    }
 
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        //System.out.println("Sono pronto");
        if(this.gameClient.getNumNaviPos() < 5)
        {
            JOptionPane.showMessageDialog(null,"Devi Prima posizionare le navi!", "Navi non posizionate", JOptionPane.INFORMATION_MESSAGE);

        }
        else
        {
            Thread thread = new Thread(this);
            thread.start();
        }
        
    }

    @Override
    public void run() {
        this.gameClient.getGiocatore().inviaPronto();
        
    }

    public GameClient getGameClient() {
        return gameClient;
    }

    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;
    }
    
    
    
}
