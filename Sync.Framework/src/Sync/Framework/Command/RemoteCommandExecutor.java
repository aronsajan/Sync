/*
Author: Aron Sajan Philip
 */
package Sync.Framework.Command;


import Sync.CommonTypes.CommandExecutionResult;
import Sync.CommonTypes.ICommand;
import Sync.CommonTypes.RemoteSystem;
import Sync.Framework.CommonProperties;
import Sync.Framework.Communicator.Transceiver;
import Sync.Framework.Serializer.BinarySerializer;

/**
 *
 * @author Aron
 */
public class RemoteCommandExecutor extends CommonProperties
{

    public void ExecuteCommand(ICommand Command, RemoteSystem Sender) throws InterruptedException
    {
        CommandExecutionResult ExecResult= Command.Execute(Sender);
        Sender.Port = Integer.parseInt(Properties.getProperty("REMOTE_EXEC_RESULT_PORT"));
        byte[] ResultStream = new BinarySerializer().Serialize(ExecResult);
        new Transceiver().SendData(ResultStream, Sender.IPAddress, Sender.Port);
    }
}
