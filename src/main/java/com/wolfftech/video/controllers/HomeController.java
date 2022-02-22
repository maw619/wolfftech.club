package com.wolfftech.video.controllers;

import com.wolfftech.video.model.Video;
import com.wolfftech.video.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class HomeController {
    public String UPLOAD_DIR = "src/main/resources/static/videos/";
    public String UPLOAD_DIR_IMG = "src/main/resources/static/images/";

    @Autowired
    private HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    public HomeController(){

    }
    @GetMapping
    public String home(Model model){
        model.addAttribute("listar", homeService.getAllVideos());
        return "index.html";
    }


    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("listar", homeService.getAllVideos());
        return "test.html";
    }

    @GetMapping("/update")
    public String updateform(@RequestParam("id") Long id, Model model){
        Optional<Video> video = homeService.getVideoById(id);
        model.addAttribute("video",video);
        return "admin/update.html";
    }

    @PostMapping("/processForm")
    public String updateVideo(@ModelAttribute("video") Video video,@RequestParam("file") MultipartFile file, @RequestParam("imgFile") MultipartFile imgFile){

        // check if file is empty

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String imageFileName = StringUtils.cleanPath(imgFile.getOriginalFilename());
        // save the file on the local file system
        video.setImageUrl("/images/"+imageFileName);
        video.setVideoUrl("/videos/"+fileName);

        video.setImageUrl("/images/"+imageFileName);
        video.setVideoUrl("/videos/"+fileName);
        try {
            Path path_img = Paths.get(UPLOAD_DIR_IMG + imageFileName);
            Files.copy(imgFile.getInputStream(), path_img, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(path_img);
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        homeService.updateVideo(video);
        return "/";
    }


    @GetMapping("/dashboard")
    public String goToDash(Model model){
        model.addAttribute("listar",homeService.getAllVideos());
        return "admin/index.html";
    }



    @GetMapping("/delete")
    public String deleteVideo(@RequestParam("id")Long id){
        homeService.deleteById(id);
        return "redirect:/dashboard";
    }





    @GetMapping("/showform")
    public String showForm(Model model){
        Video video = new Video();
        model.addAttribute("video", video);
        return "/add.html";
    }


    @PostMapping("/add")
    public String addVideo(@RequestParam("file") MultipartFile file, @RequestParam("imgFile") MultipartFile imgFile, RedirectAttributes attributes, @ModelAttribute("video") Video video) {
        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }
        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String imageFileName = StringUtils.cleanPath(imgFile.getOriginalFilename());
        // save the file on the local file system
        video.setImageUrl("/images/"+imageFileName);
        video.setVideoUrl("/videos/"+fileName);


        try {
            Path path_img = Paths.get(UPLOAD_DIR_IMG + imageFileName);
            Files.copy(imgFile.getInputStream(), path_img, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(path_img);
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        homeService.addVideo(video);
        return "/add";
    }

}
