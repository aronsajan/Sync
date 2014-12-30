/*
Author: Aron Sajan Philip
 */
package Sync.Framework.Communicator;

import Sync.Framework.CommonProperties;
import Sync.Framework.Utilities.Helper;
import java.io.*;
import java.net.*;

/**
 *
 * @author Aron
 */
public class Transceiver extends CommonProperties
{
    public void SendData(byte[] DataStream,String DestAddress,int port)
    {
        try
        {
         Socket ClientSocket=new Socket(DestAddress,port);
         OutputStream OutStream=ClientSocket.getOutputStream();
         DataOutputStream ClientOut=new DataOutputStream(OutStream);
         ClientOut.write(DataStream);
         ClientOut.close();
         OutStream.close();
        }
        catch(Exception Ex)
        {
            
        }
    }
    
    public ReceptionOutput ReceiveData(int ListenPort)
    {
        byte[] ReceivedData=null;
        ReceptionOutput output=new ReceptionOutput();
        try
        {
            ByteArrayOutputStream OutMemory=new ByteArrayOutputStream();
            ServerSocket ListenSocket=new ServerSocket(ListenPort);
            Socket InSocket= ListenSocket.accept();
            output.Sender.IPAddress=InSocket.getInetAddress().getHostAddress();
            ListenSocket.close();
            InputStream ServerRead=InSocket.getInputStream();
            DataInputStream ReceiveStream=new DataInputStream(ServerRead);
            byte[] ReceiveBuffer=new byte[Integer.parseInt(Properties.getProperty("NETWORK_BUFFER"))];
            int BytesRead=1;
            while((BytesRead=ReceiveStream.read(ReceiveBuffer))!=-1)
            {     
                OutMemory.write(ReceiveBuffer, 0, BytesRead);
            }
            
           ReceivedData=OutMemory.toByteArray();
            OutMemory.close();
            InSocket.close();
        }
        catch(Exception Ex)
        {
           int a=7;
        }
        finally
        {
           
        }
        output.ReceivedData=ReceivedData;
         return output;
    }
}
