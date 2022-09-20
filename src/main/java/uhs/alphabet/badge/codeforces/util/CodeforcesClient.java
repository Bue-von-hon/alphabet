package uhs.alphabet.badge.codeforces.util;

import uhs.alphabet.badge.codeforces.dto.CodeforcesResponse;

public interface CodeforcesClient {
    CodeforcesResponse getData(String handle);
}
