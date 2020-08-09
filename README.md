#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
- ServletContextListener를 상속받고 @WebListener 애노테이션이 달린 클래스가 있다면 서블릿 컨텍스트가 실행되면서 해당 클래스의 contextInitialized() 메서드를 실행시킨다. 이 과정에서 클래스패쓰에 저장되어있는 jwp.sql 파일을 읽어드리고, 이 파일에 입력되어있는 SQL 스크립트를 ConnectionManager에 선언한 DBMS로 실행시킨다. 이 과정으로 구동과 서비스에 필요한 자료들을 사전에 DB에 저장해 둘 수 있다.

- HttpServlet를 상속받고 @WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)가 달린 DispatcherServlet클래스를 선언하면 루트요청 '/'을 받는 FrontController가 생성된다. 이 클래스는 loadOnStartUp 속성이 1 이상이라 서블릿 컨텍스트가 초기화 될때 미리 만들어지게 된다. (0보다 큰 숫자중에 낮은 숫자부터 초기화 된다.) 요청 url을 key로 해당 요청을 처리할 controller를 value로 만들어진 Map으로 저장해두고, 요청 url에 따라 처리할 Contorller를 반환한다. 모든 요청은 DispatcherServlet을 거쳐야 한다.

- Q) Listener가 서블릿보다 먼저 실행될까? 정확히 언제 실행되는걸까?

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
1. jwp-basic 컨트롤러의 WebServlet은 DispatcherServlet 단 하나 뿐이다. (예전JCJ보드는 모든 요청마다 모든 WebServlet을 만들었는데, 이와 대조적) (Dispatcher의 사전적의미는 (열차·버스·비행기 등이 정시 출발하도록 관리하는) 운행 관리원 혹은 관제사)
2. 이 하나뿐인 웹서블렛 DispatcherServlet이 요청을 받으면 RequestMapping이 요청된 url을 처리할 수있는지 확인한다.
3. "/" 요청은 HomeController에게 매핑되어있다.
4. DispatcherServlet가 RequestMapping에게 반환받은 HomeController의 execute()메서드가 실행된다.
5. HomeController의 상위클래스인 AbsctractController에게 선언되어 있는 jspView() 메서드에서 JspView 객체를 새로 만든다.
6. JspView에서 렌더링하면서 사용할 model을 ModelAndView addObject()메서드로 담아준다. questionDao.findAll()의 질문 목록들을 담는다.
7. HomeController는 ModelAndView를 반환한다.
8. DispatcherServlet은 반환반은 ModelAndView의 View를 반환받고 View의 render()메서드를 호출한다.
9. 반환받은 View는 JspView로 viewName으로 http 요청을 포워드 시킨다. (포워드는 받은 요청을 위임하는 느낌이고 리다이렉트는 다시 요청하라고 하는 느낌)
10. viewName이 home.jsp이므로 요청 정적파일인 hoem.jsp 페이지로 포워드되고 jsp로 렌더링된 html이 응답으로 내려간다.
[Dispatcher방식과-Redirect-방식](https://devbox.tistory.com/entry/Comporison-Dispatcher%EB%B0%A9%EC%8B%9D%EA%B3%BC-Redirect-%EB%B0%A9%EC%8B%9D)

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 
