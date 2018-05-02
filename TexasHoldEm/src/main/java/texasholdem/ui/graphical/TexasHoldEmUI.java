/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texasholdem.ui.graphical;

import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import texasholdem.domain.Game;

/**
 *
 * @author josujosu
 */
public class TexasHoldEmUI extends Application{
    
    
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Texas Hold'Em");
        
        GridPane startMenu = new GridPane();
        GridPane manageMainMenu = new GridPane();
        GridPane addUserMenu = new GridPane();
        
        

        //StartMenu
        Button goToManageUsers = new Button("Manage users");
        Button playGameButton = new Button("Play");
        Button statsButton = new Button("Check Player Stats");
        
        startMenu.setAlignment(Pos.CENTER);
        startMenu.setHgap(10);
        startMenu.setVgap(20);
        startMenu.setPadding(new Insets(25, 25, 25, 25));
        
        startMenu.add(goToManageUsers, 0, 0);
        startMenu.add(playGameButton, 0, 1);
        startMenu.add(statsButton, 0, 2);
        
        
        //ManageUsersMainMenu
        Button goToMainMenu = new Button("Main menu");
        Button goToAddUser = new Button("Add user");       
        
        manageMainMenu.setAlignment(Pos.CENTER);
        manageMainMenu.setHgap(10);
        manageMainMenu.setVgap(20);
        manageMainMenu.setPadding(new Insets(25, 25, 25, 25));
        
        manageMainMenu.add(goToAddUser, 0, 0);
        manageMainMenu.add(goToMainMenu, 0, 1);
        
        
        //AddUserMenu
        HBox addUserHbox = new HBox();
        Label addUserLabel = new Label("Username: ");
        TextField addUsertf = new TextField();        
        addUserHbox.getChildren().addAll(addUserLabel, addUsertf);
        addUserHbox.setSpacing(10);
        
        addUserMenu.setAlignment(Pos.CENTER);
        addUserMenu.setHgap(10);
        addUserMenu.setVgap(20);
        addUserMenu.setPadding(new Insets(25, 25, 25, 25));
        
        addUserMenu.add(addUserHbox, 0, 0);
        
        
        
        //Scenes
        Scene startScene = new Scene(startMenu, 300, 250);
        Scene manageMainScene = new Scene(manageMainMenu, 300, 250);
        Scene addUserScene = new Scene(addUserMenu, 300, 250);
        
        
        // Actions
        // Universal
        goToManageUsers.setOnAction((event) -> {
            primaryStage.setScene(manageMainScene);
        });
        goToMainMenu.setOnAction((event) -> {
            primaryStage.setScene(startScene);
        });
        // Main Menu
        // Manage Users Main Menu
        goToAddUser.setOnAction((event) -> {
            primaryStage.setScene(addUserScene);
        });
        
        primaryStage.setScene(startScene);
        primaryStage.show();
    }
    

    
}
