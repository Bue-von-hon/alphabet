package uhs.alphabet.badge.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uhs.alphabet.badge.students.StudentNumber;

public class StudentNumberTest {

    @Test
    @DisplayName("8자리 학번 생성 테스트")
    public void test1() {
        Assertions.assertDoesNotThrow(() -> new StudentNumber("20221122"));
    }

    @Test
    @DisplayName("6자리 학번 생성 테스트")
    public void test2() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            new StudentNumber("123456");
        });
        Assertions.assertTrue(runtimeException.getMessage().contains("학번은 8자리입니다."));
    }

    @Test
    @DisplayName("null 학번 생성 테스트")
    public void test3() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            new StudentNumber(null);
        });
        Assertions.assertTrue(runtimeException.getMessage().contains("학번은 null이 안됩니다."));
    }
}
