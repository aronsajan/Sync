/*
Author: Aron Sajan Philip
 */
package Sync.CommonTypes;

import java.util.UUID;

/**
 *
 * @author Aron
 */
public interface ICommand
{
    UUID GetUUID();
    void StoreUUID(UUID Arg);
    CommandExecutionResult Execute(RemoteSystem Sender);
}
