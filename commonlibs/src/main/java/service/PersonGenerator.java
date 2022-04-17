package service;

import domain.Person;

import java.util.List;

public interface PersonGenerator {

    Person generateUser();

    List<Person> generateUsers(int limit);
}
