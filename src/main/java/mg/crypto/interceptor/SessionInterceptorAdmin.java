package mg.crypto.interceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptorAdmin implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false); // Ne crée pas de nouvelle session si elle n'existe pas

        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("/login/admin");
        }

        return true; // Continue l'exécution du contrôleur
    }
}
