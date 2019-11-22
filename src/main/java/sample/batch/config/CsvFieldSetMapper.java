package sample.batch.config;

import sample.batch.model.Employee;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class CsvFieldSetMapper implements FieldSetMapper<Employee> {

    @Override
    public Employee mapFieldSet(FieldSet fieldSet) {
        final Employee employee = new Employee();
        employee.setName(fieldSet.readString("name"));
        employee.setPlace(fieldSet.readString("place"));
        return employee;
    }
}
