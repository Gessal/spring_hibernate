package hiber;

import hiber.config.AppConfig;
import hiber.dao.CarDaoImp;
import hiber.dao.CarsDao;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

import static java.util.Objects.nonNull;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);
      /* Создаю объекты машин */
      Car c1 = new Car("car1", 1);
      Car c2 = new Car("car2", 2);
      Car c3 = new Car("car3", 3);
      Car c4 = new Car("car4", 4);
      /* Добавляю объекты машин в базу */
      CarService carService = context.getBean(CarService.class);
      carService.add(c1);
      carService.add(c2);
      carService.add(c3);
      carService.add(c4);

      /* Создаю пользователей с машинами и добавляю в базу */
      UserService userService = context.getBean(UserService.class);
      userService.add(new User("User1", "Lastname1", "user1@mail.ru", c1));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", c2));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru", c3));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru", c4));

      /* Выбираю пользователя с машиной (name = car3; series = 3) */
      List<User> users = userService.listUsersWithCar("car3", 3);
      for (User user : users) {
         Car car;
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car name= " + (nonNull(car = user.getCar()) ? car.getName() : "none"));
         System.out.println("Car series= " + (nonNull(car = user.getCar()) ? car.getSeries() : "none"));
         System.out.println();
      }

      context.close();
   }
}
