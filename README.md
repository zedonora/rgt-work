# 프로젝트 라이브러리 설정

이 프로젝트는 다음과 같은 주요 라이브러리와 의존성을 사용합니다:

## 핵심 의존성

- Spring Boot 3.3.2
- Java 17

## 주요 라이브러리

1. **Spring Boot Starter Data JPA**: JPA를 사용한 데이터 접근 계층 구현
2. **Spring Boot Starter Security**: 애플리케이션 보안 기능 구현
3. **Spring Boot Starter Validation**: 입력 데이터 유효성 검사
4. **Spring Boot Starter Web**: 웹 애플리케이션 개발
5. **Spring Boot DevTools**: 개발 생산성 향상 도구
6. **H2 Database**: 인메모리 데이터베이스 (개발 및 테스트용)
7. **Lombok**: 반복적인 코드 감소를 위한 어노테이션 프로세서
8. **Spring Boot Starter Test**: 단위 및 통합 테스트 지원
9. **Spring Security Test**: 보안 관련 테스트 지원
10. **Springdoc OpenAPI UI**: API 문서화 (Swagger UI) - 버전 2.1.0

## 데이터베이스 설계

이 프로젝트는 다음과 같은 테이블 구조를 가지고 있습니다:

1. **users**: 사용자 정보 저장
    - id: 기본 키
    - username: 사용자 이름 (고유)
    - password: 비밀번호
    - role: 사용자 역할

2. **restaurants**: 식당 정보 저장
    - id: 기본 키
    - name: 식당 이름
    - owner_id: 식당 소유자 ID (users 테이블 참조)

3. **tables**: 식당 테이블 정보 저장
    - id: 기본 키
    - restaurant_id: 식당 ID (restaurants 테이블 참조)
    - table_number: 테이블 번호

4. **menus**: 메뉴 정보 저장
    - id: 기본 키
    - restaurant_id: 식당 ID (restaurants 테이블 참조)
    - name: 메뉴 이름
    - price: 가격
    - description: 메뉴 설명

5. **cart_items**: 장바구니 항목 저장
    - id: 기본 키
    - user_id: 사용자 ID (users 테이블 참조)
    - menu_id: 메뉴 ID (menus 테이블 참조)
    - quantity: 수량

6. **orders**: 주문 정보 저장
    - id: 기본 키
    - user_id: 사용자 ID (users 테이블 참조)
    - table_id: 테이블 ID (tables 테이블 참조)
    - status: 주문 상태
    - created_at: 주문 생성 시간

7. **order_items**: 주문 항목 저장
    - id: 기본 키
    - order_id: 주문 ID (orders 테이블 참조)
    - menu_id: 메뉴 ID (menus 테이블 참조)
    - quantity: 수량
    - price: 가격

이 데이터베이스 구조는 식당 주문 시스템의 주요 기능을 지원하며, 사용자, 식당, 메뉴, 주문 등의 정보를 효율적으로 관리할 수 있도록 설계했습니다.

## 시스템 설계

테이블 주문 플랫폼 서비스의 주요 기능과 요구사항을 바탕으로 시스템을 설계했습니다.

### 주요 엔티티

- User (사용자)
- Restaurant (식당)
- Table (식당 테이블)
- Menu (메뉴)
- CartItem (장바구니 항목)
- Order (주문)
- OrderItem (주문 항목)

### API 엔드포인트

#### 사용자 관리
- POST /api/users/register: 사용자 등록
- POST /api/users/login: 로그인

#### 식당 및 테이블
- GET /api/restaurants: 식당 목록
- GET /api/restaurants/{restaurantId}/tables: 식당의 테이블 목록

#### 메뉴
- GET /api/restaurants/{restaurantId}/menus: 식당의 메뉴 목록
- GET /api/menus/{menuId}: 메뉴 상세 정보

#### 장바구니
- POST /api/cart/items: 장바구니에 메뉴 추가
- GET /api/cart: 장바구니 조회
- PUT /api/cart/items/{cartItemId}: 장바구니 항목 수정
- DELETE /api/cart/items/{cartItemId}: 장바구니 항목 삭제

#### 주문
- POST /api/orders: 주문 생성
- GET /api/orders: 사용자의 주문 내역 조회
- GET /api/restaurants/{restaurantId}/orders: 식당의 주문 내역 조회 (사장님용)
- PUT /api/orders/{orderId}/status: 주문 상태 변경
- GET /api/orders/{orderId}: 주문 상세 정보 조회

## Swagger UI를 통한 API 테스트 방법

### 메뉴 관련 API

1. 손님 사용자가 식당의 메뉴 리스트를 확인하기 위한 API:
   - API: GET /api/menus/restaurants/{restaurantId}
   - Swagger에서:
      1. 'menu-controller' 섹션을 펼칩니다.
      2. GET /api/menus/restaurants/{restaurantId} 엔드포인트를 찾습니다.
      3. 'Try it out' 버튼을 클릭합니다.
      4. restaurantId 값(예: 1 또는 2)을 입력합니다.
      5. 'Execute' 버튼을 클릭합니다.

2. 손님 사용자가 식당의 메뉴의 상세 정보를 확인하기 위한 API:
   - API: GET /api/menus/{menuId}
   - Swagger에서:
      1. 'menu-controller' 섹션을 펼칩니다.
      2. GET /api/menus/{menuId} 엔드포인트를 찾습니다.
      3. 'Try it out' 버튼을 클릭합니다.
      4. menuId 값(예: 1, 2, 3 등)을 입력합니다.
      5. 'Execute' 버튼을 클릭합니다.

### 장바구니 관련 API

3. 손님 사용자가 메뉴를 장바구니에 담기 위한 API:
   - API: POST /api/carts/add
   - Swagger에서:
      1. 'cart-controller' 섹션을 펼칩니다.
      2. POST /api/carts/add 엔드포인트를 찾습니다.
      3. 'Try it out' 버튼을 클릭합니다.
      4. Request body에 다음과 같은 JSON을 입력합니다:
         ```json
         {
           "menuId": 1,
           "quantity": 2
         }
         ```
      5. 'Execute' 버튼을 클릭합니다.

4. 손님 사용자가 장바구니에 담긴 메뉴를 수정하기 위한 API:
   - API: PUT /api/carts/update
   - Swagger에서:
      1. 'cart-controller' 섹션을 펼칩니다.
      2. PUT /api/carts/update 엔드포인트를 찾습니다.
      3. 'Try it out' 버튼을 클릭합니다.
      4. Request body에 다음과 같은 JSON을 입력합니다:
         ```json
         {
           "cartItemId": 1,
           "quantity": 3
         }
         ```
      5. 'Execute' 버튼을 클릭합니다.

### 주문 관련 API

5. 손님 사용자가 장바구니에 있는 메뉴들을 주문하기 위한 API:
   - API: POST /api/orders/create
   - Swagger에서:
      1. 'order-controller' 섹션을 펼칩니다.
      2. POST /api/orders/create 엔드포인트를 찾습니다.
      3. 'Try it out' 버튼을 클릭합니다.
      4. tableId 파라미터에 값(예: 1)을 입력합니다.
      5. 'Execute' 버튼을 클릭합니다.

6. 손님 사용자가 주문한 내역을 확인하기 위한 API:
   - API: GET /api/orders/customer
   - Swagger에서:
      1. 'order-controller' 섹션을 펼칩니다.
      2. GET /api/orders 엔드포인트를 찾습니다.
      3. 'Try it out' 버튼을 클릭합니다.
      4. 필요한 경우 파라미터를 입력합니다 (예: tableId).
      5. 'Execute' 버튼을 클릭합니다.

### 디렉토리 설명

- `config/`: 애플리케이션의 설정 클래스들이 위치합니다. 여기에는 보안 설정 등이 포함됩니다.
- `controller/`: API 엔드포인트를 정의하는 컨트롤러 클래스들이 위치합니다.
- `entity/`: 데이터베이스 테이블과 매핑되는 JPA 엔티티 클래스들이 위치합니다.
- `dto/`: API 요청 및 응답에 사용되는 데이터 모델 클래스들이 위치합니다.
- `repository/`: 데이터 접근 계층을 위한 Spring Data JPA 리포지토리 인터페이스들이 위치합니다.
- `service/`: 비즈니스 로직을 처리하는 서비스 클래스들이 위치합니다.
- `util/`: 유틸리티 클래스들이 위치합니다. 여기에는 JWT 관련 기능 등이 포함됩니다.
- `security/`: 보안관련 클래스들이 위치합니다.

이 구조는 관심사의 분리 원칙을 따르며, 각 컴포넌트의 역할을 명확히 구분합니다. 이를 통해 코드의 가독성과 유지보수성을 향상시킬 수 있습니다.

## 설치 및 실행

이 프로젝트는 Maven을 사용하여 의존성을 관리합니다. 프로젝트를 실행하려면 다음 단계를 따르세요:

1. 프로젝트를 클론합니다.
2. 프로젝트 루트 디렉토리에서 다음 명령을 실행합니다:

   ```
   mvn clean install
   ```

3. 애플리케이션을 실행합니다:

   ```
   mvn spring-boot:run
   ```

모든 필요한 의존성은 Maven에 의해 자동으로 다운로드되고 관리됩니다.

## 참고사항

- 이 프로젝트는 Java 17을 사용합니다. JDK 17이 설치되어 있는지 확인하세요.
- API 문서는 애플리케이션 실행 후 `http://localhost:8080/swagger-ui.html` 에서 확인할 수 있습니다.