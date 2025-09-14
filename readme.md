تمام 👌، جهزتلك نسخة كاملة من **README.md** تقدر تحطها في الريبو بتاعك على GitHub:

---

📄 **README.md**

```markdown
# 🎬 Spring Boot Movie App with JWT Authentication

This project is a **Spring Boot 3 application** that demonstrates how to use **Spring Security + JWT (JSON Web Token)** for authentication and authorization.  
It includes:
- User registration & login
- Role-based access control (`USER`, `ADMIN`)
- JWT authentication & validation
- Secured Movie API

---

## 🚀 Features
- ✅ Register a new user with username, password, and role  
- ✅ Login to get a JWT token  
- ✅ Access protected endpoints using JWT  
- ✅ Role-based access (USER vs ADMIN)  
- ✅ Stateless authentication (no sessions)  

---

## 📂 Project Structure
```

src/main/java/com/fawry/movie
├── config
│   └── SecurityConfig.java        # Spring Security configuration
├── controllers
│   ├── AuthController.java        # Authentication (login/register)
│   └── MovieController.java       # Protected movie API
├── entities
│   ├── User.java                  # User entity
│   └── Role.java                  # Enum for roles
├── secruity
│   ├── CustomUserDetailsService.java
│   ├── JwtUtil.java
│   └── JwtRequestFilter.java
├── services
│   └── UserService.java           # Business logic for user
└── MovieApplication.java          # Main entry point

````

---

## ⚙️ Requirements
- Java 17+
- Maven 3.8+
- MySQL (or H2 for testing)
- Postman (for testing APIs)

---

## 🛠️ Setup & Run

### 1. Clone the repo
```bash
git clone https://github.com/<USERNAME>/<REPO_NAME>.git
cd <REPO_NAME>
````

### 2. Configure Database

Edit **`application.properties`**:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/moviedb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

App will run at:
👉 `http://localhost:8080`

---

## 🔑 API Endpoints

### 1️⃣ Register a new user

**POST** `http://localhost:8080/auth/register`

Body:

```json
{
  "username": "admin",
  "password": "admin123",
  "role": "ADMIN"
}
```

---

### 2️⃣ Login to get JWT

**POST** `http://localhost:8080/auth/login`

Body:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Response:

```json
{
  "jwt": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### 3️⃣ Access Movies (JWT required)

**GET** `http://localhost:8080/api/movies`

Headers:

```
Authorization: Bearer <your_jwt_token>
```

Response:

```json
[
  { "id": 1, "title": "Inception", "year": 2010 },
  { "id": 2, "title": "The Matrix", "year": 1999 }
]
```

---

### 4️⃣ Admin-only endpoint

**GET** `http://localhost:8080/api/admin/dashboard`

Headers:

```
Authorization: Bearer <your_jwt_token>
```

If user is not `ADMIN`, response will be `403 Forbidden`.

---

## 🧪 Testing with Postman

1. Register a new user.
2. Login with the same credentials → copy the JWT.
3. Use the JWT in `Authorization` header (`Bearer <jwt>`) for accessing movies.
4. Try accessing admin routes with a USER role → should get **403 Forbidden**.

---

## 📝 License

This project is for learning purposes.
Feel free to fork and modify. 🚀

```

---

تحب أزودلك كمان جزء عن **troubleshooting** (زي لو حصل 403 أو مشكلة في الـ JWT) ولا تسيبه كده خفيف وبسيط؟
```
