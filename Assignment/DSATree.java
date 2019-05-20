/* *
 * Luke McDougall
 * 
 * Interface to be implemented by all tree ADT's. Makes using multiple different
 * types of tree easier.
 *
 * Last updated 16/05/2019
 * */
public interface DSATree<T>
{
    public abstract int size();

    public abstract int height();

    public abstract int idealHeight();

    public abstract T find(String key);

    public abstract void insert(String key, T value);

    public abstract void remove(String key);

    public abstract void clear();

    public abstract String toString();

    public abstract DSAQueue<T> values();
}
