package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 {

  private long count;
  final int PERSONS_SKIP = 1;

  /* Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
     Здесь лучше использовать метод skip без лишних проверок длины массива. Если список пуст - он тоже вернет пустой
     список.
     0 лучше вынести в константу - сегодня у нас 1 фальшивая песона, а завтра 10 и менять этот параметр по коду неудобно.
   */
  public List<String> getNames(List<Person> persons) {
    return persons.stream().skip(PERSONS_SKIP).map(Person::getFirstName).collect(Collectors.toList());
  }

  /* ну и различные имена тоже хочется
    Нет необходимости пропускать данные через stream, достаточно преобразовать сразу в HashSet.
    Это обеспечит уникальность элементов.
   */
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  /* Для фронтов выдадим полное имя, а то сами не могут
    Оба метода возвращают строки, нет необходимости создания пустой строки для дальнейшей конкатенации.
    Судя по конструктору класса Person, атрибут FirstName является обязательным, соответственно не может быть пустым.
    Достаточно выполнить проверку для getSecondName()
   */
  public String convertPersonToString(Person person) {
    String personSecondName = person.getSecondName();
    if (personSecondName == null) {
      return person.getFirstName();
    }
    return person.getFirstName() + " " + personSecondName;
  }

  /* словарь id персоны -> ее имя
  Не совсем понял, зачем начальная емкость словаря указана и опять захардкожена 1 вместо константы
  Кажется, не очень хорошо называть переменную map так же, как тип данных. Можно обойтись без переменной.
  Элегантнее собрать словарь через stream
   */
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(Person::getId, Person::getFirstName));
  }

  /*
   есть ли совпадающие в двух коллекциях персоны?
    Собираем две коллекции в один сет. Если количество элементов в общем сете не равно сумме элементов в каждом сете
    поотдельности - значит был неуникальный объект.
    Наверное, собрать сет и без стрима.
   */
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> personsSum = Stream.concat(persons1.stream(), persons2.stream())
            .collect(Collectors.toSet());
    return persons1.size() + persons2.size() != personsSum.size();
  }

  //можно воспользоваться стандартным методом потока без использования лишней памяти
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }
}
