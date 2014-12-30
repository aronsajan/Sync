/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sync.PC.UI;

import Sync.Core.SyncManager;
import Sync.Framework.FileEngine.FileManager;
import Sync.Framework.SyncTable.*;
import Sync.Framework.SyncTable.SyncTable;
import Sync.PC.Synchronize;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javax.swing.*;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import org.joda.time.DateTime;

/**
 *
 * @author Aron
 */
public class MainForm extends javax.swing.JFrame
{

    /**
     * Creates new form MainForm
     */
    JButton AddFileButton;
    JButton ExitButton;
    JButton DeleteButton;
    JButton SynchronizeButton;
    JButton CloudDevicesButton;
    JButton RefreshView;
   
    JFileChooser FileChooser;
    SyncManager SynchronizationManager;
    FileManager SyncFileManager;
    String SHARED_DIRECTORY;
    SyncTable LogTable;

    public MainForm()
    {
        try
        {
            SynchronizationManager = new SyncManager();
            SyncFileManager = new FileManager();
            SHARED_DIRECTORY = "Shared";
            
            initComponents();
            RenderListView();
            RenderToolbar();
            AddActionsToButtons();

        }
        catch (Exception Ex)
        {

        }
    }

    public void RenderListView()
    {
        try
        {
           
           RefreshListView();
            FilesListView.setFixedCellHeight(70);
            FilesListView.setFixedCellWidth(this.getWidth());
            FilesListView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            FilesListView.setVisibleRowCount(0);
            FilesListView.setCellRenderer(new ListRenderer());
            FilesListView.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        }
        catch (Exception Ex)
        {
            System.out.println(Ex.getMessage());
        }

    }
    
    public void RefreshListView()
    {
        try
        {
        LogTable = new SyncTable();
        List<LogRecord> FilesList = new ArrayList<LogRecord>();
            for (LogRecord LogInfo : LogTable.GetLogTable())
            {
                if (!LogInfo.DeleteFlag)
                {

                    FilesList.add(LogInfo);
                }
            }

            FilesListView.setListData(FilesList.toArray());
        }
        catch(Exception Ex)
        {
            this.ShowError(Ex);
        }
    }

    private void RenderToolbar()
    {
        AddFileButton = new JButton();
        ExitButton = new JButton();
        DeleteButton = new JButton();
        RefreshView=new JButton();
       
        CloudDevicesButton=new JButton();
        SynchronizeButton=new JButton();
        AddFileButton.setText("Add File");
        DeleteButton.setText("Delete File");
        SynchronizeButton.setText("Synchronize");
        RefreshView.setText("Refresh View");
        
        CloudDevicesButton.setText("Cloud Devices");
        ExitButton.setText("Exit");
        AddFileButton.setIcon(new ImageIcon("Resources/add.png"));
        DeleteButton.setIcon(new ImageIcon("Resources/delete.png"));
        RefreshView.setIcon(new ImageIcon("Resources/display.png"));
        SynchronizeButton.setIcon(new ImageIcon("Resources/sync.png"));
        CloudDevicesButton.setIcon(new ImageIcon("Resources/network.png"));
       
        ExitButton.setIcon(new ImageIcon("Resources/exit.png"));
        SyncToolbar.add(AddFileButton);
        SyncToolbar.add(DeleteButton);
        
        SyncToolbar.add(SynchronizeButton);
        SyncToolbar.add(RefreshView);
        SyncToolbar.add(CloudDevicesButton);
        SyncToolbar.add(ExitButton);
    }

    private void AddActionsToButtons()
    {
        AddFileButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                try
                {
                    FileChooser = new JFileChooser();
                    int FileChooserResponse = FileChooser.showOpenDialog(MainForm.this);
                    if (FileChooserResponse == JFileChooser.APPROVE_OPTION)
                    {
                        File SelectedFileObject = FileChooser.getSelectedFile();
                        SyncFileManager.CopyFile(SelectedFileObject.getAbsolutePath(), SHARED_DIRECTORY);
                        LogTable.AddFile(SelectedFileObject.getName(), DateTime.now());
                    }
                   RenderListView();
                }
                catch (Exception Ex)
                {
                    ShowError(Ex);
                }
            }
        });

        DeleteButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                try
                {
                    if (!FilesListView.isSelectionEmpty())
                    {
                        LogRecord DeleteRecord = (LogRecord) FilesListView.getSelectedValue();
                        int DeleteResponse = JOptionPane.showConfirmDialog(MainForm.this, "Are you sure you want to delete file -" + DeleteRecord.Filename + " ?", "Sync - Confirm Delete", JOptionPane.YES_NO_OPTION);
                        if (DeleteResponse == JOptionPane.YES_OPTION)
                        {

                            String FilePath = SHARED_DIRECTORY + File.separator + DeleteRecord.Filename;
                            LogTable.DeleteFile(DeleteRecord.Filename, DateTime.now());
                            SyncFileManager.DeleteFile(FilePath);
                            RenderListView();
                        }
                    }
                    else
                    {
                        ShowError(new Exception("Select a file to delete"));
                    }
                }

                catch (Exception Ex)
                {
                    ShowError(Ex);
                }

            }
        });
        
        
        
        
        
        SynchronizeButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                try
                {
                   
                    Synchronize SyncExecute = new Synchronize();
                    SyncWindow SyncLoad = new SyncWindow();
                    SyncExecute.SyncResponse=SyncLoad;
                    SyncLoad.show();
                    SyncExecute.start();
                    while(SyncExecute.isAlive())
                    {
                        Thread.sleep(1000);
                    }
                    SyncLoad.hide();
                    SyncLoad.dispose();

                    RenderListView();
                }
                catch(Exception Ex)
                {
                    ShowError(Ex);
                }
            }
            
        });
        
        
        RefreshView.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                RenderListView();
            }
            
        }
        );
        
        CloudDevicesButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
               CloudDevices DeviceManager=new CloudDevices();
               DeviceManager.show();
            }
            
        });
               

        ExitButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                int ExitResponse = JOptionPane.showConfirmDialog(MainForm.this, "Are you sure you want to exit?", "Sync - Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (ExitResponse == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }

        });

    }

    private void ShowError(Exception Ex)
    {
        JOptionPane.showMessageDialog(MainForm.this, "Exception Details - " + Ex.getMessage(), "Sync - Error", JOptionPane.ERROR_MESSAGE);
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

        SyncToolbar = new javax.swing.JToolBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        FilesListView = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sync");

        SyncToolbar.setRollover(true);

        FilesListView.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        FilesListView.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        FilesListView.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        FilesListView.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                FilesListViewValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(FilesListView);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SyncToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SyncToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void FilesListViewValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_FilesListViewValueChanged
    {//GEN-HEADEREND:event_FilesListViewValueChanged

    }//GEN-LAST:event_FilesListViewValueChanged

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainForm().setVisible(true);
            }
        });
    }

    public void Init()
    {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList FilesListView;
    private javax.swing.JToolBar SyncToolbar;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
