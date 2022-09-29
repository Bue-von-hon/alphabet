package uhs.alphabet.badge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BadgeServiceTest {

    private final BadgeService badgeService = mock(BadgeService.class);

    @Test
    @DisplayName("handle을 통해 뱃지 서비스가 호출되는지 테스트")
    public void test1() {
        badgeService.getRankedBadge("a");
        verify(badgeService, times(1)).getRankedBadge(anyString());
    }
}