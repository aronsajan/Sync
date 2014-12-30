/*
Author: Aron Sajan Philip
 */
package Sync.Framework.Communicator.Async;

/**
 *
 * @author Aron
 */
public class AsyncSpinLock
{
    public static boolean AsyncReadCompleted;
    public static void ResetSpinLock()
    {
        AsyncReadCompleted=false;
    }
}
