package com.alex.wxmp.core.mail;

import java.util.Map;

public class Mail {

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

    public Mail(String name, String email,
                String subject,
                Map<String, Object> variables,
                String template) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.variables = variables;
        this.template = template;
    }


    public static Mail of(String name, String email,
                String subject,
                Map<String, Object> variables,
                String template) {

        return new Mail(name,email,subject,variables,template);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
