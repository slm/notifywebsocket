package controllers;


import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.JsonNode;
import models.Notification;
import org.h2.engine.User;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;
import akka.actor.*;
import play.libs.F.*;
import play.mvc.WebSocket;
import java.util.ArrayList;
import java.util.List;
import akka.actor.*;
import views.html.sockettest;

import static play.libs.Json.toJson;


public class Application extends Controller {
    static WebSocket.In<String> socketin;
    static WebSocket.Out<String> socketout;
    static boolean isReady=false;

    public static Result index() {
        Notification n = new Notification();
        Form<Notification> notificationForm = Form.form(Notification.class);
        List<Notification> nots = new Model.Finder(Notification.class, Notification.class).all();
        return ok(index.render("Notifications",nots));
    }

    public static Result Notifications(){
        List<Notification> nots = new Model.Finder(Notification.class, Notification.class).all();

        return  ok(toJson(nots));

    }

    public static Result addNotification(){
        Notification notification = Form.form(Notification.class).bindFromRequest().get();
        notification.save();
        return redirect(routes.Application.index());

    }
    public static Result delAll(){
        List<Notification> nots = new Model.Finder(Notification.class, Notification.class).all();
        for(Notification n :nots){
            n.delete();
        }
        return redirect(routes.Application.index());

    }

    public static Result sockettest(){

        return ok(sockettest.render());

    }

    public static Result delNot(String id) {
        Notification not =(Notification) new Model.Finder(Notification.class, Notification.class).byId(id);
        not.delete();
        return redirect(routes.Application.index());

    }
    public static Result sendNot(String id) {
        Notification not =(Notification) new Model.Finder(Notification.class, Notification.class).byId(id);

        JsonNode json = toJson(not);

        socketout.write(json.toString());

        return redirect(routes.Application.index());

    }

    public static WebSocket<String> websocket() {
        return new WebSocket<String>() {

            // Called when the Websocket Handshake is done.
            public void onReady(final WebSocket.In<String> in, final WebSocket.Out<String> out) {

                isReady=true;
                socketin=in;
                socketout=out;

                // For each event received on the socket,
                in.onMessage(new Callback<String>() {
                    public void invoke(String event) {

                        // Log events to the console
                        System.out.println(event);

                    }
                });

                // When the socket is closed.
                in.onClose(new Callback0() {
                    public void invoke() {
                        isReady=false;
                        System.out.println("Disconnected");

                    }
                });

                // Send a single 'Hello!' message
                out.write("Hello!");


            }

        };
    }



}

