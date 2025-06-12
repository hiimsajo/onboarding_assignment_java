# onboarding_assignment_java
바로인턴 백엔드 개발 온보딩 과제 Java

회원가입과 로그인 기능 개발

1. 회원가입("/users/signup")
  
- requestBody
```
{
  "username": "string",
  "nickname": "string",
  "password": "string"
}
```
- responseBody
```
{
  "username": "string",
  "nickname": "string",
  "authorities": [
    {
      "authorityName": "string"
    }
  ]
}
```


2. 로그인("/users/sign")

- requestBody
```
{
  "username": "string",
  "password": "string"
}
```
- responseBody
```
{
 "token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyUm9sZSI6IlJPTEVfZ2FtamFMb3ZlciIsInN1YiI6IlVTRVIiLCJpYXQiOjE3Mzk3MjQzNDYsImV4cCI6MTczOTgxMDc0Nn0.5DK1KH10KS5jij4ZFpiDaq9lIAmuzl5XmBCzqNyvXQo"
}
```


3. Swagger UI 페이지(http://13.209.74.95:8080/swagger-ui/index.html) 이 주소로 확인 및 테스트 가능
   
![image](https://github.com/user-attachments/assets/ffa26e94-2160-467e-a74d-5f71b5983782)
![image](https://github.com/user-attachments/assets/6b902b01-bbc8-4a56-a1a8-aa456c83581e)
![image](https://github.com/user-attachments/assets/51c3096e-e747-4736-942a-91c27f7edbe5)
![image](https://github.com/user-attachments/assets/e752440c-d847-472d-88e9-a310394aa4b6)
![image](https://github.com/user-attachments/assets/687cbd66-781f-4808-8cb5-8d0e467c99c8)
