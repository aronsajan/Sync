/*
Author: Aron Sajan Philip
 */
package Sync.Framework.SyncTable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

/**
 *
 * @author Aron
 */
public class SyncTableComparator
{

    private List<LogRecord> NativeSyncTable;
    private List<LogRecord> RemoteSyncTable;
    DateTimeComparator DateTimeCompare;
   
   
   public SyncTableComparator()
   {
       DateTimeCompare=DateTimeComparator.getInstance();
   }
   
    public SyncComparisonResult CompareSyncTables(List<LogRecord> NativeSyncTable, List<LogRecord> RemoteSyncTable)
    {
        this.NativeSyncTable = NativeSyncTable;
        this.RemoteSyncTable = RemoteSyncTable;
        SyncComparisonResult ComparisonResult = new SyncComparisonResult();
        ComparisonResult.DownloadList=GetDownloadList();
        ComparisonResult.UploadList=GetUploadList();
        ComparisonResult.LocalDeleteList=GetLocalDeleteList();
        ComparisonResult.RemoteDeleteList=GetRemoteDeleteList();
        return ComparisonResult;
    }

    private List<LogRecord> GetDownloadList()
    {
         //UpdateList Preparation

        List<LogRecord> FirstPass = new ArrayList<LogRecord>();
        List<LogRecord> SecondPass = new ArrayList<LogRecord>(); 

        //Filter 1 - Filters ot exclusive elements in RemoteSyncTable which are not marked Delete
        for (LogRecord RemoteRecord : RemoteSyncTable)
        {

            boolean RecordFound = false;
            for (LogRecord NativeRecord : NativeSyncTable)
            {
                if (NativeRecord.Filename.compareTo(RemoteRecord.Filename) == 0)
                {
                    if (NativeRecord.DeleteFlag == RemoteRecord.DeleteFlag)
                    {
                        if (DateTimeCompare.compare(NativeRecord.DateTime, RemoteRecord.DateTime)==0)
                        {
                            RecordFound = true;
                            break;
                        }

                    }
                }
            }
            if (!RecordFound)
            {
                if (!RemoteRecord.DeleteFlag)
                {
                    FirstPass.add(RemoteRecord);
                }
            }

        }

        for (LogRecord CandidateRecord : FirstPass)
        {
            boolean FileNameFound = false;
            DateTime LatestNativeChangeEntry = null;
            for (LogRecord NativeRecord : NativeSyncTable)
            {
                if (NativeRecord.Filename.compareTo(CandidateRecord.Filename) == 0)
                {
                    LatestNativeChangeEntry = NativeRecord.DateTime;
                    FileNameFound = true;
                    break;
                }
            }

            if (FileNameFound)
            {
                if (CandidateRecord.DateTime.isAfter(LatestNativeChangeEntry))
                {
                     SecondPass.add(CandidateRecord);
                }
            }
            else
            {
                SecondPass.add(CandidateRecord);
            }
        }
        return SecondPass;
    }
    
    private List<LogRecord> GetUploadList()
    {
          List<LogRecord> FirstPass = new ArrayList<LogRecord>();
        List<LogRecord> SecondPass = new ArrayList<LogRecord>(); 
        
        for(LogRecord NativeRecord:NativeSyncTable)
        {
            boolean RecordFound=false;
            for(LogRecord RemoteRecord:RemoteSyncTable)
            {
                if (NativeRecord.Filename.compareTo(RemoteRecord.Filename) == 0)
                {
                    if (NativeRecord.DeleteFlag == RemoteRecord.DeleteFlag)
                    {
                        if (DateTimeCompare.compare(NativeRecord.DateTime, RemoteRecord.DateTime)==0)
                        {
                            RecordFound = true;
                            break;
                        }

                    }
                }
            }
            
            if(!RecordFound)
            {
                if(!NativeRecord.DeleteFlag)
                {
                    FirstPass.add(NativeRecord);
                }
            }
        }
        
        for(LogRecord CandidateRecord:FirstPass)
        {
            boolean FileNameFound=false;
            DateTime LatestRemoteChangeEntry=null;
            for(LogRecord RemoteRecord:RemoteSyncTable)
            {
                if(RemoteRecord.Filename.compareTo(CandidateRecord.Filename)==0)
                {
                    FileNameFound=true;
                    LatestRemoteChangeEntry=RemoteRecord.DateTime;
                    break;
                }
            }
            
            if(FileNameFound)
            {
                if(CandidateRecord.DateTime.isAfter(LatestRemoteChangeEntry))
                {
                    SecondPass.add(CandidateRecord);
                }
            }
            else
            {
                SecondPass.add(CandidateRecord);
            }
        }
        
        return SecondPass;
    }
    
    
    private List<LogRecord> GetLocalDeleteList()
    {
        List<LogRecord> FirstPass=new ArrayList<LogRecord>();
         List<LogRecord> SecondPass=new ArrayList<LogRecord>();
       for(LogRecord RemoteRecord:RemoteSyncTable)
       {
           for(LogRecord NativeRecord:NativeSyncTable)
           {
               if (NativeRecord.Filename.compareTo(RemoteRecord.Filename) == 0)
                {
                    
                    if(RemoteRecord.DeleteFlag!=NativeRecord.DeleteFlag)
                    {
                        if(RemoteRecord.DeleteFlag==true)
                        {
                            FirstPass.add(RemoteRecord);
                            break;
                        }
                    }
                }
                
           }
       }
       
       for(LogRecord CandidateRecord:FirstPass)
       {
           for(LogRecord NativeRecord:NativeSyncTable)
           {
               if(NativeRecord.Filename.compareTo(CandidateRecord.Filename)==0)
               {
                   if(CandidateRecord.DateTime.isAfter(NativeRecord.DateTime))
                   {
                       SecondPass.add(CandidateRecord);
                       break;
                   }
               }
           }
       }
       
       return SecondPass;
    }
    
    
    private List<LogRecord> GetRemoteDeleteList()
    {
        List<LogRecord> FirstPass=new ArrayList<LogRecord>();
         List<LogRecord> SecondPass=new ArrayList<LogRecord>();
       for(LogRecord NativeRecord:NativeSyncTable)
       {
           for(LogRecord RemoteRecord:RemoteSyncTable)
           {
               if (NativeRecord.Filename.compareTo(RemoteRecord.Filename) == 0)
                {
                    
                    if(RemoteRecord.DeleteFlag!=NativeRecord.DeleteFlag)
                    {
                        if(NativeRecord.DeleteFlag)
                        {
                            FirstPass.add(NativeRecord);
                            break;
                        }
                    }
                }
           }
       }
       
       for(LogRecord CandidateRecord:FirstPass)
       {
           
           for(LogRecord RemoteRecord:RemoteSyncTable)
           {
                if(RemoteRecord.Filename.compareTo(CandidateRecord.Filename)==0)
                {
                    if(CandidateRecord.DateTime.isAfter(RemoteRecord.DateTime))
                    {
                        SecondPass.add(CandidateRecord);
                        break;
                    }
                }
           }
       }
       return SecondPass;
    }
}
