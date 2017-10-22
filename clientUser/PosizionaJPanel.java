/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author Kotss
 */
public class PosizionaJPanel extends JPanel{
    private List <JButton> posizionaButtons;
    private List <JButton> ruotaButtons;
    
    private JButton ruotaCorrente;
    private JButton posizioneCorrente;
    
    public PosizionaJPanel() {
        
        this.posizionaButtons = new ArrayList<>();
        this.ruotaButtons = new ArrayList<>();

        this.setLayout(new GridBagLayout());
                 
    }
    
    public void aggiungiRuotaBtn(JButton ruota, ActionListener actionListener, GridBagConstraints c)
    {
            ruota.addActionListener(actionListener);
            this.add(ruota,c);
            this.ruotaButtons.add(ruota);
    }
    
    public void aggiungiPosizionaBtn(JButton posiziona, ActionListener actionListener, GridBagConstraints c)
    {      
            posiziona.addActionListener(actionListener);
            this.add(posiziona,c);
            this.posizionaButtons.add(posiziona);
    }
    
    public void aggiungiNave(GridBagConstraints c)
    {
        
    }

    public JButton getPosizioneCorrente() {
        return this.posizioneCorrente;
    }

    public void setPosizioneCorrente(JButton posizioneCorrente) {
        this.posizioneCorrente = posizioneCorrente;
    }

    public JButton getRuotaCorrente() {
        return ruotaCorrente;
    }

    public void setRuotaCorrente(JButton ruotaCorrente) {
        this.ruotaCorrente = ruotaCorrente;
    }
    
    public List<JButton> getPosizionaButtons() {
        return posizionaButtons;
    }

    public List<JButton> getRuotaButtons() {
        return ruotaButtons;
    }

    public ComponentUI getUi() {
        return ui;
    }

    public void setUi(ComponentUI ui) {
        this.ui = ui;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }
    
    
    
}
