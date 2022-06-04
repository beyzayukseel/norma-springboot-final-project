package com.tr.beyzanur.exception;

public class ValidationOperationException{

    public static class IdNotValidator extends BaseException {
        public IdNotValidator(String message) {
            super(message);
        }
    }

    public static class CanNotBeNullException extends BaseException {
        public CanNotBeNullException(String message) {
            super(message);
        }
    }

    public static class NotValidException extends BaseException {
        public NotValidException(String message) {
            super(message);
        }
    }
}
