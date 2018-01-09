# Lab 4:

(Note: not really lab 4 but soit; btw my vm is running on IP: 192.168.56.101; I prefer ssh'n and running other tools on my host so if I use that IP you should use localhost, if I use localhost it is on the vm and you should also use localhost! )

# Step 1: Mongo running in a standalone container called mymongo

```
# create Dockerfile or
# search on docker hub 
docker search mongo
docker pull mongo

# run container with fancy stuff -> but less is more here
sudo docker run -e MONGODB_USER="root" -e MONGODB_PASS="password" -e MONGODB_DATABASE="nieuwmongo" -p 27017:27017 -d --name mymongo mongo

# This is enough
sudo docker run -p 27017:27017 -d --name mymongo mongo

# check the mongodb
mongo --host localhost --port 27017


... 

```

# Step 2: Creating Spring boot application (no messaging all REST)

application.properties
```
server.port=2222

#spring.datasource.url=jdbc:mongodb://localhost:27017/mynewdb
#spring.datasource.username=root
#spring.datasource.password=password

spring.data.mongodb.host=mymongo
spring.data.mongodb.port=27017
spring.data.mongodb.database=composeversie
```

UserController
```
@RestController
public class UserController {

        @Autowired
        private UserRepository repository;

        @GetMapping(value="/user")
        public User getUserByFirstName(@RequestParam("username") String userName) {
                User user = repository.findByUserName(userName);
                return user;
        }

        @GetMapping(value="/test")
        public List<User> getUsersUsernameC(){
                List<User> users = repository.findByUserNameStartingWith("C");
                return users;
        }
}
```

UserRepository
```
public interface UserRepository extends CrudRepository<User, String>{

        //@Query("{userName: ?0}")

        //List<User> getUsersWithUsernameC(String userName);

        User findByUserName(String userName);
        User findByFirstName(String firstName);
        User findByLastName(String lastName);

        List<User> findByUserNameStartingWith(String regexp);

}
```

User
```
@Document(collection="User")
public class User {

        @Id
        public String userId;

        public String userName;
        public String firstName;
        public String lastName;
        public String getUserId() {
                return userId;
        }
        // getters & setters
}
```

App
```
@SpringBootApplication
public class XMongoApplication {

        private static final Logger log = LoggerFactory.getLogger(XMongoApplication.class);

        public static void main(String[] args) {
                SpringApplication.run(XMongoApplication.class, args);
        }

        @Bean
        public CommandLineRunner demo(UserRepository repository) {
                return (args) -> {
                        // save a couple of customers
                        repository.save(new User("1", "Ciberth", "Thomas", "Clauwaert"));
                        repository.save(new User("2", "Maser00", "Aaron", "M"));
                        repository.save(new User("3", "Cibje", "Th", "Cl"));

                        // fetch all users
                        log.info("Users found with findAll():");
                        log.info("-------------------------------");
                        for (User u : repository.findAll()) {
                                log.info(u.toString());
                        }
                        log.info("");


                        // fetch Users by username c
                        log.info("Users found with username starting with c:");
                        log.info("--------------------------------------------");
                        for (User u : repository.findByUserNameStartingWith("C")) {
                                log.info(u.toString());
                        }
                        log.info("");
                };
        }

}
```

# Step 3: Running app and docker in different containers

Create Dockerfile & build with ``docker build -t myapp .``

## Contents of Dockerfile

```
FROM openjdk:8-jdk-alpine
ENTRYPOINT ["java", "-jar", "myapp-0.0.1-SNAPSHOT.jar"]

```

## Run

``docker run -d -p 2222:2222  --name cmyapp myapp``