package com.example.vaadin.model;

import com.example.hibernate.entity.Data;
import com.example.hibernate.repository.DataRepository;
import com.example.vaadin.UI.forms.Tab1Form;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;


public class GridData {
    private Data data;
    private Button button;
    private DataRepository dataRepository = DataRepository.getInstance();
    private Tab1Form form;

    public GridData(Data data, Tab1Form form) {
        this.data = data;
        this.form = form;
        button = new Button();
        button.setIcon(VaadinIcons.TRASH);
        button.setSizeFull();
        button.setCaption("Delete");
        button.setStyleName("delete");

        button.addClickListener(e -> {
            dataRepository.delete(data);
            form.updateList();
        });
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
