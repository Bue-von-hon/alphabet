package uhs.alphabet.badge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uhs.alphabet.badge.domain.RankedBadge;
import uhs.alphabet.badge.students.StudentBadge;

public class BadgeTest {

    @Test
    @DisplayName("of 메소드를 이용하여 뱃지 생성 테스트")
    public void test1() {
        String name = "hong";
        String handle = "blue";
        String badge = StudentBadge.of(name, handle);
        Assertions.assertNotNull(badge);
        Assertions.assertTrue(badge.contains(name));
        Assertions.assertTrue(badge.contains(handle));
    }

    @Test
    @DisplayName("뱃지 객체의 getBadge 메소드 테스트")
    public void test2() {
        RankedBadge badge = new RankedBadge("handle={handle}, color={color}", "jack", "red");
        String ret = badge.getBadge();
        Assertions.assertEquals("handle=jack, color=red", ret);
    }
}
