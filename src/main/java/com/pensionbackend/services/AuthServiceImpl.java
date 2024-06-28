package com.pensionbackend.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pensionbackend.dtos.AuthRequest;
import com.pensionbackend.dtos.AuthResponse;
import com.pensionbackend.dtos.PensionUserDto;
import com.pensionbackend.dtos.VerfRequest;
import com.pensionbackend.Util.JwtUtil;
import com.pensionbackend.entities.PensionUser;
import com.pensionbackend.enums.Role;
import com.pensionbackend.repositories.PensionUserRepository;
import com.pensionbackend.tfauth.Tfa;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

import java.util.logging.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthServiceImpl  implements AuthService{
    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

       private final PensionUserRepository pensionuserRepository;
         private final Tfa tfaService;
              public final JwtUtil jwtUtil;
                private final AuthenticationManager authenticationManager;
                private final EmailSendDoc emailSenddoc;

    public AuthServiceImpl(PensionUserRepository pensionuserRepository,Tfa tfaService,JwtUtil jwtUtil,AuthenticationManager authenticationManager, EmailSendDoc emailSenddoc) {
        this.pensionuserRepository = pensionuserRepository;
        this.tfaService = tfaService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.emailSenddoc = emailSenddoc;


    }

    @PostConstruct
    public void createAdminAccount() {
        PensionUser adminAccount = pensionuserRepository.findByRole(Role.ADMIN);
        if (adminAccount==null) {
            PensionUser admin = new PensionUser();
            admin.setEmail("admin123@gmail.com");
            admin.setFirstName("admin");
            admin.setLastName("admin");
      
            admin.setPassword(new BCryptPasswordEncoder().encode("admin123")); 
            admin.setRole(Role.ADMIN);
            pensionuserRepository.save(admin);
        }
    }
    @Override
   public PensionUserDto createUser(PensionUser userData) {
    PensionUser user = new PensionUser();
    user.setFirstName(userData.getFirstName());
    user.setLastName(userData.getLastName());

    user.setEmail(userData.getEmail());
    user.setPassword(new BCryptPasswordEncoder().encode(userData.getPassword()));
    user.setRole(userData.getRole());
    user.setPhoneNumber(userData.getPhoneNumber());
   
    user.setMfaEnabled(userData.isMfaEnabled());

    if (userData.isMfaEnabled()) {
        String secret = tfaService.generateSecret();
        user.setSecret(secret);
    }

    PensionUser createdUser = pensionuserRepository.save(user);

 
    
    PensionUserDto createdUserDto = new PensionUserDto();
    createdUserDto.setId(createdUser.getId());
    createdUserDto.setFirstName(createdUser.getFirstName());
    createdUserDto.setLastName(createdUser.getLastName());
  
    createdUserDto.setEmail(createdUser.getEmail());

    if (userData.isMfaEnabled()) {
        String qrCodeUri = tfaService.generateQrCodeImageUri(user.getSecret());
          
         
        createdUserDto.setQrCodeUri(qrCodeUri);
   


    }
  
       sendPensionFile(createdUser.getEmail());
    

    
    return createdUserDto;
}


private void sendPensionFile(String toEmail) {
    try {
        String pdfFilePath = "src/main/java/com/pensionbackend/Files/dwp022.pdf";
        ;

        File pdfFile = new File(pdfFilePath);

        if (!pdfFile.exists()) {
            throw new FileNotFoundException("PDF file not found: " + pdfFilePath);
        }

        MultipartFile pensionFile = convertFileToMultipartFile(pdfFile);

        String subject = "Your Pension Documents";
        MultipartFile[] pensionFiles = { pensionFile };
        emailSenddoc.sendPensionFile(toEmail, subject, pensionFiles);
    } catch (IOException e) {
        throw new RuntimeException("Failed to send pension file", e);
    }
}

private MultipartFile convertFileToMultipartFile(File file) throws IOException {
    FileInputStream input = new FileInputStream(file);
    return new MockMultipartFile("file", file.getName(), "application/pdf", IOUtils.toByteArray(input));
}


@Override
public AuthResponse verifyCode(VerfRequest verificationRequest) {
    PensionUser user = getUserByEmail(verificationRequest.getEmail());
    validateOtp(user.getSecret(), verificationRequest.getCode());
    
    String jwtToken = generateJwtToken(user.getEmail());
    return AuthResponse.builder()
            .jwt(jwtToken)
            .userRole(user.getRole())
            .userId(user.getId())
            .mfaEnabled(user.isMfaEnabled())
            .build();
}


private PensionUser getUserByEmail(String email) {
    return pensionuserRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format("No user found with email: %s", email)));
}

private void validateOtp(String secret, String code) {
    if (tfaService.isOtpInvalid(secret, code)) {
        logger.info("Wrong OTP");
        throw new BadCredentialsException("Invalid OTP code");
    }
}

private String generateJwtToken(String voterId) {
    return jwtUtil.generateToken(voterId);
}

@Override
public void deleteUserById(Long userId) {
    Optional<PensionUser> userOptional = pensionuserRepository.findById(userId);
    if (userOptional.isPresent()) {
        PensionUser user = userOptional.get();
        pensionuserRepository.delete(user);
    } else {
        throw new EntityNotFoundException("User not found with ID: " + userId);
    }

  
}



@Override
public List<PensionUser> listAllUsers() {
    return pensionuserRepository.findAll();
}












    
}
