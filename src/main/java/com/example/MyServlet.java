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

@Singleton
public class MyServlet extends HttpServlet {
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
        resp.getOutputStream().print(greeting);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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