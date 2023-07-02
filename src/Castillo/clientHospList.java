package Castillo;
import Castillo.Cliente;
import Castillo.registroEstado;
import Castillo.HotelData;

public class clientHospList extends javax.swing.JFrame {
    HotelData hotelData;
    Boolean existe;
    registroEstado record;
    
    public clientHospList(HotelData data, Boolean CExiste, registroEstado record) {
        this.hotelData = data;
        this.existe = CExiste;
        this.record = record;
        initComponents();
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        String jlabel2Text = "Cliente: "+record.cliente.primer_nombre+" "+record.cliente.apellido+" - Tlf: "+record.cliente.celular;
        if(record.habitacion != null)  {
            jlabel2Text += " - Habitación: "+Integer.toString(record.habitacion.num_habitacion);
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Busqueda de Cliente Hospedado");

        jLabel2.setText(jlabel2Text);

        jToggleButton1.setText("Finalizar Hospedación");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(108, 108, 108))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }                       

    public void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        Integer numHab = 2;
        if(record.habitacion != null) {
            numHab = record.habitacion.num_habitacion;
        }
        String[] registroHistorialVals = {
            record.cliente.ci.toString(),
            record.cliente.primer_nombre,
            record.cliente.apellido,
            record.cliente.email,
            record.cliente.genero.toString(),
            record.llegada,
            numHab.toString()
        };
        hotelData.eliminarHospedacion(record);
        hotelData.crearRegistroHistorial(registroHistorialVals);
        setVisible(false);
    }                                              
                
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    javax.swing.JToggleButton jToggleButton1;
}
