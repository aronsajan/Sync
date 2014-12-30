/*
Author: Aron Sajan Philip
 */
package Sync.CommandEngine.Commands.OpenDataChannel;

import Sync.Framework.CommonProperties;
import Sync.Framework.Communicator.Async.AsyncReceiveBuffer;
import Sync.Framework.Communicator.Async.AsyncSpinLock;

/**
 *
 * @author Aron
 */
public class DataBuffer
{  
   public static byte[] GetBufferedData()throws InterruptedException
   {

      CommonProperties GeneralProperties=new CommonProperties();
        int SpinLockInterval=Integer.parseInt(GeneralProperties.Properties.getProperty("SPIN_LOCK_INTERVAL"));
       while(!AsyncSpinLock.AsyncReadCompleted)
       {
          Thread.sleep(SpinLockInterval);
       }
       byte[] ReceivedData=AsyncReceiveBuffer.Buffer.toByteArray();
        AsyncSpinLock.ResetSpinLock();
       AsyncReceiveBuffer.FlushBuffer();
       return ReceivedData;
   }
   public static void FlushBuffer()
   {
       AsyncReceiveBuffer.FlushBuffer();
   }
}
