package com.samuelbernard147.myunittesting;

public class MainPresenter {
    private MainView view;

    MainPresenter(MainView view) {
        this.view = view;
    }

    public double volume(double length, double width, double height) {
        return length * width * height;
    }

    public void calculateVolume(double length, double width, double height) {
        double volume = volume(length, width, height);
        MainModel model = new MainModel(volume);
        view.showVolume(model);

    }
}