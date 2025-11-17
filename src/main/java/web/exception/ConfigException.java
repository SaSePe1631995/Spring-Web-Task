package web.exception;

public class ConfigException extends RuntimeException{
    public ConfigException(String message, Throwable e) {
        super(message, e);
    }
    public ConfigException(String message) {
        super(message);
    }

    public ConfigException() {}
}
