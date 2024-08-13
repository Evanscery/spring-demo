# RESTful APIs
  This project is a prototype code that strips business data from an actual project.

  The main content is a backend interface package based on the spring-boot and RESTful standards.

  The primary goal is to provide interfaces for the frontend to implement high-concurrency coupon collection and points-based lottery.

  The frontend accesses the interface via HTTP requests.
  

Implementation     | Implementation
:----- | :-----
Unified return interface format  |Common utility classes
Global exception handling      |Interface document format
Parameter non-null validation |Interface security
High concurrency handling|Table design and batch task logic

Example of a response data packet
```javascript
{
	"status": 200,
	"msg": "OK",
	"data": {
		"ibean_balance": 480
	}
}
```

```
Local debugging port open at: http://localhost:8080
Using data source: MySql
```

## Interface List

### 1 Receive coupons, welfare activities
### 2 Set coupon title, subtitle, and icon
### 3 Get the banner for a specific interface (including clickable images and their redirect links and types)
### 4 Retrieve the list of all welfare activities from the database
### 5 Retrieve user points from the database
### 6 Retrieve prize pool information from the database (prize list, id, description, icon)
### 7 Use points to enter a lottery and return the lottery result (empty if unsuccessful)

### API Document Example
#### /Get Reward Information

```text
This interface is used to retrieve prize information from the prize pool.
It returns a list of prizes,
including icon, name, and winning probability.

```

##### Interface Status

> Completed

##### Interface URL

> http://localhost:8080/raffle/rewards?token=12asd2dasd2

##### Request Method

> GET

##### Content-Type

> none

##### Request Header Parameters

| Parameter Name | Example Value | Parameter Type | Required | Description |
| --- | --- | ---- | ---- | ---- |
| Authorization | - | String | Yes | Bearer token |

##### Request Query Parameters

| Parameter Name | Example Value | Parameter Type | Required | Description |
| --- | --- | ---- | ---- | ---- |
| token | 12asd2dasd2 | String | Yes | User token |

##### Authentication Method

> Bearer Token

> Add the Authorization parameter in the Header, with the value being a space followed by the access token after Bearer.

> Authorization: Bearer your_access_token

##### Success 200

```javascript
{
	"status": 27,
	"msg": "ok",
	"data": {
		"rewards": [
			{
				"icon": "http://dummyimage.com/100x100",
				"description": "10 Yuan WeChat instant deduction",
				"percentage": 2700
			}
		]
	}
}
```

| Parameter Name | Example Value | Parameter Type | Description |
| --- | --- | ---- | ---- |
| status | 24 | Number | Status |
| msg | ok | String | Message |
| data | - | Object | - |
| data.ibean_balance | 3 | Number | IBEAN balance |

##### Failure 404

```javascript
{
	"status": "404",
	"msg": "Resource not found",
	"data": null
}
```

| Parameter Name | Example Value | Parameter Type | Description |
| --- | --- | ---- | ---- |
| status | 404 | String | Status information |
| msg | Resource not found | String | Message |
| data | - | Null | - |

### API Documentation URL
> https://doc.apipost.net/docs/2f71cb142c66000
