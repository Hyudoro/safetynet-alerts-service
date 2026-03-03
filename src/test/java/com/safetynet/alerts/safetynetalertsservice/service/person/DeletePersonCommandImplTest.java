package com.safetynet.alerts.safetynetalertsservice.service.person;

import com.safetynet.alerts.safetynetalertsservice.model.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.model.exception.OldPersonNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.person.impl.DeletePersonCommandImpl;
import com.safetynet.alerts.safetynetalertsservice.service.person.impl.PersonServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
public class DeletePersonCommandImplTest {
    @Mock private UpdatePersonCommand updatePersonCommand;
    @Mock AddPersonCommand addPersonCommand;
    @Mock DataRepository repository;

    private PersonService service;

    @BeforeEach
    public void setUp() {
        DeletePersonCommand deletePersonCommand = new DeletePersonCommandImpl(repository);
        service = new PersonServiceImpl(addPersonCommand,deletePersonCommand,updatePersonCommand);
    }

    @Test
    void shouldDeletePersonTest(){
        Person targetedperson = new Person(
                "John",
                "Doe",
                "123 Main Street",
                "Paris",
                "06000",
                "060-102-03042",
                "john.doe@email.com");


        doAnswer(invocation -> {
            UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
            List<Person> currentPeople = new ArrayList<>(List.of(targetedperson));
            DataWrapper currentData = new DataWrapper(List.copyOf(currentPeople),List.of(),List.of());
            DataWrapper newData = lambda.apply(currentData);
            assertTrue(newData.persons().isEmpty());
            return null;
        }).when(repository).update(any());
    service.deletePerson("Doe","John");
    }

    @Test
    void shouldThrowsExceptionIfPersonNotFound(){
       doAnswer(invocation ->{
          UnaryOperator<DataWrapper> lambda = invocation.getArgument(0);
          return lambda.apply(new DataWrapper(List.of(),List.of(),List.of()));
       }).when(repository).update(any());

       assertThatThrownBy(() -> service.deletePerson("Doe","John")).isInstanceOf(
               OldPersonNotFoundException.class
       );
    }
}
