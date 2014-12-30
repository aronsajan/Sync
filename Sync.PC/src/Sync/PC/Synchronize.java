/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sync.PC;

import Sync.CommonTypes.RemoteSystem;
import Sync.Core.SyncManager;
import Sync.PC.UI.CloudDevices;
import Sync.PC.UI.SyncWindow;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author Aron
 */
public class Synchronize extends Thread
{
    public SyncWindow SyncResponse;
    public void run()
    {
        
        List<String> CloudDeviceList=CloudDevices.GetDevicesList();
        for(String DeviceID:CloudDeviceList)
        {
           SyncResponse.SetResponseMessage("Synchronizing with device "+DeviceID+" ...");
            RemoteSystem Remote=new RemoteSystem();
            Remote.IPAddress=DeviceID;
            try
            {
              SynchronizeWithDevice(Remote);
            }
            catch(Exception Ex)
            {
                Logger.SaveExceptionLog(Ex);
            }
        }
    }
    
   
    public void SynchronizeWithDevice(RemoteSystem RemoteDevice)throws Exception
    {
        SyncManager SynMgr=new SyncManager();
        SynMgr.Synchronize(RemoteDevice);
    }
}
