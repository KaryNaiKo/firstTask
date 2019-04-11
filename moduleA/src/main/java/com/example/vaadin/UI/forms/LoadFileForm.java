package com.example.vaadin.UI.forms;

import com.example.hibernate.entity.Data;
import com.example.vaadin.UI.MainView;
import com.example.vaadin.model.XLSXReceiver;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.List;

public class LoadFileForm extends VerticalLayout {
    private final MainView view;
    private Grid<Data> grid = new Grid<>();
    private Button saveButton = new Button();

    public LoadFileForm(MainView view) {
        this.view = view;
        setSizeUndefined();
        init();
    }

    public void init() {
        XLSXReceiver receiver = new XLSXReceiver(this);
        Upload upload = new Upload();
        upload.setButtonCaption("Upload");
        upload.setReceiver(receiver);
        upload.addSucceededListener(receiver);
        upload.addFailedListener(receiver);

        grid.addColumn(Data::getId).setCaption("Id");
        grid.addColumn(Data::getData1).setCaption("Data1");
        grid.addColumn(Data::getData2).setCaption("Data2");

        saveButton.setCaption("Save file");
        saveButton.setVisible(false);
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                new Notification("File has been saved", Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
                view.closeAllWindow();
            }
        });

        this.addComponents(upload, grid, saveButton);
        this.setExpandRatio(upload, 0.05f);
        this.setExpandRatio(grid, 0.9f);
        this.setExpandRatio(saveButton, 0.05f);
    }

    public void insertDataToGrid(List list) {
        grid.setItems(list);
    }

    public void setButtonVisible(){
        saveButton.setVisible(true);
    }
}
