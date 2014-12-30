/*
Author: Aron Sajan Philip
 */
package Sync.Framework.FileEngine;

import Sync.Framework.CommonProperties;
import java.io.*;

/**
 *
 * @author Aron
 */
public class FileManager extends CommonProperties
{

    int FILE_BUFFER;

    public FileManager()
    {
        try
        {
            FILE_BUFFER = Integer.parseInt(Properties.getProperty("FILE_BUFFER"));
        }
        catch (Exception Ex)
        {
           
        }
    }

    public void CopyFile(String Filename, String DestinationPath)
    {
        try
        {
            File FileInfo = new File(Filename);
            String OutFilename = String.format("%s"+File.separator+"%s", DestinationPath, FileInfo.getAbsoluteFile().getName());
            FileInputStream ReadStream = new FileInputStream(Filename);
            FileOutputStream WriteStream = new FileOutputStream(OutFilename);
            byte[] ReadBuffer = new byte[FILE_BUFFER];
            int BytesRead = 0;
            while ((BytesRead = ReadStream.read(ReadBuffer)) != 0)
            {
                WriteStream.write(ReadBuffer, 0, BytesRead);
            }

            ReadStream.close();
            WriteStream.close();

        }
        catch (Exception Ex)
        {

        }

    }

    public void SaveAsFile(byte[] Data, String Filename) throws IOException
    {

        FileOutputStream OutStream = new FileOutputStream(Filename);
        OutStream.write(Data);
        OutStream.close();
    }

    public void DeleteFile(String Filename) throws Exception
    {
       
            File FileInfo = new File(Filename);
            if(FileInfo.exists())
            {
             FileInfo.delete();
            }
            else
            {
                throw new FileNotFoundException();
            }   
    }

    public byte[] LoadFromFile(String Filename)throws Exception
    {
        ByteArrayOutputStream ByteArrayStream=new ByteArrayOutputStream();
        
            FileInputStream Reader = new FileInputStream(Filename);
            int BytesRead = 0;
           byte[] ReadBuffer = new byte[Integer.parseInt(Properties.getProperty("FILE_BUFFER"))];
            
            while ((BytesRead = Reader.read(ReadBuffer)) != -1)
            {
                ByteArrayStream.write(ReadBuffer, 0, BytesRead);
            }
            Reader.close();
        
        
        return ByteArrayStream.toByteArray();
    }
}
