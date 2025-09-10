# Task Manager API

A **Flask-based RESTful API** for managing tasks, featuring JWT-based authentication. This project demonstrates backend development skills with Python, Flask, SQLAlchemy, and automated testing — perfect for a portfolio or professional showcase.

---

## 🛠 Features

* Create, read, update, and delete tasks (CRUD)
* Task fields: `title`, `description`, `status`, `owner`, `created_at`
* JWT-based authentication to secure endpoints
* SQLite database for lightweight storage
* Unit tests using `pytest`
* Fully structured for maintainability and scalability

---

## 💻 Tech Stack

* **Python 3.12**
* **Flask** – lightweight web framework
* **Flask-SQLAlchemy** – ORM for database operations
* **Flask-JWT-Extended** – secure token-based authentication
* **SQLite** – relational database
* **Pytest** – testing framework

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Bghawthorne/portfolio.git
cd task-manager
```

### 2. Create and activate a virtual environment

```powershell
python -m venv venv
.\venv\Scripts\activate
```

### 3. Install dependencies

```bash
pip install -r requirements.txt
```

### 4. Run the application

```powershell
python app/main.py
```

Visit [http://127.0.0.1:5000](http://127.0.0.1:5000) to test endpoints.

---

## 📝 API Endpoints

### Authentication

| Method | Endpoint          | Description              |
| ------ | ----------------- | ------------------------ |
| POST   | `/api/auth/login` | Returns JWT access token |

**Example Login Request:**

```json
{
  "username": "Brian",
  "password": "mypassword"
}
```

**Response:**

```json
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### Task Management (JWT Protected)

| Method | Endpoint          | Description         |
| ------ | ----------------- | ------------------- |
| GET    | `/api/tasks/`     | Get all tasks       |
| POST   | `/api/tasks/`     | Create a new task   |
| PUT    | `/api/tasks/<id>` | Update a task by ID |
| DELETE | `/api/tasks/<id>` | Delete a task by ID |

**Authorization Header:**

```
Authorization: Bearer <your-jwt-token>
```

**Example POST JSON:**

```json
{
    "title": "Finish Portfolio Project",
    "description": "Write README and tests"
}
```

> Owner is automatically set from the JWT identity.

---

## 🧪 Running Tests

```powershell
pytest -s -v
```

* Runs all unit tests in `tests/` folder
* `-v` enables verbose output

Tests cover JWT authentication and CRUD operations.

---

## 📈 Skills Demonstrated

* Python backend development and REST API design
* JWT-based authentication and secure endpoints
* SQLAlchemy database modeling
* Automated testing with pytest
* Project structure for maintainability and scalability

---

## ⚡ Next Steps / Enhancements

* Add refresh tokens for longer-lived sessions
* Implement user registration with hashed passwords
* Integrate a front-end UI (React/Vue)
* Switch SQLite to PostgreSQL for production-ready deployment
* Dockerize the application for containerized deployment
