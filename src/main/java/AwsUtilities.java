import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by enbiya on 24.08.2016.
 */
public class AwsUtilities {


    public void createBucket(AmazonS3 s3, String bucketName) {

        if (s3.doesBucketExist(bucketName)) {
            System.out.println(bucketName + " isimli bir bucket zaten var!");
            return;
        }

        s3.createBucket(bucketName);

    }

    public void uploadFile2Bucket(AmazonS3 s3, String bucketName, File file){

        s3.putObject(new PutObjectRequest(bucketName, file.getName(), file));

    }

    public void downloadFileFromBucket(AmazonS3 s3, String bucketName, String fileName, String path ){

        S3Object object = s3.getObject(new GetObjectRequest(bucketName, fileName));

        //Dosyayı indir..
        try {

            BufferedInputStream input = new BufferedInputStream(object.getObjectContent());//..
            FileOutputStream output = new FileOutputStream(path + fileName);

            byte [] buffer = new byte[1024];
            int len;

            while ((len = input.read(buffer)) != -1){
                output.write(buffer, 0, len);
            }

            input.close();
            output.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteFile(AmazonS3 s3, String bucketName, String fileName){

        s3.deleteObject(bucketName, fileName);

    }

    public void listAllBuckets(AmazonS3 s3, String bucketName){

        System.out.println(addLine("BUCKET NAME", "FILE NAME", "STORAGE CLASS", "SIZE", "LAST MODIFIED"));

        for (S3ObjectSummary os : s3.listObjects(bucketName).getObjectSummaries()) {
            System.out.println(addLine(os.getBucketName(), os.getKey(), os.getStorageClass(), os.getSize()+"", os.getLastModified()+""));

        }
        System.out.println(addRow());

    }

    private String addLine(String... glnTxt){
        System.out.println(addRow());
        String line = "";

        for (String s : glnTxt ) {
            line+=s;
            for (int i = 0; i < (30 - s.length()); i++) {
                line += " ";
            }
            line += "| " ;

        }

        return "| " + line;
    }

    private String addRow(){
        String line = "+";

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 31; j++) {
                line += "-";
            }
            line += "+";
        }

        return line;
    }

    public void deleteBucket(AmazonS3 s3, String bucketName) {

        if (!s3.doesBucketExist(bucketName)) {
            System.out.println(bucketName + " böyle bir bucket yok!");
            return;
        }

        s3.deleteBucket(bucketName);

    }

}
