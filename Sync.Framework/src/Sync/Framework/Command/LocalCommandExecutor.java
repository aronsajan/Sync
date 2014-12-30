/*
Author: Aron Sajan Philip
 */
package Sync.Framework.Command;



import Sync.CommonTypes.CommandExecutionResult;
import Sync.CommonTypes.ICommand;
import Sync.CommonTypes.RemoteSystem;
import Sync.Framework.CommonProperties;
import Sync.Framework.Communicator.Transceiver;
import Sync.Framework.ExceptionSubsystem.ResultMismatchException;
import Sync.Framework.Serializer.BinarySerializer;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * @author Aron
 */
public class LocalCommandExecutor extends CommonProperties
{   
    public CommandExecutionResult ExecuteCommand(ICommand Command, RemoteSystem RemoteID)throws IOException,ClassNotFoundException,ResultMismatchException
    {
        RemoteID.Port=Integer.parseInt(Properties.getProperty("CONTROL_LINK_PORT"));
        Command.StoreUUID(UUID.randomUUID());
        byte[] CommandBin=new BinarySerializer().Serialize(Command);
        new Transceiver().SendData(CommandBin, RemoteID.IPAddress, RemoteID.Port);
        CommandExecutionResult ExecResult=  new RemoteExecutionResult().GetRemoteExecutionResult(Command.GetUUID());
        return ExecResult;
    }
}
