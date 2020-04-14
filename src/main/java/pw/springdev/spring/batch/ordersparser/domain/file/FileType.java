package pw.springdev.spring.batch.ordersparser.domain.file;

import java.util.regex.Pattern;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
public enum FileType {
    CSV("csv"),
    JSON("json");

    /**
     * pattern
     * Pattern compiled by using stringPattern
     */
    private final Pattern pattern;

    /**
     * stringPattern
     * pattern in String
     * Examples - ^[A-Za-z0-9_-]*.csv$, ^[A-Za-z0-9_-]*.json$
     */
    private final String stringPattern;

    public final static String FILENAME_PATTERN = "[A-Za-z0-9_-]*";
    public final static String BEGINNING_OF_INPUT = "^";
    public final static String END_OF_INPUT = "$";

    /**
     * extension
     * Examples - csv, json
     */
    FileType(String extension) {

        this.stringPattern = BEGINNING_OF_INPUT
                .concat(FILENAME_PATTERN)
                .concat(".")
                .concat(extension)
                .concat(END_OF_INPUT);
        this.pattern = Pattern.compile(this.stringPattern, Pattern.CASE_INSENSITIVE);
    }

    public Pattern pattern() {
        return pattern;
    }

    public String stringPattern() {
        return stringPattern;
    }
}
