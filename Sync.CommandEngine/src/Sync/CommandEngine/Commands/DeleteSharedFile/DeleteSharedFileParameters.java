/*
Author: Aron Sajan Philip
 */
package Sync.CommandEngine.Commands.DeleteSharedFile;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.joda.time.DateTime;

/**
 *
 * @author Aron
 */
public class DeleteSharedFileParameters implements Serializable
{
    public String Filename;
    public DateTime DateTimeStamp;
    
    public DeleteSharedFileParameters()
    {
        DateTimeStamp=null;
    }
}
