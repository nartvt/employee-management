package com.uit.controller;

import com.uit.common.StageCommon;
import com.uit.entity.Department;
import com.uit.entity.Employee;
import com.uit.repository.DepartmentRepository;
import com.uit.repository.EmployeeRepository;
import com.uit.util.Pagination;
import com.uit.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ManagementController implements Initializable {

    public ImageView imgPhongBan;
    public ImageView imgNhanVien;
    public ImageView imgGioiThieu;
    public AnchorPane sliderBar;
    public AnchorPane anchorMain;
    @FXML
    public Button previousPageBtn;
    @FXML
    public Button nextPageBtn;
    @FXML
    private Button btnAddEmployee;

    @FXML
    private Button btnNhanVien;

    @FXML
    private Button btnPhongBan;

    @FXML
    private Button btnGioiThieu;

    @FXML
    private TableView<Employee> tableViewEmployee;

    @FXML
    private TableColumn<Employee, String> tbChucVu;

    @FXML
    private TableColumn<Employee, String> tbNgayVaoLam;

    @FXML
    private TableColumn<Employee, String> tbIdentifier;

    @FXML
    private TableColumn<Employee, String> tbPhongBan;

    @FXML
    private TableColumn<Employee, String> tbQuocTich;

    @FXML
    private TableColumn<Employee, String> tbDiaChi;

    @FXML
    private TableColumn<Employee, String> tbSoDienThoai;

    @FXML
    private TableColumn<Employee, String> tbTenNhanVien;

    @FXML
    private Label nameOfScreen;

    @FXML
    private AnchorPane anchorNhanVien;

    @FXML
    private AnchorPane anchorAbout;

    @FXML
    private AnchorPane anchorDepartment;

    final ObservableList<Employee> employees = FXCollections.observableArrayList();


    final ObservableList<Department> departments = FXCollections.observableArrayList();

    @FXML
    private TableView<Department> tableViewPhongBan;

    @FXML
    private TableColumn<Department, String> tbTenPhong;

    @FXML
    private TableColumn<Department, String> tbTruongPhong;

    @FXML
    private TableColumn<Department, String> tbNgayThanhLap;

    private ScheduledExecutorService service;

    private static Stage managementStage;

    private int defaultLimit = Utils.defaultLimit;
    private int defaultPageEmps = Utils.defaultPage;
    private int defaultPageDeparts = Utils.defaultPage;

    private int sizeOfEmployee = 0;

    private int sizeOfDepartment = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enableBoard(false, false, true);
        higtlyBtnAbout();
        inittialEmployeeTableData();
        inittialDepartmentTableData();
        autoUpdateData();
        onCloseEvent();

    }

    private void enableBoard(boolean boardEmployee, boolean boardDepartment, boolean boardAbout) {
        visibleButton(false, false);
        anchorNhanVien.setVisible(boardEmployee);
        anchorDepartment.setVisible(boardDepartment);
        anchorAbout.setVisible(boardAbout);
        if (anchorAbout.isVisible()) {
            btnAddEmployee.setVisible(false);
        }
        if (anchorNhanVien.isVisible() || anchorDepartment.isVisible()) {
            btnAddEmployee.setVisible(true);
        }
        setNameOfScreen();
    }

    private void visibleButton(boolean isNextVisible, boolean isPreviousVisible) {
        nextPageBtn.setVisible(isNextVisible);
        previousPageBtn.setVisible(isPreviousVisible);
    }

    private void setNameOfScreen() {
        anchorAbout.setStyle("-fx-background-image: url(img/back-ground.png)");
        nameOfScreen.setVisible(true);
        nameOfScreen.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        nameOfScreen.setAlignment(Pos.BASELINE_CENTER);
        if (anchorNhanVien.isVisible()) {
            nameOfScreen.setText("Quản Lý Nhân Viên");
        }

        if (anchorDepartment.isVisible()) {
            nameOfScreen.setText("Quản Lý Phòng Ban");
        }

        if (anchorAbout.isVisible()) {
            nameOfScreen.setText("Đề Tài: Quản Lý Nhân Sự");
        }
    }

    private void inittialEmployeeTableData() {
        final Pagination pagination = EmployeeRepository.getEmployeesPagination(defaultLimit, (defaultPageEmps - 1) * defaultLimit);
        sizeOfEmployee = pagination.getTotal();
        if (pagination.getEmployees().size() == defaultLimit) {
            defaultPageEmps++;
            visibleButton(true, false);
        }
        employees.addAll(pagination.getEmployees());
        tbTenNhanVien.setCellValueFactory(new PropertyValueFactory<Employee, String>("fullName"));
        tbIdentifier.setCellValueFactory(new PropertyValueFactory<Employee, String>("identifier"));
        tbNgayVaoLam.setCellValueFactory(new PropertyValueFactory<Employee, String>("ngayVaoLam"));
        tbPhongBan.setCellValueFactory(new PropertyValueFactory<Employee, String>("phongBan"));
        tbChucVu.setCellValueFactory(new PropertyValueFactory<Employee, String>("vitri"));
        tbSoDienThoai.setCellValueFactory(new PropertyValueFactory<Employee, String>("soDT"));
        tbQuocTich.setCellValueFactory(new PropertyValueFactory<Employee, String>("quocTich"));
        tableViewEmployee.setItems(employees);
    }

    private void inittialDepartmentTableData() {
        final Pagination pagination = DepartmentRepository.getDepartmentsPagination(defaultLimit, (defaultPageDeparts - 1) * defaultLimit);
        sizeOfDepartment = pagination.getTotal();
        if (pagination.getDepartments().size() == defaultLimit) {
            defaultPageDeparts++;
            visibleButton(true, false);
        }
        departments.addAll(pagination.getDepartments());
        tbTenPhong.setCellValueFactory(new PropertyValueFactory<Department, String>("departmentName"));
        tbTruongPhong.setCellValueFactory(new PropertyValueFactory<Department, String>("departmentLeader"));
        tbNgayThanhLap.setCellValueFactory(new PropertyValueFactory<Department, String>("departmentCreatedDate"));
        tableViewPhongBan.setItems(departments);
    }

    @FXML
    void onAddEmployee(ActionEvent event) {
        try {
            showStageInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void openModalInputEmployeeClickOnTable(MouseEvent event) {
        if (event.getClickCount() % 2 == 0) {
            try {
                final Employee employee = tableViewEmployee.getSelectionModel().getSelectedItem();
                final FXMLLoader fxmlLoader = showStageInput();
                final EmployeeController employeeController = fxmlLoader.getController();
                employeeController.enableBtnAdd(false, true, true);
                employeeController.setEditable(false);
                employeeController.setData(employee);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void openModalInputDepartmentClickOnTable(MouseEvent event) {
        if (event.getClickCount() % 2 == 0) {
            try {
                final Department department = tableViewPhongBan.getSelectionModel().getSelectedItem();
                final FXMLLoader fxmlLoader = showStageInput();
                final DepartmentController departmentController = fxmlLoader.getController();
                departmentController.enableBtnAdd(false, true, true);
                departmentController.setEditable(false);
                departmentController.setData(department);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void onClickSidePanelDepartment(ActionEvent event) {
        higtlyBtnDepartment();
        enableBoard(false, true, false);
    }

    @FXML
    protected void onClickSidePanelEmployee(ActionEvent event) {
        higtlyBtnEmployee();
        enableBoard(true, false, false);
    }

    @FXML
    protected void onClickSidePanelAbout(ActionEvent event) {
        higtlyBtnAbout();
        enableBoard(false, false, true);
    }

    private void higtlyBtnEmployee() {
        final List<Button> resetButtons = new ArrayList<>();
        resetButtons.add(btnGioiThieu);
        resetButtons.add(btnPhongBan);
        StageCommon.styleCurrentStage(btnNhanVien, resetButtons);
    }

    private void higtlyBtnDepartment() {
        final List<Button> resetButtons = new ArrayList<>();
        resetButtons.add(btnGioiThieu);
        resetButtons.add(btnNhanVien);
        StageCommon.styleCurrentStage(btnPhongBan, resetButtons);
    }

    private void higtlyBtnAbout() {
        final List<Button> resetButtons = new ArrayList<>();
        resetButtons.add(btnNhanVien);
        resetButtons.add(btnPhongBan);
        StageCommon.styleCurrentStage(btnGioiThieu, resetButtons);
    }

    private void autoUpdateData() {
        final Runnable runnable = new Runnable() {
            public void run() {
                refreshDataForm();
            }
        };
        service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 100, 200, TimeUnit.MILLISECONDS);

    }

    public static void renderStageManagement() {
        managementStage = new Stage();
        StageCommon.displayStage(managementStage, "management.fxml",
                "Quản Lý Nhân Viên", 1200, 600);
    }

    public static void closedStage() {
        managementStage.close();
    }

    private FXMLLoader showStageInput() throws IOException {
        if (anchorNhanVien.isVisible()) {
            return EmployeeController.renderStage();
        }
        return DepartmentController.renderStage();
    }

    private void onCloseEvent() {
        managementStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // stop interval run in back-ground when is stop service
                service.shutdown();
            }
        });
    }

    @FXML
    public void onClickPreviousPage(ActionEvent event) {
        if(anchorNhanVien.isVisible()){
            int page = defaultPageEmps - 1;
            if (page < 1) {
                page = 1;
            }
            int offset = (page - 1) * defaultLimit;
            defaultPageEmps = page;
            final Pagination pagination = EmployeeRepository.getEmployeesPagination(defaultLimit, offset);
            employees.clear();
            employees.addAll(pagination.getEmployees());
            tableViewEmployee.setItems(employees);
            if (offset >= pagination.getTotal()) {
                visibleButton(false, true);
            }
        }
        if(anchorDepartment.isVisible()){
            int page = defaultPageDeparts - 1;
            if (page < 1) {
                page = 1;
            }
            int offset = (page - 1) * defaultLimit;
            defaultPageDeparts = page;
            final Pagination pagination = DepartmentRepository.getDepartmentsPagination(defaultPageDeparts, offset);
            departments.clear();
            departments.addAll(pagination.getDepartments());
            tableViewPhongBan.setItems(departments);
            if (offset >= pagination.getTotal()) {
                visibleButton(false, true);
            }
        }
    }

    @FXML
    public void onClickNextPage(ActionEvent event) {
        if(anchorNhanVien.isVisible()){
            int page = defaultPageEmps + 1;
            if (page < 1) {
                page = 1;
            }
            int offset = (page - 1) * defaultLimit;
            defaultPageEmps = page;
            final Pagination pagination = EmployeeRepository.getEmployeesPagination(defaultLimit, offset);
            employees.clear();
            employees.addAll(pagination.getEmployees());
            tableViewEmployee.setItems(employees);
            if (offset >= pagination.getTotal()) {
                visibleButton(false, true);
            }
        }
        if(anchorDepartment.isVisible()){
            int page = defaultPageDeparts + 1;
            if (page < 1) {
                page = 1;
            }
            int offset = (page - 1) * defaultLimit;
            defaultPageDeparts = page;
            final Pagination pagination = DepartmentRepository.getDepartmentsPagination(defaultPageDeparts, offset);
            departments.clear();
            departments.addAll(pagination.getDepartments());
            tableViewPhongBan.setItems(departments);
            if (offset >= pagination.getTotal()) {
                visibleButton(false, true);
            }
        }
    }

    private void refreshDataForm() {
        final List<Employee> employeesTemp = EmployeeRepository.getEmployees();
        if (EmployeeRepository.isVisibleFile()) {
            employees.clear();
            employees.addAll(employeesTemp);
            tableViewEmployee.setItems(employees);
            EmployeeRepository.deleteFile();
        }

        final List<Department> departmentsTemp = DepartmentRepository.getDepartments();
        if (DepartmentRepository.isVisibleFile()) {
            departments.clear();
            departments.addAll(departmentsTemp);
            tableViewPhongBan.setItems(departments);
            DepartmentRepository.deleteFile();
        }
        if (anchorDepartment.isVisible()) {

        }
    }
}