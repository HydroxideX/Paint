package eg.edu.alexu.csd.oop.draw;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Scene;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ShapeDialogBox {
    ShapeDialogBox(Shape shape, Engine engine, Graphics graphics) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Resize");
        window.setMinWidth(280);
        window.setMinHeight(100);
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

        Point p = shape.getPosition();
        if(p!=null)
        {
            positionX.setText(Integer.toString(shape.getPosition().x));
            positionY.setText(Integer.toString(shape.getPosition().y));
        }
        else
        {
            positionX.setText("0");
            positionY.setText("0");
        }


        position.getChildren().addAll(px,positionX,py,positionY);
        HBox shapeProperties = new HBox();
        shapeProperties.setSpacing(15);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(30);
        Button confirm = new Button("Confirm");
        Button cancel = new Button("Cancel");
        buttons.getChildren().addAll(confirm,cancel);
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        ColorPicker colorPicker2 = new ColorPicker(Color.WHITE);
        Label l1 = new Label("Color");
        Label l2 = new Label("Fill Color");
        position.getChildren().addAll(l1,colorPicker,l2,colorPicker2);
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

        Label[] labels = new Label[100];
        TextField[] texts = new TextField[100];
        int i  = 0;
        Map<String,Double> properties;
        Iterator var4 = shape.getProperties().entrySet().iterator();
        properties = shape.getProperties();
        while(var4.hasNext()) {
            Map.Entry s = (Map.Entry)var4.next();
            if(s.getKey().toString().contains("type")
                    || s.getKey().toString().contains("released")
                    || s.getKey().toString().contains("selected") ) continue;
            properties.put((String)s.getKey(), (Double) s.getValue());
            labels[i] = new Label(s.getKey().toString());
            labels[i].setFont(new Font("Arial", 15));
            texts[i] = new TextField(s.getValue().toString());
            i++;
        }
        for(int j = 0;j<i;j++){
                shapeProperties.getChildren().add(labels[j]);
                shapeProperties.getChildren().add(texts[j]);
        }

        confirm.setOnAction(e-> {
            Shape l;
            try {
                l = (Shape) shape.clone();
                int value;
                String s;
                Point p1 = new Point();
                l.setFillColor(getColor((colorPicker2.getValue())));
                l.setColor(getColor(colorPicker.getValue()));
                s = positionX.getText();
                Matcher matcher = pattern.matcher(s);
                boolean correct = matcher.matches();
                if(!correct) {
                    l = null;
                    return;
                }
                p1.x = Integer.parseInt(s);
                value = Integer.parseInt(s);
                if(value > 1000 || value < 0) {
                    l = null;
                    return;
                }
                s = positionY.getText();
                matcher = pattern.matcher(s);
                correct = matcher.matches();
                if(!correct){
                    l = null;
                    return;
                }
                p1.y = Integer.parseInt(s);
                value = Integer.parseInt(s);
                if(value > 600 || value < 0){
                    l = null;
                    return;
                }
                l.setPosition(p1);
                Map <String,Double> m = new HashMap<>();
                for(int j = 0;j<100;j++){
                    if(labels[j] != null){
                        s = texts[j].getText();
                        double y=Double.parseDouble(s);
                        int x=(int)y;
                        s=String.valueOf(x);
                        matcher = pattern.matcher(s);
                        correct = matcher.matches();
                        if(!correct){
                            l = null;
                            return;
                        }
                        value = Integer.parseInt(s);
                        if(value > 3000) {
                            l = null;
                            return;
                        }
                        m.put(labels[j].getText(),Double.valueOf(texts[j].getText()));
                    }
                }
                m.putIfAbsent("type",0d);
                if(m.get("released") != null) m.put("released",1d);
                l.setProperties(m);
                engine.removeShape(shape);
                engine.addShape(l);
                engine.refresh(graphics);
                window.close();
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }

        });
        cancel.setOnAction(e-> window.close());
        vBox.setSpacing(20);
        vBox.getChildren().addAll(position,shapeProperties,buttons);
        Scene scene = new Scene(vBox);
        window.setResizable(false);
        window.setScene(scene);
        window.showAndWait();
    }

    private java.awt.Color getColor(Color v){
        float r = (float)v.getRed();
        float b = (float)v.getBlue();
        float g = (float)v.getGreen();
        float o = (float)v.getOpacity();
        return new java.awt.Color(r,g,b,o);
    }
}