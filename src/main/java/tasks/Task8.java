package tasks;

import common.Person;

import java.util.*;
import java.util.function.Function;
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
     Да, и еще удаление первого элемента списка будет стоить нам О(n), поскольку придется сдвинуть все оставшиеся элементы
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
    Исправил через фильтрацию потока и дальнейший джоин в строку.
   */
  public String convertPersonToString(Person person) {
    return Stream.of(person.getFirstName(), person.getMiddleName(), person.getSecondName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  /* словарь id персоны -> ее имя
  Не совсем понял, зачем начальная емкость словаря указана и опять захардкожена 1 вместо константы
  Кажется, не очень хорошо называть переменную map так же, как тип данных. Можно обойтись без переменной.
  Элегантнее собрать словарь через stream.
   */
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(Person::getId, Person::getFirstName, (existing, renewed) -> existing));
  }

  /*
   есть ли совпадающие в двух коллекциях персоны?
    Собираем map из двух коллекций с уникальными объектами, где ключ - объект, значение - счетчик этого объекта.
    Возвращаем булевое значение, харакетиризующее наличие дубликатов.
    Вместо сложности О(n2), получаем O(n)
   */
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return Stream.concat(persons1.stream().distinct(), persons2.stream().distinct())
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .anyMatch(personCount -> personCount.getValue() > 1);
  }

  /*
  можно воспользоваться стандартным методом потока без использования лишней памяти
  Да, действительно, наш метод в качестве аргумента принимает поток, а мы вызываем теминальный метод и закрываем его.
  Правильнее было бы передавать коллекцию, открывать поток, проводить с ним манипуляции и закрывать.
  Пробрасывать поток по методам неправильно. Кто открыл его - тот его и закрывает.
   */
  public long countEven(Collection<Integer> numbers) {
    return numbers.stream().filter(num -> num % 2 == 0).count();
  }
}
