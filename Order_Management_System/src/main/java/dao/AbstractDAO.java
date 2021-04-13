package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ProdusBLL;
import connection.ConnectionFactory;
import model.Comenzi;
/**
 * Date: April 30-2020
 * DAO: Data Acces Object
 * @author diana
 * @version 1.0
 */

public class AbstractDAO<T> {
    /**
     * Se creeaza legatura cu Clasa specificata prin tipul generic
     */
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Constructorul fara parametri
     */

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Meoda care formeaza ca String query-urile care vor fi aplicate bazei de date cu care se realizeaza conexiunea
     * @param field campul care se cauta in interogarea SELECT* FROM WHERE
     * @return interogarea ca String
     */

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Metoda care formeaza sub forma de String interogarea/query-ul SELECT * FROM ...
     * @return interogarea
     */
    private String createSelectQueryFull() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * Metoda care returneaza Clientul, Produsul sau Comanda in functie de tipul generic care are id-ul transims ca parametru
     * @param id
     * @return T (Client, Produs, Comenzi)
     */
    public T findById(int id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Metoda care returneaza Clientul, Produsul sau Comanda in functie de tipul generic care are id-ul transims ca parametru
     * @param name numele care se cauta
     * @return Clientul, Produsul sau comanda cautat dupa nume
     */

    public T findByNume(String name) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("nume");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Metoda care afiseaza intr-o lista toti Clientii, toate Produsele sau Comenzile in functie de parametrul generic care se afla la momentul respectiv in baza mea de date
     * @return o lista
     */
    public List<T> AfiseazaTot() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQueryFull();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " Afiseaza tot " + e.getMessage());

        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Metoda care imi formeaza resultatele sub forma de Obiecte in Java
     * @param resultSet
     * @return lista
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Metoda care imi formeaza sub forma de String interogarea corespunzatoare introducerii de noi randuri in baza noastar de date
     * @return returneaza sub forma de String INSERT INTO ...
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO " + type.getSimpleName() + " (");
        Field[] fields = type.getDeclaredFields();
        sb.append(fields[0].getName());
        for(int i = 1; i < fields.length; i++) {
            sb.append("," + fields[i].getName());
        }
        sb.append(") VALUES (");
        for(int i = 0; i < fields.length; i++) {
            if(i == fields.length - 1) {
                sb.append("?)");
            }
            else {
                sb.append("?,");
            }
        }
        return sb.toString();
    }

    /**
     * Metoda care insereaza un nou rand in baza noastra de date
     * Metoda care insereaza prin intermediul clase abstracte noi randuri
     * @param t
     * @return 0 daca inserarea a avut loc cu succes, in caz contrat -1
     */
    public int insert(T t) {
        // TODO:
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        String query = createInsertQuery();

        try {
            insertStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            /*
            ResultSet rs = statement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                key = rs.getLong(1);
            }

             */
            int i = 1;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(t);
                insertStatement.setString(i++, value.toString());
            }
            if(type.equals(Comenzi.class)) {
                float unitsInStock = new ProdusBLL().findById(((Comenzi)t).getId_produs()).getCantitate();
                float unitsRequired = ((Comenzi)t).getCantitate_comandata();
                if(unitsInStock >= unitsRequired) {
                    insertStatement.executeUpdate();
                    List<String> updateProductQuantity = new ArrayList<String>();
                    updateProductQuantity.add("cantitate");
                    updateProductQuantity.add(((Float)(unitsInStock - unitsRequired)).toString());
                    new ProdusBLL().update(updateProductQuantity, ((Comenzi)t).getId_produs());
                }
                else {
                    System.err.println("S-a cerut o cantitate prea mare");
                    return -1;
                }
            }
            else {
                insertStatement.executeUpdate();
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }

        return 0;
    }

    /**
     * Meoda care construieste in String query-ul pentru DELETE
     * @param field conditia care se aplica inregistrarilor din baza de date pentru care se doreste DELETE
     * @return String-ul in care se gaseste query-ul oentru delete
     */
    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " = ?");
        return sb.toString();
    }

    /**
     * Metoda care sterge o inregistrare din baza de date dupa id
     * @param id
     * @return returneaza id-ul inregistrarii sterse din baza de date in cazul in care stergerea a reusit
     */

    public int deleteById(int id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet= null;
        String query = createDeleteQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " deleteById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return id;
    }

    /**
     * Metoda care formeaza String-ul pentru query-ul de update
     * @param fieldsToUpdate
     * @param conditionField
     * @return returneaza sub forma de String query-ul corespunzator
     */
    private String createUpdateQuery(List<String> fieldsToUpdate, String conditionField) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for(int i = 0; i < fieldsToUpdate.size(); i++) {
            if((i % 2) == 0) {
                if(i == 0) {
                    sb.append(fieldsToUpdate.get(i) + " = ?");
                }
                else {
                    sb.append(", " + fieldsToUpdate.get(i) + " = ?");
                }
            }
        }
        sb.append(" WHERE " + conditionField + " = ?");
        return sb.toString();
    }

    /**
     * Metoda care realizeaza operatia de Update in baza de date in cazul in care id-ul unei inregistrari este cel dat ca parametru
     * @param fieldsToUpdate
     * @param id
     * @return returneaza 0 in cazul in care metoda s-a efectuat cu succes
     */
    public int updateById(List<String> fieldsToUpdate, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet= null;
        String query = createUpdateQuery(fieldsToUpdate, "id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int k = 1;
            for(int i = 0; i < fieldsToUpdate.size(); i++) {
                if((i % 2 ) == 1) {
                    statement.setString(k++, fieldsToUpdate.get(i));
                }
            }
            statement.setInt(k, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: updatedById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }
}
