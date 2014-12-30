/*
Author: Aron Sajan Philip
 */
package Sync.Framework.ExceptionSubsystem;

/**
 *
 * @author Aron
 */
public class FileOverWriteException extends Exception
{
    public FileOverWriteException()
    {
        super("The path already contains a file with the same name");
    }
}
