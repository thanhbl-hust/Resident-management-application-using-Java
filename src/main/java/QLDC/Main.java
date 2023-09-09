package QLDC;

import Controller.MainScreenController;

public class Main {
    public static void main(String[] args) {
        MainScreenController mainScreenController = new MainScreenController();
        mainScreenController.on(args);
    }
}
