/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sync.cloud;

import Sync.CommonTypes.ICommand;
import Sync.Framework.Command.RemoteCommandExecutor;
import Sync.Framework.CommonProperties;
import Sync.Framework.Communicator.ReceptionOutput;
import Sync.Framework.Communicator.Transceiver;
import Sync.Framework.Serializer.BinarySerializer;

/**
 *
 * @author Aron
 */
public class Cloud extends Thread
{
    CloudForm Display;
      public void run()
    {
        StartCloud();
    }

    public void StartCloud()
    {

        CommonProperties GeneralProperties = new CommonProperties();

        int ControlPort = Integer.parseInt(GeneralProperties.Properties.getProperty("CONTROL_LINK_PORT"));

        for (;;)
        {
            try
            {
                ReceptionOutput out = new Transceiver().ReceiveData(ControlPort);
                Display.AddLog("Connection Received from "+out.Sender.IPAddress);
                
                BinarySerializer Serializer = new BinarySerializer();
                ICommand cmdObject = (ICommand) Serializer.Deserialize(out.ReceivedData);
                Display.AddLog("Command to be executed is of type : "+cmdObject.toString());
                RemoteCommandExecutor executor = new RemoteCommandExecutor();
                 Display.AddLog("Command Executed Successfully");
                executor.ExecuteCommand(cmdObject, out.Sender);
            }
            catch (Exception Ex)
            {
                Display.AddLog("Exception Encountered - "+Ex.getMessage());
            }
        }

    }
}
