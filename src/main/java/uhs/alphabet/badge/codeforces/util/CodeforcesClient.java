package uhs.alphabet.badge.codeforces.util;

import uhs.alphabet.badge.application.CodeforcesResponse;

public interface CodeforcesClient {
    CodeforcesResponse getCodeforcesResponse(String handle);
}
