package dao;

import java.util.List;

public interface IMetier<T> {
    public T add(T o);
    public List<T> getAll();
    public T findByNom(String nom);
    public void delete(String nom);
    public void saveAll();
}
