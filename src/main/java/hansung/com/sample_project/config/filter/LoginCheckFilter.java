package hansung.com.sample_project.config.filter;

import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckFilter implements Filter {

    private static final String SESSION_ID = "Token";

    private static final String[] whitelist = {"/", "/login", "/logout", "/review", "/hello"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            //log.info("인증 체크 필터 시작 {}", requestURI);

            if (isLoginCheckPath(requestURI)) {
               // log.info("인증 체크 로직 실행 {}", requestURI);

                HttpSession session = httpRequest.getSession(false);

                if (session == null || session.getAttribute(SESSION_ID) == null) {
//                    log.info("미인증 사용자 요청 {}", requestURI);

                    // 로그인 페이지로 redirect
                    // 로그인 시 다시 해당 페이지로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);

                    return;
                }
            }

            // 아래 코드를 추가하지 않을 경우 필터 체인 적용이 안되면서 다음 필터로 이어지지 않음
            chain.doFilter(request, response);
        } catch (Exception e) {
            // 예외 로깅 가능하지만, 톰캣까지 예외를 보내줘야함
            throw e;
        } finally {
//            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크 안함
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}