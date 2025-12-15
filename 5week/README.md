article_list.html을 생성해 게시글 목록 페이지를 구현했습니다.
th:each 문법을 사용해 DB의 게시글 데이터를 반복 출력했습니다.
Article.java를 작성해 Entity 클래스를 정의했습니다.
@Entity, @Id, @GeneratedValue, @Builder 등을 사용했습니다.
BlogRepository.java를 생성해 JPA 기반 데이터 접근 계층을 구현했습니다.
BlogService.java에서 findAll() 메소드를 통해 전체 게시글 조회 기능을 구현했습니다.
BlogController.java에서 /article_list 요청을 받아 목록 페이지와 연결했습니다.
