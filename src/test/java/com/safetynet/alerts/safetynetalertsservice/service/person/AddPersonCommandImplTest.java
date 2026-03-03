package com.safetynet.alerts.safetynetalertsservice.service.person;

import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.DuplicatePersonMappingException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.impl.AddPersonCommandImpl;
import com.safetynet.alerts.safetynetalertsservice.service.person.impl.DeletePersonCommandImpl;
import com.safetynet.alerts.safetynetalertsservice.service.person.impl.PersonServiceImpl;
import com.safetynet.alerts.safetynetalertsservice.service.person.impl.UpdatePersonCommandImpl;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.AddPersonCommand;
import com.safetynet.alerts.safetynetalertsservice.service.person.interfaces.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
public class AddPersonCommandImplTest {
    @Mock DeletePersonCommandImpl deletePersonCommandImpl;
    @Mock UpdatePersonCommandImpl updatePersonCommandImpl;
    @Mock DataRepository repository;

    private PersonService service;

    @BeforeEach
    public void setUp() {
        AddPersonCommand addPersonCommand = new AddPersonCommandImpl(repository);
        service = new PersonServiceImpl(addPersonCommand, deletePersonCommandImpl, updatePersonCommandImpl);
    }
    @Test
    void shouldAddPersonCommandTest(){
        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            DataWrapper newData = lambda.apply(new DataWrapper(List.of(),List.of(),List.of()));
            Person person = newData.persons().getFirst();
            assertTrue(person.firstName().equals("John") && person.lastName().equals("Doe"));
            return null;
        }).when(repository).update(any());
        service.addPerson(new Person(
                "John",
                "Doe",
                "123 Main Street",
                "Paris",
                "06000",
                "060-102-03042",
                "john.doe@email.com"));
    }

    @Test
    void shouldThrowsRuntimeExceptionIfPersonAlreadyExists(){
        Person person = new Person(
                "John",
                "Doe",
                "123 Main Street",
                "Paris",
                "06000",
                "060-102-03042",
                "john.doe@email.com");

        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            List<Person> oldPeople = new ArrayList<>(List.of(person));
            return lambda.apply(new DataWrapper(oldPeople,List.of(),List.of()));
        }).when(repository).update(any());
        assertThatThrownBy(() -> service.addPerson(new Person(
                "John",
                "Doe",
                "123 Main Street",
                "Paris",
                "06000",
                "060-102-03042",
                "john.doe@email.com"))).isInstanceOf(DuplicatePersonMappingException.class);

    }
}
