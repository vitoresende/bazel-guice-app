package com.example;

import java.io.IOException;

import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// Imports the Google Cloud client library
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;

// Import FluentLogger
import com.google.common.flogger.FluentLogger;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

@Singleton
public class MyServlet extends HttpServlet {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private String greeting;

    // Instantiates a Datastore client
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    // The kind for the new entity
    String kind = "whateveryouwant";

    @Inject
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    // The default request to /
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.atInfo().log("-------------> MyServlet doGet <-------------");

        Query<Entity> query = Query.newEntityQueryBuilder().setKind(kind).build();
        QueryResults<Entity> results = datastore.run(query);

        Document document = DocumentHelper.createDocument();
        Element html = document.addElement("html");
        Element head = html.addElement("head");
        head.addElement("title").addText("Bazel Guice App");

        Element body = html.addElement("body");
        body.addElement("h1").addText(greeting);
        Element ul = body.addElement("ul");

        while (results.hasNext()) {
            Entity entity = results.next(); // String message
            ul.addElement("li").addText(entity.getKey().toString());
        }

        resp.setContentType("text/html;");
        resp.getOutputStream().write(document.asXML().getBytes("UTF-8"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.atInfo().log("-------------> MyServlet doPost <-------------");
        // The name/ID for the new entity
        String name = UUID.randomUUID().toString().replace("-", "");

        // The Cloud Datastore key for the new entity
        Key taskKey = datastore.newKeyFactory().setKind(kind).newKey(name);
        try {
            String json = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            // Prepares the new entity
            Entity task = Entity.newBuilder(taskKey)
                    .set("body", StringValue.newBuilder(json).setExcludeFromIndexes(true).build()).build();

            // Saves the entity
            datastore.put(task);
            resp.getOutputStream().print("Successful request!");
        } catch (IOException e) {
            resp.getOutputStream().print("Error");
        }

    }

}
