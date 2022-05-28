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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;
import reservas.dao.RecursoDao;
import reservas.modelo.Recurso;
/**
 *
 * @author xXSorzXx
 */
public class FXMLRecursoController implements Initializable{
     @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<String> cboAprobacion;

    @FXML
    private ComboBox<String> cboConfirma;

    @FXML
    private ComboBox<String> cboEstado;

    @FXML
    private TableView<Recurso> tvUsuarios;

    @FXML
    private TextField txtCosto;

    @FXML
    private TextField txtIdOrg;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTiempo;

    private RecursoDao recursoDao;
    
    private ContextMenu cmOpciones;
    
    private Recurso recursoSelect;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] opciones = {"Activo", "Inactivo"};
        ObservableList<String> items = FXCollections.observableArrayList(opciones);
        
        cboEstado.setItems(items);
        cboEstado.setValue("Seleccione el Estado");
        
        String[] opciones1 = {"Si", "No"};
        ObservableList<String> items1 = FXCollections.observableArrayList(opciones1);
        
        cboAprobacion.setItems(items1);
        cboAprobacion.setValue("Seleccione");
        
        cboConfirma.setItems(items1);
        cboConfirma.setValue("Seleccione");
        
        this.recursoDao = new RecursoDao();
        
        cargarRecurso();
        
        cmOpciones = new ContextMenu();
        
        MenuItem miEditar = new MenuItem("Editar");
        
        cmOpciones.getItems().addAll(miEditar);
        
        miEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = tvUsuarios.getSelectionModel().getSelectedIndex();
                recursoSelect = tvUsuarios.getItems().get(index);
                
                System.out.println(recursoSelect);
                
                txtNombre.setText(recursoSelect.getNombre());
                txtTiempo.setText(recursoSelect.getTiempo());
                txtCosto.setText(recursoSelect.getCosto());
                txtIdOrg.setText(String.valueOf(recursoSelect.getIdOrg()));
                
                btnCancelar.setDisable(false);
                txtNombre.setDisable(true);
            }
        });
        tvUsuarios.setContextMenu(cmOpciones);
    }

    
    @FXML
    void btnGuardarOnAcion(ActionEvent event) {
         if (recursoSelect == null){
            Recurso recurso = new Recurso();
            
            recurso.setNombre(txtNombre.getText());
            recurso.setAutorizacion(cboAprobacion.getSelectionModel().getSelectedItem());
            recurso.setConfirmacion(cboConfirma.getSelectionModel().getSelectedItem());
            recurso.setTiempo(txtTiempo.getText());
            recurso.setCosto(txtCosto.getText());
            recurso.setIdOrg(Integer.parseInt(txtIdOrg.getText()));
            recurso.setEstado(cboEstado.getSelectionModel().getSelectedItem());
            
            System.out.println(recurso.toString());
            
            boolean rsp = this.recursoDao.registrar(recurso);
            
            if (rsp){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Exito");;
                alert.setHeaderText(null);
                alert.setContentText("El registro se guardó correctamente");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
                limpiarCampos();
                cargarRecurso();
                
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");;
                alert.setHeaderText(null);
                alert.setContentText("Hubieron errores al guardar el registro");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            }
        } else {
            recursoSelect.setAutorizacion(cboAprobacion.getSelectionModel().getSelectedItem());
            recursoSelect.setConfirmacion(cboConfirma.getSelectionModel().getSelectedItem());
            recursoSelect.setTiempo(txtTiempo.getText());
            recursoSelect.setCosto(txtCosto.getText());
            recursoSelect.setIdOrg(Integer.parseInt(txtIdOrg.getText()));
            recursoSelect.setEstado(cboEstado.getSelectionModel().getSelectedItem());
            
            boolean rsp = this.recursoDao.editar(recursoSelect);
            
            if (rsp){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Exito");;
                alert.setHeaderText(null);
                alert.setContentText("El registro se guardó correctamente");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
                limpiarCampos();
                cargarRecurso();
                
                recursoSelect = null;
                
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
        txtNombre.setText("");
        txtTiempo.setText("");
        txtCosto.setText("");
        txtIdOrg.setText("");
    }
    
    public void cargarRecurso(){
        tvUsuarios.getItems().clear();
        tvUsuarios.getColumns().clear();
        
        List<Recurso> usuario = this.recursoDao.listar();
        
        ObservableList<Recurso> data = FXCollections.observableArrayList(usuario);
        
        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn rolCol = new TableColumn("Nombre");
        rolCol.setCellValueFactory(new PropertyValueFactory("nombre"));
        
        TableColumn usuarioCol = new TableColumn("Autorización");
        usuarioCol.setCellValueFactory(new PropertyValueFactory("Autorizacion"));
        
        TableColumn nombreCol = new TableColumn("Confirmación");
        nombreCol.setCellValueFactory(new PropertyValueFactory("confirmacion"));
        
        TableColumn emailCol = new TableColumn("Tiempo");
        emailCol.setCellValueFactory(new PropertyValueFactory("tiempo"));
        
        TableColumn telCol = new TableColumn("Costo");
        telCol.setCellValueFactory(new PropertyValueFactory("costo"));
        
        TableColumn direcCol = new TableColumn("Estado");
        direcCol.setCellValueFactory(new PropertyValueFactory("estado"));
        
        TableColumn estadoCol = new TableColumn("Organización");
        estadoCol.setCellValueFactory(new PropertyValueFactory("idOrg"));
        
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
        recursoSelect = null;
        limpiarCampos();        
        btnCancelar.setDisable(true);
        txtNombre.setDisable(false);
    }
}
