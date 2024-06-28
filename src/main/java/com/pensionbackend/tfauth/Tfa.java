package com.pensionbackend.tfauth;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

@Service
@Slf4j
public class Tfa {

    private static final Logger logger = Logger.getLogger(Tfa.class.getName());
    private final CodeGenerator codeGenerator;
    private final QrGenerator qrGenerator;

    public Tfa() {
        this.codeGenerator = new DefaultCodeGenerator();
        this.qrGenerator = new ZxingPngQrGenerator();
    }

    public String generateSecret() {
        DefaultSecretGenerator secretGenerator = new DefaultSecretGenerator();
        return secretGenerator.generate();
    }

    public String generateQrCodeImageUri(String secret) {
        QrData qrData = buildQrData(secret);

        byte[] qrCodeImageData;
        try {
            qrCodeImageData = qrGenerator.generate(qrData);
        } catch (QrGenerationException e) {
            log.error("Error while generating QR code", e);
            throw new RuntimeException("Error while generating QR code", e);
        }

        return getDataUriForImage(qrCodeImageData, qrGenerator.getImageMimeType());
    }

    public boolean verifyOtp(String secret, String code) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        
        CodeVerifier codeVerifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        return codeVerifier.isValidCode(secret, code);
    }

    public boolean isOtpInvalid(String secret, String code) {
        return !verifyOtp(secret, code);
    }

    private QrData buildQrData(String secret) {
        return new QrData.Builder()
                .label("Pension Authenticator")
                .secret(secret)
                .issuer("Pension")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();
    }
}
