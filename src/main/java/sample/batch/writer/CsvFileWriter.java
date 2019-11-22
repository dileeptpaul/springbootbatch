package sample.batch.writer;

import sample.batch.model.Employee;
import sample.batch.repository.BkrCounterRepository;
import org.springframework.batch.item.ItemWriter;

import javax.annotation.Resource;
import java.util.List;

public class CsvFileWriter implements ItemWriter<Employee> {

    @Resource
    private BkrCounterRepository bkrCounterRepository;

    public void write(List<? extends Employee> employeeList) throws Exception {
        System.out.println(employeeList);

        for (Employee employee : employeeList) {
            bkrCounterRepository.save(employee);
        }
    }
}
