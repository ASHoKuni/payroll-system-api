package com.payroll.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.payroll.login.dto.ResetPassword;
import com.payroll.login.dto.User;
import com.payroll.login.repository.UserLoginRepository;



@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    // @Autowired
    // private JavaMailSender mailSender;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MongoOperations mongoOperations;
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User loginUser=userLoginRepository.findByEmail(email);
        if (loginUser==null) return null;
        String emailFound=loginUser.getEmail();
        String passwordFound=loginUser.getPassword();
        return new org.springframework.security.core.userdetails.User(emailFound,passwordFound,new ArrayList<>());
    }

    // public java.lang.Long getSeqNumber(String sequenceName){
    //     Query query =new Query(Criteria.where("id").is(sequenceName));
    //     Update update =new Update().inc("seq",1);
    //     Sequence counter = mongoOperations.findAndModify(query,update,options().returnNew(true).upsert(true),Sequence.class);

    //     return java.lang.Long.valueOf( !Objects.isNull(counter)?counter.getSeq():1);
    // }

    // public Boolean sendPasswordEmail(String email,String token){
    //     String from="sidmehtavines@gmail.com";
    //     String resetPasswordUrl = "http://localhost:4200/reset/"+token;
    //     String mailSubject = "Password Reset PtWikki";
    //     String mailContent=String.format("<a href=%s><strong>HELLO</strong></a>",resetPasswordUrl);
    //     if(sendEmail(from,email,mailSubject,mailContent)){
    //         return true;
    //     }
    //     return false;

    // }

    // public Boolean sendEmail(String from,String to,String subject,String content){
    //     try{
    //         MimeMessage message = mailSender.createMimeMessage();
    //         MimeMessageHelper helper = new MimeMessageHelper((message));
    //         helper.setFrom(from);
    //         helper.setTo(to);
    //         helper.setSubject(subject);
    //         helper.setText(content,true);
    //         mailSender.send(message);
    //         System.out.println("Email Sent");
    //     }catch (Exception e){
    //         e.printStackTrace();
    //         System.out.println("Email Error");
    //         return false;
    //     }
    //     return true;
    // }

    public String addResetToken(String email){
        User user = userLoginRepository.findByEmail(email);
        String resetToken = generateRandomChars();
        user.setPasswordResetToken(resetToken);
        user.setPasswordResetTokenDate(new Date(System.currentTimeMillis()+60*60*10*50));
        userLoginRepository.save(user);
        return resetToken;
    }

    public User verifyPasswordToken(String token){
        User user = userLoginRepository.findByPasswordResetToken(token);
        if(user.getPasswordResetTokenDate().after(new Date())){
            return user;
        }
        return null;
    }

    public Boolean resetPassword(ResetPassword resetPassword){
        try{
            String newEncodedPassword=bCryptPasswordEncoder.encode(resetPassword.getPassword());
            User user= userLoginRepository.findByPasswordResetToken(resetPassword.getToken());
            user.setPassword(newEncodedPassword);
            user.setPasswordResetTokenDate(new Date(System.currentTimeMillis()));
            userLoginRepository.save(user);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static String generateRandomChars() {
        String candidateChars="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length=30;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }
}