package Castillo;
public class Cliente {
    public Integer ci;
    public String primer_nombre;
    public String segundo_nombre;
    public String apellido;
    public String email;
    public Integer genero;
    public String celular;
    
    public Cliente(Integer Ci, String primerNombre, String SegundoNombre, String Apellido, String Email, Integer Genero, String Celular) {
        this.ci = Ci;
        this.primer_nombre = primerNombre;
        this.segundo_nombre = SegundoNombre;
        this.apellido = Apellido;
        this.email = Email;
        this.genero = Genero;
        this.celular = Celular;
    }
}
