### 1. Register
##### POST `/register`
#### Request
```json
{
  "email": "foo@bar.com",
  "password": "password123",
  "displayName": "Foo Bar"
}
```

#### Response
###### Success
201 Created

###### Failure
400 Bad Request

`This display name already exists.`

`This email already exists.`

---

### 2. Verify
##### GET `/verify`
#### Request
`http://localhost:8080/verify?email=foo@bar.com&key=abc123`

#### Response
###### Success
200 OK

###### Failure
400 Bad Request

---

### 3. Login
##### POST `/login`
#### Request
```json
{
  "email": "foo@bar.com",
  "password": "password123"
}
```

#### Response
##### Success
200 OK

##### Failure
401 Unauthorized

`Wrong email or password. Please try again.`

`Please verify your email.`

---

### 4. Logout
##### POST `/logout`
#### Request
N/A

#### Response
###### Success
200 Ok

##### Failure
401 Unauthorized

`You must be logged in to log out.`