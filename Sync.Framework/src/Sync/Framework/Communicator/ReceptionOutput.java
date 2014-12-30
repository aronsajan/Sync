/*
Author: Aron Sajan Philip
 */
package Sync.Framework.Communicator;

import Sync.CommonTypes.RemoteSystem;
import java.io.Serializable;

/**
 *
 * @author Aron
 */
public class ReceptionOutput implements Serializable
{
    public byte[] ReceivedData;
    public RemoteSystem Sender;
    public ReceptionOutput()
    {
        Sender=new RemoteSystem();
    }
}
