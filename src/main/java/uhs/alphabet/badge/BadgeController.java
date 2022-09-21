package uhs.alphabet.badge;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
@Validated
public class BadgeController {
    private final BadgeService badgeService;

    @ExceptionHandler
    public String CodeforcesRequestFail(RestClientException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    public String InvalidUser(ConstraintViolationException e) {
        return e.getMessage();
    }
    @GetMapping(value = "/stubadge", produces = "image/svg+xml")
    public String stubadge(@RequestParam("stuid") String stuid) {
        StudentBadgeUser user = badgeService.searchStudent(stuid);
        if (user == null) return StuBadge.of1("None", "None");
        return StuBadge.of1(user.getName(), user.getHandle());
    }

    @GetMapping(value = "/cfbadge", produces = "image/svg+xml")
    @Cacheable( cacheNames = "codeforcesCache")
    public String getCodeforcesBadge(@RequestParam("handle") @Valid @Size(min = 2, max = 24) String handle) {
        String badge = badgeService.getCodeforcesBadge(handle);
        return badge;
    }
}
