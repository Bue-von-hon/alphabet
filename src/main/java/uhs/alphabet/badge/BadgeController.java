package uhs.alphabet.badge;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import uhs.alphabet.badge.domain.RankedBadgeRequest;
import uhs.alphabet.badge.domain.Website;
import uhs.alphabet.badge.students.domain.StudentNumber;

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
    public String stubadge(@RequestParam("stuid") @Valid @Size(min = 8, max = 8) String stuid) {
        return badgeService.getStudentBadgeById(StudentNumber.createByString(stuid));
    }

    @GetMapping(value = "/cfbadge", produces = "image/svg+xml")
    @Cacheable(cacheNames = "codeforcesCache")
    public String getCodeforcesBadge(
        @RequestParam("handle") @Valid @Size(min = 2, max = 24) String handle) {
        RankedBadgeRequest request = new RankedBadgeRequest(Website.CODEFORCES, handle);
        return badgeService.requestRankedBadge(request);
    }
}
