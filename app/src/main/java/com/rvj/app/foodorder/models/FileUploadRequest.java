package com.rvj.app.foodorder.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileUploadRequest extends BaseRequest {
	
	private Long foodId;
	
	@NotNull(message = "file should not be empty/null")
	private MultipartFile file;

}
