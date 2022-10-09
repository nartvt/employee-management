package com.uit.repository;

import com.uit.entity.Employee;
import com.uit.util.Pagination;
import com.uit.util.Utils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class EmployeeRepository {

    private static final String[] EMP_HEADERS = {"maNhanVien",
            "hoNhanVien",
            "tenNhanVien",
            "identifier",
            "ngayVaoLam",
            "ngaySinh",
            "quocTich",
            "diaChi",
            "soDT",
            "phongBan",
            "vitri"};
    private static final String MA_NHANVIEN = "maNhanVien";
    private static final String HO_NHANVIEN = "hoNhanVien";
    private static final String TEN_NHANVIEN = "tenNhanVien";
    private static final String IDENTIFIER = "identifier";

    private static final String NGAY_VAOLAM = "ngayVaoLam";
    private static final String NGAY_SINH = "ngaySinh";
    private static final String QUOC_TICH = "quocTich";
    private static final String DIA_CHI = "diaChi";
    private static final String SO_DT = "soDT";
    private static final String PHONG_BAN = "phongBan";
    private static final String VI_TRI = "vitri";

    public static List<Employee> getEmployees() {
        final List<Employee> employees = new ArrayList<>();
        try (final Reader in = new FileReader(Paths.get("employee.csv").toString())) {
            final List<CSVRecord> records = CSVFormat.DEFAULT.withHeader(EMP_HEADERS).parse(in).getRecords();
            for (CSVRecord record : records.subList(1, records.size())) {
                final Employee employee = new Employee();
                employee.setMaNhanvien(record.get(MA_NHANVIEN));
                employee.setHoNhanVien(record.get(HO_NHANVIEN));
                employee.setTenNhanVien(record.get(TEN_NHANVIEN));
                employee.setIdentifier(record.get(IDENTIFIER));
                employee.setNgayVaoLam(Utils.convertFromText(record.get(NGAY_VAOLAM)));
                employee.setQuocTich(record.get(QUOC_TICH));
                employee.setNgaySinh(Utils.convertFromText(record.get(NGAY_SINH)));
                employee.setDiaChi(record.get(DIA_CHI));
                employee.setSoDT(record.get(SO_DT));
                employee.setPhongBan(record.get(PHONG_BAN));
                employee.setVitri(record.get(VI_TRI));

                employees.add(employee);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            return employees;
        }
    }

    private static void saveEmployees(final List<Employee> employees) {
        try (final Writer writer = Files.newBufferedWriter(Paths.get("employee.csv"));
             final CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(EMP_HEADERS));) {
            for (Employee employee : employees) {
                csvPrinter.printRecord(employee.getMaNhanvien(),
                        employee.getHoNhanVien(),
                        employee.getTenNhanVien(),
                        employee.getIdentifier(),
                        Utils.convertFromLocalDate(employee.getNgayVaoLam()),
                        Utils.convertFromLocalDate(employee.getNgaySinh()),
                        employee.getQuocTich(),
                        employee.getDiaChi(),
                        employee.getSoDT(),
                        employee.getPhongBan(),
                        employee.getVitri());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        triggerUpdate();
    }

    public static void saveEmployee(Employee employee) {
        int index = findEmployeeById(employee.getMaNhanvien());
        final List<Employee> employees = getEmployees();
        if (index == -1) {
            employees.add(employee);
            saveEmployees(employees);
            return;
        }
        employees.get(index).setHoNhanVien(employee.getHoNhanVien());
        employees.get(index).setTenNhanVien(employee.getTenNhanVien());
        employees.get(index).setIdentifier(employee.getIdentifier());
        employees.get(index).setQuocTich(employee.getQuocTich());
        employees.get(index).setVitri(employee.getVitri());
        employees.get(index).setPhongBan(employee.getPhongBan());
        employees.get(index).setNgayVaoLam(employee.getNgayVaoLam());
        employees.get(index).setSoDT(employee.getSoDT());
        employees.get(index).setVitri(employee.getVitri());
        saveEmployees(employees);
    }

    private static int findEmployeeById(String employeeId) {
        final List<Employee> employees = getEmployees();
        for (int i = 0; i < employees.size(); i++) {
            if (Objects.equals(employees.get(i).getMaNhanvien(), employeeId)) {
                return i;
            }
        }
        return -1;
    }

    public static void deleteEmployeeById(String employeeId) {
        final List<Employee> employees = getEmployees();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getMaNhanvien().equals(employeeId)) {
                employees.remove(i);
                break;
            }
        }
        saveEmployees(employees);
    }

    public static Pagination getEmployeesPagination(int limit, int offset) {
        final List<Employee> employees = getEmployees();
        if (employees.isEmpty()) {
            return new Pagination(0, new ArrayList<>(), new ArrayList<>());
        }
        final int size = employees.size();
        if ((offset >= size)) {
            return new Pagination(0, new ArrayList<>(), new ArrayList<>());
        }
        if ((limit + offset) > size) {
            limit = size - offset;
        }
        return new Pagination(employees.size(), employees.subList(offset, limit + offset), new ArrayList<>());
    }

    private static void triggerUpdate() {
        File file = new File("triggeremps");
        try {
            if (file.createNewFile()) {
                System.out.println("Created File triggeremps!");
                return;
            }
            System.out.println("Exists File triggeremps!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteFile() {
        if (isVisibleFile()) {
            File file = new File("triggeremps");
            file.delete();
            System.out.println("Deleted File triggeremps!");
        }
    }

    public static boolean isVisibleFile() {
         return new File("triggeremps").exists();
    }
}
