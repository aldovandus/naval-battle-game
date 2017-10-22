/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author aldo
 */
public class InviaInvito implements Runnable {

    private Socket server;
    private Socket client;
    private ServerSocket listener;
    private String idGiocatore;
    private String id;
    private String ip;
    private BufferedReader in;
    private PrintWriter pw;
    private ListaUtenti listaUtenti;
    
    private Semaphore mutex;
    
    public InviaInvito(Semaphore sem,Socket server,ServerSocket listener,String id,ListaUtenti listaUtenti) {
        
        this.server = server;
        this.listener = listener;
        this.id = id;
        this.mutex = sem;
        this.listaUtenti = listaUtenti;
    }
    
    void collegamentoClient()
    {
        try {
            
              this.client = new Socket(ip, 9091);
              this.client.setTcpNoDelay(true);
              System.out.println("Just connected to " + this.client.getRemoteSocketAddress());
              String risposta;
              //PrintWriter pw2 = new PrintWriter(client.getOutputStream(), true);

              BufferedReader in2 = new BufferedReader(new InputStreamReader(client.getInputStream()));
              System.out.println("Attesa risposta dall'altro giocatore ...");
              risposta = in2.readLine();
             
              if(risposta.equals("Si"))
              {
                  
                  this.listener.close(); // Dato che il giocatore corrente fa da client chiudo il listener
                  this.listaUtenti.setStato(false);
                  this.listaUtenti.dispose();
                  
                  this.pw.println("Si");
                  GameClient gameClient = new GameClient(this.client,null,0,new Giocatore1());
                  gameClient.setClientJFrame(this.listaUtenti.getClientJFrame());
                  gameClient.setIn(this.in);
                  gameClient.setPw(this.pw);
                  gameClient.getGiocatore().setId(this.idGiocatore);
                  gameClient.setVisible(true);
              }
              else
              {
                  this.client.close();
              }
              
             
        } catch (IOException ex) {
             Logger.getLogger(ClientJFrame.class.getName()).log(Level.SEVERE, null, ex); 
             JOptionPane.showMessageDialog(null,ex.getMessage(), ex.getLocalizedMessage(),JOptionPane.WARNING_MESSAGE);
        }
       
    }
    
    public void inviaInvito() throws InterruptedException, IOException
    {
        this.mutex.acquire();
        this.startInvito();
        this.mutex.release();
    }
    
    public void startInvito() throws IOException
    {       
            
        pw.println("invito");        
     
     
        pw.println(this.id);
           
        this.ip = in.readLine().trim();

        if(!ip.equals("No"))
        {
            this.collegamentoClient();
        } 

    
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdGiocatore() {
        return idGiocatore;
    }

    public void setIdGiocatore(String idGiocatore) {
        this.idGiocatore = idGiocatore;
    }
    
    public PrintWriter getPw() {
        return pw;
    }

    public void setPw(PrintWriter pw) {
        this.pw = pw;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }
    
  
    
    @Override
    public void run() {
        
        try {
            this.inviaInvito();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(InviaInvito.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InviaInvito.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
