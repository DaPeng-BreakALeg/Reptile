package com.dapeng.reptile.service.impl;

import com.dapeng.reptile.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @ClassName: EmailServiceImpl
 * @Description: 邮件发送实现类
 * @author: DaPeng
 * @date: 2021年01月07日 下午3:13:01
 */
@Slf4j
@Service("emailService")
public class EmailServiceImpl implements EmailService {

	/**
	 * @fieldName: from
	 * @fieldType: String
	 * @Description: 邮件发送者邮箱
	 * @date: 2021年01月07日 下午5:25:10
	 */
	@Value("${spring.mail.from}")
	private String from;

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * @Title: sendAttachmentsManyMail
	 * @Description: TODO
	 * @param: to
	 * @param: subject
	 * @param: content
	 * @param: fileName
	 * @param: iss
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年01月11日 下午4:23:17
	 */
	@Override
	public void sendAttachmentsManyMail(String[] to, String subject, String content, String fileName,
			ByteArrayResource iss) {
		try {
			final MimeMessage message = mailSender.createMimeMessage();
			System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
			final MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);
			helper.addAttachment(fileName, iss, "application/vnd.ms-excel;charset=UTF-8");
			mailSender.send(message);
			log.error("发送附件邮件成功");
		} catch (MessagingException e) {
			log.error("发送附件邮件失败");
		}
	}

}
