package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);

    // Создание словаря для быстрого доступа к объектам класса Person по id. Временная сложность O(n).
    Map<Integer, Person> personsMap = persons.stream()
            .collect(Collectors.toMap(Person::getId, Function.identity()));

    // Преобразование из списка id в cписок объектов Person в необходимом порядке. Временная сложность O(n).
    return personIds.stream()
        .map(personsMap::get)
        .collect(Collectors.toList());
    // Общая временная сложность сводится к O(n).
  }
}
