package taller;

public abstract class Persona extends ResponsableTecnico {
    private String dni;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String celular;

    //Constructor Vacio
    public Persona(){

    }

    //Construnctor completo
    public Persona(String dni, String nombre, String apellido, String correoElectronico,String celular){
        this.dni=dni;
        this.nombre=nombre;
        this.apellido=apellido;
        this.correoElectronico=correoElectronico;
        this.celular=celular;
    }

    // Getters y Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    //get nombrecompleto
    public String getNombreCompleto(){
        return this.nombre+ " " + this.apellido;
    }
    
}


