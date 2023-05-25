#sudo apt-get update
#sudo apt-get install python3.8
#pip install torch torchvision
#pip install ftfy regex tqdm
#pip install git+https://github.com/Lednik7/CLIP-ONNX.git
#pip install git+https://github.com/openai/CLIP.git
#pip install onnxruntime-gpu
#wget https://clip-as-service.s3.us-east-2.amazonaws.com/models/onnx/ViT-B-32/visual.onnx
#wget https://clip-as-service.s3.us-east-2.amazonaws.com/models/onnx/ViT-B-32/textual.onnx
#pip install fastapi
#pip install "uvicorn[standard]"
#pip install python-multipart
#pip install dependency-injector
#uvicorn main:app --reload

import os
import torch
import clip
import numpy as np
import jwt
from fastapi import FastAPI, Depends
from PIL import Image
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from fastapi.security import OAuth2PasswordBearer
from typing_extensions import Annotated

model, preprocess = clip.load("ViT-B/32", device="cpu", jit=False)

app = FastAPI()

origins = [
    "http://localhost:3000",
    "http://localhost:3001",
    "http://localhost:3002",
    "http://localhost:3003"
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

class Item(BaseModel):
    name: str
    url: str

class ImageRequest(BaseModel):
    image: str

@app.get("/search={text}")
def search(text, token: Annotated[str, Depends(oauth2_scheme)]):
    images = []
    names = []

    for filename in [filename for filename in os.listdir("./uploads/") if filename.endswith(".png") or filename.endswith(".jpg")]:
        name = filename
        names.append(name)
        image = preprocess(Image.open(os.path.join("uploads", filename)))
        images.append(image)
    image_input = torch.tensor(np.stack(images)).cpu()
    text_input = clip.tokenize(text).cpu()
    
    with torch.no_grad():
        image_features = model.encode_image(image_input)
        text_features = model.encode_text(text_input)
        
    image_features /= image_features.norm(dim=-1, keepdim=True)
    text_features /= text_features.norm(dim=-1, keepdim=True)
    similarity = text_features @ image_features.T

    result = []

    for index in range(similarity.shape[1]):
        if(similarity[0][index] > 0.23):
            result.append(Item(name=names[index], url="http://localhost:8080/files/" + names[index]))
    return result
    
@app.post("/similarity")
def similarity(body: ImageRequest, token: Annotated[str, Depends(oauth2_scheme)]):
    accuracy = []
    names = []
    
    findImage = preprocess(Image.open("./uploads/" + body.image.split("/")[len(body.image.split("/")) - 1])).unsqueeze(0).cpu()
    with torch.no_grad():
        image_features1 = model.encode_image(findImage).float()
    image_features1 /= image_features1.norm(dim=-1, keepdim=True)
    for file in os.listdir("./uploads/"):
        if(file != body.image.split("/")[len(body.image.split("/")) - 1]):
            print(file)
            image = preprocess(Image.open("./uploads/" + file)).unsqueeze(0).cpu()
            with torch.no_grad():
                image_features2 = model.encode_image(image).float()
            
            image_features2 /= image_features2.norm(dim=-1, keepdim=True)
            similarity = image_features1.cpu().numpy() @ image_features2.cpu().numpy().T
            print(similarity)
            accuracy.append(similarity[0][0])
            names.append(file)
    flag = True
    result = []
    while(flag):
        flag = False
        for i in range(1, len(accuracy)):
            print(accuracy[i-1])
            print(accuracy[i])
            if(accuracy[i-1] < accuracy[i]):
                flag = True
                t = accuracy[i-1]
                accuracy[i-1] = accuracy[i]
                accuracy[i] = t
                t = names[i-1]
                names[i-1] = names[i]
                names[i] = t
    for index in range(len(names)):
        if(accuracy[index] > 0.5):
            result.append(Item(name=names[index], url="http://localhost:8080/files/" + names[index]))
    return result
    
# @app.get("/search_onnx")
# def search_onnx():
#     onnx_model = clip_onnx(None)
#     onnx_model.load_onnx(visual_path="visual.onnx",
#                         textual_path="textual.onnx",
#                         logit_scale=100.0000) # model.logit_scale.exp()
#     onnx_model.start_sessions(providers=["CPUExecutionProvider"])
    
#     image = preprocess(Image.open("images/cat.jpg")).unsqueeze(0).cpu() # [1, 3, 224, 224]
#     image_onnx = image.detach().cpu().numpy().astype(np.float32)

#     print("image")
#     text = clip.tokenize("a cat").cpu() # [3, 77]
#     text_onnx = text.detach().cpu().numpy().astype(np.int64)
    
#     print("text")
    
#     image_features = onnx_model.encode_image(image_onnx)
#     text_features = onnx_model.encode_text(text_onnx)
#     similarity = text_features @ image_features.T

    
#     logits_per_image, logits_per_text = onnx_model(image_onnx, text_onnx)
#     probs = logits_per_image.softmax(dim=-1).detach().cpu().numpy()
#     print("probs")
#     return str(similarity)