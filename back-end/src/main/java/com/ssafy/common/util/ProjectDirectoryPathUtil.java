package com.ssafy.common.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/*
 * 프로젝트 경로 얻어내기 위한 유틸 클래스
 */
public class ProjectDirectoryPathUtil {

    public static String getProfileImageDirectoryPath() {
        // 현재 프로젝트 경로
        String projectPath = System.getProperty("user.dir");
        // 폴더 경로
        String directoryPath =
                "src" + File.separator + // File.seperator는 OS종속적
                        "main" + File.separator +
                        "resources" + File.separator +
                        "static" + File.separator +
                        "profile_img";

        return projectPath + File.separator + directoryPath;
    }

    public static String getProfileImagePath(String fileName) {
        return getProfileImageDirectoryPath() + File.separator + fileName;
    }


}
