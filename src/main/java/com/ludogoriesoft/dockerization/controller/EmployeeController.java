package com.ludogoriesoft.dockerization.controller;

import com.ludogoriesoft.dockerization.entity.Employee;
import com.ludogoriesoft.dockerization.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    //    @RequestMapping(value = "/employees", method = RequestMethod.GET)
//    @ResponseBody

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
//           404
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable("id") Long id) {
        employeeRepository.deleteById(id);
//        204 - successfully deleted
        return ResponseEntity.
                noContent().
                build();
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee,
                                           UriComponentsBuilder uriComponentsBuilder) {
//        http://localhost:8080/employee/id
        Employee savedEmployee = employeeRepository.save(employee);

        URI location = uriComponentsBuilder.path("/employees/{id}").
                buildAndExpand(savedEmployee.getId()).toUri();

        // 201
        return ResponseEntity.
                created(location).
                build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        Optional<Employee> foundEmployee = employeeRepository.findById(id);

        if (foundEmployee.isPresent()) {

            String name = employee.getName();
            int age = employee.getAge();

            foundEmployee.get().setName(name);
            foundEmployee.get().setAge(age);

            employeeRepository.save(foundEmployee.get());
            return ResponseEntity.ok().build();
        } else {

            return ResponseEntity.notFound().build();
        }
    }

}
