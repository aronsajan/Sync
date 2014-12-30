/*
Author: Aron Sajan Philip
 */
package Sync.Framework.Command;


import Sync.CommonTypes.CommandExecutionResult;
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

public class RemoteExecutionResult extends CommonProperties 
{

    public CommandExecutionResult GetRemoteExecutionResult(UUID CmdUniqueID) throws ClassNotFoundException, IOException,ResultMismatchException
    {
        int ResultPort = Integer.parseInt(Properties.getProperty("REMOTE_EXEC_RESULT_PORT"));
        byte[] ResultRaw = new Transceiver().ReceiveData(ResultPort).ReceivedData;
        CommandExecutionResult CmdExecResult = (CommandExecutionResult) (new BinarySerializer().Deserialize(ResultRaw));
        if (CmdUniqueID.compareTo(CmdExecResult.CommandUniqueID)!=0)
        {
              throw new ResultMismatchException();
        }
        return CmdExecResult;
    }
}
