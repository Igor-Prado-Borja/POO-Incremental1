import java.lang.Exception;

class UsuarioExistenteException extends Exception{

    public UsuarioExistenteException(String message){
        // constructor with message but with no cause
        super(message);
    }

    public UsuarioExistenteException(String message, Throwable cause){
        // constructor with message and cause
        super(message, cause);
    }

    public UsuarioExistenteException(Throwable cause){
        // constructor with no message but with cause
        super(cause);
    }
}
