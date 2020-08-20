package demo.writer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Writer {
    public Boolean saveFile(String txt){
        String home=String.format("%s/text.txt",System.getenv("HOME"));

        try(FileWriter writer=new FileWriter(home,true))
        {
            if(txt.length()==0)throw new IOException("the text was empty");
            PrintWriter printWriter=new PrintWriter(writer);
            printWriter.println(txt);

        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    public List<String> getTexts(){
        List<String> list=new ArrayList<>();
        String home=String.format("%s/text.txt",System.getenv("HOME"));
        try(FileReader reader=new FileReader(home)){
            BufferedReader bufferedReader=new BufferedReader(reader);
            list.addAll( bufferedReader.lines().collect(Collectors.toList()));
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return list;
    }
}
