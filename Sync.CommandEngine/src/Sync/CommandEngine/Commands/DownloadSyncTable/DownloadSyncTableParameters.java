/*
Author: Aron Sajan Philip
 */
package Sync.CommandEngine.Commands.DownloadSyncTable;

import Sync.CommonTypes.RemoteSystem;
import java.io.Serializable;

/**
 *
 * @author Aron
 */
public class DownloadSyncTableParameters implements Serializable
{
    public RemoteSystem SenderID;
    public DownloadSyncTableParameters()
    {
        SenderID=new RemoteSystem();
    }
}
