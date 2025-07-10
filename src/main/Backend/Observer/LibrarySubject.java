package main.Backend.Observer;

import java.util.LinkedList;
import java.util.List;

public abstract class LibrarySubject {
    private List<LibraryObserver> observers = new LinkedList<>();

    public void attach(LibraryObserver observer) {
        observers.add(observer);
    }

    public void detach(LibraryObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for(LibraryObserver observer : observers) {
            observer.update();
        }
    }

}
