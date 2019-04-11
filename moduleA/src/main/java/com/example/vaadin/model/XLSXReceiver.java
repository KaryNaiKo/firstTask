package com.example.vaadin.model;


import com.example.hibernate.entity.Data;
import com.example.vaadin.UI.forms.LoadFileForm;
import com.example.vaadin.UI.forms.Tab1Form;
import com.example.vaadin.UI.forms.Tab2Form;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XLSXReceiver implements Upload.Receiver, Upload.FailedListener, Upload.SucceededListener {
    private File file;
    private LoadFileForm form;

    public XLSXReceiver(LoadFileForm form) {
        this.form = form;
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        if (!filename.endsWith(".xlsx")) {
            new Notification("Not allowed file extension ", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
            return null;
        }

        BufferedOutputStream outputStream = null;
        try {
            file = new File(Tab2Form.PATH_TO_FILES + "/TEMP" + filename);
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        List<Data> list = readFile();
        form.insertDataToGrid(list);
        form.setButtonVisible();
    }

    @Override
    public void uploadFailed(Upload.FailedEvent event) {
        file.delete();
    }

    public void deleteFile() {
        file.delete();
    }

    public File getFile() {
        return file;
    }


    private List<Data> readFile() {
        List<Data> list = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    checkHeader(row);
                } else {
                    Data data = getData(row);
                    list.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Data getData(Row row) throws IOException {
        int id;
        String data1;
        String data2;
        try {
            id = (int) row.getCell(0).getNumericCellValue();
            data1 = row.getCell(1).getStringCellValue();
            data2 = row.getCell(2).getStringCellValue();
        } catch (Exception e) {
            new Notification("Could not read file. Corrupt data at line " + row.getRowNum(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
            throw new IOException();
        }

        return new Data(id, data1, data2);
    }

    private void checkHeader(Row row) throws IOException {
        String id = row.getCell(0).getStringCellValue();
        String data1 = row.getCell(1).getStringCellValue();
        String data2 = row.getCell(2).getStringCellValue();
        if (!id.toLowerCase().equals("id") || !data1.toLowerCase().equals("data1") || !data2.toLowerCase().equals("data2")) {
            new Notification("Could not read file. Corrupt header.", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
            throw new IOException();
        }
    }
}