/*
Author: Aron Sajan Philip
 */
package Sync.Framework.SyncTable;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.joda.time.DateTime;

/**
 *
 * @author Aron
 */
public class LogRecord implements Serializable
{
    public String Filename;
    public DateTime DateTime;
    public boolean DeleteFlag;
}
