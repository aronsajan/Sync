/*
Author: Aron Sajan Philip
 */
package Sync.Framework.Communicator.Async;

import java.io.ByteArrayOutputStream;

/**
 *
 * @author Aron
 */
public class AsyncReceiveBuffer
{
    public static ByteArrayOutputStream Buffer;
    
    public AsyncReceiveBuffer()
    {
       FlushBuffer();
    }
    public static void FlushBuffer()
    {
        Buffer=new ByteArrayOutputStream();
    }
}
