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
 * @author Kotss
 */

public class RuotaListener implements ActionListener {

    private int id;
    private GameClient gameClient;
     
    
    
    public RuotaListener(int id,GameClient gameClient) {
        this.id = id;
        this.gameClient = gameClient;
    }

    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        System.out.println(this.id);
        if((this.gameClient.getGiocatore().getNavi().get(id).getRotazione()) == 0)
        {
            this.gameClient.getGiocatore().getNavi().get(id).setRotazione(1);
        }
        else
        {
            this.gameClient.getGiocatore().getNavi().get(id).setRotazione(0);
        }
        
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
