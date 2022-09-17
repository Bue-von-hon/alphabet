package uhs.alphabet.domain.badge.codeforces;

import java.util.List;

public class CodeforcesResponse {
    private String status;
    private List<CodeforcesUser> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CodeforcesUser> getResult() {
        return result;
    }

    public void setResult(List<CodeforcesUser> result) {
        this.result = result;
    }
}
