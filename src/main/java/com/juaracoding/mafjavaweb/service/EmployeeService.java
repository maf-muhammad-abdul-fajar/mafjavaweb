package com.juaracoding.mafjavaweb.service;


import com.juaracoding.mafjavaweb.model.Employee;
import com.juaracoding.mafjavaweb.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {


    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }


    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public void saveEmployee(Employee employee) {
        this.employeeRepo.save(employee);
    }

    public Employee getEmployeeById(long id) {
        Optional<Employee> optional = employeeRepo.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return employee;
    }

    public void deleteEmployeeById(long id) {
        this.employeeRepo.deleteById(id);
    }

    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.employeeRepo.findAll(pageable);
    }
}
