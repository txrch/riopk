<h1>РиОПК</h1>

С4 модель

![image](https://github.com/user-attachments/assets/de478763-0273-4071-a158-888f5ab7df62)

Диаграмма классов

![image](https://github.com/user-attachments/assets/d00df0a8-31e5-4510-ac93-5544abe303a4)

Диаграмма деятельности

![image](https://github.com/user-attachments/assets/a35431b1-1aa9-47a4-9a6b-f341efe40201)

Диаграмма вариантов использования

![image](https://github.com/user-attachments/assets/6291986b-ce1a-49fa-bde5-06d700d7fe45)

<h1>Архитектура</h1>

Реляционная схема базы данных

![image](https://github.com/user-attachments/assets/78357223-d4fc-45d1-8d40-658a6a81fc76)

Диаграмма последовательности оценки качества услуг агрегаторов такси

![image](https://github.com/user-attachments/assets/ee3243f8-e929-4fba-8838-c8c671a2173c)

<h1>Документация</h1>

Разработанная документация для сервиса TaxiAggregatorService

![image](https://github.com/user-attachments/assets/fcab0939-5ef7-42ee-a9f9-b691394a2584)

<h1>Оценка качества</h1>

Результаты анализа

![image](https://github.com/user-attachments/assets/b98a7d7d-3cbc-4b61-b7ab-4de157fad776)

<h1>Тестирование</h1>

package com.app.taxiqos;

import com.app.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TaxiServiceQualityAnalysisIntegrationTest {

    @Autowired
    private TaxiServiceQualityRepository repository;

    @Autowired
    private TaxiServiceQualityAnalysisService service;

    private TaxiServiceQuality taxiService1;
    private TaxiServiceQuality taxiService2;
    private TaxiServiceQuality taxiService3;

    @BeforeEach
    void setUp() {
        // Настроим объекты для тестирования
        taxiService1 = new TaxiServiceQuality(1L, "service1", "2025-01-01", 4.5f, "Good service", "file1");
        taxiService2 = new TaxiServiceQuality(2L, "service2", "2025-01-02", 3.0f, "Average service", "file2");
        taxiService3 = new TaxiServiceQuality(3L, "service3", "2025-01-03", 2.0f, "Poor service", "file3");

        repository.save(taxiService1);
        repository.save(taxiService2);
        repository.save(taxiService3);
    }

    @AfterEach
    void tearDown() {
        // Очистим базу данных после каждого теста
        repository.deleteAll();
    }

    @Test
    void findAllSuccess() {
        List<TaxiServiceQuality> actualServices = service.findAllForTest();

        assertThat(actualServices).hasSize(3);
    }

    @Test
    void findByIdSuccess() {
        TaxiServiceQuality foundService = service.find("1");

        assertThat(foundService.getId()).isEqualTo(1);
        assertThat(foundService.getName()).isEqualTo(taxiService1.getName());
        assertThat(foundService.getRating()).isEqualTo(taxiService1.getRating());
        assertThat(foundService.getFeedback()).isEqualTo(taxiService1.getFeedback());
        assertThat(foundService.getFile()).isEqualTo(taxiService1.getFile());
    }

    @Test
    void findByIdNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.find("999"));
    }

    @Test
    void saveSuccess() {
        TaxiServiceQuality newService = new TaxiServiceQuality(null, "service4", "2025-01-04", 4.8f, "Excellent service", "file4");

        TaxiServiceQuality saved = service.saveForTest(newService);

        assertThat(saved.getName()).isEqualTo(newService.getName());
        assertThat(saved.getRating()).isEqualTo(newService.getRating());
        assertThat(saved.getFeedback()).isEqualTo(newService.getFeedback());
        assertThat(saved.getFile()).isEqualTo(newService.getFile());
    }

    @Test
    void updateSuccess() {
        TaxiServiceQuality update = new TaxiServiceQuality(1L, "updatedService", "2025-01-01", 5.0f, "Outstanding service", "updatedFile");

        TaxiServiceQuality updated = service.updateForTest("1", update);

        assertThat(updated.getId()).isEqualTo(1);
        assertThat(updated.getName()).isEqualTo(update.getName());
        assertThat(updated.getRating()).isEqualTo(update.getRating());
        assertThat(updated.getFeedback()).isEqualTo(update.getFeedback());
        assertThat(updated.getFile()).isEqualTo(update.getFile());
    }

    @Test
    void updateNotFound() {
        TaxiServiceQuality update = new TaxiServiceQuality(999L, "updatedService", "2025-01-01", 4.5f, "Good service", "updatedFile");

        assertThrows(ObjectNotFoundException.class, () -> service.updateForTest("999", update));
    }

    @Test
    void deleteSuccess() {
        service.deleteForTest("1");

        assertThrows(ObjectNotFoundException.class, () -> service.find("1"));
    }

    @Test
    void deleteNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.deleteForTest("999"));
    }
}

Этот тестовый класс предназначен для проверки работы сервиса оценки качества услуг агрегаторов такси (в данном случае, через сервис TaxiServiceQualityAssessmentService). Тесты используются для проверки CRUD (Create, Read, Update, Delete) операций с объектами типа TaxiServiceQualityAssessment через репозиторий TaxiServiceQualityAssessmentRepository.
Вот что делает каждый тест:
1. findAllSuccess()
Этот тест проверяет, что метод findAll() правильно возвращает список всех оценок качества сервисов такси. Он подготавливает список оценок (assessments), мокаем результат работы репозитория, чтобы вернуть этот список. Затем, вызывается метод сервиса, который должен вернуть тот же список, и проверяется его размер.
Что проверяет: корректность возврата всех оценок качества.
2. findByIdSuccess()
Этот тест проверяет, что метод findById() правильно находит и возвращает оценку качества по ID. Ожидается, что при вызове service.findForTest(1 + "") будет возвращена первая оценка из списка. Проверяется, что все поля объекта совпадают с ожидаемыми.
Что проверяет: корректность поиска оценки по ID.
3. findByIdNotFound()
Этот тест проверяет, что если метод findById() пытается найти оценку по несуществующему ID, то он выбросит исключение ObjectNotFoundException. Мокаем результат работы репозитория, чтобы он возвращал Optional.empty().
Что проверяет: обработку случая, когда оценка не найдена.
4. saveSuccess()
Этот тест проверяет, что метод save() правильно сохраняет новую оценку качества в базе данных. Он мокаем результат сохранения, чтобы убедиться, что возвращаемый объект совпадает с тем, который был передан в метод.
Что проверяет: корректность сохранения новой оценки.
5. updateSuccess()
Этот тест проверяет, что метод update() правильно обновляет существующую оценку качества. Сначала мы находим оценку по ID, затем вызываем метод обновления с новой информацией. Проверяется, что все поля в обновленной оценке совпадают с ожидаемыми.
Что проверяет: корректность обновления оценки качества.
6. updateNotFound()
Этот тест проверяет, что если мы пытаемся обновить оценку, которая не существует, то будет выброшено исключение ObjectNotFoundException. Мокаем результат работы репозитория, чтобы он не нашел нужную оценку.
Что проверяет: обработку ошибки при попытке обновить несуществующую оценку.
7. deleteSuccess()
Этот тест проверяет, что метод delete() правильно удаляет оценку по ID. Мы сначала находим оценку по ID, а затем вызываем метод удаления, проверяя, что метод репозитория был вызван для удаления.
Что проверяет: корректность удаления оценки качества.
8. deleteNotFound()
Этот тест проверяет, что если мы пытаемся удалить несуществующую оценку, то будет выброшено исключение ObjectNotFoundException. Мокаем результат работы репозитория, чтобы он не нашел нужную оценку.
Что проверяет: обработку ошибки при попытке удалить несуществующую оценку.

package com.app.taxiqos;

import com.app.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaxiServiceQualityAssessmentTest {

    @Mock
    private TaxiServiceQualityAssessmentRepository repository;

    @InjectMocks
    private TaxiServiceQualityAssessmentService service;

    List<TaxiServiceQualityAssessment> assessments = new ArrayList<>();

    @BeforeEach
    void setUp() {
        assessments.add(new TaxiServiceQualityAssessment(1L, "TaxiAggregator1", "2025-02-10", 4.5f, "Good service overall", "file1"));
        assessments.add(new TaxiServiceQualityAssessment(2L, "TaxiAggregator2", "2025-02-11", 3.9f, "Moderate service", "file2"));
        assessments.add(new TaxiServiceQualityAssessment(3L, "TaxiAggregator3", "2025-02-12", 4.8f, "Excellent service", "file3"));
    }

    @AfterEach
    void tearDown() {
        assessments.clear();
    }

    @Test
    void findAllSuccess() {
        given(repository.findAll()).willReturn(assessments);

        List<TaxiServiceQualityAssessment> actualAssessments = service.findAllForTest();

        assertThat(actualAssessments.size()).isEqualTo(assessments.size());

        verify(repository, times(1)).findAll();
    }

    @Test
    void findByIdSuccess() {
        TaxiServiceQualityAssessment assessment = assessments.get(0);

        given(repository.findById(1L)).willReturn(Optional.of(assessment));

        TaxiServiceQualityAssessment find = service.findForTest(1 + "");

        assertThat(find.getId()).isEqualTo(1);
        assertThat(find.getAggregatorName()).isEqualTo(assessment.getAggregatorName());
        assertThat(find.getDate()).isEqualTo(assessment.getDate());
        assertThat(find.getRating()).isEqualTo(assessment.getRating());
        assertThat(find.getComments()).isEqualTo(assessment.getComments());
        assertThat(find.getFile()).isEqualTo(assessment.getFile());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        given(repository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.findForTest(1 + ""));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void saveSuccess() {
        TaxiServiceQualityAssessment save = assessments.get(0);

        given(repository.save(save)).willReturn(save);

        TaxiServiceQualityAssessment saved = service.saveForTest(save);

        assertThat(saved.getAggregatorName()).isEqualTo(save.getAggregatorName());
        assertThat(saved.getDate()).isEqualTo(save.getDate());
        assertThat(saved.getRating()).isEqualTo(save.getRating());
        assertThat(saved.getComments()).isEqualTo(save.getComments());
        assertThat(saved.getFile()).isEqualTo(save.getFile());

        verify(repository, times(1)).save(save);
    }

    @Test
    void updateSuccess() {
        TaxiServiceQualityAssessment old = assessments.get(0);
        TaxiServiceQualityAssessment update = assessments.get(1);

        given(repository.findById(1L)).willReturn(Optional.of(old));
        given(repository.save(old)).willReturn(old);

        TaxiServiceQualityAssessment updated = service.updateForTest(1 + "", update);

        assertThat(updated.getId()).isEqualTo(1);
        assertThat(updated.getAggregatorName()).isEqualTo(update.getAggregatorName());
        assertThat(updated.getDate()).isEqualTo(update.getDate());
        assertThat(updated.getRating()).isEqualTo(update.getRating());
        assertThat(updated.getComments()).isEqualTo(update.getComments());
        assertThat(updated.getFile()).isEqualTo(update.getFile());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(old);
    }

    @Test
    void updateNotFound() {
        TaxiServiceQualityAssessment update = assessments.get(1);

        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.updateForTest(1 + "", update));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deleteSuccess() {
        TaxiServiceQualityAssessment assessment = assessments.get(0);

        given(repository.findById(1L)).willReturn(Optional.of(assessment));
        doNothing().when(repository).deleteById(1L);

        service.deleteForTest(1 + "");

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNotFound() {
        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.deleteForTest(1 + ""));

        verify(repository, times(1)).findById(1L);
    }
}

Этот тестовый класс предназначен для проведения интеграционных тестов на сервис оценки качества такси в контексте работы с базой данных. Интеграционные тесты проверяют правильность работы всего компонента (сервиса и репозитория) в связке, включая взаимодействие с реальной базой данных, настроенной через Spring.
Вот что делает каждый тест:
1. findAllSuccess()
Этот тест проверяет, что метод findAllForTest() возвращает все сохраненные объекты TaxiServiceQuality. Мы заранее сохраняем 3 объекта в базу данных в методе setUp(), и затем проверяем, что метод сервиса вернул их все.
Что проверяет: успешное получение всех оценок качества такси.
2. findByIdSuccess()
Этот тест проверяет, что метод find() находит объект по его ID. Мы проверяем, что возвращаемая оценка совпадает с ожидаемой, проверяя все её поля (ID, имя, рейтинг, обратную связь и файл).
Что проверяет: успешный поиск по ID.
3. findByIdNotFound()
Этот тест проверяет, что если попытаться найти объект с несуществующим ID (например, "999"), то будет выброшено исключение ObjectNotFoundException.
Что проверяет: обработку ошибки при попытке найти несуществующий объект.
4. saveSuccess()
Этот тест проверяет, что метод saveForTest() сохраняет новый объект в базе данных и правильно возвращает его. Мы создаем новый объект TaxiServiceQuality, сохраняем его через сервис и проверяем, что все поля сохраненного объекта совпадают с переданными значениями.
Что проверяет: успешное сохранение нового объекта.
5. updateSuccess()
Этот тест проверяет, что метод updateForTest() обновляет существующий объект в базе данных. Мы создаем новый объект с такими же полями, как и старый, но с измененными значениями. Затем проверяем, что обновленный объект совпадает с тем, что мы передали в метод обновления.
Что проверяет: успешное обновление объекта по ID.
6. updateNotFound()
Этот тест проверяет, что если попытаться обновить объект с несуществующим ID (например, "999"), то будет выброшено исключение ObjectNotFoundException.
Что проверяет: обработку ошибки при попытке обновить несуществующий объект.
7. deleteSuccess()
Этот тест проверяет, что метод deleteForTest() удаляет объект из базы данных. Мы удаляем объект с ID "1" и затем проверяем, что при попытке найти его снова с помощью метода find(), выбрасывается исключение ObjectNotFoundException.
Что проверяет: успешное удаление объекта.
8. deleteNotFound()
Этот тест проверяет, что если попытаться удалить объект с несуществующим ID (например, "999"), то будет выброшено исключение ObjectNotFoundException.
Что проверяет: обработку ошибки при попытке удалить несуществующий объект.
Применение:
Этот тестовый класс проверяет основные операции с данными в контексте интеграции с базой данных, а именно: создание, чтение, обновление и удаление объектов типа TaxiServiceQuality. Тесты используют Spring Boot для создания контекста приложения и выполнения реальных операций с базой данных (интеграционные тесты).
Все операции выполняются через сервисный слой, который обрабатывает логику бизнес-уровня, а репозиторий взаимодействует с базой данных.
Важные аспекты:
Интеграционные тесты: В отличие от юнит-тестов, интеграционные тесты проверяют взаимодействие компонентов системы и часто используют реальную базу данных или её подстановку.
Spring Boot: Используется аннотация @SpringBootTest для запуска контекста Spring и выполнения тестов с подключением к реальной базе данных.

<h1>Пользовательский интерфейс</h1>

User Flow для пользователя

![image](https://github.com/user-attachments/assets/84f435bd-fce7-4061-8ec1-592ee13636df)

User Flow для администратора

![image](https://github.com/user-attachments/assets/d7b5db6d-0976-421f-86f3-c76882117b47)

<h1>Примеры экранов UI</h1>

Страница авторизации в системе

![image](https://github.com/user-attachments/assets/217e3e87-effd-47ed-afe7-0326d3b909f2)

Страница регистрации

![image](https://github.com/user-attachments/assets/54e11edd-d217-462e-845c-2eb07697aca4)

Главная страница

![image](https://github.com/user-attachments/assets/bd830671-9d20-4045-a3d1-2565ed2085d9)

Инфографика

![image](https://github.com/user-attachments/assets/73f19eff-d0e7-4c86-afa4-2a5b82deb334)

Рейтинг

![image](https://github.com/user-attachments/assets/3400cdbf-3b99-4646-9c2d-dc0574553200)

Анкеты

![image](https://github.com/user-attachments/assets/64d85540-c0b8-4403-8b22-81815b3e6a58)

<h1>Безопасность</h1>

Конфигурация SecurityFilterChain

https://github.com/txrch/riopk/blob/main/taxirate/taxirate/src/main/java/com/app/security/SecurityConfiguration.java

Конфигурация BCryptPasswordEncoder

https://github.com/txrch/riopk/blob/main/taxirate/taxirate/src/main/java/com/app/security/SecurityConfiguration.java

Создание JWT

https://github.com/txrch/riopk/blob/main/taxirate/taxirate/src/main/java/com/app/security/JwtProvider.java

<h1>Развертывание</h1>

Dockerfile для сервиса TaxiServiceQualityAssessment

https://github.com/txrch/riopk/blob/main/taxirate/taxirate/src/Dockerfile

Этот Dockerfile описывает процесс сборки и запуска Java приложения, используя Maven для сборки и OpenJDK для выполнения. Первая фаза: FROM maven:3.9.8-eclipse-temurin-21 as maven-builder Это стадия использует образ Maven, который включает Java 21 от Eclipse Temurin. Имя этой стадии – maven-builder. Используя многоэтапную сборку, минимизируется размер итогового образа, копируя только необходимые артефакты. Копирование исходников и POM файла: COPY src /app/src COPY pom.xml /app Исходный код приложения и файл pom.xml копируются в директорию /app в контейнере. Это нужно для того, чтобы Maven мог выполнить сборку проекта. Рабочая директория и сборка с помощью Maven: WORKDIR /app RUN mvn clean install -U -DskipTests Устанавливается рабочая директория /app. Команда mvn clean install устанавливает все зависимости, компилирует код, упаковывает проект в JAR-файл, но пропускает тесты благодаря -DskipTests. Вторая фаза: Java Runtime Environment Использование OpenJDK для выполнения: FROM openjdk:21 Эта стадия основывается на образе OpenJDK 21. Здесь мы будем только запускать наше приложение, без всего лишнего, что используется в стадии сборки. Копирование артефакта: COPY --from=maven-builder /app/target /taxi-quality-assessment-service-1.0.0-SNAPSHOT.jar /app/app.jar Готовый JAR-файл копируется из предыдущей стадии в новую директорию /app под именем app.jar. Рабочая директория: WORKDIR /app Устанавливает рабочую директорию на /app. Открытие порта: EXPOSE 8080 Указание Docker, что контейнер будет слушать на порту 8080. Это типично для Spring Boot приложений. Команда для запуска: CMD ["java", "-jar", "app.jar"] Устанавливает команду, которая запускается при старте контейнера. В данном случае, это выполнение JAR-файла приложения с помощью java -jar. Dockerfile для сервиса paper-service схож по своей структуре с файлом для user-service.

Dockerfile для сервиса TaxiAggregatorService

https://github.com/txrch/riopk/blob/main/taxirate/taxirate/src/Dockerfile

<h1>Руководство пользователя</h1>

После успешного запуска веб-приложения, оператор встречается с страницей авторизации (рисунок 1), на которой он должен ввести свои логин и пароль. После ввода правильного логина и пароля, оператор нажимает на кнопку «Войти», откуда он перенаправляется на главную страницу приложения.

![image](https://github.com/user-attachments/assets/217e3e87-effd-47ed-afe7-0326d3b909f2)

Рисунок 1 – Страница авторизации

Если у оператора нет аккаунта, то он может создать новый, нажав на кнопку «Зарегистрироваться». Страница регистрации представлена на рисунке 2.

![image](https://github.com/user-attachments/assets/54e11edd-d217-462e-845c-2eb07697aca4)

Рисунок 2 – Страница регистрации

После успешной авторизации оператор переходит на главную страницу. Главная страница представлена на рисунке 3.

![image](https://github.com/user-attachments/assets/bd830671-9d20-4045-a3d1-2565ed2085d9)

Рисунок 3 – Главная страница

Для подробного просмотра и получения дополнительной информации о об агрегаторах оператор может нажать на кнопку «Инфографика», и он увидит данные об агрегаторах. На рисунке 4 представлен обзор процесса.

![image](https://github.com/user-attachments/assets/73f19eff-d0e7-4c86-afa4-2a5b82deb334)

Рисунок 4 – Инфографика

Для работы с данными агрегаторов и оценками пользователей оператор может перейти во вкладку «Рейтинг». На рисунке 5 представлено окно рейтинга.

![image](https://github.com/user-attachments/assets/3400cdbf-3b99-4646-9c2d-dc0574553200)

Рисунок 5 – Рейтинг

Для просмотра анкет нужно перейти на вкладку «Анкеты» в блоке навигации, и оператору будут доступны анкеты. На рисунке 6 представлены комментарии процесса.

![image](https://github.com/user-attachments/assets/64d85540-c0b8-4403-8b22-81815b3e6a58)

Рисунок 6 – Анкеты
