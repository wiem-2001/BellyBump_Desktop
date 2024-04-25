package tn.esprit.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CartView {
private Parent  view;
    public CartView()throws MalformedURLException
    {
        File uiFile = new File("cart.fxml");
        URL url = uiFile.toURI().toURL();
        try {
            Parent root =FXMLLoader.load(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public Parent getView()
    {return this.view;}
}
