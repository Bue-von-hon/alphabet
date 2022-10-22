package uhs.alphabet.badge.students.domain;

public class StudentNumber {
    private final int number;

    private StudentNumber(Integer number) {
        this.number = number;
    }

    public static StudentNumber createByString(String number) {
        isNull(number);
        isValidLength(number);
        isValidFormatextracted(number);
        return new StudentNumber(Integer.parseInt(number));
    }

    private static void isValidFormatextracted(String number) {
        if (!number.matches("\\d+")) throw new NumberFormatException("학번은 0부터 9사이 숫자만 가능합니다.");
    }

    private static void isValidLength(String number) {
        if (number.length() != 8) throw new IllegalArgumentException("학번은 8자리입니다.");
    }

    private static void isNull(String number) {
        if (number == null) throw new IllegalArgumentException("학번은 필수값입니다.");
    }

    public Integer getNumber() {
        return number;
    }
}
