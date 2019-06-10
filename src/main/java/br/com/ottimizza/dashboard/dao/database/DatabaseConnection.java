package br.com.ottimizza.dashboard.dao.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe para realizar a conexão com banco de dados.
 *
 * @author Lucas Martins (dev.lucasmartins@gmail.com)
 */
public class DatabaseConnection {

    private static EntityManagerFactory factory;

    public static EntityManagerFactory getFactory() {
        return factory;
    }

    public static EntityManager createEntityManager() {
        return factory.createEntityManager();
    }

    public static void createEntityManagerFactory(String persistenceUnit) {
        System.out.println("initializing database...");
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(persistenceUnit);
        }

        //DatabaseConnection connection = new DatabaseConnection();
        //connection.createChatsHistoryFullView();
        //connection.createChatsHistoryView();
        //connection.createChatsSummaryView();
    }

    public static void closeEntityManagerFactory() {
        if (factory != null) {
            factory.close();
        }
    }
    
}
