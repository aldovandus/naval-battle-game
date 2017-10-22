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
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
 
public class PopulateTableWorker extends SwingWorker<TableModel, Object[]> {
 
  private final DefaultTableModel model;
  private int rowIndex = 0;
  
   
    Socket socket;
    Socket p2p;
    BufferedReader in;
    PrintWriter pw;  
  
 
  public PopulateTableWorker(Socket socket,DefaultTableModel model) throws IOException{
    this.model = model;
    this.socket = socket;
    
     pw = new PrintWriter(socket.getOutputStream(), true);
     in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }
 
  @Override
  protected DefaultTableModel doInBackground() throws Exception {
    // While there are more rows
    //while(Math.random() < 0.5){
    //for(int i = 0; i< 10;i++){
      // Get the row from the slow source
      
       String num = null;
        String temp = null;
        int count = 0;
        int aldo;
        
        
        pw.println("g"); // Invio il carattere al server per ottenere la lista
        
        
       while(socket.isConnected())
        //   for(int j = 0;j<10;j++)
       {
        
        try
        {    
            num = in.readLine();
            num = num.trim();
            
        } catch (IOException ex) {
            Logger.getLogger(ListaUtenti.class.getName()).log(Level.SEVERE, null, ex);
        }
              
        
            aldo = Integer.parseInt(num);
    
         
            try { 
                this.model.setRowCount(0);
                Object[] row = new Object[3];
              for(int i=0;i<aldo;i++)  
              {
                temp = in.readLine();
                temp = temp.trim();
                row = temp.split("-");
                
                this.model.addRow(row);
                //this.model.fireTableDataChanged();
                //publish(row);
              }
              
              
              
            } catch (IOException ex) {
                Logger.getLogger(ListaUtenti.class.getName()).log(Level.SEVERE, null, ex);
            }
                  //System.out.println(temp);
                  //listModel.addElement(new String(buff[i]));
                  
           }
              
            
        
       return this.model;
      
      //Object[] row = {"1", "Java","333"};
      //Thread.sleep(2000);
 
      // Update the model with the new row
      
   // }
 
   
  }
 
  @Override
  protected void process(List<Object[]> chunks){
    for(Object[] row : chunks){
      //for(int columnIndex=0, size=row.length; columnIndex<size; columnIndex++){
      //for(int i=0;i<10;i++){
       // model.setValueAt(row[columnIndex], rowIndex, columnIndex);
        this.model.addRow(row);
        
     // }
    }
    rowIndex++;
  }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getP2p() {
        return p2p;
    }

    public void setP2p(Socket p2p) {
        this.p2p = p2p;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public PrintWriter getPw() {
        return pw;
    }

    public void setPw(PrintWriter pw) {
        this.pw = pw;
    }
}