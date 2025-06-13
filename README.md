# onboarding_assignment_java
바로인턴 백엔드 개발 온보딩 과제 Java

회원가입과 로그인 기능 개발

1. 회원가입("/users/signup")
![회원가입1](https://github.com/user-attachments/assets/d6de0324-542f-49ad-a12b-058434ae11b2)

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
![로그인](https://github.com/user-attachments/assets/7795a699-48d0-4d03-8f96-af2cd17d7fcc)


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

3. 접근권한 부여(권한 없을 시 예외)("/users/admin/{userId}/roles")
  ![접근권한없음](https://github.com/user-attachments/assets/7e3977a1-176d-4dc4-8e99-1bcdfdd2d908)
- responseBody
```
  {
    "error": {
        "code": "ACCESS_DENIED",
        "message": "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."
    }
}
```


4. Swagger UI 페이지(http://13.209.74.95:8080/swagger-ui/index.html) 이 주소로 확인 및 테스트 가능
   
![image](https://github.com/user-attachments/assets/ffa26e94-2160-467e-a74d-5f71b5983782)
![image](https://github.com/user-attachments/assets/6b902b01-bbc8-4a56-a1a8-aa456c83581e)
![image](https://github.com/user-attachments/assets/51c3096e-e747-4736-942a-91c27f7edbe5)
![image](https://github.com/user-attachments/assets/e752440c-d847-472d-88e9-a310394aa4b6)
![image](https://github.com/user-attachments/assets/687cbd66-781f-4808-8cb5-8d0e467c99c8)


5. JUnit Test

- 회원가입 테스트
![테스트_회원가입2](https://github.com/user-attachments/assets/f85d31d9-6864-4da9-b756-ddce69440e42)

- 존재하는 회원 테스트
![테스트_존재하는회원](https://github.com/user-attachments/assets/3e13e83f-88bd-427e-9d1e-258ee61328a8)

5. 데이터 잘 들어오는지 확인 H2 console 사용
![데이터](https://github.com/user-attachments/assets/fbad5c96-d49a-4e0e-9fe9-6b194b903242)


