/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author xXSorzXx
 */
public class ConexionMysql {
    private Connection connection;
    private String usuario = "root";
    private String password = "Sor1906197912";
    private String servidor = "localhost";
    private String puerto = "3306";
    private String nombreBD = "sistema_reserva";
    
    private String url = "jdbc:mysql://"+servidor+":"+puerto+"/"+nombreBD+"?serverTimezone=UTC";
    
    private String driver = "com.mysql.cj.jdbc.Driver";
    public ConexionMysql() {
        try {
            Class.forName(driver);
            
            connection = DriverManager.getConnection(url, usuario, password);
            
            if(connection != null){
                System.out.println("Conexión realizada correctamente");
            }
        } catch (Exception e) {
            System.err.println("Laconexión no se realizó");
            System.err.println("Mensaje: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
    
    
}
