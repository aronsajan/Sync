/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sync.PC.UI;

import Sync.Framework.Serializer.BinarySerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Aron
 */

public class CloudDevices extends javax.swing.JFrame
{

    /**
     * Creates new form CloudDevices
     */
   private List<String> DevicesList;
     BinarySerializer Serializer;
    public CloudDevices()
    {
        try
        {
            Serializer =new BinarySerializer();
            initComponents();
            DevicesList=new ArrayList<String>();
            LoadList();
            RenderDeviceList();
        }
        catch(Exception Ex)
        {
            
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        DeviceLabel = new javax.swing.JLabel();
        TextDeviceName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        DeviceList = new javax.swing.JList();
        DeviceListLabel = new javax.swing.JLabel();
        AddDeviceButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        OKButton = new javax.swing.JButton();
        RemoveDeviceButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cloud Device Manager");

        DeviceLabel.setText("Device Name/IP");

        jScrollPane1.setViewportView(DeviceList);

        DeviceListLabel.setText("Device List");

        AddDeviceButton.setText("Add Device");
        AddDeviceButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                AddDeviceButtonActionPerformed(evt);
            }
        });

        OKButton.setText("OK");
        OKButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                OKButtonActionPerformed(evt);
            }
        });

        RemoveDeviceButton.setText("Remove Device");
        RemoveDeviceButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                RemoveDeviceButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(DeviceLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextDeviceName, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(AddDeviceButton)
                            .addComponent(DeviceListLabel)
                            .addComponent(RemoveDeviceButton))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(OKButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DeviceLabel)
                    .addComponent(TextDeviceName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AddDeviceButton)
                .addGap(18, 18, 18)
                .addComponent(DeviceListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RemoveDeviceButton)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OKButton)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_OKButtonActionPerformed
    {//GEN-HEADEREND:event_OKButtonActionPerformed
      dispose();
    }//GEN-LAST:event_OKButtonActionPerformed

    private void AddDeviceButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_AddDeviceButtonActionPerformed
    {//GEN-HEADEREND:event_AddDeviceButtonActionPerformed
       try
       {
        String DeviceID=TextDeviceName.getText();
       DevicesList.add(DeviceID);  
       SaveList();
       RenderDeviceList();
       }
       catch(Exception Ex)
       {
           
       }
    }//GEN-LAST:event_AddDeviceButtonActionPerformed

    private void RenderDeviceList()
    {
        DeviceList.setListData(DevicesList.toArray());
    }
    
    private void LoadList()throws Exception
    {
        File FileInfo=new File("DeviceInfo.info");
        if(FileInfo.exists())
        {
            byte[] DeviceListRaw=new byte[(int)FileInfo.length()];
            FileInputStream ReadStream=new FileInputStream("DeviceInfo.info");
           ReadStream.read(DeviceListRaw);
           DevicesList=(List<String>)Serializer.Deserialize(DeviceListRaw);
        }
    }
    
    private void SaveList()throws Exception
    {
      
      byte[] DeviceListData=  Serializer.Serialize(DevicesList);
      FileOutputStream WriteStream=new FileOutputStream("DeviceInfo.info");
      WriteStream.write(DeviceListData);
      WriteStream.close();
    }
    
    public static List<String> GetDevicesList()
    {
        try
        {
        CloudDevices Dvc=new CloudDevices();
        Dvc.LoadList();
        return Dvc.DevicesList;
        }
        catch(Exception Ex)
        {
            
        }
        return null;
    }
    
    private void RemoveDeviceButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_RemoveDeviceButtonActionPerformed
    {//GEN-HEADEREND:event_RemoveDeviceButtonActionPerformed
     try
     {
        int Response=JOptionPane.showConfirmDialog(CloudDevices.this, "Are you sure you want to remove the Cloud Device "+DeviceList.getSelectedValue().toString()+" ?", "Sync - Remove Cloud Device", JOptionPane.YES_NO_OPTION);
      if(Response==JOptionPane.YES_OPTION)
      {
        DevicesList.remove(DeviceList.getSelectedIndex());
        SaveList();
        RenderDeviceList();
      }
     }
     catch(Exception Ex)
     {
         
     }
    }//GEN-LAST:event_RemoveDeviceButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(CloudDevices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(CloudDevices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(CloudDevices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(CloudDevices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new CloudDevices().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddDeviceButton;
    private javax.swing.JLabel DeviceLabel;
    private javax.swing.JList DeviceList;
    private javax.swing.JLabel DeviceListLabel;
    private javax.swing.JButton OKButton;
    private javax.swing.JButton RemoveDeviceButton;
    private javax.swing.JTextField TextDeviceName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
