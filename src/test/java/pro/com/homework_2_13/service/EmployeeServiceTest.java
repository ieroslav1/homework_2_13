package pro.com.homework_2_13.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.com.homework_2_13.exception.EmployeeAlreadyAddedException;
import pro.com.homework_2_13.exception.EmployeeNotFoundException;
import pro.com.homework_2_13.exception.EmployeeStorageIsFullException;
import pro.com.homework_2_13.exception.EmployeeStringUtilsException;
import pro.com.homework_2_13.model.Employee;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        employeeService = new EmployeeService();

    }

    @AfterEach
    public void tearDown() {
        employeeService.clear();
    }


    @Test
    public void addNewEmployee() {
        Employee employee = employeeService.add("John", "Doe", 1, 1000);
        assertEquals(employee, employeeService.findByFullName("John Doe"));
    }

    @Test
    public void addExistingEmployee() {
        employeeService.add("John", "Doe", 1, 1000);
        assertThrows(EmployeeAlreadyAddedException.class, () -> {
            employeeService.add("John", "Doe", 1, 1000);
        });
    }

    @Test
    public void addMaxEmployees() {
        employeeService.add("John", "Doe", 1, 1000);
        employeeService.add("Jane", "Doe", 2, 2000);
        employeeService.add("Bob", "Smith", 3, 3000);
        employeeService.add("Alice", "Johnson", 4, 4000);
        assertThrows(EmployeeStorageIsFullException.class, () -> {
            employeeService.add("Mike", "Davis", 1, 5000);
        });
    }

    @Test
    public void removeExistingEmployee() {
        Employee employee = employeeService.add("John", "Doe", 1, 1000);
        Employee removedEmployee = employeeService.remove("John", "Doe", 1, 1000);
        assertEquals(employee, removedEmployee);
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findByFullName("John Doe"));
    }

    @Test
    public void removeNonExistingEmployee() {
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.remove("John", "Doe", 1, 1000));
    }

    @Test
    public void findExistingEmployee() {
        Employee employee = employeeService.add("John", "Doe", 1, 1000);
        Employee foundEmployee = employeeService.find("John", "Doe", 1, 1000);
        assertEquals(employee, foundEmployee);
    }

    @Test
    public void findNonExistingEmployee() {
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.find("John", "Doe", 1, 1000));
    }

    @Test
    public void findAll() {
        employeeService.add("John", "Doe", 1, 1000);
        employeeService.add("Jane", "Doe", 2, 2000);
        employeeService.add("Bob", "Smith", 3, 3000);

        Collection<Employee> allEmployees = employeeService.findAll();
        assertEquals(3, allEmployees.size());

        Employee john = new Employee("John", "Doe", 1, 1000);
        Employee jane = new Employee("Jane", "Doe", 2, 2000);
        Employee bob = new Employee("Bob", "Smith", 3, 3000);

        assertTrue(allEmployees.contains(john));
        assertTrue(allEmployees.contains(jane));
        assertTrue(allEmployees.contains(bob));
    }

    @Test
    public void findAllEmptyList() {
        Collection<Employee> allEmployees = employeeService.findAll();
        assertTrue(allEmployees.isEmpty());
    }

    @Test
    public void checkStringValid() {
        String firstName = "John";
        String lastName = "Doe";
        employeeService.checkString(firstName, lastName);
    }

    @Test
    public void checkStringBlank() {
        String firstName = "";
        String lastName = "Doe";
        assertThrows(EmployeeStringUtilsException.class, () -> employeeService.checkString(firstName, lastName));
    }

    @Test
    public void checkStringNull() {
        String firstName = null;
        String lastName = null;
        assertThrows(EmployeeStringUtilsException.class, () -> employeeService.checkString(firstName, lastName));
    }

    @Test
    public void checkStringNonAlpha() {
        String firstName = "John1";
        String lastName = "Doe";
        assertThrows(EmployeeStringUtilsException.class, () -> employeeService.checkString(firstName, lastName));
    }

    @Test
    public void findByFullNameNotFound() {
        String fullName = "John Doe";
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findByFullName(fullName));
    }

    @Test
    public void findByFullNameSuccess() {
        String fullName = "John Doe";
        Employee employee = employeeService.add("John", "Doe", 1, 1000);
        Employee result = employeeService.findByFullName(fullName);
        assertEquals(employee, result);
    }

}