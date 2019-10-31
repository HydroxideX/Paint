package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;

import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class dialogBox {
    Shape r;
    dialogBox (Shape shape) {
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
        vBox.getChildren().addAll(position);
        HBox shapeProperties = new HBox();
        HBox Buttons = new HBox();
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
    }
    void returnShape(Shape l) throws CloneNotSupportedException {
        l = (Shape) r.clone();
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