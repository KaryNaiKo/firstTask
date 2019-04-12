package com.example.vaadin.UI.forms;

import com.example.hibernate.entity.Data;
import com.example.hibernate.repository.DataRepository;
import com.example.vaadin.UI.MainView;
import com.example.vaadin.model.GridData;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.ComponentRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;

public class Tab1Form extends VerticalLayout {
    private final MainView view;
    private DataRepository dataRepository = DataRepository.getInstance();
    private Grid<GridData> grid = new Grid<>();
    private TextField filterText = new TextField();

    public Tab1Form(MainView view) {
        this.view = view;

        setSizeFull();

        initTop();
        initMain();
    }


    private void initTop() {
        HorizontalLayout layout = new HorizontalLayout();

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

        Button addCustomerBtn = new Button("Add new Data");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            DataForm form = new DataForm(view);
            form.setData(new Data());
            view.addWindow("Add new data", form);
        });
        layout.addComponent(addCustomerBtn);

        addComponent(layout);
        setExpandRatio(layout, 0.1f);
    }


    private void initMain() {
        grid.setSizeFull();

        grid.addColumn(gridData -> gridData.getData().getId()).setCaption("Id");
        grid.addColumn(gridData -> gridData.getData().getData1()).setCaption("Data1");
        grid.addColumn(gridData -> gridData.getData().getData2()).setCaption("Data2");
        grid.addColumn(gridData -> gridData.getButton(), new ComponentRenderer()).setCaption("Delete");

        grid.addItemClickListener((ItemClickListener<GridData>) event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                DataForm form = new DataForm(view);
                form.setData(event.getItem().getData());
                view.addWindow("Edit data", form);
            }
        });

        addComponent(grid);
        setExpandRatio(grid, 0.9f);
        updateList();
    }

    public void updateList() {
        List<Data> dataList = dataRepository.getDataWithCriteria(filterText.getValue());
        List<GridData> gridDataList = getGridDataList(dataList);
        grid.setItems(gridDataList);
    }

    private List<GridData> getGridDataList(List<Data> dataList) {
        List<GridData> list = new ArrayList<>();
        for(Data data : dataList) {
            list.add(new GridData(data, this));
        }
        return list;
    }
}
