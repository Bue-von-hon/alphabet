package uhs.alphabet.badge;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uhs.alphabet.badge.application.RankWebSite;
import uhs.alphabet.badge.application.RankedBadgeFile;
import uhs.alphabet.badge.domain.RankedBadge;
import uhs.alphabet.badge.domain.RankedBadgeData;
import uhs.alphabet.badge.domain.RankedBadgeRequest;
import uhs.alphabet.badge.domain.Website;
import uhs.alphabet.badge.students.Student;
import uhs.alphabet.badge.students.StudentBadge;
import uhs.alphabet.badge.students.StudentMapper;
import uhs.alphabet.badge.students.domain.StudentNumber;
import uhs.alphabet.domain.entity.PersonEntity;
import uhs.alphabet.domain.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final PersonRepository personRepository;
    private final List<RankWebSite> webSiteList;
    private final List<RankedBadgeFile> badgeFileList;
    private Map<Website, RankWebSite> webSiteMap;
    private Map<Website, RankedBadgeFile> badgeFileMap;

    @PostConstruct
    final void init() {
        webSiteMap = webSiteList.stream().collect(Collectors.toMap(
            RankWebSite::getFrom,
            webSite -> webSite,
            (oldSite, newSite) -> newSite
        ));

        badgeFileMap = badgeFileList.stream().collect(Collectors.toMap(
            RankedBadgeFile::getFrom,
            file -> file,
            (oldFile, newFile) -> newFile
        ));
    }

    public String getStudentBadgeById(final StudentNumber studentNumber) {
        PersonEntity personEntity = personRepository.findByStunum(
            studentNumber.getNumber().toString());
        StudentMapper mapper = StudentMapper.INSTANCE;
        Optional<Student> user = Optional.ofNullable(mapper.toUser(personEntity));
        if (user.isEmpty()) {
            return StudentBadge.of("None", "None");
        }
        return StudentBadge.of(user.get().getName(), user.get().getHandle());
    }

    public String requestRankedBadge(final RankedBadgeRequest request) {
        RankWebSite webSite = getWebSite(request);
        RankedBadgeFile rankedBadgeFile = getRankedBadgeFile(request);
        RankedBadgeData badgeData = RankedBadgeData.of(request, webSite);
        return RankedBadge.getBadge(rankedBadgeFile, badgeData);
    }

    final RankWebSite getWebSite(RankedBadgeRequest request) {
        return webSiteMap.get(request.getWebsiteFromRequest());
    }

    final RankedBadgeFile getRankedBadgeFile(RankedBadgeRequest request) {
        return badgeFileMap.get(request.getWebsiteFromRequest());
    }
}
