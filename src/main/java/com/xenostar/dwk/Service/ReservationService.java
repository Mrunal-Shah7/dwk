package com.xenostar.dwk.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.xenostar.dwk.Entity.Reservation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ReservationService {

    private static final String COLLECTION_NAME = "reservations";

    public String saveReservation(Reservation reservation) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore.collection(COLLECTION_NAME).document(); // Auto-generate ID
        ApiFuture<WriteResult> collectionsApiFuture = docRef.set(reservation);
        return "Reservation created with ID: " + docRef.getId();
    }

    public Reservation getReservation(String name) throws ExecutionException, InterruptedException{
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).whereEqualTo("name",name).get();

        QuerySnapshot querySnapshot = future.get();

        if(!querySnapshot.isEmpty()){
            return querySnapshot.getDocuments().get(0).toObject(Reservation.class);
        }else {
            return null;
        }

    }

    public List<Reservation> getAllReservations() throws ExecutionException,InterruptedException{
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        QuerySnapshot querySnapshot = future.get();

        List<Reservation> reservations = new ArrayList<>();

        if(!querySnapshot.isEmpty()){
            for(QueryDocumentSnapshot document: querySnapshot.getDocuments()){
                reservations.add(document.toObject(Reservation.class));
            }
        }
        return reservations;

    }


}