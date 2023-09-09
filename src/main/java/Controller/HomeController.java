package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;




/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class HomeController implements Initializable {
    
    @FXML
    Button qLNhanKhauBtn;

    @FXML
    Button qLHoKhauBtn;

    @FXML
    Button qLTamTruBtn;

    @FXML
    Button qLTamVangBtn;

    @FXML
    Button qLPAKNBtn;

    @FXML
    Pane pane;

    @FXML
    Button logOut;
    
    private Parent currentRoot;
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NhanKhau.fxml"));
            Region root = (Region)loader.load();

            currentRoot = root;
            pane.getChildren().add(root);
            root.prefHeightProperty().bind(pane.heightProperty());
            root.prefWidthProperty().bind(pane.widthProperty());
            

            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void nhanKhauChangeScene(ActionEvent event) {
        pane.getChildren().remove(currentRoot);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NhanKhau.fxml"));
            Region root = (Region)loader.load();
            currentRoot = root;
            pane.getChildren().add(root);
            root.prefHeightProperty().bind(pane.heightProperty());
            root.prefWidthProperty().bind(pane.widthProperty());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void hoKhauChangeScene(ActionEvent event) {
        pane.getChildren().remove(currentRoot);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("HoKhau.fxml"));
            Region root = (Region)loader.load();
            currentRoot = root;
            pane.getChildren().add(root);
            root.prefHeightProperty().bind(pane.heightProperty());
            root.prefWidthProperty().bind(pane.widthProperty());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void tamTruChangeScene(ActionEvent event) {
        pane.getChildren().remove(currentRoot);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("TamTru.fxml"));
            Region root = (Region)loader.load();
            currentRoot = root;
            pane.getChildren().add(root);
            root.prefHeightProperty().bind(pane.heightProperty());
            root.prefWidthProperty().bind(pane.widthProperty());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void tamVangChangeScene(ActionEvent event) {
        pane.getChildren().remove(currentRoot);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("TamVang.fxml"));
            Region root = (Region)loader.load();
            currentRoot = root;
            pane.getChildren().add(root);
            root.prefHeightProperty().bind(pane.heightProperty());
            root.prefWidthProperty().bind(pane.widthProperty());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void phanAnhKienNghiChangeScene(ActionEvent event) {
        pane.getChildren().remove(currentRoot);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PhanAnhKienNghi.fxml"));
            Region root = (Region)loader.load();
            currentRoot = root;
            pane.getChildren().add(root);
            root.prefHeightProperty().bind(pane.heightProperty());
            root.prefWidthProperty().bind(pane.widthProperty());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML 
    protected void logOut(ActionEvent e) {
        try {
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
            // StudentDetailController controller = loader.getController();
            // Student selected = table.getSelectionModel().getSelectedItem();
            // controller.setStudent(selected);
            stage.setScene(scene);

        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }
}
