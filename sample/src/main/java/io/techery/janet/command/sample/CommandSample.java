package io.techery.janet.command.sample;

import java.util.concurrent.TimeUnit;

import io.techery.janet.ActionPipe;
import io.techery.janet.CommandActionService;
import io.techery.janet.Janet;
import io.techery.janet.command.sample.actions.ThreadSleepCommand;
import io.techery.janet.command.sample.actions.WrongCommand;
import io.techery.janet.helper.ActionStateSubscriber;
import rx.Observable;

public class CommandSample {

    public static void main(String... args) throws Throwable {
        Janet janet = new Janet.Builder()
                .addService(new CommandActionService())
                .build();

        ActionPipe<ThreadSleepCommand> actionPipe = janet.createPipe(ThreadSleepCommand.class);

        actionPipe.observe()
                .subscribe(new ActionStateSubscriber<ThreadSleepCommand>()
                        .onProgress((action, progress) -> System.out.println(progress))
                        .onSuccess(threadSleepAction -> System.out.println(threadSleepAction.getResult()))
                        .onFail((threadSleepAction1, throwable) -> throwable.printStackTrace()));

        ThreadSleepCommand action = new ThreadSleepCommand();

        Observable.fromCallable(() -> null)
                .delay(ThreadSleepCommand.DURATION - 1000, TimeUnit.MILLISECONDS)
                .subscribe(o -> actionPipe.cancel(action));

        actionPipe.send(action);

        janet.createPipe(WrongCommand.class).send(new WrongCommand());
    }
}
