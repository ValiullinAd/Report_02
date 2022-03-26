package ru.netology.delivery.data;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@Data
@AllArgsConstructor
public class DataGenerator {
    public static Faker faker = new Faker(new Locale("ru"));

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
    }

    public static String generateCity() {
        final String[] cities = {"Москва", "Казань", "Омск", "Уфа", "Томск","Кострома"};
        Random random = new Random();
        int index = random.nextInt(cities.length);
        return cities[index];
    }

    public static String generateName() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generatePhone() {
        return faker.phoneNumber().phoneNumber();
    }

    public static class Generate {

        public static UserInfo generateUser() {
            return new UserInfo(generateCity(), generateName(), generatePhone());
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}