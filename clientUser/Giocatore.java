/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author aldo
 */
public class Giocatore{
    
    private GameClient gameClient;
    private int numero;
    private String id;
    private List<Nave> navi;
    private int naviDistrutte;

    
    
    Giocatore()
    {
        this.navi = new ArrayList<>();
        this.navi.add(new Nave5());
        this.navi.add(new Nave3());
        this.navi.add(new Nave3());
        this.navi.add(new Nave2());
        this.navi.add(new Nave1());
        //this.navi.add(new Nave1());
        this.naviDistrutte=0;
    }
    
    Giocatore(GameClient gameClient)
    {
        this();
        this.gameClient = gameClient;
    }

    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public GameClient getGameClient() {
        return gameClient;
    }

    public int getNaviDistrutte() {
        return naviDistrutte;
    }
    
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<Nave> getNavi() {
        return navi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    public void inviaPronto() // Metodo Virtuale
    {
        
    }
    
    public void attesaPronto() // Metodo Virtuale
    {
        System.out.println("Attesa giocatore");
    }
    
    public void incrementaNaveDistrutte()
    {
        this.naviDistrutte++;
    }
    
   public void inviaAttacco(int id) 
    {
        
        Attacco attaccoInviato = new Attacco(id,0,0);
        Attacco attaccoRicevuto;

        System.out.println("Invia Attacco");
        try {
            
            this.gameClient.getOs().writeObject(attaccoInviato);
            this.gameClient.setTurno(0);
          
            this.gameClient.getOs().flush();
            
            
            attaccoRicevuto = (Attacco) this.gameClient.getIs().readObject();
            
            if(attaccoRicevuto.getEsitoPartita()==0) /* Se la partita non è finita */
            {
                
                if(attaccoRicevuto.getEsitoAttacco() == 0)
                {
                    this.gameClient.getGriglia2().getComponent(id).setBackground(Color.CYAN);
                    this.gameClient.setTurno(0);
                    this.gameClient.getMessaggio().setText("Turno Avversario");
                    Thread thread = new Thread(this.gameClient);
                    thread.start();
                }
                else
                {
                    this.gameClient.getGriglia2().getComponent(id).setBackground(Color.RED);
                    this.gameClient.setTurno(1);
                }
            }
            else
            {
                //int reply = JOptionPane.showConfirmDialog(null, "Hai Vinto la partita! Rivincita?", "Vincitore", JOptionPane.YES_NO_OPTION);
                this.gameClient.getGriglia2().getComponent(id).setBackground(Color.RED);
                this.gameClient.getMessaggio().setText("Hai Vinto La partita!");
                this.inviaRisultati();
                //this.gameClient.dispose();
                /*
                if(this.gameClient.listener != null)
                {
                   this.gameClient.listener.close();
                }
                
                if(this.gameClient.gameSocket != null)
                {
                    this.gameClient.gameSocket.close();
                }
                
                */
                //Thread attesa = new Thread(this.gameClient.clientJFrame);
                //attesa.start();
                //this.gameClient.getListaUtenti().startUtentiThread();
                
            }
                       
          
        } catch (IOException ex) {
            //Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Connessione Interrotta", "Attenzione",JOptionPane.WARNING_MESSAGE);
            //this.gameClient.dispose();
            //this.gameClient.getListaUtenti().startUtentiThread();
              
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Giocatore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    public void RiceviAttacco()
    {
        
         try {
            Object risposta;
            while(!Thread.currentThread().isInterrupted())
           //while(1)
            {

                System.out.println("Attesa dell'attacco");
                
                this.gameClient.getMessaggio().setText("Turno Avversario");
                
                risposta = this.gameClient.getIs().readObject();
                
                int id = ((Attacco)risposta).getId();
                
                if(((Cella)gameClient.getGriglia1().getComponent(id)).getNave() != null) /* Se la cella appartiene ad una nave */
                {
                    ((Attacco)risposta).setEsitoAttacco(1);
                    Nave nave = ((Cella)gameClient.getGriglia1().getComponent(id)).getNave();
                    nave.colpito();
                    
                    this.gameClient.getGriglia1().getComponent(((Attacco)risposta).getId()).setBackground(Color.RED);
                    this.gameClient.setTurno(0);
                    
                    if(nave.getColpiRimanenti() == 0)
                    {
                        this.naviDistrutte++;
                        if(this.naviDistrutte == this.navi.size())
                        {
                            System.out.println("Partita Finita!!!!!!!!!");
                            ((Attacco)risposta).setEsitoPartita(1);
                            Thread.currentThread().interrupt();
                            //int reply = JOptionPane.showConfirmDialog(null, "Hai Perso la partita! Rivincita?", "Vincitore", JOptionPane.YES_NO_OPTION);
                            this.gameClient.getMessaggio().setText("Hai Perso la partita!");

                            //this.gameClient.dispose();
                            /*
                            
                            if(this.gameClient.listener != null)
                            {
                                this.gameClient.listener.close();
                            }

                            if(this.gameClient.gameSocket != null)
                            {
                                this.gameClient.gameSocket.close();
                            }
*/
                            //Thread attesa = new Thread(this.gameClient.clientJFrame);
                            //attesa.start();
                            //this.gameClient.getListaUtenti().startUtentiThread();

                        }
                    }
                    
                    this.gameClient.getGriglia1().getComponent(((Attacco)risposta).getId()).setBackground(Color.RED);
                    this.gameClient.setTurno(0);
                    
                }
                else
                {
                    ((Attacco)risposta).setEsitoAttacco(0);
                    this.gameClient.getGriglia1().getComponent(((Attacco)risposta).getId()).setBackground(Color.CYAN);
                    this.gameClient.setTurno(1);
                    this.gameClient.getMessaggio().setText("E' il tuo turno");
                    Thread.currentThread().interrupt();
                }
                
                
                System.out.println("Ricevuto" + risposta);
                //this.getOs().reset();
               
                //this.gameClient.getGriglia1().getComponent(((Attacco)risposta).getId()).setBackground(Color.CYAN);
              
                this.gameClient.getOs().writeObject(risposta);
                
                
                
                //Thread thread = new Thread(this);
                //thread.start();
               // os.flush();
                System.out.println("Inviato");
                //this.os.reset();
            }
        } catch (IOException ex) {
            //Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
            
         JOptionPane.showMessageDialog(null, "Connessione Interrotta", "Attenzione",JOptionPane.WARNING_MESSAGE);
         //this.gameClient.dispose();
        // this.gameClient.getListaUtenti().startUtentiThread();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Giocatore.class.getName()).log(Level.SEVERE, null, ex);
        } 
         
    }
    
    public void inviaRisultati()
    {
        System.out.println("Risultati invio....");
        this.gameClient.getPw().println("fine");
        this.gameClient.getPw().println("0");
        {
            //this.gameClient.getPw().println(this.id);
            //pw.println("10");
        }
        System.out.println("Risultati Inviati....");
    }

    


}
