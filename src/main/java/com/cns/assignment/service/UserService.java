package com.cns.assignment.service;

import com.cns.assignment.DTO.UpdateUserDTO;
import com.cns.assignment.DTO.UserDTO;
import com.cns.assignment.entity.User;
import com.cns.assignment.repositroty.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String PUBLIC_ATTACHMENT_PATH = "classpath:public\\attachments";

    private final UserRepository userRepository;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public User create(UserDTO userDTO) {
        User user = mapper.convertValue(userDTO, User.class);
        try {
            String passportFilePath = createAttachmentFile("passport", userDTO.getPassportFile());
            String userPhotoPath = createAttachmentFile("photo", userDTO.getUserPhoto());
            user.setPassportFilePath(passportFilePath);
            user.setUserPhotoPath(userPhotoPath);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("An exception occurred while trying to save user attachments", e);
        }
        return userRepository.save(user);
    }

    private String createAttachmentFile(String filePrefix, String fileContent) throws IOException, URISyntaxException {
        Resource resourceFolder = resourceLoader.getResource(PUBLIC_ATTACHMENT_PATH);
        Path resourcePath = Paths.get(resourceFolder.getURL().toURI());
        String[] parts = fileContent.split(",");
        String mimeType = parts[0].split(";")[0].split(":")[1];
        String base64Data = parts[1];
        String fileName = filePrefix + "_" + UUID.randomUUID() + "." + getExtensionFromMimeType(mimeType);
        File imageFile = new File(resourcePath.toFile(), fileName);
        if (imageFile.createNewFile()) {
            // Decode Base64 string to byte array
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            Files.write(imageFile.toPath(), decodedBytes);
        }
        return fileName;
    }

    private String getExtensionFromMimeType(String mimeType) {
        return switch (mimeType) {
            case "image/png" -> "png";
            case "image/jpeg" -> "jpg";
            default -> "dat"; // Default extension if MIME type is unknown
        };
    }

    public User update(Long id, UpdateUserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow();
        if (userDTO.getMobileNumber() != null && !userDTO.getMobileNumber().trim().isEmpty()) user.setMobileNumber(userDTO.getMobileNumber());
        if (userDTO.getEmail() != null && !userDTO.getEmail().trim().isEmpty()) user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}
