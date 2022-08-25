package platform;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@RestController
public class Controllers {

    final String form = "yyyy/MM/dd HH:mm:ss";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(form);

    String main = "main()";
    String date = "2020/01/01 12:00:03";

    @GetMapping("/code")
    public String getCode() {
        return "<html>\n" +
                "<head>\n" +
                "    <title>Code</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<span id=\"load_date\">\n" +
                date +
                "</span>\n" +
                "<pre id=\"code_snippet\">\n" +
                main +
                "</pre>\n" +
                "</body>\n" +
                "</html>";
    }

    @GetMapping("/api/code")
    @ResponseBody
    public HashMap<String, String> apiCode() {
        HashMap<String, String> map = new HashMap<>();
        map.put("code", main);
        map.put("date", date);
        return map;
    }

    @GetMapping("/code/new")
    public String codeNew() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:th=\"https://www.thymeleaf.org/\">\n" +
                "<head>\n" +
                "    <title>Create</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<textarea id=\"code_snippet\"> ... </textarea>\n" +
                "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>\n" +
                "</body>\n" +
                "</html>";
    }

    @PostMapping("/api/code/new")
    public ResponseEntity<EmptyJsonBody> postCode(@RequestBody Json json) {
        main = json.getCode();
        date = LocalDateTime.now().format(dtf);
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
        return responseBuilder.body(new EmptyJsonBody());
    }

    @JsonSerialize
    private static class EmptyJsonBody {
    }
}