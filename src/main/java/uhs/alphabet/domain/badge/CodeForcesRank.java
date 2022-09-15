package uhs.alphabet.domain.badge;

public enum CodeForcesRank {
    Legendary_Grandmaster("Legendary Grandmaster"),
    International_Grandmaster("International Grandmaster"),
    Grandmaster("Grandmaster"),
    International_Master("International Master"),
    Master("Master"),
    Candidata_Master("Candidata Master"),
    Expert("Expert"),
    Specialist("Specialist"),
    Pupil("Pupil"),
    Newbie("Newbie");

    private String value;
    CodeForcesRank(String value) {
        this.value=value;
    }

    public String getLowerValue() {
        return value.toLowerCase();
    }
}
