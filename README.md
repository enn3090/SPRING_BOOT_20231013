주차별 개발 내용 정리
1주차: 강의 소개 및 개발 환경 준비
학습 내용:자바 웹 프로그래밍 및 Spring Boot 프레임워크 소개
프로젝트 적용: Visual Studio Code 및 Git/GitHub 개발 환경 설정 확인
기본 프로젝트 구조 설정 (.gitignore, mvnw, pom.xml 등 초기 파일)

2주차: 개발 환경 설정 및 테스트
학습 내용: Spring Boot 개발 환경 상세 설정 (VS Code 확장 프로그램 설치, JDK 연동)
Spring Boot 프로젝트 생성 (Spring Initializr 사용) 및 의존성 추가 (Web, Thymeleaf, DevTools, Lombok 등)
기본 웹 페이지 실행 (Controller, @GetMapping, Thymeleaf index.html)
GitHub 연동 및 소스 코드 관리 (Commit, Push)
Thymeleaf 기본 문법 소개 (th:text)
프로젝트 적용: pom.xml: Spring Boot Starter Web, Thymeleaf, DevTools, Lombok 의존성 추가
DemoApplication.java: Spring Boot 메인 애플리케이션 클래스
DemoController.java: 초기 @GetMapping("/hello") 등 URL 매핑 및 Model 데이터 전달
templates/index.html, templates/hello.html, templates/hello2.html: Thymeleaf를 사용한 기본 HTML 페이지 작성 및 데이터 출력 (th:text)
application.properties: 초기 설정

3주차: 포트폴리오 작성 (프론트엔드)
학습 내용: Bootstrap 5 기반 포트폴리오 템플릿 적용
Static 자원 (CSS, JS, 이미지, 라이브러리) 관리 (static 폴더)
index.html 구조 수정 및 개인 프로필 정보 업데이트 (이름, 사진, 소개 문구 등)
상세 소개 페이지 (about_detailed.html) 추가 및 연결
Thymeleaf 추가 문법 (@{}, th:href, th:with) 학습 및 적용
프로젝트 적용: templates/index.html: Bootstrap 템플릿 적용 및 내용 수정 (메뉴 한글화, 프로필 정보, 기술 스택, 경험/교육 등)
templates/about_detailed.html: 상세 소개 페이지 생성 및 내용 작성
static/: CSS, JS, 이미지, 라이브러리 파일 추가 및 관리 (main.js에 confirmClose 함수 추가)
DemoController.java: /about_detailed 경로 매핑 추가
application.properties: Static 경로 설정 추가 (spring.web.resources.static-locations)
templates/thymeleaf_test1.html: Thymeleaf 문법 테스트 페이지

4주차: 데이터베이스 연동 및 테스트
학습 내용: 데이터베이스 (RDBMS, MySQL) 소개
Spring Data JPA (ORM, Hibernate, JDBC) 개념 및 필요성
MySQL 설치 및 기본 설정 (데이터베이스 생성)
Spring Boot - MySQL 연동 설정 (application.properties에 datasource 정보 추가)
JPA Entity (@Entity, @Id, @GeneratedValue, @Column) 생성
Repository (JpaRepository 상속) 인터페이스 생성
Service 클래스 생성 및 @Autowired (또는 생성자 주입) 를 통한 의존성 주입
MVC 구조에 따른 패키지 분리 (controller, model/domain, model/repository, service)
DB 연동 테스트 (Controller에서 Service 호출, Model에 데이터 추가, Thymeleaf 페이지에서 출력)
프로젝트 적용: pom.xml: Spring Data JPA, H2 (또는 MySQL Connector) 의존성 활성화
application.properties: H2 데이터베이스 접속 정보 추가 (추후 MySQL로 변경 가능)
model/domain/TestDB.java: @Entity 어노테이션을 사용한 DB 테이블 매핑 클래스 생성
model/repository/TestRepository.java: JpaRepository를 상속받는 인터페이스 생성
service/TestService.java: @Service 어노테이션 및 Repository 의존성 주입, findByName 메소드 구현
controller/DemoController.java: TestService 의존성 주입 및 /testdb 경로 매핑, DB 조회 결과 Model에 추가
templates/testdb.html: DB 조회 결과를 Thymeleaf로 출력하는 페이지
프로젝트 구조: controller, model(domain, repository, service 하위 폴더 포함) 패키지로 코드 분리

5주차: 블로그 게시판 - 1 (조회, 글쓰기)
학습 내용: REST API 개념 및 CRUD 연동 (HTTP Method: GET, POST)
블로그 게시판 기능 구현 시작 (목록 조회, 글쓰기)
DTO (Data Transfer Object) 개념 및 사용 이유
Thymeleaf 반복문 (th:each)
@RestController 와 @Controller 차이, @PostMapping, @ModelAttribute
폼 전송 처리 및 리다이렉트 (redirect:/...)

프로젝트 적용: model/domain/Article.java: 게시글 정보를 담는 @Entity 클래스 생성 (@Builder 사용)
model/repository/BlogRepository.java: Article Entity를 위한 JpaRepository 인터페이스 생성
model/dto/AddArticleRequest.java: 게시글 추가 요청 시 데이터를 전달하는 DTO 클래스 생성
service/BlogService.java: @Service 클래스에 findAll (목록 조회), save (글 저장) 메소드 구현
controller/BlogController.java: /article_list (GET) 요청 처리 (게시글 목록 조회 및 뷰 반환), /api/articles (POST) 요청 처리 (게시글 저장 및 리다이렉트 - 연습문제에서 수정)
controller/BlogRestController.java: 초기 /api/articles (POST) API 구현 (JSON 반환 방식) - 이후 BlogController로 통합
templates/article_list.html: 게시글 목록을 테이블로 보여주고 (th:each), 글쓰기 폼을 포함하는 Thymeleaf 템플릿
index.html: 네비게이션 바에 '게시판' 메뉴 링크 추가 (/article_list)

6주차: 블로그 게시판 - 2
학습 내용: ORM 매핑과 영속성 컨텍스트 (EntityManager) 개념 복습
HTTP Method: PUT, DELETE 개념 및 RESTful API 설계
HTML 폼에서 PUT, DELETE 메서드 사용 방법 (\_method hidden input)
@PathVariable을 이용한 URL 경로 변수 처리
Optional<T>를 이용한 Null 처리
JPA Entity 수정 방법 (변경 감지 또는 명시적 save)
예외 처리 (Controller에서 특정 ID 조회 실패 시 에러 페이지 반환, @ControllerAdvice 전역 예외 처리)
history.back() 자바스크립트 사용법

프로젝트 적용: controller/BlogController.java:
/article_edit/{id} (GET) 매핑 추가 (수정 페이지 조회)
/api/article_edit/{id} (PUT) 매핑 추가 (게시글 수정 처리)
/api/article_delete/{id} (DELETE) 매핑 추가 (게시글 삭제 처리)
service/BlogService.java: findById, update, delete 메소드 구현
model/domain/Article.java: update(title, content) 메소드 추가
templates/article_list.html: 각 게시글 옆에 수정/삭제 버튼 및 관련 링크/폼 추가 (th:href, th:action)
templates/article_edit.html: 게시글 수정 폼 페이지 생성 (기존 article_list.html 재활용)
application.properties: spring.mvc.hiddenmethod.filter.enabled=true 설정 추가
templates/error_page/article_error.html: 게시글 조회 실패 시 보여줄 에러 페이지 생성
controller/GlobalExceptionHandler.java: @ControllerAdvice 와 @ExceptionHandler 를 사용하여 URL 파라미터 타입 불일치 예외 처리 (연습 문제)
