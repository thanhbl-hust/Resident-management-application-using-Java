package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import Models.UserMoldel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 */
public class LoginController implements Initializable {
    @FXML
    TextField userField;

    @FXML
    PasswordField passwordField;

    @FXML
    private Button loginButton;

    boolean ck = true;

    UserMoldel userMoldel1 = new UserMoldel(1, "1", "1");

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loginButton.setDisable(true);

        userField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());

        });
    }

    @FXML
    protected void Submit(ActionEvent event) {

        if (checkValidAccount(userField.getText(), passwordField.getText())) {
            System.out.println("true");
            try {
                changeSceneHome(event);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            String message = "Tên Đăng Nhập hoặc Mật Khẩu của bạn không chính xác";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(message);
            alert.setContentText("Vui lòng kiểm tra và thử lại");
            alert.show();
        }
    }

    private boolean checkValidAccount(String user, String password) {
        try {
            Connection conn = SQLController.getConnection(SQLController.DB_URL, SQLController.USER_NAME,
                    SQLController.PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TaiKhoan, MatKhau FROM dbo.TaiKhoan");
            if (rs == null) {
                conn.close();
                return false;
            }
            while (rs.next()) {
                if (rs.getString(1).equals(user) && rs.getString(2).equals(password)) {
                    conn.close();
                    return true;
                }
            }
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return false;
    }

    private void changeSceneHome(ActionEvent e) throws Exception {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomeScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        // StudentDetailController controller = loader.getController();
        // Student selected = table.getSelectionModel().getSelectedItem();
        // controller.setStudent(selected);
        stage.setScene(scene);

    }

}
