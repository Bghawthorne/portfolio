import pytest
from app import create_app, db
from flask_jwt_extended import create_access_token

@pytest.fixture
def app():
    app = create_app()
    app.config["TESTING"] = True
    app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///:memory:"
    with app.app_context():
        db.create_all()
        yield app
        db.drop_all()

@pytest.fixture
def client(app):
    return app.test_client()

@pytest.fixture
def auth_token(app):
    # Pre-generate a token for "Test"
    with app.app_context():
        return create_access_token(identity="Test")

def test_create_task(client, auth_token):
    response = client.post(
        "/api/tasks/",
        json={"title": "Test JWT Task", "description": "Testing JWT"},
        headers={"Authorization": f"Bearer {auth_token}"}
    )
    data = response.get_json()
    assert response.status_code == 201
    assert data["title"] == "Test JWT Task"
    assert data["owner"] == "Test"

def test_get_tasks(client, auth_token):
    # Create a task first
    client.post(
        "/api/tasks/",
        json={"title": "Another Task"},
        headers={"Authorization": f"Bearer {auth_token}"}
    )

    response = client.get(
        "/api/tasks/",
        headers={"Authorization": f"Bearer {auth_token}"}
    )
    data = response.get_json()
    assert response.status_code == 200
    assert len(data) == 1
    assert data[0]["title"] == "Another Task"
