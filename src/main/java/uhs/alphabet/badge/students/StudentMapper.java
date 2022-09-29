package uhs.alphabet.badge.students;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import uhs.alphabet.domain.entity.PersonEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper( StudentMapper.class );
    Student toUser(PersonEntity person);
}
