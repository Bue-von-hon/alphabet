package uhs.alphabet.badge;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeService badgeService;

    @GetMapping(value = "/stubadge", produces = "image/svg+xml")
    public String stubadge(@RequestParam("stuid") String stuid) {
        StudentBadgeUser user = badgeService.searchStudent(stuid);
        if (user == null) return StuBadge.of1("None", "None");
        return StuBadge.of1(user.getName(), user.getHandle());
    }

    @GetMapping(value = "/cfbadge", produces = "image/svg+xml")
    public String cfbadge(@RequestParam("handle") String handle) {
        String badge = badgeService.makeCodeforcesBadge(handle);
        return badge;
    }
}
