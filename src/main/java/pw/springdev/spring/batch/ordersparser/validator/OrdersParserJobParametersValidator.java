package pw.springdev.spring.batch.ordersparser.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;
import pw.springdev.spring.batch.ordersparser.domain.file.FileType;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pw.springdev.spring.batch.ordersparser.constant.Constants.CSV_JOB_PARAMETERS;
import static pw.springdev.spring.batch.ordersparser.constant.Constants.JSON_JOB_PARAMETERS;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
@Slf4j
public class OrdersParserJobParametersValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {

        String csvFiles = jobParameters.getString(CSV_JOB_PARAMETERS);
        String jsonFiles = jobParameters.getString(JSON_JOB_PARAMETERS);

        boolean csvFilesHasText = StringUtils.hasText(csvFiles);
        boolean jsonFilesHasText = StringUtils.hasText(jsonFiles);

        if (!csvFilesHasText && !jsonFilesHasText) {
            throw new JobParametersInvalidException("CSV and JSON can't be null simultaneously");
        }

        Set<String> wrongCsvExtensionFiles = new HashSet<>();
        Set<String> wrongJsonExtensionFiles = new HashSet<>();

        Set<String> validCsvExtensionFiles = new HashSet<>();
        Set<String> validJsonExtensionFiles = new HashSet<>();

        if (csvFilesHasText) {
            Supplier<Stream<String>> csvFilesStreamSupplier = () -> Stream.of(csvFiles.split(","));

            Set<String> wrongExtensionFiles = csvFilesStreamSupplier.get()
                    .filter(file -> !file.matches(FileType.CSV.stringPattern()))
                    .collect(Collectors.toSet());
            wrongCsvExtensionFiles.addAll(wrongExtensionFiles);

            Set<String> validExtensionFiles = csvFilesStreamSupplier.get()
                    .filter(file -> file.matches(FileType.CSV.stringPattern()))
                    .collect(Collectors.toSet());
            validCsvExtensionFiles.addAll(validExtensionFiles);
        }

        if (jsonFilesHasText) {
            Supplier<Stream<String>> jsonFilesStreamSupplier = () -> Stream.of(jsonFiles.split(","));

            Set<String> wrongExtensionFiles = jsonFilesStreamSupplier.get()
                    .filter(file -> !file.matches(FileType.JSON.stringPattern()))
                    .collect(Collectors.toSet());
            wrongJsonExtensionFiles.addAll(wrongExtensionFiles);

            Set<String> validExtensionFiles = jsonFilesStreamSupplier.get()
                    .filter(file -> file.matches(FileType.JSON.stringPattern()))
                    .collect(Collectors.toSet());
            validJsonExtensionFiles.addAll(validExtensionFiles);
        }

        Set<String> wrongFiles = new HashSet<>();

        wrongFiles.addAll(wrongCsvExtensionFiles);
        wrongFiles.addAll(wrongJsonExtensionFiles);

        if (wrongFiles.size() > 0) {
            throw new JobParametersInvalidException("There are wrong files in jobs parameters:\n" + wrongFiles);
        }

        Set<String> validFiles = new HashSet<>();
        validFiles.addAll(validCsvExtensionFiles);
        validFiles.addAll(validJsonExtensionFiles);

        Set<String> exceptionsFiles = new HashSet<>();

        for (String file : validFiles) {
            try {
                new FileSystemResource(new File(file));
            } catch (Exception exception) {
                exceptionsFiles.add(file);
            }
        }

        if (exceptionsFiles.size() > 0) {
            throw new JobParametersInvalidException("There are files which can't be open in jobs parameters:\n"
                    + exceptionsFiles);
        } else {
            log.info("All the job parameters files are correct");
            log.info(validFiles.toString());
        }
    }
}
