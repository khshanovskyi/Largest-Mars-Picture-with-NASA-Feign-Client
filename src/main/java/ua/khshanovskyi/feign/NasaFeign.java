package ua.khshanovskyi.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.khshanovskyi.entity.Photos;

@FeignClient(name = "nasa-client", url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos")
public interface NasaFeign {

    @GetMapping
    Photos getAllPhotos(@RequestParam(value = "api_key", required = false, defaultValue = "DEMO_KEY") String apiKey,
                        @RequestParam int sol,
                        @RequestParam(required = false) String camera);
}
