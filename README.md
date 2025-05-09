# SmartFarm SSE API 명세서

---
</aside>

## 1.SSE 구독 (Subscribe)

**URL** : `GET /subscribe/{id}`

특정 센서 데이터(SSE 이벤트 스트림)를 구독

id 는 String 타입, `temperature`, `humidity`, `soilMoisture` 중 하나

예시(참고용)

```jsx
const eventSource = new EventSource('http://localhost:8080/subscribe/humidity');

eventSource.addEventListener('humidity_data', (event) => {
  console.log('습도 데이터 수신:', event.data);
});

eventSource.addEventListener('connect', (event) => {
  console.log('SSE 연결 완료:', event.data);
});

eventSource.onerror = (error) => {
  console.error('SSE 오류 발생', error);
  eventSource.close();
};
```

성공 시 : HTTP 200 + 연결된 `SseEmitter` 스트림 유지

## 2.습도 데이터 조회 (Humidity)

**URL** : `POST /farm/humidity` 

저장된 습도 데이터(`humidity`)를 시간 필터링과 정렬 옵션을 기준으로 **페이징 조회함**.

```json
{
  "size": 10, 	Integer 한 페이지에 조회할 데이터 개수
  "page": 0, 	Integer 조회할 페이지 번호 (0부터 시작)
  "sort": "desc", 	String 정렬 방향 ("asc" 또는 "desc") (기본값은 desc임)
  "startTime": "2025-04-28T00:00:00", 조회 시작 시간
  "endTime": "2025-04-28T23:59:59" 	조회 끝 시간
}
```

- `startTime`, `endTime` 둘 다 생략하면 전체 데이터 조회
- `sort` 생략 시 최신순(desc)

응답
```
{
  "content": [
    {
      "value": 45.3,
      "measuredAt": "2025-04-28T14:20:10"
    },
    {
      "value": 42.1,
      "measuredAt": "2025-04-28T14:19:10"
    }
  ],
  "totalPages": 5,
  "totalElements": 50,
  "size": 10,
  "number": 0,
  "first": true,
  "last": false
}
```
'''
필드명	타입	설명
content	Array<FarmResponse>	조회된 데이터 리스트
totalPages	Integer	전체 페이지 수
totalElements	Integer	전체 데이터 수
size	Integer	요청된 페이지 크기
number	Integer	현재 페이지 번호
first	Boolean	현재 페이지가 첫 페이지인지 여부
last	Boolean	현재 페이지가 마지막 페이지인지 여부
'''
