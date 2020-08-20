package demo.servlet_Filter;

import demo.model.User;
import demo.repository.SpringDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class ServletFilter implements Filter {

        private SpringDAO dao;
        @Autowired
        public ServletFilter(SpringDAO dao){
            this.dao=dao;
        }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
      //  HttpServletResponse res=(HttpServletResponse)response;
        HttpSession session=req.getSession(false);
        if(session==null || !isExists(session.getAttributeNames())){
            req.getRequestDispatcher("/auth").forward(request,response);
            return;
        }

   // User user=dao.getOne((String) session.getAttribute("auth"));
//    if(user==null)
//        res.sendRedirect("/auth");
     chain.doFilter(request,response);
  //      return;
    }

    @Override
    public void destroy() {

    }
    private boolean isExists(Enumeration<String> enumeration){
            while (enumeration.hasMoreElements())
                if(enumeration.nextElement().equals("auth"))
                    return true;
                return false;
    }
}
