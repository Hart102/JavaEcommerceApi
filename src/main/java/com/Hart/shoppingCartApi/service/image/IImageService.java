package com.Hart.shoppingCartApi.service.image;

import com.Hart.shoppingCartApi.dto.ImageDto;
import com.Hart.shoppingCartApi.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById (Long id);
    void deleteImageBy (Long id);
    List<ImageDto> saveImages(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}

