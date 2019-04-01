package com.example.vaadin.UI;

import com.example.hibernate.entity.Data;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.example.hibernate.repository.DataRepository;
import com.vaadin.ui.themes.ValoTheme;


import java.util.List;

public class MainView extends VerticalLayout implements View {
    private DataRepository dataRepository = DataRepository.getInstance();
    private Grid<Data> grid = new Grid<>();
    private TextField filterText = new TextField();

    public MainView() {
        setSizeFull();
        setSpacing(false);

        initTop();
        initMain();
    }

    private void initTop() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");

        filterText.setPlaceholder("filter by name...");
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> filterText.clear());

        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        layout.addComponent(filtering);
        layout.setExpandRatio(filtering, 1);

        Button addCustomerBtn = new Button("Add new Data");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            DataForm form = new DataForm(this);
            form.setData(new Data());
            addWindow("Add new data", form);
        });
        layout.addComponent(addCustomerBtn);
        layout.setExpandRatio(addCustomerBtn, 1);

        Button send = new Button("Logout");
        send.addClickListener((Button.ClickListener) event -> {
            SecurityUtil.getCurrentUser().logout();
            this.getUI().getNavigator().navigateTo(FirstTask.LOGIN);
            Page.getCurrent().reload();
        });

        layout.addComponent(send);
        layout.setComponentAlignment(send, Alignment.MIDDLE_RIGHT);
        layout.setExpandRatio(send, 5);


        addComponent(layout);
        setExpandRatio(layout, 0.1f);
    }

    private void initMain() {
        grid.addColumn(Data::getId).setCaption("Id");
        grid.addColumn(Data::getData1).setCaption("Data1");
        grid.addColumn(Data::getData2).setCaption("Data2");

        grid.setHeight("100%");
        grid.setWidth("100%");

        grid.addItemClickListener((ItemClickListener<Data>) event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                DataForm form = new DataForm(this);
                form.setData(event.getItem());
                addWindow("Edit data", form);
            }
        });

        addComponent(grid);
        setExpandRatio(grid, 0.9f);
        updateList();
    }

    public void updateList() {
        List<Data> customers = dataRepository.getData(filterText.getValue());
        grid.setItems(customers);
    }

    private void addWindow(String s, Component form) {
        closeAllWindow();

        Window editWindow = new Window(s);
        editWindow.setContent(form);
        editWindow.center();
        this.getUI().addWindow(editWindow);
    }

    public void closeAllWindow() {
        for (Window window : getUI().getWindows()) getUI().removeWindow(window);
    }
}