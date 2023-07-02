package Castillo;
import Castillo.HotelData;
import Castillo.Habitacion;
import Castillo.nodoHabitacion;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class HistorialHabitacion extends javax.swing.JFrame {
    HotelData hotelData;
    Object lock = new Object();;
    javax.swing.JButton jButton1;
    javax.swing.JComboBox<String> jComboBox1;
    javax.swing.JLabel jLabel1;

    public HistorialHabitacion(HotelData data) {
        hotelData = data;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Consulte el Historial de la Habitación");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        String[] comboBoxList = new String[hotelData.habitacionesList.length];
        for(int i = 0; i < hotelData.habitacionesList.length; i++) {
            if(hotelData.habitacionesList[i] != null) {
                comboBoxList[i] = Integer.toString(hotelData.habitacionesList[i].num_habitacion);
            }
        }
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(comboBoxList));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(99, 99, 99))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }            

    public void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Integer habIndex = jComboBox1.getSelectedIndex();
        Habitacion habitacion = hotelData.habitacionesList[habIndex];
        nodoHabitacion nodo = hotelData.habitacionesTree.busqueda(hotelData.habitacionesTree, habitacion.num_habitacion);
        historialHabList pantalla = new historialHabList(hotelData, nodo);
        setVisible(false);
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
    }                                        

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
    }                                          
}