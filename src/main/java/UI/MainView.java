package UI;

import com.vaadin.navigator.View;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;
import model.Data;

import java.util.List;

public class MainView extends VerticalLayout implements View {
    private  FirstTask main;

    public MainView(FirstTask main) {
        this.main = main;
        init();
    }

    private void init() {
        setSizeFull();

        List<Data> dataList = main.getDb().getData();

        Grid<Data> grid = new Grid<>();
        grid.setItems(dataList);
        grid.addColumn(Data::getId).setCaption("Id");
        grid.addColumn(Data::getData1).setCaption("Data1");
        grid.addColumn(Data::getData2).setCaption("Data2");

        grid.setHeight("100%");
        grid.setWidth("100%");

        grid.addItemClickListener((ItemClickListener<Data>) event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                Notification.show("Value: " + event.getItem());
            }
        });

        addComponent(grid);
    }
}
