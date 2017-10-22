/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aldo
 */
    
    public class NotEditableTableModel extends DefaultTableModel {
    
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    
    }

