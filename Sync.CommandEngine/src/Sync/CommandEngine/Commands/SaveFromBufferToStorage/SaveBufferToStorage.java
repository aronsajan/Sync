/*
Author: Aron Sajan Philip
 */
package Sync.CommandEngine.Commands.SaveFromBufferToStorage;

import Sync.CommandEngine.Commands.OpenDataChannel.DataBuffer;
import Sync.CommonTypes.CommandExecutionResult;
import Sync.CommonTypes.ICommand;
import Sync.CommonTypes.RemoteSystem;
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
public class SaveBufferToStorage implements ICommand,Serializable
{

    public UUID UniqueID;
    public SaveBufferToStorageParameters Parameters;
    public SaveBufferToStorage()
    {
        Parameters=new SaveBufferToStorageParameters();
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
        CommandExecutionResult Result=new CommandExecutionResult(this.GetUUID(),false,null);
        try
        {
            byte[] BufferedData = DataBuffer.GetBufferedData();
            CommonProperties FrameworkProperties=new CommonProperties();
            String SharedDirectory=FrameworkProperties.Properties.getProperty("SHARED_DIRECTORY");
             String FilePath=SharedDirectory+File.separator+Parameters.Filename;
            FileManager FileWriter=new FileManager();
            FileWriter.SaveAsFile(BufferedData, FilePath); 
            SyncTable LogTable=new SyncTable();
            if(Parameters.DateTimeStamp==null)
            {
                throw new Exception("Invalid DateTime stamp parameter specified");
            }
            LogTable.AddFile(Parameters.Filename,Parameters.DateTimeStamp);
        }
        catch (Exception Ex)
        {
            Result.ExecutionFailed=true;
            Result.ExecutionException=Ex;
        }
        
        return Result;
    }

}
