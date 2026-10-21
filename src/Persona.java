<<<<<<< HEAD
public abstract class Persona  {
    //Atributos
=======
public abstract class Persona extends ResponsableTecnico {
>>>>>>> f6adcbd1d9effa67b1b07acff38b4ca246caa544
    private String dni;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String celular;

<<<<<<< HEAD
    //Constructor por defecto
=======
    //Constructor Vacio
>>>>>>> f6adcbd1d9effa67b1b07acff38b4ca246caa544
    public Persona(){

    }

<<<<<<< HEAD
    //Constructor parametrizado
=======
    //Construnctor completo
>>>>>>> f6adcbd1d9effa67b1b07acff38b4ca246caa544
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

<<<<<<< HEAD
    //Get nombrecompleto
=======
    //get nombrecompleto
>>>>>>> f6adcbd1d9effa67b1b07acff38b4ca246caa544
    public String getNombreCompleto(){
        return this.nombre+ " " + this.apellido;
    }
    
<<<<<<< HEAD
    //toString
    public String toString(){
        return "Dni: " + dni + ", Nombre: " + nombre + ", Apellido: " + apellido + ", Correo: " + correoElectronico + ", Telefono: " + celular;
    }
=======
>>>>>>> f6adcbd1d9effa67b1b07acff38b4ca246caa544
}


