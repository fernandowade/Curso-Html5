package Utilitarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {
             
       public static Connection abrirConexao(){
          Connection connection = null;
          try{
             connection = (Connection)
             DriverManager.getConnection("jdbc:mysql://localhost:3306/jogosdigitais", "root", "123456");                    
             //DriverManager.getConnection("jdbc:mysql://localhost:3306/inter_m1", "root", "");
            }catch(SQLException e){
              JOptionPane.showMessageDialog(null, "Atenção! Conexão não estabelecida. Por favor, verificar.");} 
        
            return connection;
       }
    
       public static void fecharConexao(Connection connection){
            try{
               connection.close();
            }catch(SQLException e){e.getMessage();}
       }
 }