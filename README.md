# School Registration System

School registration system allows students to register to an existing course via REST API calls.

### Run Local 
```
$ mvn clean package
$ cd docker
$ docker-compose up
```

### Tests
```
mvn test
```

### Docker

All docker related material is inside the directory under the same name. Files included

- docker-compose.yml 
- Dockerfile
- init.sql (database initialization)

### Actuator

It´s used to verify the correct behavior for the actuator endpoint which is used to know if our application
is running correctly:

```
GET /actuator/health
```

Default packages built for this project:

- /config
- /controller
- /dto
- /exception
- /mapper
- /repository
- /service

### StudentController

StudentController is a CRUD endpoint that allows the creation/modification and deletion of a student.
These are the service exposed by StudentController:

- GET /students
- POST /students
- PUT /students/{id}
- Delete /students/{id}

Sample payload for POST /students
```
{
  "firstname": "john",
  "lastname": "doe"
}
```

Sample response for GET /students
```
[
    {
        "id": 1,
        "firstname": "Anne",
        "lastname": "Bower"
    },
    {
        "id": 2,
        "firstname": "Frank",
        "lastname": "Butler"
    },
    {
        "id": 3,
        "firstname": "Gordon",
        "lastname": "Chapman"
    }
]
```


### CourseController

CourseController is a CRUD endpoint that allows the creation/modification and deletion of a course.
These are the service exposed by CourseController:

- GET /courses
- POST /courses
- PUT /courses/{id}
- Delete /courses/{id}

Sample payload for POST /courses
```
{
  "course-name": "How to live forever"
}
```

Sample response for GET /courses
```
[
    {
        "id": 1,
        "course-name": "Wealth and Econometrics in Philosophical Psychology"
    },
    {
        "id": 2,
        "course-name": "Psychology, Text, and Justice in the American Imaginary"
    },
    {
        "id": 3,
        "course-name": "Logic and Seniors in Politics, Markets, and Society"
    }
]
```



### RegistrationController

RegistrationController is a CRUD endpoint that allows the creation/modification and deletion 
of student-course registration item.
These are the service exposed by RegistrationController:

- GET /registration-courses
- GET /registration-courses/{id}
- POST /registration-courses
- GET /registration-courses/students?course-id={id}
- GET /registration-courses/courses?student-id={id}
- GET /registration-courses/courses/without-students
- GET /registration-courses/students/without-courses

sample payload
```
{
  "student": {
    "id": 1
  },
  "courses": [
    {
      "id": 1
    }
  ]
}
```

Sample response for GET /registration-courses
```
[
    {
        "id": 1,
        "student": {
            "id": 6,
            "firstname": "Irene",
            "lastname": "Allan"
        },
        "courses": [
            {
                "id": 3,
                "course-name": "Logic and Seniors in Politics, Markets, and Society"
            }
        ]
    }
]
```

Sample response for GET registration-courses/students?course-id={id}
```
[
    {
        "id": 6,
        "firstname": "Irene",
        "lastname": "Allan"
    }
]
```

Sample response for GET registration-courses/courses?student-id={id}
```
[
    {
        "id": 3,
        "course-name": "Logic and Seniors in Politics, Markets, and Society"
    }
]
```

Sample response for GET /registration-courses/courses/without-students
```
[
    {
        "id": 1,
        "course-name": "Wealth and Econometrics in Philosophical Psychology"
    },
    {
        "id": 2,
        "course-name": "Psychology, Text, and Justice in the American Imaginary"
    },
    {
        "id": 4,
        "course-name": "Drones and Biotechnology in History of Science & Medicine"
    },
    {
        "id": 5,
        "course-name": "Service, Video, and Devices in the History of Technology"
    },
    {
        "id": 6,
        "course-name": "Probability and Materials in East Asian Literature and Film"
    },
    {
        "id": 7,
        "course-name": "Justice, Space, and Rotations"
    },
    {
        "id": 8,
        "course-name": "Capitalism and Liberty in Environmental Health"
    },
    {
        "id": 9,
        "course-name": "Cybersecurity, Nutrition, and Abstraction"
    },
    {
        "id": 10,
        "course-name": "Domesticity, Quebec, and Genders"
    }
]
```

### ReleaseNotes

Version 0.0.1 initial version of school registration system.
