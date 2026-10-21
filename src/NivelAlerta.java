public enum NivelAlerta{
    //Tipos de niveles de alerta
    VERDE,
    AMARILLA,
    NARANJA,
    ROJA;
    public boolean requiereEquipo() {
        if (this == NARANJA || this == ROJA) {
            return true;
        } else {
            return false;
        }
    }

}