package com.test;

import com.test.repositories.RepositoryConfig;
import com.ctest.repositories.RepositoryFactory;
import com.test.repositories.RepositoryFactoryConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;


/*Esta clase se utiliza para que el Servicio se inicie en TomCat.*/

public class MyWebAppInitializer implements WebApplicationInitializer {


    public void onStartup(ServletContext container) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        
      //Si en vez de utilizar 'Java Config' se prefiere utilizar un archivo XML:
      //XmlWebApplicationContext appContext = new XmlWebApplicationContext();
      //appContext.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");

        appContext.refresh();
        
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();

        //Hay que registrar en el 'mvcContext' todas las clases 'Configuration' que crean los 'beans' para el inyector de dependencias.
        //Todas las clases con el decorador '@Configuration'.
        mvcContext.register(RepositoryConfig.class);
        mvcContext.register(WebMvcConfig.class);
        mvcContext.register(RepositoryFactoryConfig.class);

        FilterRegistration.Dynamic fr = container.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter());
        fr.addMappingForUrlPatterns(null, true, "/*");

        DispatcherServlet dispatcherServlet = new DispatcherServlet(mvcContext);
        dispatcherServlet.setDispatchOptionsRequest(true);

        ServletRegistration.Dynamic appServlet = container.addServlet("appServlet", dispatcherServlet);
        appServlet.setLoadOnStartup(5);
        System.out.println(" >> Adding mapping '/' ....");
        appServlet.addMapping("/"); //Ruta inicial del Servicio Web.
    }

 }
