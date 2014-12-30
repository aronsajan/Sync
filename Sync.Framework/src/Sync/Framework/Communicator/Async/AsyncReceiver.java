/*
Author: Aron Sajan Philip
 */
package Sync.Framework.Communicator.Async;

import Sync.Framework.CommonProperties;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Aron
 */

public class AsyncReceiver extends Thread
{
    public int Port;
    public AsyncReceiver()
    {
        AsyncReceiveBuffer.FlushBuffer();
    }
    public void AsyncDownload() throws IOException,InterruptedException
    {
       AsyncSpinLock.ResetSpinLock();

        CommonProperties GeneralProperties=new CommonProperties();
        ByteArrayOutputStream ReceiveStream=new ByteArrayOutputStream();
        ServerSocket Listener=new ServerSocket(this.Port);
        System.out.println("Asynchronous receiver opened. Waiting for client connection...");
       Socket ClientSocket = Listener.accept();
       Listener.close();
      DataInputStream ReadStream =  new DataInputStream(ClientSocket.getInputStream());
    byte[] ReceiveBuffer=new byte[Integer.parseInt(GeneralProperties.Properties.getProperty("NETWORK_BUFFER"))];
    int BytesRead=1;
            while((BytesRead=ReadStream.read(ReceiveBuffer))!=-1)
            {     
                ReceiveStream.write(ReceiveBuffer, 0, BytesRead);
            }
            AsyncReceiveBuffer.Buffer.write(ReceiveStream.toByteArray());
            ReceiveStream.close();
            ReadStream.close();
            AsyncSpinLock.AsyncReadCompleted=true;
    }
    
    public void run()
    {
        try
        {
            AsyncDownload();
        }
        catch(Exception Ex)
        {
            System.out.println("Exception - "+Ex.getMessage());
        }
    }
    
    public byte[] GetAsyncBuffer()throws InterruptedException
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
}
