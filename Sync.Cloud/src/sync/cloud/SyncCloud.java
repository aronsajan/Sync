/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sync.cloud;

/**
 *
 * @author Aron
 */
public class SyncCloud
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
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
         catch(Exception Ex)
         {
             
         }
        CloudForm display=new CloudForm();
        display.show();
    }
    
}
