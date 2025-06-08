package edu.wsiiz.repairshop.employee.application;


import edu.wsiiz.repairshop.employee.domain.employee.Employee;
import edu.wsiiz.repairshop.employee.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  final EmployeeRepository repository;

  public Employee get(Long id) {
    return repository.findById(id).orElse(null);
  }

  public Employee save(Employee employee) {
    return repository.save(employee);
  }

  public void remove(Employee employee) {
    repository.delete(employee);
  }

}
