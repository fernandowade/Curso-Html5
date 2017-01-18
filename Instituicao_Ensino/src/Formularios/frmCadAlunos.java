/*
 * frmCadAlunos.java
 *
 * Created on 03/11/2014, 11:59:12
 */
package Formularios;

import java.sql.Connection;                //biblioteca para istruções em SQL
import java.sql.SQLException;              //biblioteca para istruções em SQL 
import java.sql.ResultSet;                 //biblioteca para istruções em SQL 
import com.mysql.jdbc.PreparedStatement;   //biblioteca para istruções em Banco de Dados

import java.awt.AWTKeyStroke;              //biblioteca para tecla Enter Funcionar
import java.awt.KeyboardFocusManager;      //biblioteca para tecla Enter Funcionar
import java.awt.event.KeyEvent;            //biblioteca para tecla Enter Funcionar
import java.util.HashSet;                  //biblioteca para tecla Enter Funcionar

import javax.swing.JOptionPane;            //biblioteca mensagens de entrada, saídas em geral
import javax.swing.table.DefaultTableModel;//biblioteca para trabalhos com tabela

import Utilitarios.Conexao;                 //importação de classe
import Utilitarios.Sexo;                    //importação de classe
import Utilitarios.Fumante;                 //importação de classe
import Utilitarios.Limpar;                  //importação de classe
//import Utilitarios.ConverterData;         //importação de classe

/**
 *
 * @author professor
 */
public class frmCadAlunos extends javax.swing.JFrame {
             Limpar RM = new Limpar();      //instanciamento de métodos de uma determinada classe
             Sexo CX = new Sexo();          //instanciamento de métodos de uma determinada classe
             Fumante FU = new Fumante();    //instanciamento de métodos de uma determinada classe
            
             Connection connection = Conexao.abrirConexao(); //Conexão com a base de dados Abertura
           
    /** Creates new form frmCadAlunos */
    public frmCadAlunos() {
        initComponents();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));    // Código para a tecla ENTER funcionar
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));                                           // Código para a tecla ENTER funcionar
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);                          // Código para a tecla ENTER funcionar
        
        CarregarLista();
        
        btnSalvar.setEnabled(false);  // Desabilitar botão
        btnAlterar.setEnabled(false); // Desabilitar botão
        btnExcluir.setEnabled(false); // Desabilitar botão
    try {  
            //mascará para formatação de ferramenta de jformated
         fmsCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));  
         fmsCEP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));  
         fmsRG.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###-A")));  
         fmsNasc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));  
        }catch (java.text.ParseException ex) {  
         ex.printStackTrace();  
        }  
    }

    // método para buscar um registro na base de dados
    public void Buscar(){
             int Codigo = Integer.parseInt(txtRA.getText());
             try{
               java.sql.Statement st = connection.createStatement();                 // instanciar a criação da conexão
               PreparedStatement ps = (PreparedStatement) connection.prepareStatement("Select * From Alunos WHERE RA = ?"); // indtrução em SQL
            
               ps.setInt(1, Codigo);                //execução da instrução em SQL
               ResultSet reg = ps.executeQuery();   //execução da instrução em SQL
               reg.next();                          //execução da instrução em SQL
                  
               txtNome.setText(reg.getString("Nome"));
               txtSobreNome.setText(reg.getString("Sobre_Nome"));
               fmsRG.setText(reg.getString("RG"));
               fmsCPF.setText(reg.getString("CPF"));
               fmsCEP.setText(reg.getString("CEP"));
               fmsNasc.setText(reg.getString("Nasc"));
               
               CX.Sexo = (reg.getString("Sexo"));               
               if ("F".equals(CX.Sexo)){
                   this.rbtFem.setSelected(true);}
               else{
                   this.rbtMasc.setSelected(true);
               }
               
               FU.Fumar = (reg.getInt("Fumante"));               
               if (FU.Fumar == 1){
                   this.cboFumante.setSelectedItem("Sim");}
               else{
                   this.cboFumante.setSelectedItem("Não");
               }
              
               btnSalvar.setEnabled(false);     // Desabilitar botão
               btnExcluir.setEnabled(true);     // Habilitar botão
               btnAlterar.setEnabled(true);     // Habilitar botão
             }
             catch(SQLException e){
                  JOptionPane.showMessageDialog(null, "Atenção! RA Inexistente. Por favor, verificar.");        
                  LimparCaixas();
                  btnSalvar.setEnabled(true);     
                  txtNome.requestFocus();}
             }

        // método para limpar caixas de texto e caixas de formatação
     public void LimparCaixas(){
            txtNome.setText(null); // Limpar caixa
            txtSobreNome.setText(null); // Limpar caixa
            fmsRG.setText(null);  // Limpar caixa
            fmsCPF.setText(null); // Limpar caixa
            fmsCEP.setText(null);  // Limpar caixa
            fmsNasc.setText(null);  // Limpar caixa           
            btnSexo.clearSelection(); // Limpar caixa
            cboFumante.setSelectedIndex(0); // Limpar caixa
    }
     
         // método para carregar registros de uma base de dados   
     private void CarregarLista(){     
        try{
            java.sql.Statement st = connection.createStatement();        
            String comando = "Select * From Alunos";
            ResultSet res = st.executeQuery(comando);
            
            DefaultTableModel Modelo = (DefaultTableModel)
            tbaAlunos.getModel();
            Modelo.getDataVector().removeAllElements();

            while (res.next()){
                  String dados[] = new String[6];
                  dados[0] = res.getString("RA");
                  dados[1] = res.getString("Nome");
                  dados[2] = res.getString("Sobre_Nome");
                  dados[3] = res.getString("Sexo");
                  dados[4] = res.getString("CPF");
                  dados[5] = res.getString("Nasc");
                  Modelo.addRow(dados);
            }
            tbaAlunos.setModel(Modelo);             
        }catch(SQLException e) {JOptionPane.showMessageDialog(rootPane, e);}
}     

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnSexo = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        txtSobreNome = new javax.swing.JTextField();
        txtNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtRA = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fmsRG = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        fmsCPF = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        rbtFem = new javax.swing.JRadioButton();
        rbtMasc = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        cboFumante = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbaAlunos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        fmsNasc = new javax.swing.JFormattedTextField();
        fmsCEP = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastro e Controle de Alunos");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                formHierarchyChanged(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtSobreNome.setBackground(new java.awt.Color(255, 255, 204));

        txtNome.setBackground(new java.awt.Color(255, 255, 204));
        txtNome.setName("txtNome"); // NOI18N

        jLabel1.setText("RA");

        txtRA.setBackground(new java.awt.Color(255, 255, 204));
        txtRA.setName("txtRA"); // NOI18N
        txtRA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRAActionPerformed(evt);
            }
        });
        txtRA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRAFocusLost(evt);
            }
        });

        jLabel2.setText("Nome");

        jLabel3.setText("Sobre Nome");

        jLabel4.setText("RG");

        fmsRG.setBackground(new java.awt.Color(255, 255, 204));
        fmsRG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fmsRGActionPerformed(evt);
            }
        });

        jLabel5.setText("CPF");

        fmsCPF.setBackground(new java.awt.Color(255, 255, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSobreNome, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                    .addComponent(txtRA, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(fmsRG, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fmsCPF, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSobreNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fmsRG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(fmsCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sexo"));

        btnSexo.add(rbtFem);
        rbtFem.setText("Feminino");
        rbtFem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtFemActionPerformed(evt);
            }
        });

        btnSexo.add(rbtMasc);
        rbtMasc.setText("Masculino");
        rbtMasc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtMascActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(rbtFem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtMasc)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rbtFem)
                .addComponent(rbtMasc))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Fumante ?"));

        cboFumante.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Não", "Sim" }));
        cboFumante.setName(""); // NOI18N
        cboFumante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFumanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboFumante, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(cboFumante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cboFumante.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, 0, 46, Short.MAX_VALUE))
                .addGap(65, 65, 65))
        );

        tbaAlunos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RA", "Nome", "Sobre Nome", "Sexo", "CPF", "Nascimento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbaAlunos.setRowSelectionAllowed(false);
        jScrollPane2.setViewportView(tbaAlunos);
        tbaAlunos.getColumnModel().getColumn(0).setMinWidth(60);
        tbaAlunos.getColumnModel().getColumn(0).setPreferredWidth(60);
        tbaAlunos.getColumnModel().getColumn(0).setMaxWidth(60);
        tbaAlunos.getColumnModel().getColumn(1).setMinWidth(160);
        tbaAlunos.getColumnModel().getColumn(1).setPreferredWidth(160);
        tbaAlunos.getColumnModel().getColumn(1).setMaxWidth(160);
        tbaAlunos.getColumnModel().getColumn(2).setMinWidth(300);
        tbaAlunos.getColumnModel().getColumn(2).setPreferredWidth(300);
        tbaAlunos.getColumnModel().getColumn(2).setMaxWidth(300);
        tbaAlunos.getColumnModel().getColumn(3).setMinWidth(45);
        tbaAlunos.getColumnModel().getColumn(3).setPreferredWidth(45);
        tbaAlunos.getColumnModel().getColumn(3).setMaxWidth(45);
        tbaAlunos.getColumnModel().getColumn(4).setMinWidth(120);
        tbaAlunos.getColumnModel().getColumn(4).setPreferredWidth(120);
        tbaAlunos.getColumnModel().getColumn(4).setMaxWidth(120);

        jPanel3.setBackground(new java.awt.Color(204, 204, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Data de Nascimento");

        fmsNasc.setBackground(new java.awt.Color(255, 255, 204));

        fmsCEP.setBackground(new java.awt.Color(255, 255, 204));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("CEP");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(fmsCEP, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fmsNasc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fmsNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fmsCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Controles"));
        jPanel7.setFont(new java.awt.Font("Tahoma", 0, 14));

        btnAlterar.setText("Alterar");
        btnAlterar.setToolTipText("Altera informações e atualiza o registro na base de dados");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.setToolTipText("Exclui a informação da base de dados e limpa a tela");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.setToolTipText("Inclui as informações da tela na base de dados");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnNovo.setText("Novo");
        btnNovo.setToolTipText("Limpa a tela para novas informações, seja digitada ou pesquisada");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnSair.setText("Fechar");
        btnSair.setToolTipText("Altera informações e atualiza o registro na base de dados");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(240, Short.MAX_VALUE)
                .addComponent(btnSair)
                .addGap(50, 50, 50)
                .addComponent(btnAlterar)
                .addGap(56, 56, 56)
                .addComponent(btnExcluir)
                .addGap(50, 50, 50)
                .addComponent(btnSalvar)
                .addGap(50, 50, 50)
                .addComponent(btnNovo)
                .addGap(37, 37, 37))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNovo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSair, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-844)/2, (screenSize.height-665)/2, 844, 665);
    }// </editor-fold>//GEN-END:initComponents

private void fmsRGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fmsRGActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_fmsRGActionPerformed

private void cboFumanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFumanteActionPerformed
             FU.Fumar = cboFumante.getSelectedIndex();
}//GEN-LAST:event_cboFumanteActionPerformed

private void rbtFemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtFemActionPerformed
             CX.Sexo = "F";
}//GEN-LAST:event_rbtFemActionPerformed

private void rbtMascActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtMascActionPerformed
             CX.Sexo = "M";
}//GEN-LAST:event_rbtMascActionPerformed

private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
    try {
        java.sql.Statement st = connection.createStatement();
        st.executeUpdate("INSERT INTO Alunos (RA, Nome, Sobre_Nome, CPF, CEP, RG, Nasc, Sexo, Fumante) VALUES ("
                            +  this.txtRA.getText() + ","        
                            +  "'" + this.txtNome.getText() + "'" + ","
                            +  "'" + this.txtSobreNome.getText() + "'" + ","
                            +  "'" + this.RM.removerMascara(fmsCPF.getText()) + "'" + ","
                            +  "'" + this.RM.removerMascara(fmsCEP.getText()) + "'" + ","
                            +  "'" + this.RM.removerMascara(fmsRG.getText()) + "'" + ","
                            +  "'" + this.fmsNasc.getText() + "'" + ","                            
                            +  "'" + this.CX.StatusSexo() + "'" + ","
                            +  this.FU.StatusFumante() + ")");

        JOptionPane.showMessageDialog(rootPane, "Aluno inserido com sucesso");           

        CarregarLista(); //instanciamento do método
        LimparCaixas(); //instanciamento do método
        txtRA.setText(null);        
        txtRA.requestFocus();
        }catch(SQLException e) {JOptionPane.showMessageDialog(rootPane, e);} 
}//GEN-LAST:event_btnSalvarActionPerformed

private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    
}//GEN-LAST:event_formWindowClosed

private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
             frmCadAlunos.this.dispose(); //fechar formulário
}//GEN-LAST:event_btnSairActionPerformed

private void formHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_formHierarchyChanged
// TODO add your handling code here:
}//GEN-LAST:event_formHierarchyChanged

private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
try{
                java.sql.Statement st = connection.createStatement();                
                int Codigo = Integer.parseInt(txtRA.getText());            
            
                String nome = txtNome.getText();
                String sobre_nome = txtSobreNome.getText();
                String RG = RM.removerMascara(fmsRG.getText());
                String CPF = RM.removerMascara(fmsCPF.getText());
                String CEP = RM.removerMascara(fmsCEP.getText());
                String Sexo = CX.StatusSexo();
                String Nasc = fmsNasc.getText();                
                int Fumante = FU.StatusFumante();
                String sql = "UPDATE Alunos SET nome = ?, sobre_nome = ?, RG = ? , CPF = ?, CEP = ?, Sexo = ?, Nasc = ? , Fumante = ? "
                             + "WHERE RA = ?";
             
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
                ps.setString(1, nome);
                ps.setString(2, sobre_nome);
                ps.setString(3, RG);
                ps.setString(4, CPF);
                ps.setString(5, CEP);
                ps.setString(6, Sexo);
                ps.setString(7, Nasc);
                ps.setInt(8, Fumante);
                ps.setInt(9, Codigo);
                ps.executeUpdate();
                
            JOptionPane.showMessageDialog(null, "Cadastro Alterado com sucesso!");
            CarregarLista();
            LimparCaixas();
            txtRA.setText(null);            
            txtRA.requestFocus();                                
        }catch(SQLException e){
              JOptionPane.showMessageDialog(null, "Atenção! Código Inexistente. Por favor, verificar.");} 
}//GEN-LAST:event_btnAlterarActionPerformed

private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
             btnSalvar.setEnabled(true);
             btnAlterar.setEnabled(false);
             btnExcluir.setEnabled(false);
 
             LimparCaixas();            

             txtRA.setText(null);
             txtRA.requestFocus();
}//GEN-LAST:event_btnNovoActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
try{
            int Codigo = Integer.parseInt(txtRA.getText());
            
            java.sql.Statement st = connection.createStatement();                
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement("DELETE FROM Alunos WHERE RA = ?");
            ps.setInt(1, Codigo);            
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Aluno Excluído com sucesso!");
        
        }catch(SQLException e){
              JOptionPane.showMessageDialog(null, "Atenção! Código Inexistente. Por favor, verificar.");}

        CarregarLista();
        LimparCaixas();
        
        txtRA.setText(null);        
        txtRA.requestFocus();        
    }//GEN-LAST:event_btnExcluirActionPerformed

private void txtRAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRAActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtRAActionPerformed

private void txtRAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRAFocusLost
             Buscar();
}//GEN-LAST:event_txtRAFocusLost

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
            java.util.logging.Logger.getLogger(frmCadAlunos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCadAlunos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCadAlunos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCadAlunos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new frmCadAlunos().setVisible(true);
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup btnSexo;
    private javax.swing.JComboBox cboFumante;
    private javax.swing.JFormattedTextField fmsCEP;
    private javax.swing.JFormattedTextField fmsCPF;
    private javax.swing.JFormattedTextField fmsNasc;
    private javax.swing.JFormattedTextField fmsRG;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton rbtFem;
    private javax.swing.JRadioButton rbtMasc;
    private javax.swing.JTable tbaAlunos;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtRA;
    private javax.swing.JTextField txtSobreNome;
    // End of variables declaration//GEN-END:variables
     }
