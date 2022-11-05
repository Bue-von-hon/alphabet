package uhs.alphabet.badge;

import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uhs.alphabet.badge.adapter.Codeforces900GreyNewbieRankWebSite;
import uhs.alphabet.badge.adapter.CodeforcesBadgeFileFixture;
import uhs.alphabet.badge.application.RankWebSite;
import uhs.alphabet.badge.application.RankedBadgeFile;
import uhs.alphabet.badge.domain.RankedBadgeRequest;
import uhs.alphabet.badge.domain.Website;
import uhs.alphabet.domain.repository.PersonRepository;

class BadgeServiceTest {

    static PersonRepository personRepository = mock(PersonRepository.class);
    static RankWebSite rankWebSite = new Codeforces900GreyNewbieRankWebSite();
    static RankedBadgeFile rankedBadgeFile = new CodeforcesBadgeFileFixture();
    static private final BadgeService badgeService = new BadgeService(personRepository,
        List.of(rankWebSite), List.of(rankedBadgeFile));

    @BeforeAll
    static void init() {
        badgeService.init();
    }

    @Test
    @DisplayName("handle=jack, Rank=newbie일때 grey색의 jack 뱃지가 리턴되는지 확인")
    void test1() {
        RankedBadgeRequest request = new RankedBadgeRequest(Website.CODEFORCES, "jack");
        String rankedBadge = badgeService.requestRankedBadge(request);
        Assertions.assertTrue(rankedBadge.contains("jack"));
        Assertions.assertTrue(rankedBadge.contains("grey"));
    }
}