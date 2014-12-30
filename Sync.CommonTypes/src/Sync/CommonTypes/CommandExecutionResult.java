/*
Author: Aron Sajan Philip
 */
package Sync.CommonTypes;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Aron
 */
public class CommandExecutionResult implements Serializable
{
    public boolean ExecutionFailed;
    public Exception ExecutionException;
    public UUID CommandUniqueID;
    
   public CommandExecutionResult(UUID CmdID,boolean ExecFailed, Exception ExecException)
   {
       CommandUniqueID=CmdID;
       this.ExecutionFailed=ExecFailed;
       this.ExecutionException=ExecException;
   }
}
