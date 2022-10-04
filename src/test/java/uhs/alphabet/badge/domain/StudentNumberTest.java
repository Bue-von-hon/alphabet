package uhs.alphabet.badge.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uhs.alphabet.badge.students.domain.StudentNumber;

public class StudentNumberTest {

    @Test
    @DisplayName("8자리 학번 생성 테스트")
    public void test1() {
        Assertions.assertDoesNotThrow(() -> StudentNumber.createByString("20221122"));
    }

    @Test
    @DisplayName("6자리 학번 생성 테스트")
    public void test2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            StudentNumber.createByString("123456");
        }, "학번은 8자리입니다.");
    }

    @Test
    @DisplayName("null 학번 생성 테스트")
    public void test3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            StudentNumber.createByString(null);
        }, "학번은 필수값입니다.");
    }
}
