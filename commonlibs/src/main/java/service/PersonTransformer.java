package service;

import domain.Person;
import dto.PersonDto;

public interface PersonTransformer {

    Person transform(PersonDto dto);
}
