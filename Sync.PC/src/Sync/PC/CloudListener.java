/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sync.PC;

import Sync.Framework.CommonProperties;
import Sync.CommonTypes.ICommand;
import Sync.Framework.Command.RemoteCommandExecutor;
import Sync.Framework.Communicator.ReceptionOutput;
import Sync.Framework.Communicator.Transceiver;
import Sync.Framework.Serializer.BinarySerializer;
import Sync.PC.UI.MainForm;

/**
 *
 * @author Aron
 */
public class CloudListener extends Thread
{

    public MainForm DisplayWindow;

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
                BinarySerializer Serializer = new BinarySerializer();
                ICommand cmdObject = (ICommand) Serializer.Deserialize(out.ReceivedData);
                RemoteCommandExecutor executor = new RemoteCommandExecutor();
                executor.ExecuteCommand(cmdObject, out.Sender);
                DisplayWindow.RefreshListView();
            }
            catch (Exception Ex)
            {
                Logger.SaveExceptionLog(Ex);
            }
        }

    }
}
