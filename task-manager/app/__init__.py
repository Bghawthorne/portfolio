from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_jwt_extended import JWTManager

db = SQLAlchemy()
jwt = JWTManager()

def create_app():
    app = Flask(__name__)
    app.config.from_object("app.config.Config")
    app.config["JWT_SECRET_KEY"] = "lockedDown" 

    db.init_app(app)
    jwt.init_app(app)

    # Register blueprints
    from app.routes.tasks import tasks_bp
    from app.routes.auth import auth_bp
    app.register_blueprint(tasks_bp, url_prefix="/api/tasks")
    app.register_blueprint( auth_bp,url_prefix="/api/auth")

    return app