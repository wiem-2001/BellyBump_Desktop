package tn.esprit.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import tn.esprit.entities.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class EmailContentBuilder {
    private final static String Url="http://localhost:8000/";
    public static String buildVerificationEmail(String verificationCode) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassForTemplateLoading(EmailContentBuilder.class, "/");

        try {
            Template template = cfg.getTemplate("verification_email_template.ftl");

            Map<String, String> model = new HashMap<>();
            model.put("signedUrl", Url + verificationCode);
            //model.put("signedUrl", Url + verificationCode);

            StringWriter writer = new StringWriter();
            template.process(model, writer);
            Writer fileWriter = new FileWriter(new File("output.html"));
            try {
                template.process(model, fileWriter);
            } finally {
                fileWriter.close();
            }
            return writer.toString();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return null;
        }
    }
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
            // Add more data to the model as needed, depending on your template

            StringWriter stringWriter = new StringWriter();
            template.process(model, stringWriter);
            return stringWriter.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Failed to build email content", e);
        }
    }

}

