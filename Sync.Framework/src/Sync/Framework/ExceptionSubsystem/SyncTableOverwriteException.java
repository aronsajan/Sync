/*
Author: Aron Sajan Philip
 */
package Sync.Framework.ExceptionSubsystem;

/**
 *
 * @author Aron
 */
public class SyncTableOverwriteException extends Exception
{
    public SyncTableOverwriteException()
    {
        super("A record already exists in the Synchronization table with the same file name");
    }
}
