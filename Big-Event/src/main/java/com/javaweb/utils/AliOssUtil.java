package com.javaweb.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AliOssUtil {
    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private static String endpoint = "https://oss-cn-chengdu.aliyuncs.com";

    // 填写Bucket名称，例如examplebucket。
    private static String bucketName = "baise212lz";
    // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
    private static String path = "big-event";


    private static String ACCESS_KEY_ID="你阿里云的账号";
    private static String ACCESS_KEY_SECRET="你阿里云密码";




    public static String upload(String objectName, InputStream fileInputStream)  {
        String url=null;

        OSS ossClientBuilder = new OSSClientBuilder().build(endpoint, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        try {
            // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
//            EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
//
//            // 创建OSSClient实例。
//            ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);
//            FileInputStream fileInputStream = new FileInputStream("D:\\Data\\b3.jpg");
//            byte[] bytes = fileInputStream.readAllBytes();
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path+"/"+objectName,fileInputStream);


            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件:https://buckName.区域节点/文件目录（可不写）/ObjectName
            PutObjectResult result = ossClientBuilder.putObject(putObjectRequest);
            url="https://"+bucketName+"."+endpoint.substring(endpoint.lastIndexOf("/")+1)+"/"+path+"/"+objectName;
            return url;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClientBuilder != null) {
                ossClientBuilder.shutdown();
            }
        }
        return null;
    }
}
