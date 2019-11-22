package sample.batch.reader;

import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;
import org.springframework.stereotype.Component;

@Component
public class BkrReaderSeparatorPolicy extends SimpleRecordSeparatorPolicy {
    @Override
    public boolean isEndOfRecord(final String line) {
        return line.trim().length() != 0 && super.isEndOfRecord(line);
    }

    @Override
    public String postProcess(final String record) {
        if (record != null && record.trim().startsWith("20")) {
            return null;
        }
        return super.postProcess(record);
    }
}
