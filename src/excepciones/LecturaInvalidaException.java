package src.excepciones;

public class LecturaInvalidaException extends Exception {
    public LecturaInvalidaException(String mensaje) {
        super(mensaje);
    }
}