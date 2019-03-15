import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;

import javax.servlet.annotation.WebServlet;

import java.util.List;

public class FirstTask extends UI {
    private JDBCUtil db = new JDBCUtil();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setHeight("100%");
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
                Notification.show("Value: " + event.getItem());
            }
        });

        layout.addComponents(grid);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    public static class MyUIServlet extends VaadinServlet {
    }
}
