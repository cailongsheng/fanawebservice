package com.fana.utils;


import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class TrolleyCsv {

    public static void main(String[] args) throws IOException {


    }


    /**
     * 读取.csv文件
     * @param path 文件路径
     * @return
     */
    public List<String> readCsv(String path) {
        File file = new File(path);
        file.setReadable(true);//设置可读
        file.setWritable(true);//设置可写
        BufferedReader br = null;
        String line = "";
        String everyLine = "";
        List<String> list = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line=br.readLine())!=null){
                everyLine = line;
                list.add(everyLine);
//                System.out.println(everyLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<CsvRow> readCsv1(String path) throws IOException {
        File file = new File(path);
        CsvReader csvReader = new CsvReader();
        List<CsvRow> list = new ArrayList();
        CsvContainer csv = csvReader.read(file, StandardCharsets.UTF_8);
        for (CsvRow row : csv.getRows()) {
            //忽略第一行
            if (row.getOriginalLineNumber() != 1) {
                list.add(row);
            }
        }
        return list;
    }


}