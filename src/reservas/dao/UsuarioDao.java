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
import reservas.modelo.Usuario;

/**
 *
 * @author xXSorzXx
 */
public class UsuarioDao {
    private ConexionMysql fabricaConexion;
    
    public UsuarioDao(){
        this.fabricaConexion = new ConexionMysql();
    }
    
    public boolean registrar(Usuario usuario){
        try {
            String SQL = "insert into usuarios(tipUsr, nomUsr, pasUsr, nopUsr, emaUsr, telUsr, dirUsr, estUsr) "
                    + "values(?,?,?,?,?,?,?,'Activo')";
            
            Connection connection = this.fabricaConexion.getConnection();
            
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            
            sentencia.setString((1), usuario.getTipoUsuario());
            sentencia.setString((2), usuario.getUsuario());
            sentencia.setString((3), usuario.getPassUsuario());
            sentencia.setString((4), usuario.getNombre());
            sentencia.setString((5), usuario.getEmail());
            sentencia.setString((6), usuario.getTelefono());
            sentencia.setString((7), usuario.getDireccion());
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
    
    public List<Usuario> listar(){
        List<Usuario> listarUsuario = new ArrayList<>();
        try {
            
            String SQL = "SELECT * FROM usuarios;";
            
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            ResultSet data = sentencia.executeQuery();
            
            while(data.next()){
                Usuario usuario = new Usuario();
                
                usuario.setId(data.getInt(1));
                usuario.setTipoUsuario(data.getString(2));
                usuario.setUsuario(data.getString(3));
                usuario.setPassUsuario(data.getString(4));
                usuario.setNombre(data.getString(5));
                usuario.setEmail(data.getString(6));
                usuario.setTelefono(data.getString(7));
                usuario.setDireccion(data.getString(8));
                usuario.setEstado(data.getString(9));
                
                listarUsuario.add(usuario);
                
            }
            data.close();;
            sentencia.close();
        } catch (Exception e) {
            System.err.println("No se mostraron los datos");
            System.err.println("Mensaje: " + e.getMessage());
        }
        return listarUsuario;        
    }
    
    public boolean editar(Usuario usuario){
        try {
            String SQL = "update usuarios set tipUsr=?, pasUsr=?, nopUsr=?, "
                    + "emaUsr=?, telUsr=?, dirUsr=?, estUsr=? "
                    + "WHERE idUsr=?";
            
            Connection connection = this.fabricaConexion.getConnection();
            
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            
            sentencia.setString((1), usuario.getTipoUsuario());
            sentencia.setString((2), usuario.getPassUsuario());
            sentencia.setString((3), usuario.getNombre());
            sentencia.setString((4), usuario.getEmail());
            sentencia.setString((5), usuario.getTelefono());
            sentencia.setString((6), usuario.getDireccion());
            sentencia.setString((7), usuario.getEstado());
            
            sentencia.setInt((8), usuario.getId());
            
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
