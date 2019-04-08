package com.example.vaadin.UI;

import com.example.hibernate.entity.Data;
import com.vaadin.addon.excel.ExcelUploader;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class LoadFileForm extends VerticalLayout {
    private Grid<Data> grid = new Grid<Data>();

    public LoadFileForm() {
        setSizeUndefined();
        init();
    }

    public void init() {
        grid.addColumn(Data::getId).setCaption("Id");
        grid.addColumn(Data::getData1).setCaption("Data1");
        grid.addColumn(Data::getData2).setCaption("Data2");

        final ExcelUploader<Data> excelUploader = new ExcelUploader<>(Data.class);
        excelUploader.addSucceededListener((event, items) -> {
            if(items.size()>0) {
                grid.setItems(items);
            }
        });

        final Upload upload = new Upload();
        upload.setButtonCaption("Upload");
        upload.setReceiver(excelUploader);
        upload.addSucceededListener(excelUploader);

        this.addComponents(upload, grid);
        this.setExpandRatio(upload, 0.1f);
        this.setExpandRatio(grid, 0.9f);
    }
}
