import os

class Config:
    SECRET_KEY = os.getenv("SECRET_KEY", "spuperdupersecret")
    SQLALCHEMY_DATABASE_URI = os.getenv(
        "DATABASE_URL", "sqlite:///task_manager.db"
    )
    SQLALCHEMY_TRACK_MODIFICATIONS = False