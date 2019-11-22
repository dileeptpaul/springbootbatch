package sample.batch.repository;

import sample.batch.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BkrCounterRepository extends JpaRepository<Employee, Integer> {
}



