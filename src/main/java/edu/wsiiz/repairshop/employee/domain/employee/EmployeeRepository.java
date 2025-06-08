package edu.wsiiz.repairshop.employee.domain.employee;

import java.util.List;

import edu.wsiiz.repairshop.employee.domain.employee.Employee;
import edu.wsiiz.repairshop.employee.domain.employee.Positions;
import edu.wsiiz.repairshop.employee.domain.employee.Qualifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    List<Employee> findByPosition(Positions position);

    @Query("select e from Employee e where e.Id = :Id and (:position is null)")
    List<Employee> findForCustomer(@Param("customerId") Long customerId);

}
