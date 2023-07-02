package Castillo;
import Castillo.PantallaPrincipal;
public class Castillo {
    public static void main(String[] args) {
       HotelData hotelDataObj = new HotelData();
       hotelDataObj.procesarArchivos();
       PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(hotelDataObj);
       pantallaPrincipal.setVisible(true);
    }
}
