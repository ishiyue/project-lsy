package com.distribution.management.system.common.util;

import org.apache.poi.util.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author water
 * @desc 文件工具类
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private final static String CSV_FILE = ".csv";
    private final static String XLS_FILE = ".xls";
    private final static String XLSX_FILE = ".xlsx";

    /**
     * 根据地址获得数据的输入流
     *
     * @param fileUrl 网络连接地址
     * @return url的输入流
     */
    public static InputStream getInputStreamByUrl(String fileUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(fileUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), output);
            return new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据文件名获取文件类型，2:csv 3:excel， -1未知
     *
     * @param fileName
     * @return
     */
    public static Integer getFileType(String fileName) {
        fileName = fileName.toLowerCase();
        if (fileName.endsWith(CSV_FILE)) {
            return 2;
        } else if (fileName.endsWith(XLS_FILE) || fileName.endsWith(XLSX_FILE)) {
            return 3;
        } else {
            return -1;
        }
    }

    public static Set<Integer> getFileType(List<String> fileNames) {
        Set<Integer> set = new HashSet<>();
        for (String fileName : fileNames) {
            set.add(getFileType(fileName));
        }
        return set;
    }

    public static Set<Integer> getFileType(MultipartFile[] files) {
        Set<Integer> set = new HashSet<>();
        for (MultipartFile file : files) {
            set.add(getFileType(file.getOriginalFilename()));
        }
        return set;
    }

    public static List<String> getFileName(MultipartFile[] files) {
        List<String> fileNames = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            fileNames.add(file.getOriginalFilename());
        }
        return fileNames;
    }


}
