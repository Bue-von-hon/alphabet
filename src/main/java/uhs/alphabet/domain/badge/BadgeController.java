package uhs.alphabet.domain.badge;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uhs.alphabet.domain.dto.PersonDto;
import uhs.alphabet.domain.service.PersonService;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BadgeController {
    private final PersonService personService;

    @GetMapping(value = "/stubadge")
    public String a(@RequestParam("stuid") String stuid) {
        List<PersonDto> personDtos = personService.searchPerson(stuid);
        String handle = "None";
        String name = "None";
        if (!personDtos.isEmpty()) {
            handle = personDtos.get(0).getHandle();
            name = personDtos.get(0).getName();
        }
        return StuBadge.of2(name, handle);
    }

    @GetMapping("/cfbadge")
    public String b(@RequestParam("handle") String handle) {
        CfUser cfUser = CfUser.of(handle);
        return CfBadge.of(cfUser.getHandle(), cfUser.getColor());
    }
}
