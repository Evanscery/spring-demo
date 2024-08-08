# RESTful APIs
  这个项目是一个基于spring-boot和RESTful规范的后端接口包

  主要目的是为前端提供接口实现高并发的优惠券领取和积分抽奖
  
  前端通过HTTP请求访问接口
  

实现     | 实现
:----- | :-----
统一返回接口格式  |常用工具类
全局异常处理      |接口文档格式
参数常规非空校验|接口的安全
高并发处理|表设计和批量任务逻辑

返回数据包示例
```javascript
{
	"status": 200,
	"msg": "OK",
	"data": {
		"ibean_balance": 480
	}
}
```
## 接口列表

### 1 领取优惠券、福利活动
### 2 设置优惠券的标题、副标题、图标
### 3 获取某界面banner（包括可点击图片及其跳转链接和跳转类型）
### 4 从数据库获取全部福利活动的列表
### 5 从数据库获取用户的积分
### 6 从数据库获取奖池信息（奖品列表、id、描述、图标）
### 7 使用积分进行抽奖并返回抽奖结果（不终将则为空）

### API文档示例
#### /获取奖品信息

```text
本接口用于获取奖池内的奖品信息
，返回奖品列表
，包含图标、名称、中奖概率

```

##### 接口状态

> 已完成

##### 接口URL

> http://localhost:8080/raffle/rewards?token=12asd2dasd2

##### 请求方式

> GET

##### Content-Type

> none

##### 请求Header参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | - | String | 是 | Bearer tokne |

##### 请求Query参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| token | 12asd2dasd2 | String | 是 | 用户令牌 |

##### 认证方式

> Bearer Token

> 在Header添加参数 Authorization，其值为在Bearer之后拼接空格和访问令牌

> Authorization: Bearer your_access_token

##### 成功200

```javascript
{
	"status": 27,
	"msg": "ok",
	"data": {
		"rewards": [
			{
				"icon": "http://dummyimage.com/100x100",
				"description": "10元微信立减金",
				"percentage": 2700
			}
		]
	}
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| status | 24 | Number | 状态 |
| msg | ok | String | 信息 |
| data | - | Object | - |
| data.ibean_balance | 3 | Number | IBEAN余额 |

##### 失败404

```javascript
{
	"status": "404",
	"msg": "Resource not found",
	"data": null
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| status | 404 | String | 状态信息 |
| msg | Resource not found | String | 消息提示 |
| data | - | Null | - |

### API文档地址
> https://doc.apipost.net/docs/2f71cb142c66000
