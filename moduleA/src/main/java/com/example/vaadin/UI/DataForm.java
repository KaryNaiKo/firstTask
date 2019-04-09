package com.example.vaadin.UI;

import com.example.hibernate.repository.DataRepository;
import com.example.hibernate.entity.Data;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class DataForm extends FormLayout {
    private TextField dataTxt1 = new TextField("Data1");
    private TextField dataTxt2 = new TextField("Data2");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private DataRepository dataRepository = DataRepository.getInstance();
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
        dataRepository.delete(data);
        Broadcaster.broadcast();
        view.closeAllWindow();
    }

    private void save() {
        binder.validate();
        if(binder.isValid()) {
            if (data.isPersisted()) {
                dataRepository.update(data);
            } else {
                dataRepository.save(data);
            }
            Broadcaster.broadcast();
            view.closeAllWindow();
        }
    }
}
