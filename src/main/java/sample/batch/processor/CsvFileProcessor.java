package sample.batch.processor;

import sample.batch.model.Employee;
import org.springframework.batch.item.ItemProcessor;

public class CsvFileProcessor implements ItemProcessor<Employee, Employee> {
    @Override
    public Employee process(final Employee employee) throws Exception {
        return employee;
    }
}
