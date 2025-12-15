ORM(Object-Relational Mapping) 개념과 영속성 컨텍스트를 이해했습니다.
EntityManager가 객체 상태(생성, 조회, 수정, 삭제)를 관리하는 원리를 배웠습니다.
HTTP 메서드 중 PUT, DELETE를 활용한 RESTful CRUD 구조를 실습했습니다.
article_list.html에 수정 버튼을 추가했습니다.
수정 후 redirect:/article_list로 페이지 이동하도록 했습니다.
article_list.html에 삭제 버튼을 추가했습니다.

DELETE 요청을 보낼 수 있도록 폼 내부에 hidden input을 추가했습니다.
BlogController와 BlogService에서 삭제 로직을 작성했습니다.
존재하지 않는 게시글 접근 시 /error_page/article_error.html로 이동하도록 구현했습니다.
오류 페이지에서 사용자에게 안내 메시지를 표시하고 “이전으로 돌아가기” 버튼을 추가했습니다.
