package uhs.alphabet.domain.badge;

import lombok.RequiredArgsConstructor;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
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
    private final CacheManager cacheManager;

    private boolean isValidCFUser(CfUser user) {
        if (user == null) return false;
        return true;
    }
    @GetMapping(value = "/stubadge", produces = "image/svg+xml")
    public String stubadge(@RequestParam("stuid") String stuid) {
        List<PersonDto> personDtos = personService.searchPerson(stuid);
        String handle = "None";
        String name = "None";
        if (!personDtos.isEmpty()) {
            handle = personDtos.get(0).getHandle();
            name = personDtos.get(0).getName();
        }
        return StuBadge.of1(name, handle);
    }

    @GetMapping(value = "/cfbadge", produces = "image/svg+xml")
    public String cfbadge(@RequestParam("handle") String handle) {
        Cache<String, CfUser> codeforcesCache = cacheManager.getCache("codeforcesCache", String.class, CfUser.class);
        CfUser cfUser = codeforcesCache.get(handle);
        if (isValidCFUser(cfUser)) return CfBadge.of(cfUser.getHandle(), cfUser.getColor());

        cfUser = CfUser.of(handle);
        codeforcesCache.put(handle, cfUser);
        return CfBadge.of(cfUser.getHandle(), cfUser.getColor());
    }
}
