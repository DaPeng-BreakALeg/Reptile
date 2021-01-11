package com.dapeng.reptile.service;

import org.springframework.core.io.ByteArrayResource;

/**
 * @ClassName: EmailService
 * @Description: 邮件发送业务层
 * @author: DaPeng
 * @date: 2021年01月07日 下午3:11:50
 */
public interface EmailService {

	void sendAttachmentsManyMail(String[] to, String subject, String content, String fileName, ByteArrayResource iss);

}
