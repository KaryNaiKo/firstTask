package com.example.vaadin.UI.forms;

import com.example.vaadin.UI.MainView;
import com.example.vaadin.model.GridFile;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ComponentRenderer;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Tab2Form extends VerticalLayout {
    private MainView view;
    private Grid<GridFile> grid = new Grid<>();
    private List<GridFile> fileList = new ArrayList<>();
    private static String PATH_TO_FILES = "src/main/resources/files";

    public Tab2Form(MainView view) {
        this.view = view;

        setSizeFull();
        initFileList();
        initUI();
    }

    public void addFile(GridFile file){
        fileList.add(file);
    }

    public void updateGrid() {
        grid.setItems(fileList);
    }

    private void initFileList() {
        File folder = new File(PATH_TO_FILES);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    Path f = file.toPath();
                    BasicFileAttributes attr = Files.readAttributes(f, BasicFileAttributes.class);
                    FileTime time = attr.creationTime();
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String cTime = df.format(time.toMillis());
                    LocalDateTime date = LocalDateTime.parse(cTime, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

                    fileList.add(new GridFile(file.getName(), date, file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initUI() {
        Button load = new Button("Load file");
        load.addClickListener((Button.ClickListener) event -> view.addWindow("Load file", new LoadFileForm(view)));
        addComponent(load);
        setExpandRatio(load, 1);

        grid.setSizeFull();

        grid.addColumn(GridFile::getName).setCaption("Name");
        grid.addColumn(GridFile::getDate, new LocalDateTimeRenderer()).setCaption("Date");
        grid.addColumn(GridFile::getButton, new ComponentRenderer()).setCaption("Download");

        grid.setItems(fileList);

        addComponent(grid);
        setExpandRatio(grid, 9);
    }

}
