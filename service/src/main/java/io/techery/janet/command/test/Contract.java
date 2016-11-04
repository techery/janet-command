package io.techery.janet.command.test;


import io.techery.janet.Command;
import rx.functions.Func1;

public abstract class Contract {

    private Func1<Command, Boolean> predicate;

    Object result;
    Exception exception;

    Contract(Func1<Command, Boolean> predicate) {
        this.predicate = predicate;
    }

    public static <K extends Command> FilterContract<K> of(Func1<Command, Boolean> predicate) {
        return new FilterContract<>(predicate);
    }

    public static <K extends Command> FilterContract<K> of(Class<K> clazz) {
        return of(new ClassPredicate((Class<Command>) clazz));
    }

    public boolean check(Command command) {
        return predicate.call(command);
    }

}
