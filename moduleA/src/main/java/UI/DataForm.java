package UI;

import com.example.hibernate.dao.DataDAO;
import com.example.hibernate.entity.Data;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;

public class DataForm extends FormLayout {
    private TextField dataTxt1 = new TextField("Data1");
    private TextField dataTxt2 = new TextField("Data2");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private DataDAO dataDAO = DataDAO.getInstance();
    private Data data;
    private MainView view;
    private Binder<Data> binder = new Binder<>(Data.class);


    public DataForm(MainView view) {
        this.view = view;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        addComponents(dataTxt1, dataTxt2, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.forField(dataTxt1)
                .withValidator(d -> d.length() > 0, "Must be not null")
                .bind(Data::getData1, Data::setData1);

        binder.forField(dataTxt2)
                .withValidator(d -> d.length() > 0, "Must be not null")
                .bind(Data::getData2, Data::setData2);

        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
    }

    public void setData(Data data) {
        this.data = data;
        binder.setBean(data);
        delete.setVisible(data.isPersisted());
        dataTxt1.selectAll();
    }

    private void delete() {
        dataDAO.delete(data);
        view.updateList();
        closeWindow();
    }

    private void save() {
        if(binder.isValid()) {
            if (data.isPersisted()) {
                dataDAO.update(data);
            } else {
                dataDAO.save(data);
            }
            view.updateList();
            closeWindow();
        }
    }

    private void closeWindow() {
        ArrayList<Window> windows = new ArrayList<>(view.getUI().getWindows());
        windows.get(windows.size()-1).close();
    }
}
