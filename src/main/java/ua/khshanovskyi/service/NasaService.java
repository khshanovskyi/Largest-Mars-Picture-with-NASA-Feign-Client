package ua.khshanovskyi.service;

import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import ua.khshanovskyi.entity.Photo;
import ua.khshanovskyi.feign.NasaFeign;

@Service
@RequiredArgsConstructor
public class NasaService {

    @Value("${nasa.key}")
    private String apiKey;
    private final NasaFeign nasaFeign;
    private final RestTemplate restTemplate;

    @Cacheable("nasa-largest-picture")
    public byte[] getLargestPicture(int sol, String camera) {
        return nasaFeign.getAllPhotos(apiKey, sol, camera)
          .photos()
          .parallelStream()
          .peek(this::setPhotoSize)
          .max(Comparator.comparing(Photo::getSize))
          .map(photo -> restTemplate.getForObject(photo.getUrl(), byte[].class))
          .orElseThrow(() -> new IllegalStateException("Cannot extract largest picture."));
    }

    private void setPhotoSize(Photo photo) {
        photo.setSize(restTemplate.headForHeaders(photo.getUrl()).getContentLength());
    }

}
