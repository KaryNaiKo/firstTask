package com.example.vaadin.UI.forms;

import com.example.hibernate.repository.DataRepository;
import com.example.hibernate.entity.Data;
import com.example.vaadin.UI.Broadcaster;
import com.example.vaadin.UI.MainView;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class DataForm extends FormLayout {
    private TextField dataTxt1 = new TextField("Data1");
    private TextField dataTxt2 = new TextField("Data2");
    private Button save = new Button("Save");


    private DataRepository dataRepository = DataRepository.getInstance();
    private Data data;
    private MainView view;
    private Binder<Data> binder = new Binder<>(Data.class);


    public DataForm(MainView view) {
        setMargin(true);

        this.view = view;

        setSizeUndefined();

        addComponents(dataTxt1, dataTxt2, save);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.forField(dataTxt1)
                .withValidator(d -> d.length() > 0, "Must be not null")
                .bind(Data::getData1, Data::setData1);

        binder.forField(dataTxt2)
                .withValidator(d -> d.length() > 0, "Must be not null")
                .bind(Data::getData2, Data::setData2);

        save.addClickListener(e -> this.save());

    }

    public void setData(Data data) {
        this.data = data;
        binder.setBean(data);
        dataTxt1.selectAll();
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
