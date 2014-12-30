/*
Author: Aron Sajan Philip
 */
package Sync.CommandEngine.Commands.OpenDataChannel;

import Sync.CommonTypes.*;
import Sync.Framework.CommonProperties;
import Sync.Framework.Communicator.Async.AsyncReceiver;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Aron
 */
public class OpenDataChannel implements ICommand,Serializable
{
    private UUID UniqueID;
    
    @Override
    public CommandExecutionResult Execute(RemoteSystem Sender)
    {
        CommandExecutionResult Result=new CommandExecutionResult(this.GetUUID(),false,null);
        try
        {
        AsyncReceiver DataChannelHandler=new AsyncReceiver();
        CommonProperties FrameworkProperties=new CommonProperties();
        DataChannelHandler.Port=Integer.parseInt(FrameworkProperties.Properties.getProperty("DATA_LINK_PORT"));
         DataChannelHandler.start();
        }
        catch(Exception Ex)
        {
            Result.ExecutionException=Ex;
            Result.ExecutionFailed=true;
        }
        
        return Result;
    }

    @Override
    public UUID GetUUID()
    {
        return this.UniqueID;
    }

    @Override
    public void StoreUUID(UUID Arg)
    {
      this.UniqueID=Arg;
    }
}
