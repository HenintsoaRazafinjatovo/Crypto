package mg.crypto.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class SessionInterceptorUser implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("idUser") == null) {
            // Redirigez vers la page de connexion si l'utilisateur n'est pas connecté
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
