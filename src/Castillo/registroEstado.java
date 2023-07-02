package Castillo;
import Castillo.Cliente;
import Castillo.Habitacion;

public class registroEstado {
    Cliente cliente;
    Habitacion habitacion;
    String llegada;
    
    public registroEstado(Cliente clienteRecord, Habitacion Habitacion, String Llegada) {
        this.cliente = clienteRecord;
        this.habitacion = Habitacion;
        this.llegada = Llegada;
    }
}
