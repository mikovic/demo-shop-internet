package main.java.com.mikovic.cloud.client;


@Component
public class Produceur {
    public  String message = "Добро пожаловать";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}