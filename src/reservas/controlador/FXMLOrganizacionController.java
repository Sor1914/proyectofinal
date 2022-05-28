package reservas.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;
import reservas.dao.OrganizacionDao;
import reservas.modelo.Organizacion;

/**
 * FXML Controller class
 *
 * @author xXSorzXx
 */
public class FXMLOrganizacionController implements Initializable {
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<String> cboAdmin;
    
      @FXML
    private ComboBox<String> cboAdmin1;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;
    
    @FXML
    private TableView<Organizacion> tvOrganizacion;
    
    private OrganizacionDao organizacionDao;

    private ContextMenu cmOpciones;
    
    private Organizacion organizacionSelect;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            String[] opciones = {"Activo", "Inactivo"};
            ObservableList<String> items = FXCollections.observableArrayList(opciones);

            cboAdmin.setItems(items);
            cboAdmin.setValue("Seleccione el Estado");
            
            String[] opciones1 = {"1", "2"};
            ObservableList<String> items1 = FXCollections.observableArrayList(opciones1);

            cboAdmin1.setItems(items1);
            cboAdmin1.setValue("Seleccione el Admin");

            this.organizacionDao = new OrganizacionDao();

            cargarOrganizaciones();

            cmOpciones = new ContextMenu();

            MenuItem miEditar = new MenuItem("Editar");

            cmOpciones.getItems().addAll(miEditar);

            miEditar.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    int index = tvOrganizacion.getSelectionModel().getSelectedIndex();
                    organizacionSelect = tvOrganizacion.getItems().get(index);

                    System.out.println(organizacionSelect);
                    
                    txtNombre.setText(organizacionSelect.getNombre());
                    txtDireccion.setText(organizacionSelect.getDireccion());
                    txtTelefono.setText(organizacionSelect.getTelefono());
                    txtEmail.setText(organizacionSelect.getEmail());
                    
                    btnCancelar.setDisable(false);
                    txtNombre.setDisable(true);
                }

            });

            tvOrganizacion.setContextMenu(cmOpciones);
        
        
        
    }  
    
    @FXML
    void btnGuardarOnAcion(ActionEvent event) {
        if (organizacionSelect == null){
            Organizacion organizacion = new Organizacion();
            organizacion.setNombre(txtNombre.getText());
            organizacion.setDireccion(txtDireccion.getText());
            organizacion.setTelefono(txtTelefono.getText());
            organizacion.setEmail(txtEmail.getText());
            organizacion.setAdmin("1");
            organizacion.setEstado("Activo");

            /*organizacion.setAdmin(cboAdmin.getSelectionModel().getSelectedItem());*/

            System.out.println(organizacion.toString());

            boolean rsp = this.organizacionDao.registrar(organizacion);

            if (rsp){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Exito");;
                alert.setHeaderText(null);
                alert.setContentText("El registro se guardó correctamente");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
                limpiarCampos();
                cargarOrganizaciones();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");;
                alert.setHeaderText(null);
                alert.setContentText("Hubieron errores al guardar el registro");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            }
        } else {
            
            organizacionSelect.setDireccion(txtDireccion.getText());
            organizacionSelect.setTelefono(txtTelefono.getText());
            organizacionSelect.setEmail(txtEmail.getText());
            organizacionSelect.setAdmin(cboAdmin1.getSelectionModel().getSelectedItem());
            organizacionSelect.setEstado(cboAdmin.getSelectionModel().getSelectedItem());
            
            boolean rsp = this.organizacionDao.editar(organizacionSelect);
            
            if (rsp){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Exito");;
                alert.setHeaderText(null);
                alert.setContentText("El registro se guardó correctamente");
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
                limpiarCampos();
                cargarOrganizaciones();
                
                organizacionSelect = null;
                
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
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        cboAdmin.getSelectionModel().select("Seleccione el Estado");
        cboAdmin1.getSelectionModel().select("Seleccione el Código de Admin");
    }
    
    public void cargarOrganizaciones(){
        tvOrganizacion.getItems().clear();
        tvOrganizacion.getColumns().clear();
        
        List<Organizacion> organizacion = this.organizacionDao.listar();
        
        ObservableList<Organizacion> data = FXCollections.observableArrayList(organizacion);
        
        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn nombreCol = new TableColumn("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory("nombre"));
        
        TableColumn direccionCol = new TableColumn("Direccion");
        direccionCol.setCellValueFactory(new PropertyValueFactory("direccion"));
        
        TableColumn telefonoCol = new TableColumn("Telefono");
        telefonoCol.setCellValueFactory(new PropertyValueFactory("telefono"));
        
        TableColumn emailCol = new TableColumn("Correo Electrónico");
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));
        
        TableColumn adminCol = new TableColumn("Administrador");
        adminCol.setCellValueFactory(new PropertyValueFactory("admin"));
        
        TableColumn estadoCol = new TableColumn("Estado");
        estadoCol.setCellValueFactory(new PropertyValueFactory("estado"));
        
        tvOrganizacion.setItems(data);
        tvOrganizacion.getColumns().addAll(idCol);
        tvOrganizacion.getColumns().addAll(nombreCol);
        tvOrganizacion.getColumns().addAll(direccionCol);
        tvOrganizacion.getColumns().addAll(telefonoCol);
        tvOrganizacion.getColumns().addAll(emailCol);
        tvOrganizacion.getColumns().addAll(adminCol);
        tvOrganizacion.getColumns().addAll(estadoCol);
    }
    
    @FXML
    void btnCancelarOnAction(ActionEvent event) {
        organizacionSelect = null;
        limpiarCampos();
        
        btnCancelar.setDisable(true);
        txtNombre.setDisable(false);
    }

    
}
