/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sync.CommandEngine;

import Sync.CommandEngine.Utilities.Helper;
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
            Properties = new Properties();
            InputStream PropertiesStream = this.getClass().getClassLoader().getResourceAsStream(Helper.PROPERTIES_FILE);
            Properties.load(PropertiesStream);
        }
        catch (Exception Ex)
        {

        }
    }
}
