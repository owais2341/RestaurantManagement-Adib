package com.JavaProj;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class ReservationDAO {
    private MongoCollection<Document> collection;

    public ReservationDAO() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("RestaurantReservationSystem");
        collection = database.getCollection("reservations");
    }

    public void saveReservation(Reservation reservation) {
        Document doc = new Document("_id", reservation.getReservationId())
                .append("restaurant_id", reservation.getRestaurantId())
                .append("name", reservation.getName())
                .append("email", reservation.getEmail())
                .append("phone", reservation.getPhone())
                .append("party_size", reservation.getPartySize())
                .append("date_time", reservation.getDateTime().toString());
        collection.insertOne(doc);
    }

    public Reservation getReservationById(String id) {
        Document doc = collection.find(new Document("_id", id)).first();
        if (doc != null) {
            return new Reservation(
                    doc.getString("_id"),
                    doc.getString("restaurant_id"),
                    doc.getString("name"),
                    doc.getString("email"),
                    doc.getString("phone"),
                    doc.getInteger("party_size"),
                    LocalDateTime.parse(doc.getString("date_time"))
            );
        }
        return null;
    }
      public List<Reservation> getReservationsByRestaurantId(String restaurantId) {
        List<Reservation> reservations = new ArrayList<>();
        for (Document doc : collection.find(new Document("restaurant_id", restaurantId))) {
            reservations.add(new Reservation(
                    doc.getString("_id"),
                    doc.getString("restaurant_id"),
                    doc.getString("name"),
                    doc.getString("email"),
                    doc.getString("phone"),
                    doc.getInteger("party_size"),
                    LocalDateTime.parse(doc.getString("date_time"))
            ));
        }
        return reservations;
    }
    public void deleteReservation(String id) {
        collection.deleteOne(new Document("_id", id));
    }
    public void deleteAllReservations() {
        collection.deleteMany(new Document());
    }
    
    // Other CRUD methods
}