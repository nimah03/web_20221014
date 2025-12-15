JPA Repository 심화 및 게시글 수정/삭제 기반 마련
JPA Repository 내부 구조 학습: JPA Repository의 상속 구조 (CrudRepository, PagingAndSortingRepository, JpaRepository) 및 계층별 기능(CRUD, 페이징, 정렬)을 학습했습니다.

게시글 수정 기능 구현:
게시판 페이지에 글 수정 버튼을 추가하고 버튼 동작을 위한 맵핑을 컨트롤러에 준비했습니다.

게시글 삭제 기능 구현:
게시글 목록(article_list.html 또는 유사 페이지)에 삭제 버튼을 추가했습니다.
폼 내부에 <input type="hidden" name="_method" value="delete">를 사용하여 DELETE HTTP 메서드를 요청할 수 있도록 설정했습니다.
