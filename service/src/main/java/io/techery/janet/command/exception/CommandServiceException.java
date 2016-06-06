package io.techery.janet.command.exception;

import io.techery.janet.Command;
import io.techery.janet.JanetException;

public class CommandServiceException extends JanetException {

    public CommandServiceException(Command action, Throwable cause) {
        super("Something went wrong with " + action.getClass().getSimpleName(), cause);
    }
}
