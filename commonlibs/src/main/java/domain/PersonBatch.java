package domain;

import lombok.Data;

import java.util.List;

@Data
public class PersonBatch {

    private List<Person> batch;
}
