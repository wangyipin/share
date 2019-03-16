package com.ty.share.mailutil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

/**
 * @author 04637
 * @date 2018/12/12
 */
public class MailUtil {
    // TODO: 2019/3/16 补充各类注释
    private static final String HOST = "smtp.163.com";
    private static final Integer PORT = 25;
    private static final String USERNAME = "04637@163.com";
    private static final String PASSWORD = "jgt123";
    private static final String EMAIL_FROM = "04637@163.com";
    private static final JavaMailSenderImpl mailSender = createMailSender();
    private static final Logger logger = LogManager.getLogger();
    // TODO: 2019/3/16 补充多线程异步邮件发送

    /**
     * 邮件发送器
     *
     * @return 配置好的工具
     */
    // TODO: 2019/3/16 配置成静态代码块并解释
    private static JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "25000");
        p.setProperty("mail.smtp.auth", "false");
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 发送邮件
     *
     * @param to      接受人
     * @param subject 主题
     * @param html    发送内容
     */
    private static boolean sendHtmlMail(String to, String subject, String html) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(EMAIL_FROM, "DEVA");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(html, true);
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }

    }

    private MailUtil() {
    }


    public static void main(String[] args) {
        String html = "<strong style='color:red'>hello 王小明</strong><br><a href='http://localhost:8080/testMsgUser1.html'>进入王小明页面</a>";
        sendHtmlMail("04637@163.com", "hello wold", html);
    }
}