package gestion.observer;

public interface Observable<T> {
	public void addObserver(Observer<T> obs);
	public void updateObs(T obj, boolean bool);
	// pas de 	public void deleteObserver(); car on ne s'en sert pas
}
