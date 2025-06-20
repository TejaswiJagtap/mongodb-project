package com.artcode.thirtyfifty.utils;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.service.ServiceRegistry;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HibernateUtil {

	private static EntityManagerFactory factory;

	public static Session getSession() {
		EntityManager entityManager = null;
		Session session = null;
		try {
			if (factory == null) {
				factory = Persistence.createEntityManagerFactory("jpa-tutorial");
			}
			entityManager = factory.createEntityManager();
			session = entityManager.unwrap(org.hibernate.Session.class);
		} catch (Exception e) {
			log.error("Error while creating Hibernate session: {}", e.getMessage(), e);
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return session;
	}

	public static Session beginTransaction(String dbName) {
		Session hibernateSession = getCurrentSessionFromJPA(dbName);
		if (hibernateSession == null) {
			throw new IllegalStateException("Failed to obtain a Hibernate session for database: " + dbName);
		}
		hibernateSession.beginTransaction();
		return hibernateSession;
	}

	public static void commitTransaction(Session s) {
		s.getTransaction().commit();
	}

	public static void rollbackTransaction(Session s) {
		s.getTransaction().rollback();
	}

	public static void closeSession(Session s) {
		s.close();
	}

	public static Session getCurrentSession(String dbName) {
		Map<String, Object> settings = new HashMap<>();
		settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
		settings.put("dialect", "org.hibernate.dialect.MySQL5Dialect");
		settings.put("hibernate.connection.url", "jdbc:mysql://localhost/" + dbName);
		settings.put("hibernate.connection.username", "root");
		settings.put("hibernate.connection.password", "");
		settings.put("hibernate.current_session_context_class", "thread");
		settings.put("hibernate.show_sql", "true");
		settings.put("hibernate.format_sql", "true");
		settings.put("hibernate.hbm2ddl.auto", "update");

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();
		MetadataSources metadataSources = new MetadataSources(serviceRegistry);
		metadataSources.addPackage("com.artcode.school.tenant.entity");
		metadataSources.addPackage("com.artcode.school.entity");
//		metadataSources.addAnnotatedClass(Users.class);

		Metadata metadata = metadataSources.buildMetadata();
		SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	public static Session getCurrentSessionFromConfig() {
		Configuration config = new Configuration();
		config.configure();
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	public static Session getCurrentSessionFromJPA(String dbName) {
		Map<String, String> settings = new HashMap<>();
		settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
		settings.put("dialect", "org.hibernate.dialect.MySQL5Dialect");
		settings.put("hibernate.connection.url", "jdbc:mysql://localhost/" + dbName);
		settings.put("hibernate.connection.username", "root");
		settings.put("hibernate.connection.password", "");
		settings.put("hibernate.current_session_context_class", "thread");
		settings.put("hibernate.show_sql", "true");
		settings.put("hibernate.format_sql", "true");
		settings.put("hibernate.hbm2ddl.auto", "update");

		HibernatePersistenceProvider provider = new HibernatePersistenceProvider();
		EntityManagerFactory emf = null;
		EntityManager entityManager = null;
		Session session = null;
		try {
			emf = provider.createEntityManagerFactory(dbName + "-persistence-unit", settings);
			entityManager = emf.createEntityManager();
			session = entityManager.unwrap(org.hibernate.Session.class);
			return session; // Return the session for use
		} catch (Exception e) {
			log.error("Error obtaining Hibernate session for database '{}': {}", dbName, e.getMessage(), e);
		} finally {
			if (entityManager != null) {
				entityManager.close(); // Close if it was opened
			}
			if (emf != null) {
				emf.close(); // Close EntityManagerFactory if it's not needed anymore
			}
		}
		return null; // Return null if session couldn't be obtained
	}

}