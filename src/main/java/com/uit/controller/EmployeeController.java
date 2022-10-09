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

public class EmployeeController implements Initializable {

    @FXML
    private TextField maNhanVien;

    @FXML
    private TextField hoNhanVien;

    @FXML
    private TextField identifier;

    @FXML
    private TextField tenNhanVien;

    @FXML
    private DatePicker ngaySinh;

    @FXML
    private TextField quocTich;

    @FXML
    private TextField diaChi;

    @FXML
    private TextField soDienThoai;

    @FXML
    private ComboBox<String> phongBan;

    @FXML
    private TextField vitri;

    @FXML
    private Label alertMessage;

    private static Stage inputStage;

    @FXML
    private DatePicker ngayVaoLam;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Label onboardDateEmployee;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        visiableBtn(true, false, false);
        setEditable(true);
        setValueDepartment();
    }

    public void setEditable(boolean isEditable) {
        maNhanVien.setEditable(isEditable);
        hoNhanVien.setEditable(isEditable);
        tenNhanVien.setEditable(isEditable);
        identifier.setEditable(isEditable);
        ngaySinh.setEditable(isEditable);
        quocTich.setEditable(isEditable);
        diaChi.setEditable(isEditable);
        soDienThoai.setEditable(isEditable);
        phongBan.setEditable(isEditable);
        vitri.setEditable(isEditable);
        ngayVaoLam.setEditable(isEditable);
    }

    private Employee getDataForm() {
        final Employee employee = new Employee();
        employee.setMaNhanvien(maNhanVien.getText());
        employee.setHoNhanVien(hoNhanVien.getText());
        employee.setTenNhanVien(tenNhanVien.getText());
        employee.setIdentifier(identifier.getText());
        employee.setNgayVaoLam(ngayVaoLam.getValue());
        employee.setQuocTich(quocTich.getText());
        employee.setNgaySinh(ngaySinh.getValue());
        employee.setDiaChi(diaChi.getText());
        employee.setSoDT(soDienThoai.getText());
        employee.setPhongBan(phongBan.getValue());
        employee.setVitri(vitri.getText());
        return employee;
    }

    public void setData(Employee employee) {
        maNhanVien.setText(employee.getMaNhanvien());
        tenNhanVien.setText(employee.getTenNhanVien());
        hoNhanVien.setText(employee.getHoNhanVien());
        identifier.setText(employee.getIdentifier());
        ngayVaoLam.setValue(employee.getNgayVaoLam());
        quocTich.setText(employee.getQuocTich());
        ngaySinh.setValue(employee.getNgaySinh());
        diaChi.setText(employee.getDiaChi());
        soDienThoai.setText(employee.getSoDT());
        phongBan.setValue(employee.getPhongBan());
        vitri.setText(employee.getVitri());

        final LocalDate now = LocalDate.now();
        final long diffDate = employee.getNgayVaoLam().until(now, ChronoUnit.DAYS);
        onboardDateEmployee.setTextFill(Paint.valueOf("GREEN"));
        onboardDateEmployee.setFont(Font.font("System", FontWeight.BOLD, 18));
        onboardDateEmployee.setText(diffDate + "");
    }

    public void enableBtnAdd(boolean enableSubmit, boolean enableEdit, boolean enableDelete) {
        visiableBtn(enableSubmit, enableEdit, enableDelete);
    }

    public static FXMLLoader renderStage() {
        inputStage = new Stage();
        return StageCommon.displayStage(inputStage, "inputFormEmployee.fxml", "Thông Tin Nhân Viên", 650, 400);
    }

    public static void closedStage() {
        StageCommon.hiddenStage(inputStage);
    }


    @FXML
    void onEnableEditButton(ActionEvent event) {
        setEditable(true);
        visiableBtn(true, false, false);
    }


    @FXML
    protected void onSaveData() {
        if (!validateInputData()) {
            System.out.println("Invalid data");
            return;
        }
        final Employee employee = getDataForm();
        EmployeeRepository.saveEmployee(employee);
        closedStage();
    }

    @FXML
    void onDeleteData(ActionEvent event) {
        EmployeeRepository.deleteEmployeeById(maNhanVien.getText());
        closedStage();
    }

    private void visiableBtn(boolean submit, boolean edit, boolean delete) {
        btnSubmit.setVisible(submit);
        btnEdit.setVisible(edit);
        btnDelete.setVisible(delete);
    }


    private boolean validateInputData() {
        if (maNhanVien.getText().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Mã Nhân Viên không được rỗng", Paint.valueOf("RED"));
            return false;
        }
        if (hoNhanVien.getText().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Họ Nhân Viên không được rỗng", Paint.valueOf("RED"));
            return false;
        }
        if (tenNhanVien.getText().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Tên Nhân Viên không được rỗng", Paint.valueOf("RED"));
            return false;
        }
        if (identifier.getText().equals("")) {
            Utils.renderFailureMessage(alertMessage, "CMND không được rỗng", Paint.valueOf("RED"));
            return false;
        }
        if (ngayVaoLam.getValue().toString().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Ngày vào làm không được rỗng", Paint.valueOf("RED"));
            return false;
        }

        if (ngaySinh.getValue().toString().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Ngày Sinh không được rỗng", Paint.valueOf("RED"));
            return false;
        }
        if (quocTich.getText().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Quốc tịch không được rỗng", Paint.valueOf("RED"));
            return false;
        }
        if (diaChi.getText().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Địa chỉ không được rỗng", Paint.valueOf("RED"));
            return false;
        }
        if (soDienThoai.getText().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Số Điện thoại không được rỗng", Paint.valueOf("RED"));
            return false;
        }
//        if (phongBan.getValue().equals("")) {
//            Utils.renderFailureMessage(alertMessage, "Phòng Ban không được rỗng", Paint.valueOf("RED"));
//            return false;
//        }
        if (vitri.getText().equals("")) {
            Utils.renderFailureMessage(alertMessage, "Vị Trí không được rỗng", Paint.valueOf("RED"));
            return false;
        }
        return true;
    }

    private void setValueDepartment() {
        final List<Department> departments = DepartmentRepository.getDepartments();
        for (Department department : departments) {
            phongBan.getItems().add(department.getDepartmentName());
        }
    }
}
