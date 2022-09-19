package uhs.alphabet.badge;

import lombok.RequiredArgsConstructor;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.stereotype.Service;
import uhs.alphabet.badge.codeforces.CodeforcesBadgeFileStream;
import uhs.alphabet.badge.codeforces.CodeforceBadgeFactory;
import uhs.alphabet.badge.codeforces.util.CodeForcesRank;
import uhs.alphabet.badge.codeforces.util.CodeforcesHttpClient;
import uhs.alphabet.badge.codeforces.util.CodeforcesMapper;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final CodeforceBadgeFactory codeforceBadgeFactory;
    private final CodeforcesHttpClient codeforcesClient;
    private final CodeforcesMapper codeforcesMapper;
    private final CacheManager cacheManager;
    public StudentBadgeUser searchStudent(String stuid) {
        StudentBadgeUser student = badgeRepository.findByStunum(stuid);
        return student;
    }

    public String  makeCodeforcesBadge(String handle) {
        Cache<String, String> codeforcesCache = cacheManager.getCache("codeforcesCache", String.class, String.class);
        if (codeforcesCache.containsKey(handle)) return codeforcesCache.get(handle);
        String data = codeforcesClient.getData(handle);
        String rank = codeforcesMapper.getRank(data);
        CodeforcesBadgeFileStream badgeFileStream = new CodeforcesBadgeFileStream();
        String badge = codeforceBadgeFactory.makeBadge(handle, CodeForcesRank.getRank2Color(rank), badgeFileStream);
        codeforcesCache.put(handle, badge);
        return badge;
    }
}
