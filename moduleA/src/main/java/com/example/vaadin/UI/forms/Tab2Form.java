package com.example.vaadin.UI.forms;

import com.example.vaadin.UI.MainView;
import com.example.vaadin.model.FileManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ComponentRenderer;

import java.time.LocalDate;

public class Tab2Form extends VerticalLayout {
    private final MainView view;
    private Grid<FileManager> grid = new Grid<>();

    public Tab2Form(MainView view) {
        this.view = view;

        setSizeFull();
        init();
    }

    private void init() {
        Button load = new Button("Load file");
        load.addClickListener((Button.ClickListener) event -> view.addWindow("Load file", new LoadFileForm(view)));
        addComponent(load);
        setExpandRatio(load, 1);

        FileManager file1 = new FileManager("1", LocalDate.now(), new Button());
        FileManager file2 = new FileManager("2", LocalDate.now(), new Button());

        grid.setSizeFull();

        grid.addColumn(FileManager::getName).setCaption("Name");
        grid.addColumn(FileManager::getDate).setCaption("Date");
        grid.addColumn(FileManager::getButton, new ComponentRenderer()).setCaption("Download");

        grid.setItems(file1, file2);

        addComponent(grid);
        setExpandRatio(grid, 9);
    }

}
