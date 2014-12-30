/*
Author: Aron Sajan Philip
 */
package Sync.Framework;

import Sync.Framework.Utilities.Helper;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


/**
 *
 * @author Aron
 */
public class CommonProperties
{
   public Properties Properties;
   
   public CommonProperties()
   {
       try
       {
         Properties=new Properties();
        InputStream PropertiesStream= this.getClass().getClassLoader().getResourceAsStream(Helper.PROPERTIES_FILE);
         Properties.load(PropertiesStream);
       }
       catch(Exception Ex)
       {
           
       }
   }
}
