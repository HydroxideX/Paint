package eg.edu.alexu.csd.oop.draw;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import sun.security.provider.SHA;

import javax.swing.*;
import java.awt.*;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class newShapeDiaglogBox {
    newShapeDiaglogBox(Shape shape) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Resize");
        window.setMinWidth(250);
        window.setMinHeight(250);
        VBox vBox = new VBox();
        HBox position = new HBox();
        position.setSpacing(10);
        Label px = new Label();
        Label py = new Label();
        px.setText("Origin - X");
        py.setText("Origin - Y");
        px.setStyle("-fx-padding: 5 0 0 0;");
        py.setStyle("-fx-padding: 5 0 0 0;");
        px.setFont(new Font("Arial", 15));
        py.setFont(new Font("Arial", 15));
        TextField positionX = new TextField();
        TextField positionY = new TextField();
        positionX.setText(Integer.toString(shape.getPosition().x));
        positionY.setText(Integer.toString(shape.getPosition().y));
        position.getChildren().addAll(px,positionX,py,positionY);
        HBox shapeProperties = new HBox();
        HBox buttons = new HBox();
        Label[] labels = new Label[100];
        TextField[] texts = new TextField[100];
        int i  = 0;
        Map<String,Double> properties = new HashMap<String, Double>();
        Iterator var4 = shape.getProperties().entrySet().iterator();
        properties = shape.getProperties();
        while(var4.hasNext()) {
            Map.Entry s = (Map.Entry)var4.next();
            if(s.getKey().toString().contains("type")
                    || s.getKey().toString().contains("released")
                    || s.getKey().toString().contains("selected") ) continue;
            properties.put((String)s.getKey(), (Double) s.getValue());
            labels[i].setText((s.getKey().toString()));
            texts[i].setText((s.getValue().toString()));
            i++;
        }
        for(int j = 0;j<100;j++){
            if(labels[i] != null){
                shapeProperties.getChildren().add(labels[i]);
                shapeProperties.getChildren().add(texts[i]);
            }
        }
        vBox.getChildren().addAll(position,shapeProperties,buttons);
        HBox Buttons = new HBox();
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }
}

/*
    String s = new String();
    String regex = "\\d+";
    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(s);
    boolean correct = matcher.matches();
            if(!correct) return;
 */