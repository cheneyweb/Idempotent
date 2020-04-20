package top.xserver.idempotent.exception;

public class IdempotentException extends RuntimeException {
    public IdempotentException() {
        super();
    }

    public IdempotentException(String msg) {
        super(msg);
    }
}
