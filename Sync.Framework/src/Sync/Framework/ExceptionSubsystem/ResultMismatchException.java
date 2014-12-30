/*
Author: Aron Sajan Philip
 */
package Sync.Framework.ExceptionSubsystem;

/**
 *
 * @author Aron
 */
public class ResultMismatchException extends Exception
{
    public ResultMismatchException()
    {
        super("The ID of the result does not match with the command sent");
    }
}
