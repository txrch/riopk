С4 модель первого уровня

 ![image](https://github.com/user-attachments/assets/9379ece2-8cb0-49cb-8f2f-6528618ccb3b)
 
Второй уровень С4- модели

![image](https://github.com/user-attachments/assets/f88e510f-dc75-455f-a087-931179295a87)

С4- модель третьего уровня

![image](https://github.com/user-attachments/assets/0b5ac44c-8836-4fcb-91dc-41a4314b8e5b)

Диаграмма классов

![image](https://github.com/user-attachments/assets/f60211e6-3443-4343-a109-d156573bbbd5)

<h1>Архитектура</h1>

Реляционная схема базы данных

![image](https://github.com/user-attachments/assets/7a74fb6b-a68e-4237-a37e-dce93a010db7)

![image](https://github.com/user-attachments/assets/d112f400-d6ab-4cf0-878d-2022de491ed7)

Диаграмма последовательности


![image](https://github.com/user-attachments/assets/013464e5-1a2a-411b-9a2a-fe297308bdb6)


Диаграмма вариантов использования

Серверная часть с помощью Swagger

![image](https://github.com/user-attachments/assets/4b9f3132-1319-4cb2-a688-337e67c3faf8)

спецификация OpenAPI

![image](https://github.com/user-attachments/assets/bf1fcc1f-1549-4e99-96f3-7ccef2b0ea18)


![image](https://github.com/user-attachments/assets/0cd264c5-2b73-46ab-8dcb-945b0e876a90)


![image](https://github.com/user-attachments/assets/1633670e-f3f6-46c4-8a36-5be54aa4aa25)






Результаты расчетов в SonarCube

![image](https://github.com/user-attachments/assets/a5882055-b266-460e-9127-a8ac80ba5f7f)

Результат Junit-тестов

![image](https://github.com/user-attachments/assets/89fb4f32-21a9-411a-a525-785989b44328)

В этом тесте проверяется функциональность сервисного слоя для работы с сущностью Content. Он включает несколько тестов для различных операций с контентом: findAllSuccess: проверяет, что метод получения всех контентов возвращает корректный список записей. Репозиторий имитирует возврат заранее подготовленных данных, а тест удостоверяется, что их количество совпадает с ожидаемым.   findByIdSuccess: проверяет, что метод получения контента по ID возвращает корректный объект с правильными значениями всех его полей.findByIdNotFound: имитирует ситуацию, когда запрашиваемый контент не найден. Ожидается выброс исключения ObjectNotFoundException, а также подтверждается, что репозиторий был вызван один раз. saveSuccess: тестирует успешное сохранение объекта контента. Репозиторий имитирует возврат сохраненного объекта, а тест проверяет, что все его поля соответствуют ожидаемым значениям.
updateSuccess: проверяет успешное обновление существующего контента. Репозиторий находит объект по ID, обновляет его данными, переданными в метод, и возвращает обновленный объект. Тест подтверждает, что обновленные поля совпадают с ожидаемыми.
