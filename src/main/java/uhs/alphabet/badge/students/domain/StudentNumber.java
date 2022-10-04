package uhs.alphabet.badge.students.domain;

public class StudentNumber {
    private final int number;

    private StudentNumber(Integer number) {
        this.number = number;
    }

    public static StudentNumber createByString(String number) {
        if (number == null) throw new IllegalArgumentException("학번은 필수값입니다.");
        if (number.length() != 8) throw new IllegalArgumentException("학번은 8자리입니다.");
        return new StudentNumber(Integer.parseInt(number));
    }

    public Integer getNumber() {
        return number;
    }
}
