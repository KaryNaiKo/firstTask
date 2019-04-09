package com.example.vaadin.UI;

import com.example.hibernate.entity.Data;
import com.example.vaadin.model.XLSXReceiver;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class LoadFileForm extends VerticalLayout {
    private Grid<Data> grid = new Grid<>();

    public LoadFileForm() {
        setSizeUndefined();
        init();
    }

    public void init() {
        grid.addColumn(Data::getId).setCaption("Id");
        grid.addColumn(Data::getData1).setCaption("Data1");
        grid.addColumn(Data::getData2).setCaption("Data2");


        XLSXReceiver receiver = new XLSXReceiver(this);
        Upload upload = new Upload();
        upload.setButtonCaption("Upload");
        upload.setReceiver(receiver);
        upload.addSucceededListener(receiver);
        upload.addFailedListener(receiver);

        this.addComponents(upload, grid);
        this.setExpandRatio(upload, 0.1f);
        this.setExpandRatio(grid, 0.9f);
    }

    public void insertDataToGrid(List list) {
        grid.setItems(list);
    }
}
