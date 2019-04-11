package com.example.vaadin.model;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;

public class GridFile {
    private String name;
    private LocalDateTime date;
    private Button button;
    private File file;

    public GridFile() {
    }

    public GridFile(String name, LocalDateTime date, File file) {
        this.name = name;
        this.date = date;
        this.button = new Button();
        this.file = file;

        configButton();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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

        StreamResource myResource = createResource();
        FileDownloader fileDownloader = new FileDownloader(myResource);
        fileDownloader.extend(button);
    }

    private StreamResource createResource() {
        return new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                try {
                    return Files.newInputStream(file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, file.getName());
    }
}
