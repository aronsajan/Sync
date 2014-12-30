/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sync.PC;

import java.io.FileOutputStream;
import java.time.LocalDateTime;

/**
 *
 * @author Aron
 */
public class Logger
{
    public static void SaveExceptionLog(Exception Ex)
    {
        try
        {
        FileOutputStream write=new FileOutputStream("ExceptionLog.log",true);
        String ExceptionMsg=LocalDateTime.now().toString()+" - "+Ex.getMessage()+"\n";
        write.write(ExceptionMsg.getBytes());
        String LocalizedMessage=Ex.getLocalizedMessage();
       write.write(LocalizedMessage.getBytes());
        
        write.close();
        }
        catch(Exception Excption)
        {
            
        }
    }
}
