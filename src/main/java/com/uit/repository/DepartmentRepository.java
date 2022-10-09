package com.uit.repository;

import com.uit.entity.Department;
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

public final class DepartmentRepository {
    private final static String[] DEPART_HEADERS = {"departmentId",
            "departmentName",
            "departmentLeader",
            "departmentCreatedDate"};

    private static final String DEPARTMENT_ID = "departmentId";

    private static final String DEPARTMENT_NAME = "departmentName";

    private static final String DEPARTMENT_LEADER = "departmentLeader";

    private static final String DEPARTMENT_CREATED_DATE = "departmentCreatedDate";


    private static void saveDepartments(final List<Department> departments) {
        try (final Writer writer = Files.newBufferedWriter(Paths.get("department.csv"));
             final CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(DEPART_HEADERS));) {
            for (Department department : departments) {
                csvPrinter.printRecord(
                        department.getDepartMentId(),
                        department.getDepartmentName(),
                        department.getDepartmentLeader(),
                        Utils.convertFromLocalDate(department.getDepartmentCreatedDate()));
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        triggerUpdate();
    }

    public static void saveDepartment(Department department) {
        final List<Department> departments = getDepartments();
        int index = findDepartmentById(department);
        if (index == -1) {
            departments.add(department);
            saveDepartments(departments);
            return;
        }
        departments.get(index).setDepartmentName(department.getDepartmentName());
        departments.get(index).setDepartmentCreatedDate(department.getDepartmentCreatedDate());
        departments.get(index).setDepartmentLeader(department.getDepartmentLeader());
        saveDepartments(departments);
    }

    private static int findDepartmentById(Department department) {
        final List<Department> departments = getDepartments();
        for (int i = 0; i < departments.size(); i++) {
            if (Objects.equals(departments.get(i).getDepartMentId(), department.getDepartMentId())) {
                return i;
            }
        }
        return -1;
    }

    public static void deleteDepartmentById(String departmentId) {
        final List<Department> departments = getDepartments();
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getDepartMentId().equals(departmentId)) {
                departments.remove(i);
                break;
            }
        }
        saveDepartments(departments);
    }

    public static List<Department> getDepartments() {
        final List<Department> departments = new ArrayList<>();
        try (final Reader in = new FileReader(Paths.get("department.csv").toString())) {
            final List<CSVRecord> records = CSVFormat.DEFAULT.withHeader(DEPART_HEADERS).parse(in).getRecords();
            for (CSVRecord record : records.subList(1, records.size())) {
                final Department department = new Department();
                department.setDepartMentId(record.get(DEPARTMENT_ID));
                department.setDepartmentName(record.get(DEPARTMENT_NAME));
                department.setDepartmentLeader(record.get(DEPARTMENT_LEADER));
                department.setDepartmentCreatedDate(Utils.convertFromText(record.get(DEPARTMENT_CREATED_DATE)));
                departments.add(department);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return departments;
    }

    public static Pagination getDepartmentsPagination(int limit, int offset) {
        final List<Department> departments = getDepartments();
        if (departments.isEmpty()) {
            return new Pagination(0, new ArrayList<>(), new ArrayList<>());
        }

        final int size = departments.size();
        if ((offset >= size)) {
            return new Pagination(0, new ArrayList<>(), new ArrayList<>());
        }
        if ((limit + offset) > size) {
            limit = size - offset;
        }
        return new Pagination(departments.size(), null, departments.subList(offset, limit + offset));
    }

    private static void triggerUpdate() {
        final File file = new File("triggerdepars");
        try {
            if (file.createNewFile()) {
                System.out.println("Created File triggerdepars!");
                return;
            }
            System.out.println("Exists File triggerdepars!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteFile() {
        if (isVisibleFile()) {
            File file = new File("triggerdepars");
            file.delete();
            System.out.println("Deleted File triggerdepars!");
        }
    }

    public static boolean isVisibleFile() {
        File file = new File("triggerdepars");
        return file.exists();
    }
}
