package Castillo;
import Castillo.HotelData;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class BuscarReservacion extends javax.swing.JFrame {
    HotelData hotelData;
    Object lock = new Object();
    javax.swing.JButton jButton1;
    javax.swing.JLabel jLabel1;
    javax.swing.JTextField jTextField1;
    
    public BuscarReservacion(HotelData data) {
        hotelData = data;
        initComponents();
    }


   
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Busque la Reservación del Cliente");

        jTextField1.setText("Cédula de Identidad");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1)))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }                  

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            

    }                                           

    public void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String ci = jTextField1.getText();
        Integer ciInt;
        if(hotelData.isInteger(ci) == false) {
            JDialog d = new JDialog(this, "Error");
            JLabel jlabeldialog = new JLabel("La cédula debe ser numérica");
            d.add(jlabeldialog);
            d.setSize(300, 100);
            d.setVisible(true);
            return;
        } 
        ciInt = Integer.parseInt(ci);
        int busqueda = hotelData.binarySearchReservasList(ciInt);
        if(busqueda == -1) {
            JDialog d = new JDialog(this, "No encontrado");
            JLabel jlabeldialog = new JLabel("La persona con la cédula de identidad "+ci+" no tiene una reservación realizada.");
            d.add(jlabeldialog);
            d.setSize(500, 100);
            d.setVisible(true);
        } else {
           setVisible(false);
           Reserva reserva = hotelData.reservasList[busqueda];
           CheckIn pantalla = new CheckIn(hotelData, reserva);
           pantalla.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
           pantalla.setVisible(true);
           Thread t = new Thread() {
            public void run() {
                synchronized(lock) {
                    while (pantalla.isVisible())
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            }
        };
        t.start();

        pantalla.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                synchronized (lock) {
                    pantalla.setVisible(false);
                    lock.notify();
                    setVisible(true);
                    hotelData = pantalla.hotelData;
                }
            }

        });
        
        pantalla.jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla.jToggleButton1ActionPerformed(e);
                synchronized (lock) {
                    lock.notify();
                    pantalla.setVisible(false);
                    hotelData = pantalla.hotelData;
                    setVisible(true);
                }
            }
        });
        }
    }                                        
}
