package platform;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class Controllers {

    @GetMapping("/code")
    public String getCode() {
        return "<html>\n" +
                "<head>\n" +
                "    <title>Code</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<pre>\n" +
                "public static void main(String[] args) {\n" +
                "    SpringApplication.run(CodeSharingPlatform.class, args);\n" +
                "}</pre>\n" +
                "</body>\n" +
                "</html>";
    }

    @GetMapping("/api/code")
    public HashMap<String, String> getCodeAPI() {
        HashMap<String, String> map = new HashMap<>();
        map.put("code", "public static void main(String[] args) {\n" +
                "    SpringApplication.run(CodeSharingPlatform.class, args);\n" +
                "}");
        return map;
    }
}