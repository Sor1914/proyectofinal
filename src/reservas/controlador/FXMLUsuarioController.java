/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas.controlador;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;
import reservas.dao.UsuarioDao;
import reservas.modelo.Usuario;
/**
 *
 * @author xXSorzXx
 */
public class FXMLUsuarioController implements Initializable{
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<String> cboEstado;

    @FXML
    private ComboBox<String> cboRol;

    @FXML
    private TableView<Usuario> tvUsuarios;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombre;

     @FXML
    private PasswordField txtPass;
     
    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtUser;
    
    private UsuarioDao usuarioDao;
    
    private ContextMenu cmOpciones;
    
    private Usuario usuarioSelect;
    
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] opciones = {"Activo", "Inactivo"};
        ObservableList<String> items = FXCollections.observableArrayList(opciones);
        
        cboEstado.setItems(items);
        cboEstado.setValue("Seleccione el Estado");
        
        String[] opciones1 = {"Administrador", "Generico"};
        ObservableList<String> items1 = FXCollections.observableArrayList(opciones1);
        
        cboRol.setItems(items1);
        cboRol.setValue("Seleccione el Rol");
        
        this.usuarioDao = new UsuarioDao();
        
        cargarUsuarios();
        
        cmOpciones = new ContextMenu();
        
        MenuItem miEditar = new MenuItem("Editar");
        
        cmOpciones.getItems().addAll(miEditar);
        
        miEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = tvUsuarios.getSelectionModel().getSelectedIndex();
                usuarioSelect = tvUsuarios.getItems().get(index);
                
                System.out.println(usuarioSelect);
                
                txtUser.setText(usuarioSelect.getUsuario());
                txtPass.setText(usuarioSelect.getPassUsuario());
                txtNombre.setText(usuarioSelect.getNombre());
                txtCorreo.setText(usuarioSelect.getEmail());
                txtTelefono.setText(usuarioSelect.getTelefono());
                txtDireccion.setText(usuarioSelect.getDireccion());
                
                btnCancelar.setDisable(false);
                txtUser.setDisable(true);
            }
        });
        tvUsuarios.setContextMenu(cmOpciones);
    }
    
    
     @FXML
    void btnGuardarOnAcion(ActionEvent event) {
        if (usuarioSelect == null){
            Usuario usuario = new Usuario();
            
            usuario.setUsuario(txtUser.getText());
            usuario.setPassUsuario(txtPass.getText());
            usuario.setNombre(txtNombre.getText());
            usuario.setEmail(txtCorreo.getText());
            usuario.setTelefono(txtTelefono.getText());
            usuario.setDireccion(txtDireccion.getText());
            usuario.setTipoUsuario(cboRol.getSelectionModel().getSelectedItem());
            usuario.setEstado(cboEstado.getSelectionModel().getSelectedItem());
            
            System.out.println(usuario.toString());
            
            boolean rsp = this.usuarioDao.registrar(usuario);
            
            if (rsp){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Exito");;
                alert.setHeaderText(null);
                alert.setContentText("El registro se guardó correctamente");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
                limpiarCampos();
                cargarUsuarios();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");;
                alert.setHeaderText(null);
                alert.setContentText("Hubieron errores al guardar el registro");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            }
        } else {
            usuarioSelect.setPassUsuario(txtPass.getText());
            usuarioSelect.setNombre(txtNombre.getText());
            usuarioSelect.setEmail(txtCorreo.getText());
            usuarioSelect.setTelefono(txtTelefono.getText());
            usuarioSelect.setDireccion(txtDireccion.getText());
            usuarioSelect.setTipoUsuario(cboRol.getSelectionModel().getSelectedItem());
            usuarioSelect.setEstado(cboEstado.getSelectionModel().getSelectedItem());
            
            boolean rsp = this.usuarioDao.editar(usuarioSelect);
            
            if (rsp){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Exito");;
                alert.setHeaderText(null);
                alert.setContentText("El registro se guardó correctamente");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
                limpiarCampos();
                cargarUsuarios();
                
                usuarioSelect = null;
                
                btnCancelar.setDisable(true);
                txtNombre.setDisable(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");;
                alert.setHeaderText(null);
                alert.setContentText("Hubieron errores al guardar el registro");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            }
        }
    }
    
    private void limpiarCampos(){
        txtUser.setText("");
        txtPass.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        cboRol.getSelectionModel().select("Seleccione el Rol");
        cboEstado.getSelectionModel().select("Seleccion el Estado");
    }
    
    public void cargarUsuarios(){
        tvUsuarios.getItems().clear();
        tvUsuarios.getColumns().clear();
        
        List<Usuario> usuario = this.usuarioDao.listar();
        
        ObservableList<Usuario> data = FXCollections.observableArrayList(usuario);
        
        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn rolCol = new TableColumn("Rol");
        rolCol.setCellValueFactory(new PropertyValueFactory("tipoUsuario"));
        
        TableColumn usuarioCol = new TableColumn("Usuario");
        usuarioCol.setCellValueFactory(new PropertyValueFactory("Usuario"));
        
        TableColumn nombreCol = new TableColumn("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory("nombre"));
        
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));
        
        TableColumn telCol = new TableColumn("Telefono");
        telCol.setCellValueFactory(new PropertyValueFactory("telefono"));
        
        TableColumn direcCol = new TableColumn("Direccion");
        direcCol.setCellValueFactory(new PropertyValueFactory("direccion"));
        
        TableColumn estadoCol = new TableColumn("Estado");
        estadoCol.setCellValueFactory(new PropertyValueFactory("estado"));
        
        tvUsuarios.setItems(data);
        tvUsuarios.getColumns().addAll(idCol);
        tvUsuarios.getColumns().addAll(rolCol);
        tvUsuarios.getColumns().addAll(usuarioCol);
        tvUsuarios.getColumns().addAll(nombreCol);
        tvUsuarios.getColumns().addAll(emailCol);
        tvUsuarios.getColumns().addAll(telCol);
        tvUsuarios.getColumns().addAll(direcCol);
        tvUsuarios.getColumns().addAll(estadoCol);
    }
    
    @FXML
    void btnCancelarOnAction(ActionEvent event) {
        usuarioSelect = null;
        limpiarCampos();
        
        btnCancelar.setDisable(true);
        txtUser.setDisable(false);
    }
}
