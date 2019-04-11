package com.example.vaadin.model;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;

public class FileManager {
    private String name;
    private LocalDate date;
    private Button button;

    public FileManager() {
    }

    public FileManager(String name, LocalDate date, Button button) {
        this.name = name;
        this.date = date;
        this.button = button;

        configButton();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    private void configButton() {
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                new Notification(name + " " + date, Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
            }
        });

        button.setCaption("Click here to download");
        button.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        button.setSizeFull();
    }
}
