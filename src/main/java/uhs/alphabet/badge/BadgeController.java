package uhs.alphabet.badge;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeService badgeService;
    @GetMapping(value = "/stubadge", produces = "image/svg+xml")
    public String stubadge(@RequestParam("stuid") String stuid) {
        return badgeService.getStudentBadgeByHandle(stuid);
    }

    @GetMapping(value = "/cfbadge", produces = "image/svg+xml")
    @Cacheable( cacheNames = "codeforcesCache")
    public String getCodeforcesBadge(@RequestParam("handle") String handle) {
        String badge = badgeService.makeCodeforcesBadge(handle);
        return badge;
    }
}
