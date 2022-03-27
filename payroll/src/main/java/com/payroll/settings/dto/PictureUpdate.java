package com.payroll.settings.dto;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document
@Data
public class PictureUpdate {

    private MultipartFile file;
    private Long id;
}
