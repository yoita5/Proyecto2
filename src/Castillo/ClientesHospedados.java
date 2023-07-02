package Castillo;
import Castillo.HotelData;
import Castillo.registroEstado;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ClientesHospedados extends javax.swing.JFrame {
    HotelData hotelData;
    javax.swing.JButton jButton1;
    Object lock = new Object();
    
    public ClientesHospedados(HotelData data) {
        this.hotelData = data;
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setText("Arielle");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setText("Bragger");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Busque al Cliente Hospedado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }            

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
    }                                           

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
    }                                           

    public void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String nombre = jTextField1.getText();
        String apellido = jTextField2.getText();
        registroEstado registro = hotelData._getClienteHospedadoForUser(apellido, nombre);
        Boolean registroExiste = true;
        if(registro == null) {
            registroExiste = false;
            JDialog d = new JDialog(this, "Hospedación no encontrada");
            JLabel jlabeldialog = new JLabel(""+nombre+" "+apellido+" no está hospedado o ya finalizó su hospedación.");
            d.add(jlabeldialog);
            d.setSize(500, 100);
            d.setVisible(true);
        } else {
            clientHospList pantalla = new clientHospList(hotelData, registroExiste, registro);
            pantalla.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            setVisible(false);
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
                
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
}
