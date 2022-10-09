package com.uit.controller;

import com.uit.common.StageCommon;
import com.uit.entity.Department;
import com.uit.entity.Employee;
import com.uit.repository.DepartmentRepository;
import com.uit.repository.EmployeeRepository;
import com.uit.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;


public class DepartmentController implements Initializable {

    private static Stage inputStage;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Label alertMessage;

    @FXML
    private Label createdDateDepartment;

    @FXML
    private DatePicker departmentCreatedAt;

    @FXML
    private TextField departmentId;

//    @FXML
//    private TextField departmentLeader;

    @FXML
    private ComboBox<String> departmentLeader;

    @FXML
    private TextField departmentName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        visiableBtn(true, false, false);
        setEditable(true);
        setValueDepartmentLeader();
    }

    public static FXMLLoader renderStage() {
        inputStage = new Stage();
        return StageCommon.displayStage(inputStage, "inputDepartment.fxml", "Thông Tin Phòng Ban", 650, 400);
    }

    public void setEditable(boolean isEditable) {
        departmentId.setEditable(isEditable);
        departmentLeader.setEditable(isEditable);
        departmentName.setEditable(isEditable);
        departmentCreatedAt.setEditable(isEditable);
    }

    public void setData(Department department) {
        departmentId.setText(department.getDepartMentId());
        departmentName.setText(department.getDepartmentName());
        departmentLeader.setValue(department.getDepartmentLeader());
        departmentCreatedAt.setValue(department.getDepartmentCreatedDate());

        final LocalDate now = LocalDate.now();
        final long diffDate = department.getDepartmentCreatedDate().until(now, ChronoUnit.DAYS);
        createdDateDepartment.setTextFill(Paint.valueOf("GREEN"));
        createdDateDepartment.setFont(Font.font("System", FontWeight.BOLD, 18));
        createdDateDepartment.setText(diffDate + "");
    }

    private void setValueDepartmentLeader() {
        final List<Employee> employees = EmployeeRepository.getEmployees();
        for (Employee employee : employees) {
            departmentLeader.getItems().add(employee.getFullName());
        }
    }

    public void enableBtnAdd(boolean enableSubmit, boolean enableEdit, boolean enableDelete) {
        visiableBtn(enableSubmit, enableEdit, enableDelete);
    }

    private void visiableBtn(boolean submit, boolean edit, boolean delete) {
        btnSubmit.setVisible(submit);
        btnEdit.setVisible(edit);
        btnDelete.setVisible(delete);
    }

    public static void closedStage() {
        StageCommon.hiddenStage(inputStage);
    }

    @FXML
    protected void onEnableEditData(ActionEvent event) {
        setEditable(true);
        visiableBtn(true, false, false);
    }


    @FXML
    protected void onSubmitData() {
        if (!validateInputData()) {
            System.out.println("Invalid data");
            return;
        }
        final Department department = getDataForm();
        DepartmentRepository.saveDepartment(department);
        closedStage();
    }

    @FXML
    void onDeleteData(ActionEvent event) {
        if (!validateInputData()) {
            System.out.println("Invalid data");
            return;
        }
        final String id = departmentId.getText();
        DepartmentRepository.deleteDepartmentById(id);
        closedStage();
    }

    private Department getDataForm() {
        final Department department = new Department();
        department.setDepartMentId(departmentId.getText());
        department.setDepartmentName(departmentName.getText());
        department.setDepartmentCreatedDate(departmentCreatedAt.getValue());
        department.setDepartmentLeader(departmentLeader.getValue());
        return department;
    }

    private boolean validateInputData() {
        if (departmentId.getText().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Mã Phòng ban không được rỗng", Paint.valueOf("RED"));
            return false;
        }
        if (departmentName.getText().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Tên Phòng ban không được rỗng", Paint.valueOf("RED"));
            return false;
        }
//        if (departmentLeader.getValue().equals("")) {
//            Utils.renderFailureMessage(alertMessage, "Trưởng Phòng không được rỗng", Paint.valueOf("RED"));
//            return false;
//        }
        if (departmentCreatedAt.getValue().toString().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Ngày Thành lập phòng không được rỗng", Paint.valueOf("RED"));
            return false;
        }
        return true;
    }
}
