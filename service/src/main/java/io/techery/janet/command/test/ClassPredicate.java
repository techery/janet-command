package io.techery.janet.command.test;


import io.techery.janet.Command;
import rx.functions.Func1;

class ClassPredicate implements Func1<Command, Boolean> {
    private Class<Command> clazz;

    ClassPredicate(Class<Command> clazz) {
        this.clazz = clazz;
    }

    @Override public Boolean call(Command command) {
        return clazz == command.getClass();
    }
}
