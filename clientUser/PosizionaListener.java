/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author aldo
 */
public class PosizionaListener implements ActionListener {

    int id;
    private GameClient gameClient;
    

    public PosizionaListener()
    {
        
    }
    
    public PosizionaListener(int id,GameClient gameClient)
    {
        this.id = id;
        this.gameClient = gameClient;
    }
    
     
    @Override
    public void actionPerformed(ActionEvent ae) {
       System.out.println(this.id);
       this.gameClient.getGriglia1().setNaveCorrente(this.gameClient.getGiocatore().getNavi().get(id));
       this.gameClient.getPosizionaJPanel().setPosizioneCorrente(this.gameClient.getPosizionaJPanel().getPosizionaButtons().get(id));
       this.gameClient.getPosizionaJPanel().setRuotaCorrente(this.gameClient.getPosizionaJPanel().getRuotaButtons().get(id));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameClient getGameClient() {
        return gameClient;
    }

    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;
    }
    
    
    
}
