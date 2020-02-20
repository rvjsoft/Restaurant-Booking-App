package com.rvj.app.foodorder.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import com.box.sdk.BoxFile.ThumbnailFileType;
import com.rvj.app.foodorder.utils.AppConstants;

@Component
public class BoxClientService {
	
	private BoxAPIConnection api;
	private BoxFolder rootFolder;
	
	/*
	 * public void temp(String imageId) throws Exception { BoxAPIConnection api =
	 * new BoxAPIConnection("Q6su5BIDq5B9kp8Xglwq9LvfJptyPMey"); BoxFolder
	 * rootFolder = BoxFolder.getRootFolder(api); for (BoxItem.Info itemInfo :
	 * rootFolder) { System.out.format("[%s] %s\n", itemInfo.getID(),
	 * itemInfo.getName()); } FileInputStream stream = new
	 * FileInputStream(uploadProperties.getPath() + imageId +
	 * AppConstants.IMAGE_EXTENSION); BoxFile.Info newFileInfo =
	 * rootFolder.uploadFile(stream, "My File.txt"); stream.close(); }
	 */
	
	public BoxClientService() {
		api = new BoxAPIConnection("Q6su5BIDq5B9kp8Xglwq9LvfJptyPMey");
		rootFolder = BoxFolder.getRootFolder(api);
	}
	
	public String uploadImage(MultipartFile file, String fileName) throws IOException {
		try {
			BoxFile.Info newFileInfo = rootFolder.uploadFile(file.getInputStream(), fileName + AppConstants.IMAGE_EXTENSION);
			return newFileInfo.getID();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public byte[] getImage(String imageId) throws Exception {
		BoxFile file = new BoxFile(api, imageId);
		BoxFile.Info info = file.getInfo();
		byte[] bytes = null;
		/*
		 * FileOutputStream stream = new FileOutputStream(info.getName());
		 * file.download(stream); stream.flush(); bytes =
		 * Files.readAllBytes(Paths.get(info.getName()));
		 * Files.delete(Paths.get(info.getName()));
		 */
		bytes = file.getThumbnail(ThumbnailFileType.JPG, 320, 320, 320, 320);
		return bytes;
	}
	
}
