package uhs.alphabet.badge;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import uhs.alphabet.badge.adapter.RankWebSite;
import uhs.alphabet.badge.adapter.RankedBadgeFile;
import uhs.alphabet.badge.domain.RankedBadge;
import uhs.alphabet.badge.domain.RankedBadgeRequest;
import uhs.alphabet.badge.codeforces.CodeforcesBadgeMaster;
import uhs.alphabet.badge.domain.Website;
import uhs.alphabet.badge.students.StudentBadgeMaster;
import uhs.alphabet.domain.entity.PersonEntity;
import uhs.alphabet.domain.repository.PersonRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final PersonRepository personRepository;
    private final List<RankWebSite> webSiteList;
    private final List<RankedBadgeFile> badgeFileList;
    private Map<Website, RankWebSite> webSiteMap;
    private Map<Website, RankedBadgeFile> badgeFileMap;

    @PostConstruct
    private void init() {
        webSiteMap = webSiteList.stream().collect(Collectors.toMap(
                webSite -> webSite.getFrom(),
                webSite -> webSite,
                (oldSite, newSite) -> newSite
        ));

        badgeFileMap = badgeFileList.stream().collect(Collectors.toMap(
                file -> file.getFrom(),
                file -> file,
                (oldFile, newFile) -> newFile
        ));
    }

    public String getStudentBadgeById(String stuid) {
        PersonEntity personEntity = personRepository.findByStunum(stuid);
        return StudentBadgeMaster.getBadgeByEntity(personEntity);
    }

    public String  makeCodeforcesBadge(final String handle) {
        CodeforcesBadgeMaster codeforcesBadgeMaster = new CodeforcesBadgeMaster();
        return codeforcesBadgeMaster.getBadgeByHandle(handle);
    }

    public String getRankedBadge(final RankedBadgeRequest request) {
        RankWebSite rankWebSite = webSiteMap.get(request.getWeb());
        RankedBadgeFile rankedBadgeFile = badgeFileMap.get(request.getWeb());
        String rank = rankWebSite.getRank(request.getHandle());
        String color = rankWebSite.getColor(rank);
        String handle = request.getHandle();
        RankedBadge badge = new RankedBadge(rankedBadgeFile.getBadge(), handle, color);
        return badge.getBadge();
    }
}
