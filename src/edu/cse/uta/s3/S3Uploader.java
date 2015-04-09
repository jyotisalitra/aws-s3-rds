/**
 * Jyoti Salitra
 * UTA ID: ************
 * Cloud Computing (CSE - 6331) - David Levine
 * Programming Assignment # 3
 * Date: 10/12/2014
 */
package edu.cse.uta.s3;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

/**
 * 
 * References: 1.
 * http://docs.aws.amazon.com/AmazonS3/latest/dev/HLuploadFileJava.html
 */
public class S3Uploader {

	public static void main(String[] args) throws Exception {
		
		//bucket created via AWS Console
        String existingBucketName = "cse6331-bucket";
        
        //file names for the new files on AWSD S3
        String keyNames[]            = {"hd2013.csv", "us-pci.csv"};
        
        //local file paths for both the files
        String filePaths []           = {"./data/hd2013.csv", "./data/us-pci.csv"};
        
        //ACCESS_KEY_ID and SECRET_ACCESS_KEY for AWS S3
        String ACCESS_KEY_ID = "************";
        String SECRET_ACCESS_KEY = "************";
        
        //start the timer
        long start = System.currentTimeMillis();
        
        //create AWS credential using secret key and id
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
        
        //Use awsCredential to get an instance of TransferManager
        TransferManager tm = new TransferManager(awsCreds);      
        
        System.out.println("Starting AWS S3 upload.");
        
        // TransferManager processes all transfers asynchronously, 
        // so this call will return immediately.

        //upload hd2013.csv file
        Upload upload0 = tm.upload(existingBucketName, keyNames[0], new File(filePaths[0]));
        System.out.println("Uploading " + keyNames[0]);
        
        //upload us-pci.csv file
        Upload upload1 = tm.upload(existingBucketName, keyNames[1], new File(filePaths[1]));
        System.out.println("Uploading " + keyNames[1]);
        
        try {
        	//block and wait for the uploads to finish
        	upload0.waitForCompletion();
        	upload1.waitForCompletion();
        	System.out.println("Upload complete.");
        	
        	//calculate the time spent in uploading two csv files to AWS S3
        	long totalTime = System.currentTimeMillis() - start;
        	System.out.println("Total Time Taken: " + totalTime + " ms");
        	
        } catch (AmazonClientException amazonClientException) {
        	System.out.println("Unable to upload file, upload was aborted.");
        	amazonClientException.printStackTrace();
        }
    }
}
