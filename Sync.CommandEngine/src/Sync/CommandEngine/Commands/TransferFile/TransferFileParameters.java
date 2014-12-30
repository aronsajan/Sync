/*
Author: Aron Sajan Philip
 */
package Sync.CommandEngine.Commands.TransferFile;

import Sync.CommonTypes.RemoteSystem;
import java.io.Serializable;

/**
 *
 * @author Aron
 */
public class TransferFileParameters implements Serializable
{
   public RemoteSystem TargetMachine;
   public String Filename;
    public TransferFileParameters()
    {
        TargetMachine=new RemoteSystem();
    }
}
