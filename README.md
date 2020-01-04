# JWT

#### TODO
- Refresh token
```javascript
const token = "xxx.yyy.zzz";
// Token 중 claim(yyy) 을 base64 로 decode
const claim = JSON.parse(atob("yyy"));

// claim 에서, exp 에 1000을 곱한 것을 날짜로 추출 (Java -> JS 로 가는 과정에서 ms 단위가 다 짤리는 듯)
const exp = new Date(claim.exp * 1000);

// exp 기간을 초과했다면 token refresh 요청?
```