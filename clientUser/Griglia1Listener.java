/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author aldo
 */
public class Griglia1Listener implements MouseListener {

    private int id;
    private GameClient gameClient;
    

    public Griglia1Listener(int id,GameClient gameClient)
    {
        this.gameClient = gameClient;
        this.id = id;
        
    }
   

    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println(this.id);
        //Thread t1 = new Thread(this.gameClient.getThreadGiocatore());
        //t1.start();
       //this.gameClient.getGriglia1().getComponentAt(0, 1);
        //this.gameClient.getGriglia1().verificaCella(id);
        
        /*
        if(this.gameClient.getGriglia1().verificaCella(id, 0))
        {
            this.gameClient.getGriglia1().posizionaNave(new Nave5(), id, 0);
        }
        */
        if(this.gameClient.getGriglia1().getNaveCorrente() != null)
        {
            this.gameClient.getGriglia1().posizionaNave(this.gameClient.getGriglia1().getNaveCorrente(), id);
            this.gameClient.getPosizionaJPanel().getPosizioneCorrente().setEnabled(false);
            this.gameClient.getPosizionaJPanel().getRuotaCorrente().setEnabled(false);
            this.gameClient.getGriglia1().setNaveCorrente(null);
            this.gameClient.incrementaNumNaviPos();
        }
    }
    
    void colora()
    {
        for(int i=0;i<5;i++)
        {
            this.gameClient.getGriglia1().getComponent(id+i).setBackground(Color.RED);
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
        //if(this.bloccoClick == false)
        {
        if(this.gameClient.getGriglia1().getNaveCorrente() != null)
        {
            this.gameClient.getGriglia1().posizionaNave(this.gameClient.getGriglia1().getNaveCorrente(), id);
            //this.gameClient.getPosizionaJPanel().getPosizioneCorrente().setEnabled(false);
            //this.gameClient.getPosizionaJPanel().getRuotaCorrente().setEnabled(false);
            
        }
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
       // if(this.bloccoClick == false)
        {
        if(this.gameClient.getGriglia1().getNaveCorrente() != null)
        {
            this.gameClient.getGriglia1().rimuoviNave(this.gameClient.getGriglia1().getNaveCorrente(), id);
            //this.gameClient.getPosizionaJPanel().getPosizioneCorrente().setEnabled(false);
            //this.gameClient.getPosizionaJPanel().getRuotaCorrente().setEnabled(false);
        }
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
