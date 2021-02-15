package rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.entity.Employee;
import rest.exceptionHandling.NoSuchEmployeeException;
import rest.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainRestController {
/**/
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")           /*http://localhost:8080/api/employees*/
    public List<Employee> showAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return employees;
    }

    @GetMapping("/employees/{id}")          /*http://localhost:8080/api/employees/1*/
    public Employee getEmployeeById(@PathVariable("id") int id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee == null) {
            throw new NoSuchEmployeeException("there is no employee with id: " + id);
        }

        return employee;
    }

    @PostMapping("/employees")      /*Добавляем сотрудника. Данные отправляем в формате JSON
    jaxon конвертирует это из JSON в объект Employee. Затем записывает в БД нового сотрудника и присваевает
    сотруднику id.*/
    public Employee addNewEmployeeByPOST(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return employee;
    }

    @PutMapping("/employees")           /*PutMapping обновляет данные по сотруднику*/
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return employee;
    }

    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable("id") int id) {

        Employee employee = employeeService.getEmployee(id);
        if (employee == null) {
            throw new NoSuchEmployeeException("there is no employee with id: " + id);
        }else{
            employeeService.deleteEmployee(id);
        }

        return "Employee with id: " + id + " id - was deleted";
    }

}
