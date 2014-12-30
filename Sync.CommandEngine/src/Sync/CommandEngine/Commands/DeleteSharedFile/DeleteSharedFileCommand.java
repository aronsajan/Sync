/*
Author: Aron Sajan Philip
 */
package Sync.CommandEngine.Commands.DeleteSharedFile;

import Sync.CommonTypes.*;
import Sync.Framework.CommonProperties;
import Sync.Framework.FileEngine.FileManager;
import Sync.Framework.SyncTable.SyncTable;
import java.io.File;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Aron
 */
public class DeleteSharedFileCommand implements ICommand, Serializable
{

    public DeleteSharedFileParameters Parameters;
    private UUID UniqueID;

    public DeleteSharedFileCommand()
    {
        Parameters = new DeleteSharedFileParameters();
    }

    @Override
    public UUID GetUUID()
    {
        return UniqueID;
    }

    @Override
    public void StoreUUID(UUID Arg)
    {
        UniqueID = Arg;
    }

    @Override
    public CommandExecutionResult Execute(RemoteSystem Sender)
    {
        CommandExecutionResult Result = new CommandExecutionResult(this.GetUUID(), false, null);
        try
        {
            CommonProperties FrameworkProperties = new CommonProperties();
            String SharedPath = FrameworkProperties.Properties.getProperty("SHARED_DIRECTORY");
            String FilePath = SharedPath + File.separator + Parameters.Filename;
            new FileManager().DeleteFile(FilePath);
            if (Parameters.DateTimeStamp == null)
            {
                throw new Exception("Invalid DateTime stamp parameter specified");
            }
            
            new SyncTable().DeleteFile(Parameters.Filename, Parameters.DateTimeStamp);
        }
        catch (Exception Ex)
        {
            Result.ExecutionException = Ex;
            Result.ExecutionFailed = true;
        }
        return Result;
    }

}
