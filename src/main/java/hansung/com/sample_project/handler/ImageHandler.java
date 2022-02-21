package hansung.com.sample_project.handler;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImageHandler {



    public static List<String> upload(List<MultipartFile> images){
        List<String> imagePath = new ArrayList<>();
        if(images == null)
            return null;
        for(MultipartFile image : images) {
            String path = getRootFolder();
            String fileName = LocalDateTime.now()  + "_" + String.valueOf(image.getOriginalFilename());

            try {
                image.transferTo(new File(path+fileName));
                imagePath.add("http://13.124.207.219:8080/sample_project/image/fileName");
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return imagePath;
    }

    private static String getRootFolder() throws IllegalStateException{
        String os = System.getProperty("os.name").toLowerCase();
        String rootFolder = "";
        if (os.contains("win")) {
            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [resourceInsertImage] : [os check]");
            System.out.println("[os] : " + "Windows");
            System.out.println("=======================================");
            System.out.println("\n");
//                folderRoot = "c:/Home/Resource/assets/"; //윈도우 경로 (디스크 필요)
            rootFolder = "C:/Users/bs860/IdeaProjects/images/"; //윈도우 경로 (디스크 필요)

        } else if (os.contains("linux")) {
            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [resourceInsertImage] : [os check]");
            System.out.println("[os] : " + "Linux");
            System.out.println("=======================================");
            System.out.println("\n");
//                folderRoot = "/Home/Resource/assets/"; //리눅스 경로
            rootFolder = "/capstone/sample_project_jhs/"; //리눅스 경로
        } else {
            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [resourceInsertImage] : [os check]");
            System.out.println("[os] : " + "None");
            System.out.println("=======================================");
            System.out.println("\n");
            throw new IllegalStateException("Can't decide Image Root Path");
        }
        return rootFolder;
    }
}
