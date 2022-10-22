package uhs.alphabet.badge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uhs.alphabet.badge.students.domain.StudentNumber;

import static org.mockito.Mockito.*;

class BadgeServiceTest {

    private final BadgeService badgeService = mock(BadgeService.class);

    @Test
    @DisplayName("올바른 request 통해 ranking badge service 테스트")
    void test1() {
        badgeService.getRankedBadge(any());
        verify(badgeService, times(1)).getRankedBadge(any());
    }

    @Test
    @DisplayName("올바른 학번 입력 테스트")
    void test2() {
        Assertions.assertDoesNotThrow(() -> StudentNumber.createByString("20221122"));
    }
}