package uhs.alphabet.domain.badge;

import lombok.RequiredArgsConstructor;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.stereotype.Service;
import uhs.alphabet.domain.badge.codeforces.CodeforcesBadgeFileStream;
import uhs.alphabet.domain.badge.codeforces.CodeforceBadgeFactory;
import uhs.alphabet.domain.badge.codeforces.util.CodeForcesRank;
import uhs.alphabet.domain.badge.codeforces.util.CodeforcesHttpClient;
import uhs.alphabet.domain.badge.codeforces.util.CodeforcesMapper;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final CodeforceBadgeFactory codeforceBadgeFactory;
    private final CodeforcesHttpClient codeforcesClient;
    private final CodeforcesMapper codeforcesMapper;
    private final CacheManager cacheManager;

    private static final Map<String, String> colorMap = new HashMap<>();
    static {
        colorMap.put(CodeForcesRank.Legendary_Grandmaster.getLowerValue(), "url(#grad1)");
        colorMap.put(CodeForcesRank.International_Grandmaster.getLowerValue(), "red");
        colorMap.put(CodeForcesRank.Grandmaster.getLowerValue(), "red");
        colorMap.put(CodeForcesRank.International_Master.getLowerValue(), "orange");
        colorMap.put(CodeForcesRank.Master.getLowerValue(), "orange");
        colorMap.put(CodeForcesRank.Candidata_Master.getLowerValue(), "violet");
        colorMap.put(CodeForcesRank.Expert.getLowerValue(), "blue");
        colorMap.put(CodeForcesRank.Specialist.getLowerValue(), "cyan");
        colorMap.put(CodeForcesRank.Pupil.getLowerValue(), "green");
        colorMap.put(CodeForcesRank.Newbie.getLowerValue(), "grey");
    }
    public StudentBadgeUser searchStudent(String stuid) {
        StudentBadgeUser student = badgeRepository.findByStunum(stuid);
        return student;
    }

    public String  makeCodeforcesBadge(String handle) {
        Cache<String, String> codeforcesCache = cacheManager.getCache("codeforcesCache", String.class, String.class);
        if (codeforcesCache.containsKey(handle)) return codeforcesCache.get(handle);
        CodeforcesBadgeFileStream badgeFileStream = new CodeforcesBadgeFileStream();
        String data = codeforcesClient.getData(handle);
        String rank = codeforcesMapper.getRank(data);
        String badge = codeforceBadgeFactory.getBadge(handle, colorMap.get(rank), badgeFileStream);
        codeforcesCache.put(handle, badge);
        return badge;
    }
}
