package io.techery.janet.command.test;


import io.techery.janet.Command;
import rx.functions.Func1;

public class FilterContract<T extends Command> extends BaseContract {

    private BaseContract contract;

    FilterContract(Func1<Command, Boolean> predicate) {
        super(predicate);
    }

    FilterContract(Func1<Command, Boolean> predicate, BaseContract contract) {
        super(predicate);
        this.contract = contract;
    }

    public FilterContract filter(Func1<T, Boolean> filterFunction) {
        return new FilterContract(filterFunction, this);
    }

    @Override public boolean check(Command command) {
        boolean result = true;
        if (contract != null) {
            result = contract.check(command);
        }
        if (result) {
            result = super.check(command);
        }
        return result;
    }
}
