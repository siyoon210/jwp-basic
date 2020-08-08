#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
1. ServletContextListener를 상속받고 @WebListener 애노테이션이 달린 클래스가 있다면 서블릿 컨텍스트가 실행되면서 해당 클래스의 contextInitialized() 메서드를 실행시킨다. 이 과정에서 클래스패쓰에 저장되어있는 jwp.sql 파일을 읽어드리고, 이 파일에 입력되어있는 SQL 스크립트를 ConnectionManager에 선언한 DBMS로 실행시킨다. 이 과정으로 구동과 서비스에 필요한 자료들을 사전에 DB에 저장해 둘 수 있다.

HttpServlet를 상속받고 @WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)가 달린 DispatcherServlet클래스를 선언하면 루트요청 '/'을 받는 FrontController가 생성된다. 이 클래스는 loadOnStartUp 속성이 1 이상이라 서블릿 컨텍스트가 초기화 될때 미리 만들어지게 된다. (0보다 큰 숫자중에 낮은 숫자부터 초기화 된다.) 요청 url을 key로 해당 요청을 처리할 controller를 value로 만들어진 Map으로 저장해두고, 요청 url에 따라 처리할 Contorller를 반환한다. 모든 요청은 DispatcherServlet을 거쳐야 한다.

Q) Listener가 서블릿보다 먼저 실행될까? 정확히 언제 실행되는걸까?

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 
