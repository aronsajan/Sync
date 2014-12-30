/*
Author: Aron Sajan Philip
 */
package Sync.Core;

import Sync.CommandEngine.Commands.DeleteSharedFile.DeleteSharedFileCommand;
import Sync.CommandEngine.Commands.DownloadSyncTable.*;
import Sync.CommandEngine.Commands.OpenDataChannel.DataBuffer;
import Sync.CommandEngine.Commands.OpenDataChannel.OpenDataChannel;
import Sync.CommandEngine.Commands.SaveFromBufferToStorage.SaveBufferToStorage;
import Sync.CommandEngine.Commands.TransferFile.TransferFile;
import Sync.CommandEngine.Commands.TransferFile.TransferFileParameters;
import Sync.CommonTypes.*;
import Sync.Framework.Command.*;
import Sync.Framework.CommonProperties;
import Sync.Framework.Communicator.Async.*;
import Sync.Framework.FileEngine.FileManager;
import Sync.Framework.Serializer.BinarySerializer;
import Sync.Framework.SyncTable.LogRecord;
import Sync.Framework.SyncTable.SyncComparisonResult;
import Sync.Framework.SyncTable.SyncTable;
import Sync.Framework.SyncTable.SyncTableComparator;
import java.io.File;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author Aron
 */
public class SyncManager
{

    CommonProperties FrameworkProperties;

    public SyncManager()
    {
        FrameworkProperties = new CommonProperties();
    }

    public void Synchronize(RemoteSystem RemoteDevice) throws Exception
    {
        SyncTable LogTable = new SyncTable();
        List<LogRecord> RemoteLogTable = GetSyncTable(RemoteDevice);
        List<LogRecord> LocalLogTable = LogTable.GetLogTable();
        SyncTableComparator Comparator = new SyncTableComparator();
        SyncComparisonResult ComparisonResult = Comparator.CompareSyncTables(LocalLogTable, RemoteLogTable);
        String SharedPath = FrameworkProperties.Properties.getProperty("SHARED_DIRECTORY");
        FileManager FileHandler = new FileManager();

        //Deleting local files
        for (LogRecord DeleteLocalRecord : ComparisonResult.LocalDeleteList)
        {  
          DeleteLocalSharedFile(DeleteLocalRecord.Filename,DeleteLocalRecord.DateTime);
        }

        //Deleting remote files
        for (LogRecord DeleteRemoteRecord : ComparisonResult.RemoteDeleteList)
        {
            DeleteRemoteSharedFile(DeleteRemoteRecord.Filename, RemoteDevice,DeleteRemoteRecord.DateTime);
        }

        //Downloading new files
        for (LogRecord DownloadRecord : ComparisonResult.DownloadList)
        {
            DownloadFile(DownloadRecord.Filename, RemoteDevice,DownloadRecord.DateTime);
        }

        //Uploading files to remote device
        for (LogRecord UploadRecord : ComparisonResult.UploadList)
        {
            UploadFile(UploadRecord.Filename, RemoteDevice,UploadRecord.DateTime);
        }

    }

    public List<LogRecord> GetSyncTable(RemoteSystem RemoteID) throws Exception
    {
        List<LogRecord> SyncTable;

        //Opening Data Channel in local for receiving Sync table from Remote system
        AsyncReceiver Receiver = new AsyncReceiver();
        int DataChannel = Integer.parseInt(FrameworkProperties.Properties.getProperty("DATA_LINK_PORT"));
        Receiver.Port = DataChannel;
        Receiver.start();

        LocalCommandExecutor CmdExecutor = new LocalCommandExecutor();
        DownloadSyncTableParameters Params = new DownloadSyncTableParameters();
        Params.SenderID.Port = DataChannel;
        DownloadSyncTable RetrieveSyncTable = new DownloadSyncTable();
        RetrieveSyncTable.Parameters = Params;
        CommandExecutionResult GetSyncTableResult = CmdExecutor.ExecuteCommand((ICommand) RetrieveSyncTable, RemoteID);
        if (GetSyncTableResult.ExecutionFailed)
        {
            throw GetSyncTableResult.ExecutionException;
            //Code to stop the Data Channel opened in local
        }

        byte[] SyncTableBinary = Receiver.GetAsyncBuffer();
        BinarySerializer Deserializer = new BinarySerializer();
        SyncTable = (List<LogRecord>) Deserializer.Deserialize(SyncTableBinary);
        AsyncReceiveBuffer.FlushBuffer();
        return SyncTable;

    }

    public void DeleteLocalSharedFile(String Filename,DateTime NewDateTimeStamp)throws Exception
    {
        DeleteSharedFileCommand DeleteShared = new DeleteSharedFileCommand();
          DeleteShared.Parameters.Filename = Filename;
          DeleteShared.Parameters.DateTimeStamp=NewDateTimeStamp;
        CommandExecutionResult ExecResult= DeleteShared.Execute(null);
        if(ExecResult.ExecutionFailed)
        {
            throw ExecResult.ExecutionException;
        }
    }
    
    public void DeleteRemoteSharedFile(String Filename, RemoteSystem TargetSystem, DateTime NewDateTimeStamp) throws Exception
    {
        DeleteSharedFileCommand DeleteShared = new DeleteSharedFileCommand();
        DeleteShared.Parameters.Filename = Filename;
        DeleteShared.Parameters.DateTimeStamp=NewDateTimeStamp;
        LocalCommandExecutor CmdExecutor = new LocalCommandExecutor();
        CommandExecutionResult DeleteSharedResult = CmdExecutor.ExecuteCommand(DeleteShared, TargetSystem);
        if (DeleteSharedResult.ExecutionFailed)
        {
            throw DeleteSharedResult.ExecutionException;
        }
    }

    public void DownloadFile(String Filename, RemoteSystem DownloadSystem, DateTime NewDateTimeStamp) throws Exception
    {
        OpenDataChannel DataReceiver = new OpenDataChannel();
        CommandExecutionResult DataChannelResult = DataReceiver.Execute(null);

        if (DataChannelResult.ExecutionFailed)
        {
            throw DataChannelResult.ExecutionException;
        }
        int DataChannel = Integer.parseInt(FrameworkProperties.Properties.getProperty("DATA_LINK_PORT"));
        String SharedPath = FrameworkProperties.Properties.getProperty("SHARED_DIRECTORY");
        String WriteFile = SharedPath + File.separator + Filename;
        TransferFileParameters DownloadParameters = new TransferFileParameters();
        DownloadParameters.Filename = Filename;
        DownloadParameters.TargetMachine.Port = DataChannel;
        TransferFile DownloadFile = new TransferFile();
        DownloadFile.Parameters = DownloadParameters;

        LocalCommandExecutor LocalExecutor = new LocalCommandExecutor();
        CommandExecutionResult DownloadFileResult = LocalExecutor.ExecuteCommand(DownloadFile, DownloadSystem);

        if (DownloadFileResult.ExecutionFailed)
        {
            throw DownloadFileResult.ExecutionException;
            //Have to add code for stopping the listening data channel in the local machine
        }
        
        SaveBufferToStorage WriteToStorageCmd=new SaveBufferToStorage();
        WriteToStorageCmd.Parameters.Filename=Filename;
        WriteToStorageCmd.Parameters.DateTimeStamp=NewDateTimeStamp;
        CommandExecutionResult WriteToStorageResult=WriteToStorageCmd.Execute(null);
        if(WriteToStorageResult.ExecutionFailed)
        {
            throw WriteToStorageResult.ExecutionException;
        }
                

    }

    public void UploadFile(String Filename, RemoteSystem UploadSystem,DateTime NewDateTimeStamp) throws Exception
    {
        OpenDataChannel DataChannel = new OpenDataChannel();
        LocalCommandExecutor CmdExecutor = new LocalCommandExecutor();
        CommandExecutionResult OpenDataChannelResult = CmdExecutor.ExecuteCommand(DataChannel, UploadSystem);
        if (OpenDataChannelResult.ExecutionFailed)
        {
            throw OpenDataChannelResult.ExecutionException;
        }

        TransferFile UploadManager = new TransferFile();
        UploadManager.Parameters.Filename = Filename;
        CommandExecutionResult UploadResult = UploadManager.Execute(UploadSystem);

        if (UploadResult.ExecutionFailed)
        {
            throw UploadResult.ExecutionException;
        }

        SaveBufferToStorage WriteBufferCmd = new SaveBufferToStorage();
        WriteBufferCmd.Parameters.Filename = Filename;
        WriteBufferCmd.Parameters.DateTimeStamp=NewDateTimeStamp;
       CommandExecutionResult WriteToStorageResult= CmdExecutor.ExecuteCommand(WriteBufferCmd, UploadSystem);
        if(WriteToStorageResult.ExecutionFailed)
        {
            throw WriteToStorageResult.ExecutionException;
        }
       
    }
}
