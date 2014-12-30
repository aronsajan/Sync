/*
Author: Aron Sajan Philip
 */
package Sync.CommandEngine.Commands.DownloadSyncTable;

import Sync.CommonTypes.*;
import Sync.Framework.Communicator.Transceiver;
import Sync.Framework.Serializer.BinarySerializer;
import Sync.Framework.SyncTable.LogRecord;
import Sync.Framework.SyncTable.SyncTable;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Aron
 */
public class DownloadSyncTable implements ICommand,Serializable
{
    
    private UUID UniqueID;
    
    public DownloadSyncTableParameters Parameters;
    
    public DownloadSyncTable()
    {
        Parameters=new DownloadSyncTableParameters();
    }
    
     @Override
    public UUID GetUUID()
    {
        return UniqueID;
    }
    
    @Override
    public void StoreUUID(UUID ID)
    {
        UniqueID=ID;
    }
    @Override
    public CommandExecutionResult Execute(RemoteSystem Sender) 
    {
         CommandExecutionResult Result=new CommandExecutionResult(this.GetUUID(),false,null);
        try
        {
            Parameters.SenderID.IPAddress=Sender.IPAddress;
            List<LogRecord> SyncTable=FetchLogRecords();
            byte[] LogRecordsStream=new BinarySerializer().Serialize(SyncTable);
            new Transceiver().SendData(LogRecordsStream, Parameters.SenderID.IPAddress, Parameters.SenderID.Port);
        }
        catch(Exception Ex)
        {
            Result.ExecutionException=Ex;
            Result.ExecutionFailed=true;
        }
        return Result;
    }
    
    private List<LogRecord> FetchLogRecords()throws Exception
    {
        SyncTable SyncTableInstance=new SyncTable();
        SyncTableInstance.LoadLogRecordsFromFile();
      
        return SyncTableInstance.GetLogTable();
    }
}
