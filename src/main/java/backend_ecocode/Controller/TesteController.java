package backend_ecocode.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    @GetMapping("/oi")
    public String oi() {
        return "O back tá vivo!";
    }
}