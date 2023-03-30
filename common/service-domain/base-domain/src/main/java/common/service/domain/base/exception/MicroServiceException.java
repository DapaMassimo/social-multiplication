package common.service.domain.base.exception;

public class MicroServiceException extends Exception{

    private static final long serialVersionUID = 7982073383570857168L;

    public MicroServiceException(String message) {
        super(message);
    }

    public MicroServiceException(String message, Throwable throwable){
        super(message, throwable);
    }

    public MicroServiceException(Throwable throwable){
        super(throwable);
    }
}
