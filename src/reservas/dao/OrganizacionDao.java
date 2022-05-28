/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import reservas.conexion.ConexionMysql;
import reservas.modelo.Organizacion;

/**
 *
 * @author xXSorzXx
 */
public class OrganizacionDao {
    private ConexionMysql fabricaConexion;
    public OrganizacionDao(){
        this.fabricaConexion = new ConexionMysql();
    }
    
    public boolean registrar(Organizacion organizacion){
        try {
            String SQL = "insert into organizacion(nomOrg, dirOrg, telOrg, emaOrg, idAdmin, estOrg) "
                    + "values(?,?,?,?,?,'Activo')";
            
            Connection connection = this.fabricaConexion.getConnection();
            
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            
            sentencia.setString(1, organizacion.getNombre());
            sentencia.setString(2, organizacion.getDireccion());
            sentencia.setString(3, organizacion.getTelefono());
            sentencia.setString(4, organizacion.getEmail());
            sentencia.setString(5, organizacion.getAdmin());
            
            sentencia.executeUpdate();
            sentencia.close();
            
            return true;
            
        } catch (Exception e) {
            System.err.println("No se Insertaron los datos");
            System.err.println("Mensaje: " + e.getMessage());
            
            return false;
        }
       
    }
    
    public List<Organizacion> listar(){
        List<Organizacion> listaOrganizacion = new ArrayList<>();
        try {
            
            String SQL = "SELECT * FROM organizacion;";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            ResultSet data = sentencia.executeQuery();
            while(data.next()){
                Organizacion organizacion = new Organizacion();
                
                organizacion.setId(data.getInt(1));
                organizacion.setNombre(data.getString(2));
                organizacion.setDireccion(data.getString(3));
                organizacion.setTelefono(data.getString(4));
                organizacion.setEmail(data.getString(5));
                organizacion.setAdmin(data.getString(6));
                organizacion.setEstado(data.getString(7));
                
                listaOrganizacion.add(organizacion);
                
                
            }
            data.close();
            sentencia.close();
        } catch (Exception e) {
             System.err.println("No se mostraron los datos");
            System.err.println("Mensaje: " + e.getMessage());
            
            
        }
        return listaOrganizacion;
    }
    
    public boolean editar(Organizacion organizacion){
        try {
            String SQL = "update organizacion set dirOrg = ?, telOrg=?, emaOrg=?, idAdmin=?, estOrg=? "
                    + "WHERE idOrg=?" ;
            
            Connection connection = this.fabricaConexion.getConnection();
            
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            
            sentencia.setString((1), organizacion.getDireccion());
            sentencia.setString((2), organizacion.getTelefono());
            sentencia.setString((3), organizacion.getEmail());
            sentencia.setString((4), organizacion.getAdmin());
            sentencia.setString((5), organizacion.getEstado());
            
            sentencia.setInt((6), organizacion.getId());
            
            sentencia.executeUpdate();
            sentencia.close();
            return true;
            
        } catch (Exception e) {
            System.err.println("No se editaron los datos");
            System.err.println("Mensaje: " + e.getMessage());
            return false;
        }
        
    }
}
