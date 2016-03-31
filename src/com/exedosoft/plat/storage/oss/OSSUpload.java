package com.exedosoft.plat.storage.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;

import com.aliyun.openservices.ClientConfiguration;
import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.exedosoft.plat.util.DOGlobals;

public class OSSUpload {

	private static String ACCESS_ID = DOGlobals.getValue("oss.access.id");
	private static String ACCESS_KEY = DOGlobals.getValue("oss.access.key");
	private static String OSS_ENDPOINT = DOGlobals.getValue("oss.endpoint");

	static {
		if (ACCESS_ID == null) {
			ACCESS_ID = "IhSzDz3fBlI9SPKY";
		}

		if (ACCESS_KEY == null) {
			ACCESS_KEY = "yPlMtVZkp0LZq4DdxemTKUPv3S3bUS";
		}

		if (OSS_ENDPOINT == null) {
			OSS_ENDPOINT = "http://oss.aliyuncs.com";
		}
	}

	/**
	 * @param args
	 */
	public static void upload(String key, String filePath) throws Exception {

		String bucketName = DOGlobals.getValue("oss.bucket.name");

		if (bucketName == null) {
			bucketName = "eeplatfile";
		}

		// String downloadFilePath = "d:/temp/网站首页2.png";

		// 可以使用ClientConfiguration对象设置代理服务器、最大重试次数等参数。
		ClientConfiguration config = new ClientConfiguration();
		OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY,
				config);

		try {
			File file = new File(filePath);
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentLength(file.length());

			System.out.println("正在上传...");
			client.putObject(bucketName, key, new FileInputStream(file),
					objectMeta);

			System.out.println("上传成功");

			// ///保存到数据中的路径，这个路径可以直接在浏览器中访问
			// String lujing = OSS_ENDPOINT + "/" + bucketName + "/" + key;
			//
			//
			// System.out.println("正在下载...");
			// downloadFile(client, bucketName, key, downloadFilePath);
		} finally {
			// deleteBucket(client, bucketName);
		}
	}

	/**
	 * @param args
	 */
	public static void upload(String key, FileItem aFileItem) throws Exception {

		String bucketName = DOGlobals.getValue("oss.bucket.name");

		if (bucketName == null) {
			bucketName = "eeplatfile";
		}

		// String downloadFilePath = "d:/temp/网站首页2.png";

		// 可以使用ClientConfiguration对象设置代理服务器、最大重试次数等参数。
		ClientConfiguration config = new ClientConfiguration();
		OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY,
				config);

		try {

			System.out.println("正在上传...");
			uploadFile(client, bucketName, key, aFileItem);

			System.out.println("上传成功");

			// ///保存到数据中的路径，这个路径可以直接在浏览器中访问
			// String lujing = OSS_ENDPOINT + "/" + bucketName + "/" + key;
			//
			//
			// System.out.println("正在下载...");
			// downloadFile(client, bucketName, key, downloadFilePath);
		} finally {
			// deleteBucket(client, bucketName);
		}
	}

	// 上传文件
	public static void uploadFile(String bucketName, String key,
			String contentType, int size, InputStream is) throws OSSException,
			ClientException, IOException {

		ClientConfiguration config = new ClientConfiguration();
		OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY,
				config);

		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(size);
		// 可以在metadata中标记文件类型
		if (contentType != null) {
			objectMeta.setContentType(contentType);
		}
		client.putObject(bucketName, key, is, objectMeta);

	}

	// 上传文件
	public static void uploadFile(String bucketName, String key,
			String contentType, File aFile) throws OSSException,
			ClientException, IOException {

		ClientConfiguration config = new ClientConfiguration();
		OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY,
				config);

		ObjectMetadata objectMeta = new ObjectMetadata();

		objectMeta.setContentLength(aFile.length());
		// 可以在metadata中标记文件类型
		if (contentType != null) {
			objectMeta.setContentType(contentType);
		}
		client.putObject(bucketName, key, new FileInputStream(aFile),
				objectMeta);

	}

	// 上传文件
	private static void uploadFile(OSSClient client, String bucketName,
			String key, FileItem fileItem) throws OSSException,
			ClientException, IOException {

		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(fileItem.getSize());
		// 可以在metadata中标记文件类型
		// objectMeta.setContentType("image/jpeg");
		client.putObject(bucketName, key, fileItem.getInputStream(), objectMeta);
	}

	// 下载文件
	private static void downloadFile(OSSClient client, String bucketName,
			String key, String filename) throws OSSException, ClientException {
		client.getObject(new GetObjectRequest(bucketName, key), new File(
				filename));
	}

	// 下载文件
	public static void downloadFile(String bucketName, String key,
			String filename) throws OSSException, ClientException {

		ClientConfiguration config = new ClientConfiguration();
		OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY,
				config);
		client.getObject(new GetObjectRequest(bucketName, key), new File(
				filename));
	}
}
