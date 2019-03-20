package UI;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import model.Data;
import repository.JDBCUtil;

import java.util.List;

public class MainView extends VerticalLayout implements View {
    private JDBCUtil db = JDBCUtil.getInstance();

    public MainView() {
        init();
    }

    private void init() {
        setSizeFull();
        setSpacing(false);

        Button send = new Button("Logout");
        send.addClickListener((Button.ClickListener) event -> {
            SecurityUtil.getCurrentUser().logout();
            this.getUI().getNavigator().navigateTo(FirstTask.LOGIN);
            Page.getCurrent().reload();
        });

        addComponent(send);
        setComponentAlignment(send, Alignment.TOP_RIGHT);
        setExpandRatio(send, 0.1f);

        List<Data> dataList = db.getData();

        Grid<Data> grid = new Grid<>();
        grid.setItems(dataList);
        grid.addColumn(Data::getId).setCaption("Id");
        grid.addColumn(Data::getData1).setCaption("Data1");
        grid.addColumn(Data::getData2).setCaption("Data2");

        grid.setHeight("100%");
        grid.setWidth("100%");

        grid.addItemClickListener((ItemClickListener<Data>) event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                // Create a sub-window and set the content
                Window subWindow = new Window("Sub-window");
                VerticalLayout subContent = new VerticalLayout();
                subWindow.setContent(subContent);

                // Put some components in it
                subContent.addComponent(new Label("Value: " + event.getItem()));

                // Center it in the browser window
                subWindow.center();

                // Open it in the UI
                this.getUI().addWindow(subWindow);
            }
        });

        addComponent(grid);
        setExpandRatio(grid, 0.9f);
    }
}
