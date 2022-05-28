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
import reservas.modelo.Recurso;

/**
 *
 * @author xXSorzXx
 */
public class RecursoDao {
    private ConexionMysql fabricaConexion;
    
    public RecursoDao(){
        this.fabricaConexion = new ConexionMysql();
    }
    
    public boolean registrar(Recurso recurso){
         try {
            String SQL = "insert into recursos(nomRec, autRec, conRec, tieRec, cosRec, estRec, idOrg) "
                    + "values(?,?,?,?,?,'Activo',?)";
            
            Connection connection = this.fabricaConexion.getConnection();
            
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            
            sentencia.setString((1), recurso.getNombre());
            sentencia.setString((2), recurso.getAutorizacion());
            sentencia.setString((3), recurso.getConfirmacion());
            sentencia.setString((4), recurso.getTiempo());
            sentencia.setString((5), recurso.getCosto());            
            sentencia.setInt((6), recurso.getIdOrg());
            //sentencia.setString((8), usuario.getEstado());
            
            sentencia.executeUpdate();
            sentencia.close();
            
            return true;
        } catch (Exception e) {
            System.err.println("No se insertaron los datos");
            System.err.println("Mensaje: " + e.getMessage());
            
            return false;
        }
    }
    
    public List<Recurso> listar(){
        List<Recurso> listarRecurso = new ArrayList<>();
        try {
            
            String SQL = "SELECT * FROM recursos;";
            
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            ResultSet data = sentencia.executeQuery();
            
            while(data.next()){
                Recurso recurso = new Recurso();
                
                recurso.setId(data.getInt(1));
                recurso.setNombre(data.getString(2));
                recurso.setAutorizacion(data.getString(3));
                recurso.setConfirmacion(data.getString(4));
                recurso.setTiempo(data.getString(5));
                recurso.setCosto(data.getString(6));
                recurso.setEstado(data.getString(7));
                recurso.setReservado(data.getString(8));
                recurso.setIdOrg(data.getInt(9));
                
                listarRecurso.add(recurso);
                
            }
            data.close();;
            sentencia.close();
        } catch (Exception e) {
            System.err.println("No se mostraron los datos");
            System.err.println("Mensaje: " + e.getMessage());
        }
        return listarRecurso;        
    }
    
    public boolean editar(Recurso recurso){
        try {
            String SQL = "update recursos set nomRec=?, autRec=?, conRec=?, "
                    + "tieRec=?, cosRec=?, estRec=?, idOrg=? "
                    + "WHERE idRec=?";
            
            Connection connection = this.fabricaConexion.getConnection();
            
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            
            sentencia.setString((1), recurso.getNombre());
            sentencia.setString((2), recurso.getAutorizacion());
            sentencia.setString((3), recurso.getConfirmacion());
            sentencia.setString((4), recurso.getTiempo());
            sentencia.setString((5), recurso.getCosto());
            sentencia.setString((6), recurso.getEstado());
            sentencia.setInt((7), recurso.getIdOrg());
            
            sentencia.setInt((8), recurso.getId());
            
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
    
    
    
