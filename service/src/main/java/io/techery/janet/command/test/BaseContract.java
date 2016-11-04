package io.techery.janet.command.test;


import io.techery.janet.Command;
import rx.functions.Func1;

public abstract class BaseContract extends Contract {

    BaseContract(Func1<Command, Boolean> predicate) {
        super(predicate);
    }

    public Contract result(Object result) {
        this.result = result;
        return this;
    }

    public Contract exception(Exception exception) {
        this.exception = exception;
        return this;
    }

}
