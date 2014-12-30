/*
Author: Aron Sajan Philip
 */
package Sync.Framework.SyncTable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aron
 */
public class SyncComparisonResult
{
    public List<LogRecord> LocalDeleteList;
    public List<LogRecord> RemoteDeleteList;
    public List<LogRecord> DownloadList;
    public List<LogRecord> UploadList;
    boolean HasChanges;
    
    public SyncComparisonResult()
    {
        LocalDeleteList=new ArrayList<LogRecord>();
        RemoteDeleteList=new ArrayList<LogRecord>();
        DownloadList=new ArrayList<LogRecord>();
        UploadList=new ArrayList<LogRecord>();
        HasChanges=false;
        
    }
    
}
