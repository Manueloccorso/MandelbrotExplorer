package manuelocc.utils;

/**
 * <p>Interface of an Observer of a T message.</p>
 * @param <T>
 */
public interface Observer<T>{

    /**
     * Manage an update.
     * @param message container of the informations to be managed.
     */
    void update(T message);
}
