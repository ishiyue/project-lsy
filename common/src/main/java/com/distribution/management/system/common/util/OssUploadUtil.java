package com.distribution.management.system.common.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.*;
import java.util.Date;


/**
 * @author admin
 */
@Component
public class OssUploadUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(OssUploadUtil.class);
    @Value("${watt.oss.key}")
    private String key;
    @Value("${watt.oss.secret}")
    private String secret;
    @Value("${watt.oss.endpoint}")
    private String endpoint;
    @Value("${watt.oss.bucket}")
    private String bucket;
    @Value("${watt.oss.url}")
    private String url;

    /**
     * 上传文件
     * 前段自定义目录
     */
    public String upLoad(File file, String directory) {
        LOGGER.info("------OSS文件上传开始--------" + file.getName());
        String url = "";
        // 判断文件
        if (file == null) {
            return null;
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, key, secret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!ossClient.doesBucketExist(bucket)) {
                ossClient.createBucket(bucket);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
                ossClient.createBucket(createBucketRequest);
            }
            // 设置文件路径和名称
            String fileUrl = directory + "/" + LocalDate.now() + "/" + file.getName();
            url = "https://" + bucket + "." + endpoint + "/" + fileUrl;

            ossClient.generatePresignedUrl(bucket, key, getDateLaterMins(new Date(), 1));
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucket, fileUrl, file));
            // 设置权限(公开读)
            ossClient.setBucketAcl(bucket, CannedAccessControlList.PublicRead);
            if (result != null) {
                LOGGER.info("------OSS文件上传成功------" + fileUrl);
            }
        } catch (OSSException oe) {
            LOGGER.error(oe.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }

    /**
     * 通过文件名下载文件
     *
     * @param objectName    要下载的文件名
     * @param localFileName 本地要创建的文件名
     */
    public void downloadFile(String objectName, String localFileName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, key, secret);
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucket, objectName), new File(localFileName));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 文件流上传
     *
     * @param bos       保存了文件信息的流，将该流传入oss中
     * @param directory 保存到oss的文件目录
     * @param fileName  文件名
     * @return 文件在oss的地址
     * @throws Exception 异常抛出是因为调用该方法的导出接口需catch异常来判断如何设置下载记录的状态
     */
    public String upLoadByFileStream(ByteArrayOutputStream bos, String directory, String fileName) throws Exception {
        LOGGER.info("------OSS文件流上传开始--------" + fileName);
        String url = "";
        // 判断流是否为空
        if (bos == null) {
            LOGGER.error("OSS文件流上传，流为空");
            return null;
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, key, secret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!ossClient.doesBucketExist(bucket)) {
                ossClient.createBucket(bucket);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
                ossClient.createBucket(createBucketRequest);
            }
            // 设置文件路径和名称
            String fileUrl = directory + "/" + LocalDate.now() + "/" + fileName;
            url = "https://" + bucket + "." + endpoint + "/" + fileUrl;

            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucket, fileUrl, new ByteArrayInputStream(bos.toByteArray())));
            // 设置权限(公开读)
            ossClient.setBucketAcl(bucket, CannedAccessControlList.PublicRead);
            if (result != null) {
                LOGGER.info("------OSS文件流上传成功------" + fileUrl);
            }
        } catch (OSSException oe) {
            LOGGER.error(oe.getMessage());
            throw new Exception(oe);
        } finally {
            bos.close();
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }

    /**
     * @param fileUrl 需要删除的文件URL
     */
    public void deleteOssFile(String fileUrl) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, key, secret);
        try {
            fileUrl = fileUrl.replace(url, "");
            ossClient.deleteObject(bucket, fileUrl);
        } catch (Exception e) {
            LOGGER.error("OSS删除文件异常", e);
        } finally {
            ossClient.shutdown();
        }

    }


    public static LocalDateTime parseToLocalDateTime(Date date) {
        ZoneId zid = ZoneId.systemDefault();
        Instant ins = date.toInstant();
        return ins.atZone(zid).toLocalDateTime();
    }

    public static Date getDateLaterMins(Date date, long mins) {
        LocalDateTime localDateTime = parseToLocalDateTime(date);
        return parseToDate(localDateTime.plusMinutes(mins));
    }

    /**
     * LocalDateTime转Date对象
     *
     * @param localDateTime
     * @return
     */
    private static Date parseToDate(LocalDateTime localDateTime) {
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zid);
        return Date.from(zdt.toInstant());
    }
}