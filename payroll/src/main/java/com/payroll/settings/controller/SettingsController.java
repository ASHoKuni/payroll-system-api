package com.payroll.settings.controller;
import io.swagger.annotations.ApiOperation;

import com.payroll.login.dto.User;
import com.payroll.login.dto.UserResponseMessage;
import com.payroll.login.repository.UserLoginRepository;
import com.payroll.settings.dto.PictureUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Base64;
@RestController
public class SettingsController {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @ApiOperation(value = "updateProfilePhoto", notes = "Update profile photo of user", nickname = "updateProfilePhoto")
    @PostMapping("api/updateProfilePhoto")
    public UserResponseMessage updateProfilePhoto(@ModelAttribute PictureUpdate pictureUpdate) throws IOException{
        Long id = pictureUpdate.getId();
        MultipartFile file= pictureUpdate.getFile();
        Optional<User> user = userLoginRepository.findById(id);
        if(user.isPresent()){
            User user1=user.get();
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            System.out.println(" filename :"+ fileName);
            user1.setProfilPicture(Base64.getEncoder().encodeToString(file.getBytes()));
           // user1.setProfilePicture(Base64.getEncoder().encodeToString(file.getBytes()));
            userLoginRepository.save(user1);
            System.out.println(user1);
            return new UserResponseMessage("Update profile photo sucessfuly","200");
        }
        return new UserResponseMessage("Error Occured","500");
    }
    
    
}
