/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sync.PC;

import Sync.PC.UI.MainForm;
import javax.swing.JOptionPane;

/**
 *
 * @author Aron
 */
public class SyncPC
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
        {       
                if(info.getName().equals("Nimbus"))
                {
                 javax.swing.UIManager.setLookAndFeel(info.getClassName());
                   break;
                }
            
           
        }
         MainForm SyncPCMain = new MainForm();
         CloudListener cloudListener=new CloudListener(); 
         cloudListener.DisplayWindow=SyncPCMain;
         cloudListener.start();
       
        SyncPCMain.show();
        }
        catch(Exception Ex)
        {
          Logger.SaveExceptionLog(Ex);
        }
    }
    

}
