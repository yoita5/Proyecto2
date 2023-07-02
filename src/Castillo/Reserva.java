package Castillo;
import Castillo.Cliente;
public class Reserva {
    Cliente cliente;
    Integer tipo_hab;
    String llegada;
    String salida;
    Boolean checkedIn;
    Integer numHabHospedado;
    
    public Reserva(Cliente clienteRecord, Integer tipoHab, String Llegada, String Salida) {
        this.cliente = clienteRecord;
        this.tipo_hab = tipoHab;
        this.llegada = Llegada;
        this.salida = Salida;
        this.checkedIn = false;
        this.numHabHospedado = 0;
    }
}
