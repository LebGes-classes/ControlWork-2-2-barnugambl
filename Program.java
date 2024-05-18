package ControlWork2;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class Program {

    private String channel;

    private BroadcastsTime time;

    private String name;

    public Program(String channel, BroadcastsTime time, String name) {
        this.channel = channel;
        this.time = time;
        this.name = name;
    }
    public Program(){

    }

    public List<Program> getAllPrograms(Map<BroadcastsTime, List<Program>> programMap) { // 4 пункт
        List<Program> allPrograms = new ArrayList<>();
        for (List<Program> programs : programMap.values()) {
            allPrograms.addAll(programs);
        }
        return allPrograms;
    }


    public void printSortedPrograms(List<Program> programs) { // 5 пункт
        programs.sort(Comparator.comparing(Program::getChannel).thenComparing(Program::getTime));
        for (Program program : programs) {
            System.out.println(program.getChannel() + " " + program.getTime().getHour() + ":" + program.getTime().getMinute() + " " + program.getName());
        }
    }

    public void printCurrentPrograms(List<Program> programs) { // 6 пункт
        LocalTime currentTime = LocalTime.now();
        for (Program program : programs) {
            if (currentTime.getHour() == program.getTime().getHour() && currentTime.getMinute() == program.getTime().getMinute()) {
                System.out.println(program.getChannel() + " " + program.getTime().getHour() + ":" + program.getTime().getMinute() + " " + program.getName());
            }
        }
    }

    public List<Program> findProgramsByName(List<Program> programs, String name) { // 7 пункт
        List<Program> foundPrograms = new ArrayList<>();
        for (Program program : programs) {
            if (program.getName().equals(name)) {
                foundPrograms.add(program);
            }
        }
        return foundPrograms;
    }

    public void printCurrentChannelPrograms(List<Program> programs, String channel) { // 8 пункт
        LocalTime currentTime = LocalTime.now();
        for (Program program : programs) {
            if (program.getChannel().equals(channel) && currentTime.getHour() == program.getTime().getHour() && currentTime.getMinute() == program.getTime().getMinute()) {
                System.out.println(program.getChannel() + " " + program.getTime().getHour() + ":" + program.getTime().getMinute() + " " + program.getName());
            }
        }
    }
    public void findChannelProgramsInTimeRange(List<Program> allPrograms, String channel, BroadcastsTime startTime, BroadcastsTime endTime) { // 9 пункт
        System.out.println("Программы канала " + channel + " в промежутке времени с " + startTime + " до " + endTime + ":");

        for (Program program : allPrograms) {
            if (program.getChannel().equals(channel) && isProgramInTimeRange(program.getTime(), startTime, endTime)) {
                System.out.println(program.getName() + " (" + program.getTime() + ")");
            }
        }
    }

    private boolean isProgramInTimeRange(BroadcastsTime programTime, BroadcastsTime startTime, BroadcastsTime endTime) { // вспомогательный метод для поиск времени в определеном диапозоне
        return programTime.compareTo(startTime) >= 0 && programTime.compareTo(endTime) <= 0;
    }

    public void saveToExcel(List<Program> programs, String fileName) { // 10 пункт
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("программа");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("канал");
        headerRow.createCell(1).setCellValue("время");
        headerRow.createCell(2).setCellValue("название");
        int rowNum = 1;
        for (Program program : programs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(program.getChannel());
            row.createCell(1).setCellValue(program.getTime().getHour()
                    + ":" + program.getTime().getMinute());
            row.createCell(2).setCellValue(program.getName());
        }

        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public String getChannel() {
        return channel;
    }

    public BroadcastsTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
