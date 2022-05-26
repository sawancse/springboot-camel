package com.learncamel.launch;

import com.learncamel.routes.jms2jdbc.Jms2DBRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.main.Main;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class AppLauncher {

    public static void main(String[] args) throws Exception {
        Main main = new Main();

        String url = "jdbc:h2:mem:shedlock_DB;INIT=CREATE SCHEMA IF NOT EXISTS shedlock;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";

        main.bind("myDataSource",setupDataSource(url));  //map based registry
        //main.bind();

        main.addRouteBuilder(new Jms2DBRoute());

        System.out.println("Starting Camel JMS to DB Route.");

        main.run();


    }


    private static DataSource setupDataSource(String connectURI) {
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername("sa");
        ds.setDriverClassName("org.h2.Driver");
        ds.setPassword("");
        ds.setUrl(connectURI);
        return ds;
    }
}
