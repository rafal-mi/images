import os
import dotenv
import httpx
import json

dotenv.load_dotenv()

API_KEY = os.getenv("API_KEY")
BASE_URL = os.getenv("BASE_URL")

headers = {
    "Authorization": f"Client-ID {API_KEY}"
}

URL = f"{BASE_URL}/search/photos"
params = {
    "query": "serval",
    "per_page": 10
}

response = httpx.get(URL, headers=headers, params=params)

print(response.status_code)

json_obj = response.json()

json_str = json.dumps(json_obj, indent=2)

print(json_str)



