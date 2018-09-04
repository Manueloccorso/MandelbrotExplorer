package manuelocc.utils;


import java.util.ArrayList;

/**
 * <p>Observable class: every class in the model (who needs to be observed by the View) extends this class. </p>
 * @param <T> Type of the Message to notify (generally a Message, but can be specific)
 *
 * @author Manuel Occorso
 */
public class Observable <T> {

    //Observers to notify (in this case, just the View)
    private ArrayList<Observer<T>> obs = new ArrayList<>();


    /**
     * Adds an Observer to be notified on notify()
     */
    public void addObserver(Observer<T> o) {
        if(!obs.contains(o)) obs.add(o);
    }

    /**
     * Adds more than one observer to be notified on notify()
     */
    public void addObservers(ArrayList<Observer<T>> observers){
        obs.addAll(observers);
    }

    /**
     * Remove an observer
     */
    public void removeObserver(Observer<T> o){
        if (o == null) throw new NullPointerException();
        if(obs.contains(o)) obs.remove(o);
    }

    /**
     * get all the observers
     */
    public ArrayList<Observer<T>> getObservers(){
        return obs;
    }

    /**
     * Notify all the observers.
     * @exception NullPointerException if m is null
     */
    public void notify(T m){
        if (m == null) throw new NullPointerException();
        for (Observer<T> o : obs){
            if(o != null) o.update(m);
        }
    }

    public void removeObservers() {
        obs = new ArrayList<>();
    }
}
