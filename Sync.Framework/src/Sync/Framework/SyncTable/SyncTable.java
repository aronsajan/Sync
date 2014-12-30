/*
Author: Aron Sajan Philip
 */
package Sync.Framework.SyncTable;

import Sync.Framework.CommonProperties;
import Sync.Framework.ExceptionSubsystem.*;
import Sync.Framework.FileEngine.FileManager;
import Sync.Framework.Serializer.BinarySerializer;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author Aron
 */
public class SyncTable extends CommonProperties
{
    private List<LogRecord> LogTable;
    public SyncTable()throws Exception
    {
        LogTable=new ArrayList<LogRecord>();
        LoadLogRecordsFromFile();
    }
    
    public void AddFile(String Filename, DateTime DateTimeStamp)throws Exception
    {
        boolean RecordFoundFlag=false;
        for(LogRecord LogEntity:LogTable)
        {
            if(LogEntity.Filename.compareTo(Filename)==0)
            {
                if(LogEntity.DeleteFlag)
                {
                    LogTable.remove(LogEntity);
                    LogEntity.DeleteFlag=false;
                    LogEntity.DateTime=DateTimeStamp;
                    LogTable.add(LogEntity);
                    RecordFoundFlag=true;
                    
                    break;
                }
                else
                {
                  throw new SyncTableOverwriteException();
                }
            }
        }
        
        if(!RecordFoundFlag)
        {
           LogRecord Record=new LogRecord();
           Record.Filename=Filename;
           Record.DateTime=DateTimeStamp;
           Record.DeleteFlag=false;
           LogTable.add(Record);
        }
        
        SaveLogRecord();
    }
    
    public void DeleteFile(String Filename, DateTime DateTimeStamp)throws Exception
    {
        boolean Deleted=false;
        for(LogRecord Record:LogTable)
        {
            if(Record.Filename.compareTo(Filename)==0)
            {
                Record.DeleteFlag=true;
                Record.DateTime=DateTimeStamp;
                Deleted=true;
                break;
            }
        }
        
        if(Deleted)
        {
         SaveLogRecord();
        }
        else
        {
          throw  new SyncTableRecordNotFoundException();
        }
    }
    
    private void SaveLogRecord()throws Exception
    {
        String SyncTableFile=Properties.getProperty("SYNC_TABLE_FILE");
        BinarySerializer Serializer=new BinarySerializer();
       byte[] SyncTableBin=Serializer.Serialize(LogTable); 
       new FileManager().SaveAsFile(SyncTableBin, SyncTableFile);
    }
    
    public void LoadLogRecordsFromFile()throws Exception
    {
        String SyncTableFile=Properties.getProperty("SYNC_TABLE_FILE");
        File SyncLogFile=new File(SyncTableFile);
        if(SyncLogFile.exists())
        {
            FileManager FileHandler=new FileManager();
          byte[] SyncLogRaw=FileHandler.LoadFromFile(SyncTableFile);
          LogTable=(List<LogRecord>)new BinarySerializer().Deserialize(SyncLogRaw);
        }
    }
    
    public List<LogRecord> GetLogTable()
    {
        return LogTable;
    }
    
}
