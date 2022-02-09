# sso
## 通过网关请求获取token
post请求
http://127.0.0.1:8800/oauther2/oauth/token?grant_type=password&username=aa&password=123456&scope=ROLE_ADMIN&client_id=app-one&client_secret=123

## 测试请求app1
get请求，携带token
http://127.0.0.1:8800/app1/user/get
