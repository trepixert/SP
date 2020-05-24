package org.example.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 * Мне было слишком лень разбивать все на классы (
 */

public class Main {

    enum Size {
        BYTE(1L, 0, "B"),
        KILOBYTE(1024L, 1, "K"),
        MEGABYTE(1048576L, 2, "M"),
        GIGABYTE(1_073_741_824L, 3, "G");

        private long min;
        private long count;
        private String extension;

        Size(long min, long count, String extension) {
            this.min = min;
            this.count = count;
            this.extension = extension;
        }
    }

    public static final String LS_COMMAND = "ls";
    public static final String QUIT = "qt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String commandAndArgs = scanner.nextLine().trim();
            String command = commandAndArgs.substring(0, 2).trim();
            if (command.equals(QUIT)) {
                break;
            }
            if (!command.equals(LS_COMMAND)) {
                throw new UnsupportedOperationException(String.format("Неизвестная команда %s", command));
            }
            String resultArgs = "";
            if (commandAndArgs.length() > 2) {
                String arguments = commandAndArgs.substring(3).trim();
                resultArgs = String.join("", arguments.split("-")).trim();
            }
            File dir = new File(System.getProperty("user.dir"));
            File[] listFiles = dir.listFiles();
            Objects.requireNonNull(listFiles);
            switch (resultArgs) {
                case "a":
                    Arrays.stream(listFiles).forEach(file -> System.out.println(file.getName()));
                    break;
                case "a C":
                    int columnsCount = (int) Math.sqrt(listFiles.length);
                    for (int i = 0, listFilesLength = listFiles.length; i < listFilesLength; i++) {
                        File listFile = listFiles[i];
                        System.out.print(listFile.getName());
                        System.out.print("\t\t");
                        if ((i+1) % columnsCount == 0) System.out.println();
                    }
                    break;
                case "l":
                    listWideFormat(listFiles, false);
                    break;
                case "l h":
                    listWideFormat(listFiles, true);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    private static void listWideFormat(File[] listFiles, boolean isHumanReadable) {
        Arrays.stream(listFiles)
                .forEach(file -> {
                    char r, w, x, t = 'f';
                    r = w = x = '-';
                    if (file.canRead())
                        r = 'r';
                    if (file.canWrite())
                        w = 'w';
                    if (file.canExecute())
                        x = 'x';
                    if (file.isDirectory())
                        t = 'd';

                    long length = 0;
                    try {
                        length = Files.size(file.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String humanReadableSize = "0B";
                    if (isHumanReadable && length > 0) {
                        Size temp = null;
                        for (Size value : Size.values()) {
                            if (value.min <= length) {
                                temp = value;
                            } else break;
                        }
                        double d = length / Math.pow(temp.min, temp.count);
                        humanReadableSize = d + temp.extension;
                    }
                    System.out.printf(" %c%c%c%c\t\t%s\t\t%ta %<tb %<td %<tR\t\t\t%s\n", t, r, w, x, isHumanReadable ? humanReadableSize : length, new Date(file.lastModified()), file.getName());
                });
    }
}
