/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author xXSorzXx
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws MalformedURLException, IOException {
       mostrarPantalla("src/reservas/vistas/FXMLOrganizacion.fxml");
    }
    
      @FXML
    void btnUsuariosAction(ActionEvent event) throws MalformedURLException, IOException {
          mostrarPantalla("src/reservas/vistas/FXMLUsuario.fxml");
    }
    
    @FXML
    void btnRecursosAction(ActionEvent event) throws MalformedURLException, IOException{
          mostrarPantalla("src/reservas/vistas/FXMLRecursos.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void mostrarPantalla(String SUrl) throws MalformedURLException, IOException{
         Stage stage = new Stage();
        
        URI uri = Paths.get(SUrl).toAbsolutePath().toUri();
        System.out.println("URI: " + uri.toString());
                
        Parent root = FXMLLoader.load(uri.toURL());
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    
 
    
}
