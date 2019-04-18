package com.example.demo.test;

import com.example.demo.util.Constants;
import com.example.demo.util.FTPUtil;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class FileController {
    private String uploadDir = "test/";

    @RequestMapping("/file")
    public String file() {
        return "game_upload_list";
    }


    @RequestMapping(value = "/home")
    public String home() {
        System.out.println("redirect to home page!");
        return "index";
    }


    @RequestMapping(value = "/home/page")
    @ResponseBody
    public ModelAndView goHome() {
        System.out.println("go to the home page!");
        ModelAndView mode = new ModelAndView();
        mode.addObject("name", "zhangsan");
        mode.setViewName("index");
        return mode;
    }

    @RequestMapping(value = "/gameUpload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request, Model model) {
        String saveFilename = "";
        if (!multipartFile.isEmpty()) {
            //设置文件的保存路径      
            //filepath= Utils.format(filepath);
//            String filePath = request.getServletContext().getRealPath("/")+uploadDir+"/" + multipartFile.getOriginalFilename();
            String fileName = multipartFile.getOriginalFilename();
            if (!checkFile(fileName)) {
                return ResultUtil.getFailResult("文件类型错误,请上传安装游戏包(.APK)");
            }
            //得到上传文件的扩展名
            String fileExtName = fileName.substring(fileName.lastIndexOf("."));
            //得到文件保存的名称
            saveFilename = makeFileName(fileExtName);
//            String filePath = Constants.FX_APP_UPLOAD_TEP + saveFilename;
            String filePath = "D:\\usr\\" + multipartFile.getOriginalFilename();
            File files = new File(filePath);
//            if (!files.exists()) {
////                //创建临时目录
////                files.mkdir();
////            }
            System.out.println("打印文件保存位置：" + filePath);
            //转存文件

            try {
                multipartFile.transferTo(files);
                //到此为止，文件已经上传服务器成功

                //下一步是把文件上传到FTP服务器,与FTP文件服务器对接

                boolean ftpStatus = FTPUtil.uploadFile(files, uploadDir);
                //上传完之后，删除upload下面的文件
//                files.delete();
                //判断是否将文件上传FTP
                if (!ftpStatus) {
                    return ResultUtil.getFailResult("error,上传游戏包失败,请联系管理员!");
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("上传文件异常");
                return ResultUtil.getFailResult("error,上传异常，请重试");
            }
        }

        return ResultUtil.getSuccessResult(Constants.OEM_APP_URL + uploadDir + "/" + saveFilename);
    }

    //多文件上传
    @RequestMapping("/toFileUploadFiles")
    public String toUploadFiles() {
        return "fileUpload/fileUploadFiles";
    }

    @RequestMapping("fileUploadFiles")
    @ResponseBody
    //此处用@RequestParam（"xx"）来指定参数名，不加会报错
    public String uploadFiles(@RequestParam("multipartFile") MultipartFile[] multipartfiles) throws IOException {
        String savePath = "D:\\MultipartFile\\";
        if (multipartfiles != null && multipartfiles.length != 0) {
            if (null != multipartfiles && multipartfiles.length > 0) {
                //遍历并保存文件
                for (MultipartFile file : multipartfiles) {
                    file.transferTo(new File(savePath + file.getOriginalFilename()));
                }
            }
        }
        return "success";
    }


    /*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping(value = "act=springUpload")
    public Result springUpload(HttpServletRequest request) throws IllegalStateException, IOException {
        long startTime = System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    String path = "E:/springUpload" + file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                }

            }

        }
        long endTime = System.currentTimeMillis();
        System.out.println("方法三的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        return ResultUtil.getSuccessResult("success");
    }

    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    private boolean checkFile(String fileName) {
        //设置允许上传文件类型
        String suffixList = "apk";
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")
                + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     * 文件重命名
     * 避免中文和重名
     *
     * @param filename
     * @return
     */
    private String makeFileName(String filename) {  //2.jpg
        // 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UUID.randomUUID().toString().replaceAll("-", "") + filename;
    }

    /***
     * 避免一个文件夹文件太多，用hash算法打散存储
     * @param filename
     * @param savePath
     * @return
     */
    private String makePath(String filename, String savePath) {
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        int dir1 = hashcode & 0xf;  //0--15
        int dir2 = (hashcode & 0xf0) >> 4;  //0-15
        //构造新的保存目录
        String dir = savePath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if (!file.exists()) {
            //创建目录
            file.mkdirs();
        }
        return dir;
    }

    @Value("${ftp.server.ip}")
    String ftpIp;
    @Value("${ftp.user}")
     String ftpUser;
    @Value("${ftp.pass}")
    private  String ftpPass;
    @Value("${ftp.port}")
    String ftpPort;

    @RequestMapping("/quick")
    @ResponseBody
    public String quick() {
        return "springboot 访问成功! name=" + ftpIp + ",age=" + ftpPass;
    }
    @RequestMapping("/ex")
    @ResponseBody
    public String exceptionss() {
        int i = 10/0;
        return "springboot 访问成功! name=" + ftpIp + ",age=" + ftpPass;
    }
    
    @RequestMapping("/ge")
    @ResponseBody
    public Map geValid(@Valid AuthorizeIn authorizeIn, BindingResult ret){
        if (ret.hasFieldErrors()) {
            List<FieldError> errorList  = ret.getFieldErrors();
//            errorList.stream().forEach(item -> Assert.isTrue(false,item.getDefaultMessage()));
            Map<String,String> map = new HashMap<>();
            errorList.stream().forEach(item -> map.put("ss",item.getDefaultMessage()));
            return map;
        }
        return null;
    }
    
}