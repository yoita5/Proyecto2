package Castillo;
import Castillo.Reserva;
public class CheckIn extends javax.swing.JFrame {
    HotelData hotelData;
    Reserva reservacion;

    public CheckIn(HotelData data, Reserva r) {
        hotelData = data;
        reservacion = r;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        String jLabelText = "Reservación de Cliente: "+reservacion.cliente.primer_nombre+" "+reservacion.cliente.apellido+" CI: "+reservacion.cliente.ci+" - "+reservacion.llegada;
        if(reservacion.checkedIn) {
            jLabelText += " (hospedado en habitación #"+reservacion.numHabHospedado.toString()+" )";
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Check-In");

        jToggleButton1.setText("Asignar Habitación");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        Integer counter = 0;
        for(int i = 0; i < hotelData.habitacionesList.length; i++) {
            if(hotelData.habitacionesList[i] != null) {
                if(hotelData.habitacionesList[i].tipo_hab.equals(reservacion.tipo_hab)) {
                    counter += 1;
                }
            }
        }
        String[] habitacionesArr = new String[counter];
        counter = 0;
        for(int i = 0; i < hotelData.habitacionesList.length; i++) {
            if(hotelData.habitacionesList[i] != null) {
                if(hotelData.habitacionesList[i].tipo_hab.equals(reservacion.tipo_hab)) {
                    habitacionesArr[counter] = hotelData.habitacionesList[i].num_habitacion.toString();
                    counter += 1;
                }
            }
        }
        
        jLabel2.setText(jLabelText);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(habitacionesArr));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jToggleButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton1)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        
        if(reservacion.checkedIn) {
            jComboBox1.setVisible(false);
            jToggleButton1.setVisible(false);
        }

        pack();
    }                 

    public void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        Integer habIndex = jComboBox1.getSelectedIndex();
        Habitacion habitacion = hotelData.habitacionesList[habIndex];
        String[] arrVals = {
                habitacion.num_habitacion.toString(), 
                reservacion.cliente.primer_nombre, 
                reservacion.cliente.apellido, 
                reservacion.cliente.email, 
                reservacion.cliente.genero.toString(), 
                reservacion.cliente.celular, 
                reservacion.llegada
        };
        hotelData.crearHospedacion(arrVals);
        reservacion.checkedIn = true;
        reservacion.numHabHospedado = habitacion.num_habitacion;
        setVisible(false);
    }                                              

    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    javax.swing.JToggleButton jToggleButton1;
}
