package com.payroll.login.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import com.payroll.login.dto.ResetPassword;
import com.payroll.login.dto.ResponseMessage;
import com.payroll.login.dto.Role;
import com.payroll.login.dto.User;
import com.payroll.login.dto.UserAuth;
import com.payroll.login.dto.UserResponseMessage;
import com.payroll.login.repository.RoleRepository;
import com.payroll.login.repository.UserLoginRepository;
import com.payroll.login.service.SequenceService;
import com.payroll.login.service.UserService;
import com.payroll.login.utils.JwtUtil;

@RestController
public class LoginController {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SequenceService seqService;

    @Autowired
    private RoleRepository roleRepository;
    
    @ApiOperation(value = "allUser", notes = "Get All User data", nickname = "all")
    @GetMapping("/api/allUser")
    public List<User> getAll(){
        
        return userLoginRepository.findAll();
    }

    @ApiOperation(value = "getLoginUserDetails", notes = "Get the logged in User", nickname = "getLoginUserDetails")
    @GetMapping("/api/getLoginUserDetails")
    public User getLoginUserDetails(@RequestHeader("Authorization") String token){
        String jwttoken=token.substring(7);
        User loggedInUser=userLoginRepository.findByEmail(jwtUtil.extractUsername(jwttoken));
        return loggedInUser;
    }

    @ApiOperation(value = "auth", notes = "Authenticate User", nickname = "auth")
    @PostMapping("/api/auth")
    public UserResponseMessage authenticateUser(@RequestBody UserAuth userAuth){
        String email = userAuth.getEmail().toLowerCase();
        String password=userAuth.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        } catch (Exception e){
            return new UserResponseMessage("BAD CREDENTIALS",null);
        }
        UserDetails loadedUser = userService.loadUserByUsername(email);
        String token= jwtUtil.generateToken(loadedUser);
        return new UserResponseMessage("SUCCESS",token);
    }

    @ApiOperation(value = "forgotPassword", notes = "Verify Forgot password token", nickname = "forgotPassword")
    @GetMapping("/api/forgotPassword")
    public User verifyToken(@RequestParam("token")String token){
        return userService.verifyPasswordToken(token);
    }

    @ApiOperation(value = "resetPassword", notes = "Reset Password of the User", nickname = "resetPassword")
    @PostMapping("/api/resetPassword")
    public ResponseEntity<String> resetToken(@RequestBody ResetPassword resetPasswordObject){
        if(userService.resetPassword(resetPasswordObject)){
            System.out.println("HEREEE");
            return new ResponseEntity<>("Changed",HttpStatus.OK);
        }
        return new ResponseEntity<>("Error",HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "createUser", notes = "Create a New User", nickname = "createUser")
    @PostMapping("/api/createUser")
    public UserResponseMessage createUser(@RequestBody User user){
        try {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setId(Long.valueOf( seqService.getSeqNumber("user")));
            userLoginRepository.insert(user);
            return new UserResponseMessage("SUCCESS", "200");
        }catch (Exception e) {
            return new UserResponseMessage("Error",e.getMessage());
        }
    }

    @ApiOperation(value = "getRoles", notes = "get list of all roles", nickname = "getRoles")
    @GetMapping("api/getRoles")
    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    
    @ApiOperation(value = "createRole", notes = "Create a New Role", nickname = "createRole")
    @PostMapping("/api/createRole")
    public UserResponseMessage createRole(@RequestBody Role role){
        try {
            roleRepository.insert(role);
            return new UserResponseMessage("SUCCESS", "200");
        }catch (Exception e) {
            return new UserResponseMessage("Error",e.getMessage());
        }
    }

    @ApiOperation(value = "updateUserProfile", notes = "Edit updateUserProfile record", nickname = "updateUserProfile")
    @PostMapping("/api/updateUserProfile")
    public UserResponseMessage updateProfile(@RequestParam Long id,User user) {
        Optional<User> userdata = userLoginRepository.findById(id);
        if(userdata.isPresent()){
        	User user1 = userdata.get();
            user1.setFirstName(user.getFirstName());
            user1.setMiddleName(user.getMiddleName());
            user1.setLastName(user.getLastName());
            user1.setEmail(user.getEmail());
            user1.setContactNo(user.getContactNo());
            user1.setUserName(user.getUserName());
            userLoginRepository.save(user1);
            return new UserResponseMessage("User Profile update","200");
        }
        else {
          
          return new UserResponseMessage("User not found","500");
        }
        
      }


      @ApiOperation(value = "updateRole", notes = "Edit Role record", nickname = "updateRole")
      @PostMapping("/api/updateRole")
      public UserResponseMessage updateRole(@RequestParam Long id,Role role) {
          Optional<Role> roledata = roleRepository.findById(id);
          if(roledata.isPresent()){
              Role role1 = roledata.get();
              role1.setRoleName(role.getRoleName());
              role1.setRoleDescription(role.getRoleDescription());
              roleRepository.save(role1);
              return new UserResponseMessage("role update","200");
          }
          else {
            
            return new UserResponseMessage("role not found","500");
          }
          
        }
      

    @ApiOperation(value = "deleteUser", notes = "Delete a User record ", nickname = "deleteUser")
    @PostMapping("/api/deleteUser")
    public ResponseMessage deleteUser(@RequestParam Long id) {
        Optional<User> userdata = userLoginRepository.findById(id);
        if(userdata.isPresent()){
            userLoginRepository.deleteById (id);
            return new ResponseMessage("user deleted succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("user not found",HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "deleteRole", notes = "Delete a Role record ", nickname = "deleteRole")
    @PostMapping("/api/deleteRole")
    public ResponseMessage deleteRole(@RequestParam Long id) {
        Optional<Role> roledata = roleRepository.findById(id);
        if(roledata.isPresent()){
            roleRepository.deleteById (id);
            return new ResponseMessage("role deleted succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("role not found",HttpStatus.NOT_FOUND);
    }

    
}
