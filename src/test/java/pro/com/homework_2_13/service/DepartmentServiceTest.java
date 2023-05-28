package pro.com.homework_2_13.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.com.homework_2_13.model.Employee;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DepartmentServiceTest {
    private DepartmentService departmentService;
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        employeeService = mock(EmployeeService.class);
        departmentService = new DepartmentService(employeeService);
    }

    @Test
    public void getEmployeeWithMinSalaryFromDepartment() {
        // Arrange
        Employee employee1 = new Employee("John", "Doe", 1, 1000);
        Employee employee2 = new Employee("Jane", "Doe", 1, 2000);
        Employee employee3 = new Employee("Bob", "Spenser", 2, 1500);
        when(employeeService.findAll()).thenReturn(Arrays.asList(employee1, employee2, employee3));

        Employee result = departmentService.getEmployeeWithMinSalaryFromDepartment(1);

        assertEquals(employee1, result);
    }

    @Test
    public void getEmployeeWithMaxSalaryFromDepartment() {
        // Arrange
        Employee employee1 = new Employee("John", "Doe", 1, 1000);
        Employee employee2 = new Employee("Jane", "Doe", 1, 2000);
        Employee employee3 = new Employee("Bob", "Spenser", 2, 1500);
        when(employeeService.findAll()).thenReturn(Arrays.asList(employee1, employee2, employee3));

        Employee result = departmentService.getEmployeeWithMaxSalaryFromDepartment(1);

        assertEquals(employee2, result);
    }

    @Test
    public void getEmployeesByDepartment() {
        Employee employee1 = new Employee("John", "Doe", 1, 1000);
        Employee employee2 = new Employee("Jane", "Doe", 1, 2000);
        Employee employee3 = new Employee("Bob", "Spenser", 2, 1500);
        when(employeeService.findAll()).thenReturn(Arrays.asList(employee1, employee2, employee3));

        Map<Integer, List<Employee>> result = departmentService.getEmployeesByDepartment();

        assertEquals(2, result.size());
        assertEquals(Arrays.asList(employee1, employee2), result.get(1));
        assertEquals(Collections.singletonList(employee3), result.get(2));
    }

    @Test
    public void getAllEmployeesFromDepartment() {
        Employee employee1 = new Employee("John", "Doe", 1, 1000);
        Employee employee2 = new Employee("Jane", "Doe", 1, 2000);
        Employee employee3 = new Employee("Bob", "Spenser", 2, 1500);
        when(employeeService.findAll()).thenReturn(Arrays.asList(employee1, employee2, employee3));

        List<Employee> result = departmentService.getAllEmployeesFromDepartment(1);

        assertEquals(2, result.size());
        assertTrue(result.contains(employee1));
        assertTrue(result.contains(employee2));
        assertFalse(result.contains(employee3));
    }

    @Test
    public void getEmployeeWithMinSalaryFromDepartment_NoEmployees() {
        when(employeeService.findAll()).thenReturn(Collections.emptyList());
        Employee result = departmentService.getEmployeeWithMinSalaryFromDepartment(1);
        assertNull(result);
    }

    @Test
    public void getEmployeeWithMaxSalaryFromDepartment_NoEmployees() {
        when(employeeService.findAll()).thenReturn(Collections.emptyList());
        Employee result = departmentService.getEmployeeWithMaxSalaryFromDepartment(1);
        assertNull(result);
    }

    @Test
    public void getEmployeesByDepartment_NoEmployees() {
        when(employeeService.findAll()).thenReturn(Collections.emptyList());
        Map<Integer, List<Employee>> result = departmentService.getEmployeesByDepartment();
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllEmployeesFromDepartment_NoEmployees() {
        when(employeeService.findAll()).thenReturn(Collections.emptyList());
        List<Employee> result = departmentService.getAllEmployeesFromDepartment(1);
        assertTrue(result.isEmpty());
    }
}