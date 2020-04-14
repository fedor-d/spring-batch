package pw.springdev.spring.batch.ordersparser.helper;

import pw.springdev.spring.batch.ordersparser.domain.file.FileType;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pw.springdev.spring.batch.ordersparser.constant.Constants.CSV_JOB_PARAMETERS;
import static pw.springdev.spring.batch.ordersparser.constant.Constants.JSON_JOB_PARAMETERS;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
public class ConsoleArgsHelper {

    public static String[] getNewArgs(String[] args) {

        Set<String> csvFiles = getFilesByFileType(args, FileType.CSV);
        Set<String> jsonFiles = getFilesByFileType(args, FileType.JSON);

        return new String[]{
                CSV_JOB_PARAMETERS.concat("=").concat(String.join(",", csvFiles)),
                JSON_JOB_PARAMETERS.concat("=").concat(String.join(",", jsonFiles))
        };
    }


    private static Set<String> getFilesByFileType(String[] args, FileType fileType) {
        return Stream.of(args)
                .filter(fileType.pattern().asPredicate())
                .collect(Collectors.toSet());
    }
}
