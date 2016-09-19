package bhs.javafx.validation;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

public class FXMLController implements Initializable {

    @FXML
    private TextField nameTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField emailTxt;

    private BeanValidationSupport validationSupport = new BeanValidationSupport();
    private final Person person = new Person();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validationSupport.setValidationDecorator(new GraphicValidationDecoration());
        person.nameProperty().bindBidirectional(nameTxt.textProperty());
        validationSupport.registerValidator(nameTxt, person, "name");
        person.addressProperty().bindBidirectional(addressTxt.textProperty());
        validationSupport.registerValidator(addressTxt, person, "address");
        person.emailProperty().bindBidirectional(emailTxt.textProperty());
        validationSupport.registerValidator(emailTxt, person, "email");
    }
}
