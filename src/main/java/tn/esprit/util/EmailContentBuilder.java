package tn.esprit.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import tn.esprit.entities.User;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class EmailContentBuilder {

    public static String buildDeleteNotificationEmail(User user) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setClassForTemplateLoading(EmailContentBuilder.class, "/");
            Template template = configuration.getTemplate("delete_notification_email_template.ftl");
            StringWriter stringWriter = new StringWriter();
            template.process(user.toMap(), stringWriter);
            return stringWriter.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Failed to build email content", e);
        }
    }
    public static String buildAccountChangeNotificationEmail(User user,int status) {
        Template template;
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setClassForTemplateLoading(EmailContentBuilder.class, "/");
            if(status==1)
            {
                 template = configuration.getTemplate("account_desactivated_email_template.ftl");
            }
            else{
                 template = configuration.getTemplate("account_activated_email_template.ftl");
            }
            Map<String, String> model = new HashMap<>();
            model.put("userName", user.getFirst_name() + " " + user.getLast_name());
            StringWriter stringWriter = new StringWriter();
            template.process(model, stringWriter);
            return stringWriter.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Failed to build email content", e);
        }
    }
    public static String buildResetPasswordEmailContent(String userEmail, String resetToken) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setClassForTemplateLoading(EmailContentBuilder.class, "/");
            Template template = configuration.getTemplate("resetPasswordEmailTemplate.ftl");

            Map<String, String> model = new HashMap<>();
            model.put("userEmail", userEmail);
            model.put("resetToken", resetToken);

            StringWriter stringWriter = new StringWriter();
            template.process(model, stringWriter);

            return stringWriter.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Failed to build email content", e);
        }
    }

}

