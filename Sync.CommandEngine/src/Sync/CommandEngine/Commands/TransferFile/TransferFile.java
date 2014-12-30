/*
Author: Aron Sajan Philip
Transfer file from one system to the Asynchronous buffer of another system
 */
package Sync.CommandEngine.Commands.TransferFile;


import Sync.CommonTypes.CommandExecutionResult;
import Sync.CommonTypes.ICommand;
import Sync.CommonTypes.RemoteSystem;
import Sync.Framework.CommonProperties;
import Sync.Framework.Communicator.Transceiver;
import Sync.Framework.FileEngine.FileManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Aron
 */
public class TransferFile implements ICommand, Serializable
{
    private UUID UniqueID;
    public TransferFileParameters Parameters;
    
    public TransferFile()
    {
        Parameters=new TransferFileParameters();
    }
    
    @Override
    public CommandExecutionResult Execute(RemoteSystem Sender)
    {
        CommandExecutionResult Result=new CommandExecutionResult(this.GetUUID(),false,null);
        try
        {
            Parameters.TargetMachine.IPAddress=Sender.IPAddress;
            CommonProperties FrameworkProperties=new CommonProperties();
            String SharedDirectory=FrameworkProperties.Properties.getProperty("SHARED_DIRECTORY");
            int DataPort=Integer.parseInt(FrameworkProperties.Properties.getProperty("DATA_LINK_PORT"));
            String FilePath=SharedDirectory+File.separator+Parameters.Filename;
            File FileInfo=new File(FilePath);
            if(FileInfo.exists())
            {
               FileManager FileReader=new FileManager();
                byte[] FileData=FileReader.LoadFromFile(FilePath);
                Transceiver NetworkSender=new Transceiver();
                NetworkSender.SendData(FileData, Parameters.TargetMachine.IPAddress, DataPort);
            }
            else
            {
               throw new FileNotFoundException();
            }
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
        return UniqueID;
    } 

    @Override
    public void StoreUUID(UUID Arg)
    {
        UniqueID=Arg;
    }
}
