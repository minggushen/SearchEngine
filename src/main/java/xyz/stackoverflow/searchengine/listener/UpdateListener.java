package xyz.stackoverflow.searchengine.listener;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Application Lifecycle Listener implementation class UpdateListener
 *
 */

public class UpdateListener implements ServletContextListener {
	
    /**
     * Default constructor. 
     */
    public UpdateListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	ServletContext application = arg0.getServletContext();  
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(application);  
        TimerTask task = (TimerTask)ctx.getBean("task");
        Timer timer = new Timer();
        timer.schedule(task,600000,600000);
    }
	
}
