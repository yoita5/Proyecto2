package Castillo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import Castillo.Habitacion;
import Castillo.Reserva;
import Castillo.Cliente;
import Castillo.registroEstado;
import Castillo.registroHistorial;
import Castillo.Nodo;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class HotelData {
    FileReader habitacionesFile;
    FileReader reservasFile;
    FileReader historicoFile;
    FileReader estadoFile;
    String message;
    String status;
    Habitacion[] habitacionesList;
    Integer[] habitacionNodosRecorridos;
    Reserva[] reservasList;
    registroEstado[] estadosList;
    registroHistorial[] historialList;
    Cliente[] clientesHospedadosHashTable;
    Reserva[][] clientesReservasHashTable;
    Integer[] historialNodosVisitados;
    nodoHabitacion habitacionesTree;
    
    public void procesarArchivos() {
        try{
            String habitacionesFilePath = "./habitaciones.csv";
            String reservasFilePath = "./reservas.csv";
            String historicoFilePath = "./historico.csv";
            String estadoFilePath = "./estado.csv";
            habitacionesFile = new FileReader(habitacionesFilePath);
            reservasFile = new FileReader(reservasFilePath);
            historicoFile = new FileReader(historicoFilePath);
            estadoFile = new FileReader(estadoFilePath);
            _procesarLineasArchivo(habitacionesFile, "habitaciones");
            habitacionesTree = crearHabitacionesTree();
            _procesarLineasArchivo(reservasFile, "reservas");
            ordenarReservasList();
            _procesarLineasArchivo(estadoFile, "estado");
            _procesarLineasArchivo(historicoFile, "historico");  
        } catch(Exception e) {
            System.out.println("Error:"+e.getMessage());
            this.message = "Error inesperado.";
            this.status = "wrong";
        }
    }
       
    private void _procesarLineasArchivo(FileReader file, String type) {
        try{
            if(file.ready()) {
                Integer i = 0;
                BufferedReader reader = new BufferedReader(file);
                String fileLine;
                
                while((fileLine = reader.readLine()) != null) {
                    if(i == 0) {
                        reader.mark(1000000000);
                    }
                    i += 1;
                }
                if(type == "habitaciones") {
                    habitacionesList = new Habitacion[i];
                }
                if(type == "reservas") {
                    reservasList = new Reserva[i];
                }
                if(type == "estado") {
                    estadosList = new registroEstado[i];
                    clientesHospedadosHashTable = new Cliente[i];
                }
                if(type == "historico") {
                    historialList = new registroHistorial[i];
                }                
                reader.reset();
                fileLine = "";
                i = 1;
                
                while((fileLine = reader.readLine()) != null) {
                        boolean checkCommas = checkCommas(fileLine);
                        String vals[];
                        if(!checkCommas) {
                            this.message = "El archivo CSV debe estar delimitado por comas (,)";
                            this.status = "wrong";
                        }

                        vals = fileLine.split(";");
                        if(type == "habitaciones") {
                            addHabitacionLine(vals, i-1);
                        }
                        if(type == "reservas") {
                            addReservaLine(vals, i-1);
                        }
                        if(type == "estado") {
                            addEstadoLine(vals, i-1);
                        }
                        if(type == "historico") {
                            addHistoricoLine(vals, i-1);
                        }
                        i += 1;
                }
            } else {
                this.message = "El archivo no está listo para ser leído";
                this.status = "wrong";
            }
        } catch(Exception e) {
            System.out.println("Error:"+e.getMessage());
            this.message = "Error inesperado.";
            this.status = "wrong";
        }
    }
    
    private boolean checkCommas(String Textline) {
        int dotIndex = Textline.lastIndexOf(';');
        return (dotIndex == -1) ? false : true;
    }
    
    private void addHabitacionLine(String[] vals, Integer index) {
        if(vals.length == 3) {
            Habitacion habitacionRecord = new Habitacion(Integer.parseInt(vals[0]), _getTipoHabitacion(vals[1]), Integer.parseInt(vals[2]));
            habitacionesList[index] = habitacionRecord;
        }
    }
    
    private void addReservaLine(String[] vals, Integer index) {
        if(vals.length == 9) {
            Integer genero = this._getClienteGenero(vals[4]);
            Cliente cliente = new Cliente(
                        Integer.parseInt(vals[0].replace(".", "")), 
                        vals[1],
                        vals[2],
                        "",
                        vals[3],
                        genero,
                        vals[6]
            );
            Reserva reservaRecord = new Reserva(cliente, this._getTipoHabitacion(vals[5]), vals[7], vals[8]);
            reservasList[index] = reservaRecord;
        }

    }
    
    private void addEstadoLine(String[] vals, Integer index) {
        if(vals.length == 7) {
                Habitacion habitacion = habitacionesList[getHabitacionIndex(Integer.parseInt(vals[0]))];
                Integer genero = this._getClienteGenero(vals[4]);
                Cliente cliente = new Cliente(
                            0, 
                            vals[1],
                            "",
                            vals[2],
                            vals[3],
                            genero,
                            vals[5]
                );
                this._addClienteHospedado2HashTavble(cliente);
                registroEstado record = new registroEstado(cliente, habitacion, vals[6]);
                estadosList[index] = record;
        }

    }
    
    //4
    public void crearHospedacion(String[] vals) {
        registroEstado[] newArr = new registroEstado[estadosList.length+1];
        for (int i = 0; i < estadosList.length; i++) { 
            newArr[i] = estadosList[i];
        }
        estadosList = newArr;
        clientesHospedadosHashTable = new Cliente[estadosList.length+1];
        addEstadoLine(vals, estadosList.length-1);
        _resetClientesHospedadosHashTable();
    }
    
    //5
    public void crearRegistroHistorial(String[] vals) {
        registroHistorial[] newArr = new registroHistorial[historialList.length+1];
        for (int i = 0; i < historialList.length; i++) { 
            newArr[i] = historialList[i];
        }
        historialList = newArr;
        addHistoricoLine(vals, historialList.length-1);
    }
    
    //5
    public void ordenarHospedacionesList() {
        registroEstado temp;     
        for (int i = 0; i < estadosList.length; i++) { 
            if(estadosList[i]!= null) {
                for (int j = i+1; j < estadosList.length; j++) {     
                    if(estadosList[j] != null)  {
                      if(estadosList[i].habitacion.num_habitacion > estadosList[j].habitacion.num_habitacion) {
                          temp = estadosList[i];    
                          estadosList[i] = estadosList[j];    
                          estadosList[j] = temp;    
                      }  
                    }
                }     
            }
        }    
    }
    
    //5
    public Integer getHospedacionByNumHab(Integer numHab) {
        int firstIndex = 0;
        int lastIndex = estadosList.length - 1;
        while(firstIndex <= lastIndex) {
            int middleIndex = (firstIndex + lastIndex) / 2;
            if(estadosList[middleIndex] != null) {
                if (estadosList[middleIndex].habitacion.num_habitacion == numHab) {
                    return middleIndex;
                }
                else if (estadosList[middleIndex].habitacion.num_habitacion < numHab)
                    firstIndex = middleIndex + 1;
                else if (estadosList[middleIndex].habitacion.num_habitacion > numHab)
                    lastIndex = middleIndex - 1;
            } else {
                firstIndex = middleIndex + 1;
            }
        }
        return -1;
    }
    
    //5
    public void eliminarHospedacion(registroEstado hospedacion) {
        registroEstado[] newArr = new registroEstado[estadosList.length-1];
        for (int i = 0; i < estadosList.length-1; i++) { 
            if(estadosList[i] != hospedacion) {
                newArr[i] = estadosList[i];
            }
        }
        estadosList = newArr;
        _resetClientesHospedadosHashTable();
    }
    
    public void _resetClientesHospedadosHashTable() {
        clientesHospedadosHashTable = new Cliente[estadosList.length];
        for (int i = 0; i < estadosList.length-1; i++) { 
            if(estadosList[i] != null) {
                _addClienteHospedado2HashTavble(estadosList[i].cliente);
            }
        }
    }
    
    //4
    public void _addClienteHospedado2HashTavble(Cliente clienteRecord) {
        Integer Index = 0;
        Integer ApellidoSum = _getBasicStrHashVal(clienteRecord.apellido);
        Integer NombreSum = _getBasicStrHashVal(clienteRecord.primer_nombre);
        Index = (ApellidoSum+NombreSum) % clientesHospedadosHashTable.length;
        while(clientesHospedadosHashTable[Index] != null){
            Index = (Index+1)%clientesHospedadosHashTable.length;
        }
        clientesHospedadosHashTable[Index] = clienteRecord;
    }
    
    //1
    public Integer _getClienteHospedadoHash(String Apellido, String Nombre) {
        Integer Index = 0;
        Integer ApellidoSum = _getBasicStrHashVal(Apellido);
        Integer NombreSum = _getBasicStrHashVal(Nombre);
        Index = (ApellidoSum+NombreSum) % clientesHospedadosHashTable.length;
        while(clientesHospedadosHashTable[Index] != null){
            Index = (Index+1)%clientesHospedadosHashTable.length;
        }
        return Index;
    }
    
    public registroEstado _getClienteHospedadoForUser(String Apellido, String Nombre) {
        Integer Index = 0;
        registroEstado record = null;
        for(int i = 0; i < 300; i++) {
           if(record == null) {
                if(i < estadosList.length) {
                    if(estadosList[i] != null) {
                        if(estadosList[i].cliente.primer_nombre.equals(Nombre) && estadosList[i].cliente.apellido.equals(Apellido)) {
                         record = estadosList[i];
                         break;
                    }
               }
            }
        }

    }
        return record;
    }
     
    public Integer changeClientHospeHashKey(Integer Index, String Nombre, String Apellido) {
        if(clientesHospedadosHashTable[Index] != null) {
            if(!clientesHospedadosHashTable[Index].primer_nombre.equals(Nombre) || !clientesHospedadosHashTable[Index].apellido.equals(Apellido)) {
                Index = changeClientHospeHashKey(Index, Nombre, Apellido);
            }
        } else {
            Index = changeClientHospeHashKey(Index, Nombre, Apellido);
        }
        return Index;
    }
    
    private Integer _getBasicStrHashVal(String strVal) {
        Integer sum = 0;
        for (int i = 0; i < strVal.length(); i++) {
            Integer asciiVal = (int) strVal.charAt(i);
            sum += (asciiVal*(i+1));
        }
        return sum;
    }
    
    //2 y 4
    public int binarySearchReservasList(int ci) {
        int firstIndex = 0;
        int lastIndex = reservasList.length - 1;
        while(firstIndex <= lastIndex) {
            int middleIndex = (firstIndex + lastIndex) / 2;
            if(reservasList[middleIndex] != null) {
                if (reservasList[middleIndex].cliente.ci == ci) {
                    return middleIndex;
                }
                else if (reservasList[middleIndex].cliente.ci < ci)
                    firstIndex = middleIndex + 1;
                else if (reservasList[middleIndex].cliente.ci > ci)
                    lastIndex = middleIndex - 1;
            } else {
                firstIndex = middleIndex + 1;
            }
        }
        return -1;
    }
    
    public void ordenarReservasList() {
        Reserva temp;     
        for (int i = 0; i < reservasList.length; i++) { 
            if(reservasList[i]!= null) {
                for (int j = i+1; j < reservasList.length; j++) {     
                    if(reservasList[j] != null)  {
                      if(reservasList[i].cliente.ci > reservasList[j].cliente.ci) {
                          temp = reservasList[i];    
                          reservasList[i] = reservasList[j];    
                          reservasList[j] = temp;    
                      }  
                    }
                }     
            }
        }    
    }
        
    public nodoHabitacion crearHabitacionesTree() {
        nodoHabitacion root = new nodoHabitacion(habitacionesList[0].num_habitacion);
        for(int i = 1; i < habitacionesList.length; i++) {
            if(habitacionesList[i] != null) {
                root.insertar(root, habitacionesList[i].num_habitacion);
            }
        }
        return root;
    }
    
    public void addHistoricoLine(String[] vals, Integer index) {
        if(vals.length == 7) {
            Habitacion habitacion = habitacionesList[getHabitacionIndex(Integer.parseInt(vals[6]))];
            Integer genero = this._getClienteGenero(vals[4]);
            Cliente cliente = new Cliente(
                        Integer.parseInt(vals[0].replace(".", "")), 
                        vals[1],
                        "",
                        vals[2],
                        vals[3],
                        genero,
                        ""
            );
            registroHistorial record = new registroHistorial(cliente, habitacion, vals[5]);
            historialList[index] = record;
            nodoHabitacion nodoHab = habitacionesTree.busqueda(habitacionesTree, habitacion.num_habitacion);
            registroHistorial[] newArr = new registroHistorial[nodoHab.historialHab.length+1];
            for(int a = 0; a < nodoHab.historialHab.length; a++) {
                newArr[a] = nodoHab.historialHab[a];
            }
            newArr[nodoHab.historialHab.length] = record;
            nodoHab.historialHab = newArr;
        }

    }
    
    public Integer getHabitacionIndex(Integer hab_num) {
        return hab_num-1;
    }
    
    public Integer _getClienteGenero(String generoStr) {
        Integer genero = 1;
        if(generoStr.equals("Female") || generoStr.equals("2")) {
            genero = 2;
        }
        return genero;
    }
    
    public Integer _getTipoHabitacion(String tipoHabStr) {
        Integer tipoHab = 1;
        if(tipoHabStr.equals("doble") || tipoHabStr.equals("1")) {
            tipoHab = 2;
        }
        if(tipoHabStr.equals("triple") || tipoHabStr.equals("3")) {
            tipoHab = 3;
        }
        if(tipoHabStr.equals("suite") || tipoHabStr.equals("4")) {
            tipoHab = 4;
        }
        return tipoHab;
    }
    
    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int result = Integer.parseInt(strNum);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
