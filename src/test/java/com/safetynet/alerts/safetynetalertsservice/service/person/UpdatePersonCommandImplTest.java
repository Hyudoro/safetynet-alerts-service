package com.safetynet.alerts.safetynetalertsservice.service.person;

import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldPersonNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.impl.PersonServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.person.impl.UpdatePersonCommandImpl;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.AddPersonCommand;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.DeletePersonCommand;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.PersonService;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.UpdatePersonCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
public class UpdatePersonCommandImplTest {
    @Mock DeletePersonCommand deletePersonCommand;
    @Mock AddPersonCommand addPersonCommand;
    @Mock DataRepository repository;

    private PersonService service;

    @BeforeEach
    void setUp() {
        UpdatePersonCommand updatePersonCommand = new UpdatePersonCommandImpl(repository);
        service = new PersonServiceImpl(addPersonCommand, deletePersonCommand, updatePersonCommand);
    }

    @Test
    void shouldUpdatePersonTest(){
        Person oldPersonData = new Person(
                "John",
                "Doe",
                "123 Main Street",
                "Paris",
                "06000",
                "060-102-03042",
                "john.doe@email.com");
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            List<Person> oldPeople = new ArrayList<>(List.of(oldPersonData));
            DataWrapper oldData = new DataWrapper(List.copyOf(oldPeople),List.of(),List.of());
            DataWrapper newData = lambda.apply(oldData);
            Person newPersonData = newData.persons().getFirst();
            assertThat(newPersonData).usingRecursiveComparison()
                    .ignoringFields(
                            "firsName", "lastName")
                    .isEqualTo(new Person(
                            "John",
                            "Doe",
                            "300 Busch Avenue",
                            "Denver",
                            "84024",
                            "724-102-03042",
                            "jonny.doe@email.com"
                    ));
                return null;
                }).when(repository).update(any());
        service.updatePerson("Doe","John",
                new Person(
                "John",
                "Doe",
                "300 Busch Avenue",
                "Denver",
                "84024",
                "724-102-03042",
                "jonny.doe@email.com"
                ));
    }

    @Test
    void shouldThrowsExceptionIfPersonNotFoundTest(){
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda =  invocation.getArgument(0);
            DataWrapper oldData = new DataWrapper(List.of(),List.of(),List.of());
            return lambda.apply(oldData);
        }).when(repository).update(any());
        assertThatThrownBy(()-> service.updatePerson("Doe","John",new Person(
                "John",
                "Doe",
                "300 Busch Avenue",
                "Denver",
                "84024",
                "724-102-03042",
                "jonny.doe@email.com"))).isInstanceOf(OldPersonNotFoundException.class);
    }
}
