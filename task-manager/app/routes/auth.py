from flask import Blueprint, request, jsonify
from flask_jwt_extended import create_access_token
#from app.models import User  
from werkzeug.security import check_password_hash

auth_bp = Blueprint("auth", __name__)

@auth_bp.route("/login", methods=["POST"])
def login():
    username = request.json.get("username")

    access_token = create_access_token(identity=username)
    return jsonify(access_token=access_token), 200
