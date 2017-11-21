package com.websocket.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class MailSenderController {
    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private HttpServletRequest request;

    @Value("${spring.mail.username}")
    private String sender;

    @RequestMapping("/simpleSendMail/test")
    public String sendMail(){
        SimpleMailMessage message = new SimpleMailMessage();
        //发送者
        message.setFrom(sender);
        //接收者
        message.setTo("1807401232@qq.com");
        message.setSubject("测试邮件（邮件主题）");
        message.setText("这是邮件内容");
        javaMailSender.send(message);
        return "ok";
    }

    @RequestMapping("/sendMail")
    public ModelAndView sendMailWithFile(@RequestParam("file")MultipartFile multipartFile)throws MessagingException,IOException{
        if (!multipartFile.isEmpty()){
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

            helper.setFrom(sender);
            helper.setTo("1807401232@qq.com");
            helper.setSubject("测试附件（邮件主题）");
            helper.setText("这是邮件内容，有附件哦。。。");
            String path = request.getSession().getServletContext().getRealPath("/");
            System.out.println("转存问价路径："+path);
            System.out.println("文件名："+multipartFile.getOriginalFilename());
            System.out.println("文件类型："+multipartFile.getContentType());
            File file = new File(path,multipartFile.getOriginalFilename());
            //转存文件
            multipartFile.transferTo(file);
            FileSystemResource fileSystemResource = new FileSystemResource(file);
//            helper.addAttachment("附件",fileSystemResource,multipartFile.getContentType());
            helper.addAttachment(multipartFile.getOriginalFilename(),fileSystemResource);
            javaMailSender.send(mimeMessage);
            System.out.println("发送结束。。。");
            return new ModelAndView("redirect:/sendMailWithFile");
        }else {
            return new ModelAndView("redirect:/sendMailWithFile");
        }

    }
}
