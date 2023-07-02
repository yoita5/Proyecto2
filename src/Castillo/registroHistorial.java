package Castillo;
import Castillo.Cliente;
import Castillo.Habitacion;

public class registroHistorial {
    Cliente cliente;
    Habitacion habitacion;
    String llegada;
    
    public registroHistorial(Cliente clienteRecord, Habitacion Habitacion, String Llegada) {
        this.cliente = clienteRecord;
        this.habitacion = Habitacion;
        this.llegada = Llegada;
    }
}
