package uhs.alphabet.domain.service;

import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uhs.alphabet.domain.dto.PersonDto;
import uhs.alphabet.domain.entity.PersonEntity;
import uhs.alphabet.board.BoardRepository;
import uhs.alphabet.domain.repository.PersonRepository;

import java.util.List;

@SpringBootTest
public class PersonServiceTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @After
    public void cleanup() {
        personRepository.deleteAll();
        boardRepository.deleteAll();
    }
    @BeforeEach
    public void cleanupEach() {
        personRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("savePerson 1번 테스트")
    public void savePersonTest() {
        PersonDto personDto = PersonDto.builder()
                .handle("saveTest")
                .name("saveTest")
                .rating(1700)
                .stunum("1234")
                .build();
        personService.savePerson(personDto);
        List<PersonEntity> personEntities =  personRepository.findAll();
        Assertions.assertEquals(1, personEntities.size());
    }
    @Test
    @DisplayName("savePerson 10번 테스트")
    public void savePersonTest10() {
        PersonDto personDto = PersonDto.builder()
                .handle("saveTest")
                .name("saveTest")
                .rating(1700)
                .stunum("1234")
                .build();

        for (int i = 0; i < 10; i++) personService.savePerson(personDto);
        List<PersonEntity> personEntities =  personRepository.findAll();
        Assertions.assertEquals(10, personEntities.size());
    }

    @Test
    @DisplayName("searchPersonTest 없는 경우")
    public void searchPersonTestNotExist() {
        List<PersonDto> personDtos = personService.searchPerson("1234");
        Assertions.assertEquals(0, personDtos.size());
    }
    @Test
    @DisplayName("searchPersonTest 있는 경우")
    public void searchPersonTestExist() {
        PersonDto personDto = PersonDto.builder()
                .handle("saveTest")
                .name("saveTest")
                .rating(1700)
                .stunum("1234")
                .build();
        Long id = personService.savePerson(personDto);
        List<PersonDto> personDtos = personService.searchPerson("1234");
        PersonDto personDtoTest = personDtos.get(0);

        Assertions.assertEquals(personDto.getHandle(), personDtoTest.getHandle());
        Assertions.assertEquals(personDto.getName(), personDtoTest.getName());
        Assertions.assertEquals(personDto.getRating(), personDtoTest.getRating());
        Assertions.assertEquals(personDto.getStunum(), personDtoTest.getStunum());
    }
}
