package UI;

import com.example.hibernate.entity.Data;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.example.hibernate.repository.DataRepository;


import java.util.List;

public class MainView extends VerticalLayout implements View {
    private DataRepository dataRepository = DataRepository.getInstance();
    private Grid<Data> grid = new Grid<>();

    public MainView() {
        setSizeFull();
        setSpacing(false);

        initTop();
        initMain();
    }

    private void initTop() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");

        Button addCustomerBtn = new Button("Add new Data");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            DataForm form = new DataForm(this);
            form.setData(new Data());
            addWindow("Add new data", form);
        });
        layout.addComponent(addCustomerBtn);
        layout.setComponentAlignment(addCustomerBtn, Alignment.MIDDLE_LEFT);

        Button send = new Button("Logout");
        send.addClickListener((Button.ClickListener) event -> {
            SecurityUtil.getCurrentUser().logout();
            this.getUI().getNavigator().navigateTo(FirstTask.LOGIN);
            Page.getCurrent().reload();
        });

        layout.addComponent(send);
        layout.setComponentAlignment(send, Alignment.MIDDLE_RIGHT);

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
        List<Data> customers = dataRepository.getData();
        grid.setItems(customers);
    }

    private void addWindow(String s, Component form) {
        closeAllWindow();

        Window editWindow = new Window(s);
        editWindow.setContent(form);
        editWindow.center();
        this.getUI().addWindow(editWindow);
    }

    private void closeAllWindow() {
        for (Window window : getUI().getWindows()) getUI().removeWindow(window);
    }
}