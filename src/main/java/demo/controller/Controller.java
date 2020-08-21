package demo.controller;

import demo.model.Message;
import demo.model.User;
import demo.repository.DAOmessage;
import demo.repository.SpringDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class Controller {

    private DAOmessage daOmessage;
    private SpringDAO userDao;

    @Autowired
    public Controller(DAOmessage daOmessage, SpringDAO dao){
       this.daOmessage=daOmessage;
       userDao=dao;
    }

    @GetMapping(value = {"/","/home"})
    public ModelAndView getDate(){
        ModelAndView modelAndView=new ModelAndView("home");
        String s=String.format("%1$ta %1$tB",LocalDate.now());
        modelAndView.addObject("date",LocalDate.now());
        modelAndView.addObject("ss",s);
        return modelAndView;
    }
    @PutMapping(value="/etc")
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Integer etc(@RequestParam(name = "int",defaultValue = "10")Integer iParam){
        return iParam*iParam;
    }
    @GetMapping(value = "/download")
    public ResponseEntity<Object> download() throws IOException {
        ClassPathResource resource=new ClassPathResource("/static/movies/SpongeBob.mkv");
        InputStreamResource streamResource=new InputStreamResource(resource.getInputStream());
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Disposition",String.format("attachment ; filename=\"%s\"",resource.getFilename()));
        headers.add("Cache-Control","no-cache, no-store, must-revalidate");
        headers.add("Pragma","no-cache");
        headers.add("Expires","0");
        return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(streamResource);
    }
    @PostMapping(value = "/process/data")
    public void process(@RequestParam(name = "text")String txt, HttpServletResponse response,HttpSession session)throws IOException {
        if(txt==null || txt.isEmpty())
            return;
       Message message=new Message(txt,userDao.getReference(((User)session.getAttribute("auth")).getUserName()));
       daOmessage.save(message);
       response.sendRedirect("/home");
    }
    @GetMapping("/texts")
    public ModelAndView getLists(@RequestParam String find,@RequestParam(required = false,name = "size",defaultValue = "10")Integer size){
        ModelAndView modelAndView=new ModelAndView("messages");
        Pageable pageable=PageRequest.of(0,size,Sort.by("time").descending());
        List<Message> page;
        if(find.equals("all"))
        page=daOmessage.findAll(pageable).getContent();
        else
        page=daOmessage.findParticular(find,pageable);
        page=page.stream().sorted(Comparator.comparing(Message::getTime)).collect(Collectors.toList());
        modelAndView.addObject("messages",page);
        return modelAndView;
    }

    @GetMapping(value = "/auth")
    public ModelAndView auth(){
        return new ModelAndView("auth");

    }
    @PostMapping(value = "/auth")
    public void submitAuth(@ModelAttribute User user, HttpServletResponse response, HttpServletRequest request){
        System.out.println("SEND ACCOMPLISHED       "+user.getUserName());
        Optional<User> user1 =userDao.findById(user.getUserName());
        if(user1.isPresent() && user1.get().getPasswd().equals(user.getPasswd()))
            request.getSession().setAttribute("auth",user);
        try {
            response.sendRedirect("/home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
