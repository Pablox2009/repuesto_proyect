
import codigo.fomularioCode;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author coron
 */
public class Formulario extends javax.swing.JFrame {
    
    int puntos_venta = 0;
    int dev = 0;
    double t1 = 0, t2 = 0, t3 = 0, t4 = 0; 
    double tr = 0, tr1 = 0;
    double efectivo = 0, transferencia = 0, gastos = 0, cobranza = 0;
    double result = 0;
    double dif = 0;
    double rendicionR = 0, rendicionT = 0; 
    fomularioCode fc = new fomularioCode();
    private HashMap<String, String> codigoc;
            
    public Formulario() {
        initComponents();
        textos();
        rendicion_pvTXT.setText(String.valueOf(puntos_venta));
        total_realTXT.setText(String.format("%.2f", tr));
        diferenciaTXT.setText(String.format("%.2f", dif));
        
        codigoc = new HashMap<>();
        cargarClientes();
        
        DefaultTableModel fiado = new DefaultTableModel(new Object[]{"Código", "Cliente", "Saldo", "Estado"}, 0);
        fiado.addRow(new Object[]{"", "", 0, ""});
        fiado_table.setModel(fiado);
        saldoFiado(fiado);
        fiado.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 0) {
                    String codigo = (String) fiado.getValueAt(row, column);
                    String cliente = codigoc.get(codigo);
                    if (cliente != null) {
                        fiado.setValueAt(cliente, row, 1);
                    } else {
                        fiado.setValueAt("", row, 1);
                    }
                }
                if (column == 2) {
                    saldoFiado(fiado);
                    totalRendicion();
                }
            }
        });
        
        DefaultTableModel devolucion = new DefaultTableModel(new Object[]{"Código", "Cliente", "Saldo"}, 0);
        devolucion.addRow(new Object[]{"", "", 0});
        devolucion_table.setModel(devolucion);
        saldoDevolucion(devolucion);
        devolucion.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent d) {
                int row = d.getFirstRow();
                int column = d.getColumn();
                if (column == 0) {
                    String codigo = (String) devolucion.getValueAt(row, column);
                    String cliente = codigoc.get(codigo);
                    if (cliente != null) {
                        devolucion.setValueAt(cliente, row, 1);
                        dev++;
                    } else {
                        devolucion.setValueAt("Error", row, 1);
                        dev--;
                    }
                    pvTotal();
                }
                if (column == 2) {
                    saldoDevolucion(devolucion);
                    totalReal();
                }
                
            }
        });
        
        DefaultTableModel prd = new DefaultTableModel(new Object[]{"Cant.", "Desc", "Precio U,", "Total"}, 0);
        prd.addRow(new Object[]{"", "", "",""});
        prd_table.setModel(prd);
        saldoPrd(prd);
        prd.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent p) {
                int row = p.getFirstRow();
                int column = p.getColumn();
                if (column == 0 || column == 2) {
                    try {
                        int cant = Integer.parseInt(prd.getValueAt(row, 0).toString());
                        int precio = Integer.parseInt(prd.getValueAt(row, 2).toString());
                        prd.setValueAt(cant * precio, row, 3);
                    } catch (NumberFormatException ex) {
                    }
                }
                if (column == 3) {
                    saldoPrd(prd);
                    totalReal();
                }
            }
        });
        fila_fiado.addActionListener(e -> {
            fiado.addRow(new Object[]{"", "", 0,""});
        });
        fila_devolucion.addActionListener(d ->{
            devolucion.addRow(new Object[]{"", "", 0});
        });
        fila_prd.addActionListener(p -> {
            prd.addRow(new Object[]{0,"",0,""});
        });
    }
    
    public void pvTotal(){
        String p = pv_inicialTXT.getText();
        if(p.equals("")){
            rendicion_pvTXT.setText(String.valueOf(0));
            dev = 0;
        }
        else{
            int pv = Integer.parseInt(p);
            puntos_venta = pv - dev;
            rendicion_pvTXT.setText(String.valueOf(puntos_venta));
        }
    }
    
    public void Dev(DefaultTableModel model){
        dev =0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Object fila = model.getValueAt(i, 0);
            if (fila == null || fila.toString().trim().equals("")) {
                dev--;
            } else {
                dev++;
            }
        }
    }
    
    public void saldoFiado(DefaultTableModel model) {
        double saldoTotal = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Object saldoObj = model.getValueAt(i, 2);
            if (saldoObj != null) {
                try {
                    double saldo = Double.parseDouble(saldoObj.toString());
                    saldoTotal += saldo;
                    t4 = saldoTotal;
                } 
                catch (NumberFormatException e) {
                }
            }
        }
        saldo_fiadoTXT.setText(String.format("%.2f", saldoTotal));
    }
    
    public void saldoDevolucion(DefaultTableModel model) {
        double saldoTotal = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Object saldoObj = model.getValueAt(i, 2);
            if (saldoObj != null) {
                try {
                    double saldo = Double.parseDouble(saldoObj.toString());
                    saldoTotal += saldo;
                    t2 = saldoTotal;
                } catch (NumberFormatException e) {
                }
            }

        }
        devolucion_totalTXT.setText(String.format("%.2f", saldoTotal));
    }
    
    public void saldoPrd(DefaultTableModel model) {
        double saldoTotal = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Object saldoObj = model.getValueAt(i, 3);
            if (saldoObj != null) {
                try {
                    double saldo = Double.parseDouble(saldoObj.toString());
                    saldoTotal += saldo;
                    t3 = saldoTotal;
                } catch (NumberFormatException e) {
                }
            }
        }
        producto_devolucion_totalTXT.setText(String.format("%.2f", saldoTotal));
    }
    
    public void cargarClientes() {
        fc.clientes(codigoc);
    }
    
    public void textos(){
        saldo_fiadoTXT.setEditable(false);
        devolucion_totalTXT.setEditable(false);
        producto_devolucion_totalTXT.setEditable(false);
        total_realTXT.setEditable(false);
        rendicion_totalTXT.setEditable(false);
        rendicion_pvTXT.setEditable(false);
    }
    
    public void totalReal() {
        try {
            tr = t1-t2-t3;
            total_realTXT.setText(String.format("%.2f",tr));
            diferencia();
        }
        catch(Exception e){
            total_realTXT.setText("Error");
        }
    }
    
    public void totalRendicion(){
        try{
            tr1 = t4 + result;
            rendicion_totalTXT.setText(String.format("%.2f", tr1));
            diferencia();
        }
        catch(Exception e){
            rendicion_totalTXT.setText(String.format("%.2f", 0.0));
        }
    }
    
    public void diferencia(){
        try{
            dif = tr1 - tr;
            diferenciaTXT.setText(String.format("%.2f", dif));
        }
        catch(Exception e){
            
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        fiado_table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fechaTXT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        zonaTXT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        total_inicialTXT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        pv_inicialTXT = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        prd_table = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        devolucion_table = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        fila_prd = new javax.swing.JButton();
        producto_devolucion_totalTXT = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        efectivoTXT = new javax.swing.JTextField();
        cobranzaTXT = new javax.swing.JTextField();
        gastosTXT = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        transferenciaTXT = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        total_rTXT = new javax.swing.JTextField();
        panel1 = new java.awt.Panel();
        saldo_fiadoTXT = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        fila_fiado = new javax.swing.JButton();
        panel2 = new java.awt.Panel();
        devolucion_totalTXT = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        fila_devolucion = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        total_realTXT = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        rendicion_totalTXT = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        rendicion_pvTXT = new javax.swing.JTextField();
        boton_guardar = new javax.swing.JButton();
        boton_salir = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        diferenciaTXT = new javax.swing.JTextField();

        jFormattedTextField1.setText("jFormattedTextField1");
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        fiado_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(fiado_table);

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jLabel1.setText("Fecha:");

        jLabel2.setText("Zona:");

        jLabel3.setText("Total:");

        total_inicialTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                total_inicialTXTKeyReleased(evt);
            }
        });

        jLabel4.setText("Puntos de venta:");

        pv_inicialTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pv_inicialTXTKeyReleased(evt);
            }
        });

        prd_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(prd_table);

        devolucion_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(devolucion_table);

        jLabel5.setText("Fiado");

        jLabel7.setText("Devolución");

        jLabel8.setText("Producto devuelto");

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));

        fila_prd.setText("Agregar fila");

        jLabel19.setText("Total:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(fila_prd)
                .addGap(54, 54, 54)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(producto_devolucion_totalTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fila_prd)
                    .addComponent(jLabel19)
                    .addComponent(producto_devolucion_totalTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 102, 0)));

        efectivoTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                efectivoTXTKeyReleased(evt);
            }
        });

        cobranzaTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cobranzaTXTKeyReleased(evt);
            }
        });

        gastosTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                gastosTXTKeyReleased(evt);
            }
        });

        jLabel14.setText("Efectivo:");

        jLabel15.setText("Transferencia:");

        jLabel16.setText("Cobranza:");

        jLabel17.setText("Gastos:");

        transferenciaTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                transferenciaTXTKeyReleased(evt);
            }
        });

        jLabel20.setText("Total:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel16)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(109, 109, 109)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cobranzaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(gastosTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addGap(117, 117, 117)
                                    .addComponent(efectivoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(89, 89, 89)
                                .addComponent(transferenciaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(total_rTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(efectivoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(transferenciaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cobranzaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gastosTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(total_rTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        panel1.setBackground(new java.awt.Color(153, 204, 255));

        jLabel13.setText("Total:");

        fila_fiado.setText("Agregar fila");
        fila_fiado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fila_fiadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(fila_fiado)
                .addGap(64, 64, 64)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(saldo_fiadoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saldo_fiadoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(fila_fiado))
                .addContainerGap())
        );

        panel2.setBackground(new java.awt.Color(153, 204, 255));

        jLabel18.setText("Total:");

        fila_devolucion.setText("Agregar fila");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(fila_devolucion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(devolucion_totalTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(devolucion_totalTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(fila_devolucion))
                .addContainerGap())
        );

        jLabel9.setText("Total real:");

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 51, 0)));

        jLabel10.setText("Total de rendición");

        jLabel11.setText("Total:");

        jLabel12.setText("Puntos de venta:");

        boton_guardar.setBackground(new java.awt.Color(51, 255, 51));
        boton_guardar.setText("Guardar");
        boton_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_guardarActionPerformed(evt);
            }
        });

        boton_salir.setBackground(new java.awt.Color(255, 51, 51));
        boton_salir.setForeground(new java.awt.Color(255, 255, 255));
        boton_salir.setText("Salir");
        boton_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_salirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rendicion_totalTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(rendicion_pvTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(boton_guardar)
                .addGap(18, 18, 18)
                .addComponent(boton_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_guardar)
                    .addComponent(boton_salir)
                    .addComponent(rendicion_pvTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(rendicion_totalTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(28, 28, 28))
        );

        jLabel6.setText("Rendición");

        jLabel21.setText("Devolución:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(total_realTXT, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(diferenciaTXT))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fechaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(zonaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(total_inicialTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(pv_inicialTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1)
                                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fechaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(zonaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(total_inicialTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(pv_inicialTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(total_realTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(diferenciaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void fila_fiadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fila_fiadoActionPerformed

    }//GEN-LAST:event_fila_fiadoActionPerformed

    private void pv_inicialTXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pv_inicialTXTKeyReleased
        String pvi = pv_inicialTXT.getText();
        if(pvi.equals("")){
            rendicion_pvTXT.setText(String.valueOf(0));
        }
        else{
            int pv = Integer.parseInt(pvi);
            puntos_venta = pv;
            rendicion_pvTXT.setText(String.valueOf(puntos_venta));
        }
    }//GEN-LAST:event_pv_inicialTXTKeyReleased

    private void total_inicialTXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_total_inicialTXTKeyReleased
        String total = total_inicialTXT.getText();
        if(total.equals("")){
            total_realTXT.setText(String.format("%.2f", 0.0));
        }
        else{
            t1 = Double.parseDouble(total_inicialTXT.getText());
            tr = t1;
            total_realTXT.setText(String.format("%.2f", tr));
            diferencia();
        }
    }//GEN-LAST:event_total_inicialTXTKeyReleased

    private void efectivoTXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_efectivoTXTKeyReleased
        String efect = efectivoTXT.getText();
        if(efect.equals("")){
            efectivoTXT.setText(String.format("%.2f", 0.0));
            efectivo = 0;
        }
        else{
            efectivo = Double.parseDouble(efect);
            result = efectivo+transferencia-gastos-cobranza;
            total_rTXT.setText(String.format("%.2f",(result)));
        }
        totalRendicion();
    }//GEN-LAST:event_efectivoTXTKeyReleased

    private void transferenciaTXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_transferenciaTXTKeyReleased
        String trans = transferenciaTXT.getText();
        if(trans.equals("")){
            transferenciaTXT.setText(String.format("%.2f", 0.0));
            transferencia = 0;
        }
        else{
            transferencia = Double.parseDouble(trans);
            result = efectivo+transferencia-gastos-cobranza;
            total_rTXT.setText(String.format("%.2f",(result)));
        }
        totalRendicion();
    }//GEN-LAST:event_transferenciaTXTKeyReleased

    private void cobranzaTXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cobranzaTXTKeyReleased
        String cobr = cobranzaTXT.getText();
        if(cobr.equals("")){
            cobranzaTXT.setText(String.format("%.2f", 0.0));
            cobranza = 0;
        }
        else{
            cobranza = Double.parseDouble(cobr);
            result = efectivo+transferencia-gastos-cobranza;
            total_rTXT.setText(String.format("%.2f",(result)));
        }
        totalRendicion();
    }//GEN-LAST:event_cobranzaTXTKeyReleased

    private void gastosTXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gastosTXTKeyReleased
        String gast = gastosTXT.getText();
        if(gast.equals("")){
            gastosTXT.setText(String.format("%.2f", 0.0));
            gastos = 0;
        }
        else{
            gastos = Double.parseDouble(gast);
            result = efectivo+transferencia-gastos-cobranza;
            total_rTXT.setText(String.format("%.2f",(result)));
        }
        totalRendicion();
    }//GEN-LAST:event_gastosTXTKeyReleased

    private void boton_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_salirActionPerformed
        this.dispose();
    }//GEN-LAST:event_boton_salirActionPerformed

    private void boton_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_guardarActionPerformed
        String zona = zonaTXT.getText();
        String total_i = total_inicialTXT.getText();
        String pv = pv_inicialTXT.getText();
        String efectivoR = efectivoTXT.getText();
        String transferenciaR = transferenciaTXT.getText();
        String cobranzaR = cobranzaTXT.getText();
        String gastosR = gastosTXT.getText();
        if(zona.equals("")){
            JOptionPane.showMessageDialog(null, "No se definió la zona","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        if(total_i.equals("")){
            JOptionPane.showMessageDialog(null, "No se definió el total inicial","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        if(pv.equals("")){
            JOptionPane.showMessageDialog(null, "No se definió los puntos de venta iniciales","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        if(efectivoR.equals("")){
            JOptionPane.showMessageDialog(null, "No se definió el efectivo de la rendición","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        if(transferenciaR.equals("")){
            JOptionPane.showMessageDialog(null, "No se definió la transferencia de la rendición","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        if(cobranzaR.equals("")){
            JOptionPane.showMessageDialog(null, "No se definió las cobranzas de la rendición","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        if(gastosR.equals("")){
            JOptionPane.showMessageDialog(null, "No se definió los gastos de la rendición","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_boton_guardarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Formulario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_guardar;
    private javax.swing.JButton boton_salir;
    private javax.swing.JTextField cobranzaTXT;
    private javax.swing.JTable devolucion_table;
    private javax.swing.JTextField devolucion_totalTXT;
    private javax.swing.JTextField diferenciaTXT;
    private javax.swing.JTextField efectivoTXT;
    private javax.swing.JTextField fechaTXT;
    private javax.swing.JTable fiado_table;
    private javax.swing.JButton fila_devolucion;
    private javax.swing.JButton fila_fiado;
    private javax.swing.JButton fila_prd;
    private javax.swing.JTextField gastosTXT;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private javax.swing.JTable prd_table;
    private javax.swing.JTextField producto_devolucion_totalTXT;
    private javax.swing.JTextField pv_inicialTXT;
    private javax.swing.JTextField rendicion_pvTXT;
    private javax.swing.JTextField rendicion_totalTXT;
    private javax.swing.JTextField saldo_fiadoTXT;
    private javax.swing.JTextField total_inicialTXT;
    private javax.swing.JTextField total_rTXT;
    private javax.swing.JTextField total_realTXT;
    private javax.swing.JTextField transferenciaTXT;
    private javax.swing.JTextField zonaTXT;
    // End of variables declaration//GEN-END:variables
}
