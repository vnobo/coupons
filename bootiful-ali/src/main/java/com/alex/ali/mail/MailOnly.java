package com.alex.ali.mail;

import lombok.Data;

import java.util.Map;

@Data
public class MailOnly {

    // 显示名
    private String name;
    // 发送地址
    private String email;
    // 邮件标头
    private String subject;
    // 模板邮件参数
    private Map<String, Object> variables;
    // 模板邮件文件名
    private String template;

    public MailOnly(String name, String email,
                    String subject,
                    Map<String, Object> variables,
                    String template) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.variables = variables;
        this.template = template;
    }


    public static MailOnly of(String name, String email,
                              String subject,
                              Map<String, Object> variables,
                              String template) {

        return new MailOnly(name, email, subject, variables, template);
    }

}
