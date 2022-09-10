package uhs.alphabet.domain.badge;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import uhs.alphabet.domain.dto.PersonDto;
import uhs.alphabet.domain.service.PersonService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

@RestController
@RequiredArgsConstructor
public class BadgeController {
    private final PersonService personService;

    @GetMapping(value = "/stubadge")
    public String a(@RequestParam("stuid") String stuid) {
        List<PersonDto> personDtos = personService.searchPerson(stuid);
        String handle = "None";
        String name = "None";
        if (!personDtos.isEmpty()) {
            handle = personDtos.get(0).getHandle();
            name = personDtos.get(0).getName();
        }
        return StuBadge.of2(name, handle);
    }

    @GetMapping("/cfbadge")
    public String b(@RequestParam("handle") String handle) {
        String data = "";
        String color = "blue";
        ArrayList<String> colList = new ArrayList<String>(Arrays.asList("grey", "green", "cyan", "blue", "violet", "orange", "red", "url(#grad1)"));
        String baseUrl = "https://codeforces.com/api/user.info?handles=";
        String url = baseUrl + handle;
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(url);
        WebClient client = WebClient.builder().uriBuilderFactory(factory).build();
        Object trg = client.get().retrieve().bodyToMono(Object.class).block();
        Integer rating = null;
        if (trg != null) {
            data = trg.toString();
            data = data.replaceAll(" ", "");
            StringTokenizer tokens = new StringTokenizer(data, "{}[]=\",");
            ArrayList<String> strArr = new ArrayList<String>();
            while (tokens.hasMoreTokens()) {
                String tmp = tokens.nextToken();
                strArr.add(tmp);
            }
            rating = Integer.parseInt(strArr.get(8));
        } else rating = 300;
        if (rating >= 3000) {
            color = colList.get(7).toString();
        } else if (rating >= 2400) {
            color = colList.get(6).toString();
        } else if (rating >= 2100) {
            color = colList.get(5).toString();
        } else if (rating >= 1900) {
            color = colList.get(4).toString();
        } else if (rating >= 1600) {
            color = colList.get(3).toString();
        } else if (rating >= 1400) {
            color = colList.get(2).toString();
        } else if (rating >= 1200) {
            color = colList.get(1).toString();
        } else {
            color = colList.get(0).toString();
        }
        return CfBadge.of(handle, color);
    }
}
