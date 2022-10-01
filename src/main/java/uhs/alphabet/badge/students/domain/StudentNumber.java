package uhs.alphabet.badge.students.domain;

public class StudentNumber {
    private final String number;

    private StudentNumber(String number) {
        this.number = number;
    }

    public static StudentNumber createByString(String number) {
        if (number == null) throw new RuntimeException("학번은 null이 안됩니다.");
        if (number.length() != 8) throw new RuntimeException("학번은 8자리입니다.");
        Integer.parseInt(number);
        return new StudentNumber(number);
    }

    public String getNumber() {
        return number;
    }
}
