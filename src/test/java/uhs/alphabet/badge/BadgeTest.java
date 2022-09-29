package uhs.alphabet.badge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uhs.alphabet.badge.students.StudentBadge;

public class BadgeTest {

    @Test
    @DisplayName("of 메소드를 이용하여 뱃지 생성 테스트")
    public void test1() {
        String name = "hong";
        String handle = "blue";
        String badge = StudentBadge.of(name, handle);
        Assertions.assertNotNull(badge);
        Assertions.assertEquals(true, badge.contains(name));
        Assertions.assertEquals(true, badge.contains(handle));
    }
}
