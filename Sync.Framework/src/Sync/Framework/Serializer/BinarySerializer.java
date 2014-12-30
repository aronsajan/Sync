/*
Author: Aron Sajan Philip
 */


package Sync.Framework.Serializer;


import java.io.*;
/**
 *
 * @author Aron
 */
public class BinarySerializer
{
    public byte[] Serialize(Object Data)
    {
         byte[] BinaryStream=null;
        try
        {
           
            ByteArrayOutputStream MemoryStream=new ByteArrayOutputStream();
            ObjectOutputStream ObjectOutStream=new ObjectOutputStream(MemoryStream);
            ObjectOutStream.writeObject(Data);
            ObjectOutStream.close();
           BinaryStream = MemoryStream.toByteArray();
        }
        catch(Exception Ex)
        {
           
        }
        return BinaryStream;
    }
    
    public Object Deserialize(byte[] Data)throws IOException,ClassNotFoundException
    {
        ByteArrayInputStream ByteStream=new ByteArrayInputStream(Data);
        ObjectInputStream ObjectInStream=new ObjectInputStream(ByteStream);
              Object DeserializedObject=  ObjectInStream.readObject();
              return DeserializedObject;
    }
}
