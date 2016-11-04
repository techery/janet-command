package io.techery.janet.command.sample.actions;


import io.techery.janet.Command;
import io.techery.janet.command.annotations.CommandAction;

@CommandAction
public class CommandWithResult extends Command<String> {

    @Override
    protected void run(CommandCallback<String> callback) throws Throwable {
        callback.onSuccess("Result");
    }
}
