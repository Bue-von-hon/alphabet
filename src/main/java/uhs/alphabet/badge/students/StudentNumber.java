package uhs.alphabet.badge.students;

public class StudentNumber {
    private final String number;

    public StudentNumber(String number) {
        if (number == null) throw new RuntimeException("학번은 null이 안됩니다.");
        if (number.length() != 8) throw new RuntimeException("학번은 8자리입니다.");
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
