/*
 * This Class demonstrates use of annotations using reflection.
 * 
 * @author Yashwant Golecha (ygolecha@gmail.com)
 * @version 1.0
 * 
 */

package priv.zxy.moonstep.AnnotationsSamble;

import java.lang.reflect.Method;
/**
 * 为了便于了解注释的额外代码，与项目无关
 */
public class TodoReport {
    public TodoReport() {
        super();
    }

    public static void main(String[] args) {
        getTodoReportForBusinessLogic();
    }

    /**
     * This method iterates through all messages of BusinessLogic class and fetches annotations defined on each of them.
     * After that it displays the information from annotation accordingly.
     */
    private static void getTodoReportForBusinessLogic() {
        
        Class businessLogicClass = BusinessLogic.class;
        for(Method method : businessLogicClass.getMethods()) {
            Todo todoAnnotation = (Todo)method.getAnnotation(Todo.class);
            if(todoAnnotation != null) {
                System.out.println(" Method Name : " + method.getName());
                System.out.println(" Author : " + todoAnnotation.author());
                System.out.println(" Priority : " + todoAnnotation.priority());
                System.out.println(" Status : " + todoAnnotation.status());
                System.out.println(" --------------------------- ");
            }
        }
    }
}
