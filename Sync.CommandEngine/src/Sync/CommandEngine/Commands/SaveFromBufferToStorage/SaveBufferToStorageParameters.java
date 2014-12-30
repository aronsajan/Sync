/*
Author: Aron Sajan Philip
 */
package Sync.CommandEngine.Commands.SaveFromBufferToStorage;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.joda.time.DateTime;

/**
 *
 * @author Aron
 */
public class SaveBufferToStorageParameters implements Serializable
{
    public String Filename;
    public DateTime DateTimeStamp;
    
    public SaveBufferToStorageParameters()
    {
        DateTimeStamp=null;
    }
    
}
