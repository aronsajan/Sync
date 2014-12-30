/*
Author: Aron Sajan Philip
 */
package Sync.Framework.ExceptionSubsystem;

/**
 *
 * @author Aron
 */
public class SyncTableRecordNotFoundException extends Exception
{
    public SyncTableRecordNotFoundException()
    {
        super("The entry for deletion cannot be found in the Synchronization table");
    }
}
