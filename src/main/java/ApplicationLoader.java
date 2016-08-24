import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by enbiya on 05.08.2016.
 */
public class ApplicationLoader {


    //mycrudbucket
    private static final String ACCESS_KEY = "my_access_key";
    private static final String SECRET_KEY = "mey_secret_key";
    private static final String bucketName = "mycrudbucket";

    private static AwsUtilities awsUtil;

    public static void main(String[] args) {

        AmazonS3 s3 = new AmazonS3Client(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
        s3.setRegion(Region.getRegion(Regions.US_WEST_2));

        awsUtil = new AwsUtilities();



        //1. bucket oluşturma
//        awsUtil.createBucket(s3, bucketName);

        //2. istenilen bucket'a dosya yükleme
//        awsUtil.uploadFile2Bucket(s3, bucketName, createTxt());

        //3. istenilen bucket'tan dosya indirme
//        awsUtil.downloadFileFromBucket(s3, bucketName, "tempFile.txt", "d:\\");

//        4. istenilen bucket'taki dosyayı silme
//        awsUtil.deleteFile(s3, bucketName, "tempFile.txt");


//        5. bucket içindeki dosyaları detaylı olarak listeleme
        awsUtil.listAllBuckets(s3, bucketName);


//        6. oluşturduğumuz bucket'ı silme
//        awsUtil.deleteBucket(s3, bucketName);


    }


    private static File createTxt() {

        File tmpFile = null;
        BufferedWriter bw;
        StringBuilder builder;

        try {

            tmpFile = new File("d:\\tempFile.txt");
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile, true), "UTF-8"));
            builder = new StringBuilder();


            if (!tmpFile.exists()) {
                tmpFile.createNewFile();
            }

            builder.append("Enbiya");
            builder.append(System.getProperty("line.separator"));
            builder.append("Tarih: " + (new SimpleDateFormat("YYYY.dd.MM - HH:mm:ss").format(new Date())).toString());

            bw.write(builder.toString());
            bw.close();
            tmpFile.deleteOnExit();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tmpFile;
    }

}
