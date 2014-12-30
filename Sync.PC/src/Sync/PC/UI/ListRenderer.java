/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sync.PC.UI;
import Sync.Framework.SyncTable.LogRecord;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author Aron
 */
public class ListRenderer extends JLabel implements ListCellRenderer
{
    public ListRenderer()
    {
        setOpaque(true);
        setIconTextGap(5);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        LogRecord Log=(LogRecord)value;
        this.setText(Log.Filename+" - "+Log.DateTime.toString());
        String img="Resources/FileIcon.png";
        this.setBackground(Color.white);
        ImageIcon c=new ImageIcon(img);
        BufferedImage q=new BufferedImage(48,48,BufferedImage.TYPE_INT_RGB);
       Graphics x=q.getGraphics();
      
       x.drawImage(c.getImage(), 0, 0, 48,48,Color.white,null);
        this.setIcon(new ImageIcon(q));
       if(isSelected)
       {
           //list.get
          this.setBackground(new Color(41,75,133));
          this.setForeground(Color.white);
       }
       else
       {
           this.setForeground(Color.black);
       }
       
        return this;
    }
    
}
