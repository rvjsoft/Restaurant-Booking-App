package com.rvj.app.foodorder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "foodorder.file")
@Component
public class ImageUploadProperties {

	public String path;
}
